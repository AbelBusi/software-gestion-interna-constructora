package com.dev.CsiContratistas.infrastructure.dto.Usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CrearUsuarioDTO {

    private Integer id_empleado;
    @Email(message = "El correo debe tener un formato valido")
    private String correo;
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[@#$%^&+=]).{6,}$" , message = "La clave de acceso debe tener al menos 6 caracteres, incluir una letra mayúscula, un número y un carácter especial.")
    private String clave_acceso;
    private LocalDateTime fecha_asignacion;
    private Boolean estado;

}
