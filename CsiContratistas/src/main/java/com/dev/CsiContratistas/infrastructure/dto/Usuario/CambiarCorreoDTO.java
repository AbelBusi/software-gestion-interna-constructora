package com.dev.CsiContratistas.infrastructure.dto.Usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CambiarCorreoDTO {


    @NotBlank(message = "El correo no puede estar vacío")
    @Email(message = "El correo debe tener un formato válido")
    private String correo;

    @NotBlank(message = "La clave no puede estar vacía")
    @Size(min = 6, message = "La clave debe tener al menos 6 caracteres")
    private String clave;

    @NotBlank(message = "La clave de acceso no puede estar vacía")
    @Size(min = 6, message = "La clave de acceso debe tener al menos 6 caracteres")
    private String claveConfirmacion;

}
