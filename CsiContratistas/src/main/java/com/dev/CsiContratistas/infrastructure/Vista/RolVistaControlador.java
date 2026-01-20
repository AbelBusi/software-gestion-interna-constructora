package com.dev.CsiContratistas.infrastructure.Vista;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dev.CsiContratistas.application.Services.PermisoServicio;
import com.dev.CsiContratistas.application.Services.RolPermisoServicio;
import com.dev.CsiContratistas.application.Services.RolServicio;
import com.dev.CsiContratistas.application.Services.RolUsuarioServicio;
import com.dev.CsiContratistas.domain.model.Permiso;
import com.dev.CsiContratistas.domain.model.Rol;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("administrador/roles")
public class RolVistaControlador {

    private final RolServicio rolServicio;
    private final RolUsuarioServicio rolUsuarioServicio;
    private final PermisoServicio permisoServicio;
    private final RolPermisoServicio rolPermisoServicio;
    private final ObjectMapper objectMapper;
    
    @Qualifier("taskExecutor")
    private final Executor taskExecutor;

    @GetMapping
    public String listarRoles(Model model,
            @ModelAttribute("success") String success,
            @ModelAttribute("error") String error) throws Exception {
        
        log.info("Cargando lista de roles...");
        long startTime = System.currentTimeMillis();

        CompletableFuture<List<Rol>> rolesFuture = CompletableFuture.supplyAsync(() -> {
            try {
                return rolServicio.leerObjetos();
            } catch (Exception e) {
                log.error("Error cargando roles", e);
                return List.of();
            }
        }, taskExecutor);
        
        CompletableFuture<List<Permiso>> permisosFuture = CompletableFuture.supplyAsync(() -> {
            try {
                return permisoServicio.leerObjetos();
            } catch (Exception e) {
                log.error("Error cargando permisos", e);
                return List.of();
            }
        }, taskExecutor);

        List<Rol> roles = rolesFuture.get();
        List<Permiso> permisos = permisosFuture.get();

        Map<Integer, Integer> usuariosPorRol = new HashMap<>();
        Map<Integer, List<Integer>> permisosPorRol = new HashMap<>();
        
        roles.parallelStream().forEach(rol -> {
            try {
                int cantidad = rolUsuarioServicio.contarUsuariosPorRol(rol.getId_rol());
                usuariosPorRol.put(rol.getId_rol(), cantidad);
                
                List<Integer> permisosAsignados = rolPermisoServicio.obtenerIdsPermisosPorRol(rol.getId_rol());
                permisosPorRol.put(rol.getId_rol(), permisosAsignados);
            } catch (Exception e) {
                log.error("Error procesando rol {}", rol.getId_rol(), e);
            }
        });
        
        String permisosPorRolJson = objectMapper.writeValueAsString(permisosPorRol);
        
        model.addAttribute("permisos", permisos);
        model.addAttribute("permisosPorRolJson", permisosPorRolJson);
        model.addAttribute("roles", roles);
        model.addAttribute("usuariosPorRol", usuariosPorRol);
        model.addAttribute("success", success);
        model.addAttribute("error", error);
        
        long endTime = System.currentTimeMillis();
        log.info("Lista de roles cargada en {} ms", endTime - startTime);
        
        return "usuarios/roles";
    }

    @GetMapping("/tabla")
    public String obtenerTablaRoles(Model model) throws Exception {
        log.info("Cargando tabla de roles (AJAX)...");
        long startTime = System.currentTimeMillis();

        CompletableFuture<List<Rol>> rolesFuture = CompletableFuture.supplyAsync(() -> {
            try {
                return rolServicio.leerObjetos();
            } catch (Exception e) {
                log.error("Error cargando roles", e);
                return List.of();
            }
        }, taskExecutor);
        
        CompletableFuture<List<Permiso>> permisosFuture = CompletableFuture.supplyAsync(() -> {
            try {
                return permisoServicio.leerObjetos();
            } catch (Exception e) {
                log.error("Error cargando permisos", e);
                return List.of();
            }
        }, taskExecutor);

        List<Rol> roles = rolesFuture.get();
        List<Permiso> permisos = permisosFuture.get();

        Map<Integer, Integer> usuariosPorRol = new HashMap<>();
        Map<Integer, List<Integer>> permisosPorRol = new HashMap<>();
        
        roles.parallelStream().forEach(rol -> {
            try {
                int cantidad = rolUsuarioServicio.contarUsuariosPorRol(rol.getId_rol());
                usuariosPorRol.put(rol.getId_rol(), cantidad);
                
                List<Integer> permisosAsignados = rolPermisoServicio.obtenerIdsPermisosPorRol(rol.getId_rol());
                permisosPorRol.put(rol.getId_rol(), permisosAsignados);
            } catch (Exception e) {
                log.error("Error procesando rol {}", rol.getId_rol(), e);
            }
        });
        
        model.addAttribute("roles", roles);
        model.addAttribute("usuariosPorRol", usuariosPorRol);
        model.addAttribute("permisosPorRol", permisosPorRol);
        
        long endTime = System.currentTimeMillis();
        log.info("Tabla de roles cargada en {} ms", endTime - startTime);
        
        return "usuarios/fragmentos/tablaRoles";
    }

    @PostMapping("/guardar")
    public String guardarRol(@ModelAttribute Rol rol, RedirectAttributes redirectAttributes) {
        try {
            if (rol.getFecha_asignacion() == null) {
                rol.setFecha_asignacion(java.time.LocalDateTime.now());
            }
            rolServicio.crearObjeto(rol);
            redirectAttributes.addFlashAttribute("success", "Rol creado correctamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/administrador/roles";
    }

    @PostMapping("/editar")
    public String editarRol(@RequestParam("id_rol") Integer idRol,
            @RequestParam("nombre") String nombre,
            @RequestParam("descripcion") String descripcion,
            RedirectAttributes redirectAttributes) {
        try {
            Rol rolOriginal = rolServicio.leerObjeto(idRol)
                    .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado"));

            Rol rol = new Rol();
            rol.setId_rol(idRol);
            rol.setNombre(nombre);
            rol.setDescripcion(descripcion);
            rol.setFecha_asignacion(rolOriginal.getFecha_asignacion());
            rol.setEstado(rolOriginal.getEstado());

            rolServicio.modificarObjeto(idRol, rol);
            redirectAttributes.addFlashAttribute("success", "Rol actualizado correctamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/administrador/roles";
    }

    @PostMapping("/eliminar")
    public String eliminarRol(@RequestParam("id_rol") Integer idRol, RedirectAttributes redirectAttributes) {
        try {
            int cantidadUsuarios = rolUsuarioServicio.contarUsuariosPorRol(idRol);
            if (cantidadUsuarios > 0) {
                redirectAttributes.addFlashAttribute("error", "No se puede eliminar el rol porque hay " + cantidadUsuarios + " usuarios asignados a él");
                return "redirect:/administrador/roles";
            }
            rolServicio.eliminarObjeto(idRol);
            redirectAttributes.addFlashAttribute("success", "Rol eliminado correctamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar el rol: " + e.getMessage());
        }
        return "redirect:/administrador/roles";
    }

    @GetMapping("/permisos/{idRol}")
    public String gestionarPermisos(@PathVariable Integer idRol, Model model) {
        Rol rol = rolServicio.leerObjeto(idRol).orElseThrow(() -> new IllegalArgumentException("Rol no encontrado"));
        List<Permiso> permisos = permisoServicio.leerObjetos();
        List<Integer> permisosAsignados = rolPermisoServicio.obtenerIdsPermisosPorRol(idRol);

        model.addAttribute("rol", rol);
        model.addAttribute("permisos", permisos);
        model.addAttribute("permisosAsignados", permisosAsignados);
        return "administrador/usuarios/fragmentos/modal_permisos_rol";
    }

    @PostMapping("/asignar-permisos")
    public String asignarPermisos(@RequestParam("id_rol") Integer idRol,
            @RequestParam(value = "permisos", required = false) List<Integer> permisos,
            RedirectAttributes redirectAttributes) {
        try {
            if (permisos == null) {
                permisos = List.of();
            }
            rolPermisoServicio.actualizarPermisosRol(idRol, permisos);
            redirectAttributes.addFlashAttribute("success", "Permisos actualizados correctamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al actualizar permisos: " + e.getMessage());
        }
        return "redirect:/administrador/roles";
    }

    @PostMapping("/guardar-ajax")
    @ResponseBody
    public ResponseEntity<?> guardarRolAjax(@ModelAttribute Rol rol) {
        try {
            if (rol.getFecha_asignacion() == null) {
                rol.setFecha_asignacion(java.time.LocalDateTime.now());
            }
            rolServicio.crearObjeto(rol);
            return ResponseEntity.ok(Map.of("message", "Rol creado correctamente"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @PostMapping("/editar-ajax")
    @ResponseBody
    public ResponseEntity<?> editarRolAjax(@RequestParam("id_rol") Integer idRol,
            @RequestParam("nombre") String nombre,
            @RequestParam("descripcion") String descripcion) {
        try {
            Rol rolOriginal = rolServicio.leerObjeto(idRol)
                    .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado"));

            Rol rol = new Rol();
            rol.setId_rol(idRol);
            rol.setNombre(nombre);
            rol.setDescripcion(descripcion);
            rol.setFecha_asignacion(rolOriginal.getFecha_asignacion());
            rol.setEstado(rolOriginal.getEstado());

            rolServicio.modificarObjeto(idRol, rol);
            return ResponseEntity.ok(Map.of("message", "Rol actualizado correctamente"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @PostMapping("/eliminar-ajax")
    @ResponseBody
    public ResponseEntity<?> eliminarRolAjax(@RequestParam("id_rol") Integer idRol) {
        try {
            int cantidadUsuarios = rolUsuarioServicio.contarUsuariosPorRol(idRol);
            if (cantidadUsuarios > 0) {
                return ResponseEntity.badRequest().body(Map.of("message", 
                    "No se puede eliminar el rol porque hay " + cantidadUsuarios + " usuarios asignados a él"));
            }
            rolServicio.eliminarObjeto(idRol);
            return ResponseEntity.ok(Map.of("message", "Rol eliminado correctamente"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", "Error al eliminar el rol: " + e.getMessage()));
        }
    }

    @PostMapping("/asignar-permisos-ajax")
    @ResponseBody
    public ResponseEntity<?> asignarPermisosAjax(@RequestParam("id_rol") Integer idRol,
            @RequestParam(value = "permisos", required = false) List<Integer> permisos) {
        try {
            if (permisos == null) {
                permisos = List.of();
            }
            rolPermisoServicio.actualizarPermisosRol(idRol, permisos);
            return ResponseEntity.ok(Map.of("message", "Permisos actualizados correctamente"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", "Error al actualizar permisos: " + e.getMessage()));
        }
    }
}
