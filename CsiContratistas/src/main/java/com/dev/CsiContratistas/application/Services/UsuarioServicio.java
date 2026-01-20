package com.dev.CsiContratistas.application.Services;

import com.dev.CsiContratistas.domain.model.Rol_usuario;
import com.dev.CsiContratistas.domain.model.Usuario;
import com.dev.CsiContratistas.domain.ports.in.*;
import com.dev.CsiContratistas.infrastructure.Entity.UsuarioEntidad;
import com.dev.CsiContratistas.infrastructure.Repository.on.JpaUsuarioRepositorioAdapter;
import lombok.RequiredArgsConstructor;
import com.dev.CsiContratistas.infrastructure.dto.Usuario.UsuarioVistaDTO;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@RequiredArgsConstructor
public class UsuarioServicio implements ICrearCasoUso<Usuario>, ILeerCasoUso<Usuario>, IModificarCasoUso<Usuario>, IEliminarCasoUso<Integer>, IVerificarObjetoCasoUso<String>,IcambiarClaveAccesoCasoUso {
    private final ILeerCasoUso<Usuario> leerUsuario;

    private final ICrearCasoUso<Usuario> crearUsuario;

    private final IModificarCasoUso<Usuario> modificarUsuario;

    private final IEliminarCasoUso<Integer> eliminarUsuarioPorId;

    private final IVerificarObjetoCasoUso<String> verificarUsuarioPorCorreo;

    private final IcambiarClaveAccesoCasoUso claveAccesoCambiado;

    private static final Logger logger = LoggerFactory.getLogger(UsuarioServicio.class);

    private final JpaUsuarioRepositorioAdapter jpaUsuarioRepositorioAdapter;
    private final UsuarioHistorialServicio usuarioHistorialServicio;

    public boolean existCorreo(String correo) {
        return jpaUsuarioRepositorioAdapter.existEmail(correo);
    }
    public Usuario buscarPorCorreo(String correo) {
        return jpaUsuarioRepositorioAdapter.buscarPorCorreo(correo).orElse(null);
    }
    public boolean existByEmpleado(Integer idEmpleado) {
        return jpaUsuarioRepositorioAdapter.existByIdEmpleado(idEmpleado);
    }
    public boolean existByEmpleadoExcludingUsuario(Integer idEmpleado, Integer idUsuario) {
        return jpaUsuarioRepositorioAdapter.existByIdEmpleadoExcludingUsuario(idEmpleado, idUsuario);
    }
    private String getUsuarioResponsable() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
            return auth.getName();
        }
        return "sistema";
    }
    private Integer getAuthenticatedUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return null;
        }
        Object principal = auth.getPrincipal();

        if (principal instanceof UsuarioEntidad usuarioEntidad) {
            return usuarioEntidad.getId_usuario();
        }
        return null;
    }

    @Override
    public Usuario crearObjeto(Usuario usuario) {
        if(existCorreo(usuario.getCorreo())) {
            throw new IllegalArgumentException("El correo ya existe");
        }
        if(existByEmpleado(usuario.getId_empleado().getId_empleado())){
            throw new IllegalArgumentException("El empleado ya tiene un usuario asignado");
        }
        Usuario creado = crearUsuario.crearObjeto(usuario);
        usuarioHistorialServicio.registrarHistorial(
            creado.getId_usuario(),
            "CREADO",
            "Usuario creado con correo: " + creado.getCorreo(),
            getUsuarioResponsable()
        );
        return creado;
    }

    @Override
    public Integer eliminarObjeto(Integer idUsuario) {
        Integer idUsuarioAutenticado = getAuthenticatedUserId();

        if (idUsuarioAutenticado != null && idUsuarioAutenticado.equals(idUsuario)) {
            throw new IllegalStateException("No puedes eliminar tu propia cuenta mientras estás autenticado.");
        }
        Integer resultado = eliminarUsuarioPorId.eliminarObjeto(idUsuario);
        if (resultado == 1) {
            usuarioHistorialServicio.registrarHistorial(
                idUsuario,
                "ELIMINADO",
                "Usuario eliminado (borrado lógico)",
                getUsuarioResponsable()
            );
        }
        return resultado;
    }

    @Override
    public Optional<Usuario> leerObjeto(Integer id) {
        return leerUsuario.leerObjeto(id);
    }

    @Override
    public List<Usuario> leerObjetos() {
        return leerUsuario.leerObjetos();
    }

    @Override
    public Optional<Usuario> modificarObjeto(Integer id,Usuario usuario) {
        Optional<Usuario> usuarioExistente = leerObjeto(id);
        if(usuarioExistente.isEmpty()) {
            return Optional.empty();
        }

        if(!usuario.getId_empleado().getId_empleado().equals(usuarioExistente.get().getId_empleado().getId_empleado())) {
            if(existByEmpleadoExcludingUsuario(usuario.getId_empleado().getId_empleado(), id)) {
                return Optional.empty();
            }
        }

        if(!usuario.getCorreo().equals(usuarioExistente.get().getCorreo())) {
            if(existCorreo(usuario.getCorreo())) {
                return Optional.empty();
            }
        }

        usuario.setId_usuario(id);
        Optional<Usuario> modificado = modificarUsuario.modificarObjeto(id, usuario);
        if (modificado.isPresent()) {
            usuarioHistorialServicio.registrarHistorial(
                id,
                "EDITADO",
                "Usuario editado. Nuevo correo: " + usuario.getCorreo(),
                getUsuarioResponsable()
            );
        }
        return modificado;
    }

    @Override
    public boolean obtenerPorParametro(String parametro) {
        return verificarUsuarioPorCorreo.obtenerPorParametro(parametro);
    }

    @Override
    public boolean actualizarPassword(String correo, String nuevaPassword) {
        return claveAccesoCambiado.actualizarPassword(correo,nuevaPassword);
    }

    public List<UsuarioVistaDTO> leerUsuariosDTO(Map<Integer, String> rolesPorUsuario) {
        return jpaUsuarioRepositorioAdapter.leerUsuariosConEmpleadoYRoles().stream().map(u -> {
            UsuarioVistaDTO dto = new UsuarioVistaDTO();
            dto.setIdUsuario(u.getId_usuario());
            if (u.getId_empleado() != null) {
                dto.setIdEmpleado(u.getId_empleado().getId_empleado());
                dto.setEmpleadoNombre(u.getId_empleado().getNombre());
                dto.setEmpleadoApellidos(u.getId_empleado().getApellidos());
                dto.setDni(u.getId_empleado().getDni());
                dto.setDireccion(u.getId_empleado().getDireccion());
                dto.setTelefono(u.getId_empleado().getTelefono());

            }
            dto.setCorreo(u.getCorreo());
            dto.setFechaAsignacion(u.getFecha_asignacion() != null ? u.getFecha_asignacion().toLocalDate().toString() : "");
            dto.setRol(rolesPorUsuario.getOrDefault(u.getId_usuario(), "Sin rol"));
            dto.setEstado(u.getEstado());
            return dto;
        }).toList();
    }
    public long contarUsuariosActivos() {
        return jpaUsuarioRepositorioAdapter.countUsuariosActivos();
    }
}