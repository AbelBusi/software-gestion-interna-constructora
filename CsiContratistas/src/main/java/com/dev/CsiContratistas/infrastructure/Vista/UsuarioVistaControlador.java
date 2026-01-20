package com.dev.CsiContratistas.infrastructure.Vista;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

import com.dev.CsiContratistas.infrastructure.reportes.UsuarioReportePdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dev.CsiContratistas.application.Services.EmpleadoServicio;
import com.dev.CsiContratistas.application.Services.RolServicio;
import com.dev.CsiContratistas.application.Services.RolUsuarioServicio;
import com.dev.CsiContratistas.application.Services.UsuarioHistorialServicio;
import com.dev.CsiContratistas.application.Services.UsuarioServicio;
import com.dev.CsiContratistas.domain.model.Empleado;
import com.dev.CsiContratistas.domain.model.Rol;
import com.dev.CsiContratistas.domain.model.Rol_usuario;
import com.dev.CsiContratistas.domain.model.Usuario;
import com.dev.CsiContratistas.infrastructure.dto.Usuario.UsuarioVistaDTO;
import com.dev.CsiContratistas.infrastructure.Entity.UsuarioHistorialEntidad;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("administrador/usuarios")
@Slf4j
public class UsuarioVistaControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;
    @Autowired
    private EmpleadoServicio empleadoServicio;

    @Autowired
    private RolServicio rolServicio;

    @Autowired
    private RolUsuarioServicio rolUsuarioServicio;

    @Qualifier("taskExecutor")
    @Autowired
    private Executor taskExecutor;

    @Autowired
    private UsuarioHistorialServicio usuarioHistorialServicio;
    @Autowired
    private UsuarioReportePdfService usuarioReportePdfService;


    @GetMapping
    public String listarUsuarios(Model model) {
        log.info("Cargando lista de usuarios...");
        long startTime = System.currentTimeMillis();

        CompletableFuture<List<Usuario>> usuariosFuture = CompletableFuture.supplyAsync(() -> {
            try {
                return usuarioServicio.leerObjetos();
            } catch (Exception e) {
                log.error("Error cargando usuarios", e);
                return List.of();
            }
        }, taskExecutor);

        CompletableFuture<List<Rol_usuario>> rolesUsuariosFuture = CompletableFuture.supplyAsync(() -> {
            try {
                return rolUsuarioServicio.leerObjetos();
            } catch (Exception e) {
                log.error("Error cargando roles de usuarios", e);
                return List.of();
            }
        }, taskExecutor);

        CompletableFuture<List<Empleado>> empleadosFuture = CompletableFuture.supplyAsync(() -> {
            try {
                return empleadoServicio.leerObjetos();
            } catch (Exception e) {
                log.error("Error cargando empleados", e);
                return List.of();
            }
        }, taskExecutor);

        CompletableFuture<List<Rol>> rolesFuture = CompletableFuture.supplyAsync(() -> {
            try {
                return rolServicio.leerObjetos();
            } catch (Exception e) {
                log.error("Error cargando roles", e);
                return List.of();
            }
        }, taskExecutor);

        List<Usuario> usuarios = usuariosFuture.join();
        List<Rol_usuario> rolesUsuarios = rolesUsuariosFuture.join();
        List<Empleado> empleados = empleadosFuture.join();
        List<Rol> roles = rolesFuture.join();

        List<Integer> empleadosConUsuario = usuarios.parallelStream()
                .filter(u -> u.getId_empleado() != null)
                .map(u -> u.getId_empleado().getId_empleado())
                .collect(Collectors.toList());

        List<Empleado> empleadosDisponibles = empleados.parallelStream()
                .filter(e -> !empleadosConUsuario.contains(e.getId_empleado()))
                .collect(Collectors.toList());

        Map<Integer, String> rolesPorUsuario = rolesUsuarios.parallelStream()
                .collect(Collectors.toMap(
                        ru -> ru.getId_usuario().getId_usuario(),
                        ru -> ru.getId_rol().getNombre()));

        Map<Integer, Integer> idRolPorUsuario = rolesUsuarios.parallelStream()
                .collect(Collectors.toMap(
                        ru -> ru.getId_usuario().getId_usuario(),
                        ru -> ru.getId_rol().getId_rol()));

        List<UsuarioVistaDTO> usuariosDTO = usuarioServicio.leerUsuariosDTO(rolesPorUsuario).stream()
                .filter(dto -> !"Sin rol".equals(dto.getRol()))
                .collect(Collectors.toList());

        model.addAttribute("idRolPorUsuario", idRolPorUsuario);
        model.addAttribute("usuarios", usuariosDTO);
        model.addAttribute("rolesPorUsuario", rolesPorUsuario);
        model.addAttribute("empleados", empleados);
        model.addAttribute("empleadosDisponibles", empleadosDisponibles);
        model.addAttribute("roles", roles);

        long endTime = System.currentTimeMillis();
        log.info("Lista de usuarios cargada en {} ms", endTime - startTime);

        return "usuarios/usuarios";
    }

    @PostMapping("/guardar")
    public String guardarUsuario(
            @RequestParam("id_empleado") Integer idEmpleado,
            @RequestParam("correo") String correo,
            @RequestParam("clave_acceso") String claveAcceso,
            @RequestParam("id_rol") Integer idRol,
            @RequestParam("estado") boolean estado,
            RedirectAttributes redirectAttributes) {

        try {

            if (!isValidPassword(claveAcceso)) {
                throw new IllegalArgumentException(
                        "La contraseña debe tener al menos 6 caracteres, una mayúscula, una minúscula, un número y un carácter especial");
            }

            if (usuarioServicio.existCorreo(correo)) {
                throw new IllegalArgumentException("El correo electrónico ya está registrado");
            }

            Empleado empleado = empleadoServicio.leerObjeto(idEmpleado)
                    .orElseThrow(() -> new IllegalArgumentException("Empleado no encontrado"));

            Rol rol = rolServicio.leerObjeto(idRol)
                    .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado"));

            Usuario usuario = new Usuario();
            usuario.setId_empleado(empleado);
            usuario.setCorreo(correo);
            usuario.setClave_acceso(claveAcceso);
            usuario.setEstado(true);

            Usuario usuarioGuardado = usuarioServicio.crearObjeto(usuario);

            Rol_usuario rolUsuario = new Rol_usuario();
            rolUsuario.setId_usuario(usuarioGuardado);
            rolUsuario.setId_rol(rol);
            rolUsuarioServicio.crearObjeto(rolUsuario);

            redirectAttributes.addFlashAttribute("success", "Usuario creado exitosamente");

        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al crear el usuario: " + e.getMessage());
        }

        return "redirect:/administrador/usuarios";
    }

    @PostMapping("/editar/{id}")
    public String editarUsuario(
            @PathVariable Integer id,
            @RequestParam("id_empleado") Integer idEmpleado,
            @RequestParam("correo") String correo,
            @RequestParam("id_rol") Integer idRol,
            @RequestParam("estado") boolean estado,
            RedirectAttributes redirectAttributes) {
        try {
            Usuario usuario = usuarioServicio.leerObjeto(id)
                    .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

            if (!usuario.getId_empleado().getId_empleado().equals(idEmpleado)) {
                if (usuarioServicio.existByEmpleadoExcludingUsuario(idEmpleado, id)) {
                    throw new IllegalArgumentException("El empleado seleccionado ya tiene un usuario asignado");
                }

                Empleado nuevoEmpleado = empleadoServicio.leerObjeto(idEmpleado)
                        .orElseThrow(() -> new IllegalArgumentException("Empleado no encontrado"));
                usuario.setId_empleado(nuevoEmpleado);
            }

            if (!usuario.getCorreo().equals(correo)) {
                if (usuarioServicio.existCorreo(correo)) {
                    throw new IllegalArgumentException("El correo electrónico ya está registrado");
                }
            }

            usuario.setCorreo(correo);
            usuario.setEstado(estado);
            usuarioServicio.modificarObjeto(id, usuario);

            rolUsuarioServicio.asignarRolAUsuario(id, idRol, usuario);

            redirectAttributes.addFlashAttribute("success", "Usuario actualizado exitosamente");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al actualizar el usuario: " + e.getMessage());
        }

        return "redirect:/administrador/usuarios";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            usuarioServicio.eliminarObjeto(id);
            rolUsuarioServicio.eliminarPorUsuario(id);
            redirectAttributes.addFlashAttribute("success", "Usuario eliminado exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar el usuario: " + e.getMessage());
        }
        return "redirect:/administrador/usuarios";
    }

    private boolean isValidPassword(String password) {
        if (password == null || password.length() < 6) {
            return false;
        }
        boolean hasUppercase = password.matches(".*[A-Z].*");
        boolean hasLowercase = password.matches(".*[a-z].*");
        boolean hasNumber = password.matches(".*\\d.*");
        boolean hasSpecial = password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*");

        return hasUppercase && hasLowercase && hasNumber && hasSpecial;
    }

    @GetMapping("/historial")
    public String verHistorialCompleto(Model model) {
        model.addAttribute(
                "historial",
                usuarioHistorialServicio.obtenerHistorialCompletoVista(usuarioServicio, rolUsuarioServicio)
        );
        return "usuarios/historialUsuarios";
    }

    @GetMapping("/historial/{idUsuario}")
    @ResponseBody
    public List<UsuarioHistorialEntidad> verHistorialUsuario(@PathVariable Integer idUsuario) {
        return usuarioHistorialServicio.obtenerHistorialPorUsuario(idUsuario);
    }
    @GetMapping("/reporte/pdf")
    public ResponseEntity<byte[]> descargarReporteUsuarios() {
        try {
            Map<Integer, String> rolesMap = rolUsuarioServicio.leerObjetos().stream()
                    .collect(Collectors.toMap(
                            ru -> ru.getId_usuario().getId_usuario(),
                            ru -> ru.getId_rol().getNombre()));
            List<UsuarioVistaDTO> usuarios = usuarioServicio.leerUsuariosDTO(rolesMap);

            byte[] pdf = usuarioReportePdfService.generarReporte(usuarios);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=usuarios_reporte.pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdf);

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(("Error al generar el reporte PDF: " + e.getMessage()).getBytes());
        }
    }

}