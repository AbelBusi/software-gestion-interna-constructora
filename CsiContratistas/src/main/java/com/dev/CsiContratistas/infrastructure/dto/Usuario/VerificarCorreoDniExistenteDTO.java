package com.dev.CsiContratistas.infrastructure.dto.Usuario;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VerificarCorreoDniExistenteDTO {

    @NotBlank(message = "Debes ingresar el dni correctamente")
    private String dni;

    @NotBlank(message = "Debes ingresar el correo de forma correcta")
    private String correo;

}