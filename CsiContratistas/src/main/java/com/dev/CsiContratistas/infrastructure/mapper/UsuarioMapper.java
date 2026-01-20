package com.dev.CsiContratistas.infrastructure.mapper;

import com.dev.CsiContratistas.domain.model.*;
import com.dev.CsiContratistas.infrastructure.dto.Empleado.VerificarDniExistenteDTO;
import com.dev.CsiContratistas.infrastructure.dto.Usuario.*;

public class UsuarioMapper {

    public static Usuario crearDtoDomain(CrearUsuarioDTO dto, Empleado empleado) {

        return new Usuario(
                null,
                empleado,
                dto.getCorreo(),
                dto.getClave_acceso(),
                dto.getFecha_asignacion(),
                dto.getEstado()
        );

    }

    public static LeerUsuarioDTO leerDTOUsuario(Usuario usuario) {
        return new LeerUsuarioDTO(
                usuario.getId_usuario(),
                usuario.getId_empleado().getId_empleado(),
                usuario.getId_empleado().getNombre(),
                usuario.getCorreo(),
                usuario.getClave_acceso(),
                usuario.getFecha_asignacion(),
                usuario.getEstado()
        );
    }

    public static VerificarCorreoDniExistenteDTO verificarCorreoDniEmpleadoDTO(String dni, String correo){

        return new VerificarCorreoDniExistenteDTO(
                dni,correo
        );

    }

    public static Usuario actualizarDtoDomain(ModificarUsuarioDTO dtoEmpleado, Empleado empleado) {

        return new Usuario(
                null,
                empleado,
                dtoEmpleado.getCorreo(),
                dtoEmpleado.getClave_acceso(),
                dtoEmpleado.getFecha_asignacion(),
                dtoEmpleado.getEstado()
        );

    }

    public static EliminarUsuarioDTO eliminarUsuarioDTODomain(Usuario dto) {
        return new EliminarUsuarioDTO(
                dto.getId_usuario()
        );
    }


    public static Usuario cambiarCorreoDtoADomain(CambiarCorreoDTO dto, Usuario usuarioExistente) {
        return new Usuario(
                usuarioExistente.getId_usuario(),
                usuarioExistente.getId_empleado(),
                dto.getCorreo(),
                dto.getClave(), // Se asume que claveAcceso reemplaza la actual
                usuarioExistente.getFecha_asignacion(),
                usuarioExistente.getEstado()
        );
    }


}