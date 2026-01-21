package com.dev.CsiContratistas.infrastructure.dto.Usuario;
import lombok.Data;
@Data
public class UsuarioVistaDTO {
    private Integer idUsuario;
    private Integer idEmpleado;
    private String empleadoNombre;
    private String empleadoApellidos;
    private String dni;
    private String direccion;
    private String telefono;
    private String correo;
    private String fechaAsignacion;
    private String rol;
    private Boolean estado;
    
}
