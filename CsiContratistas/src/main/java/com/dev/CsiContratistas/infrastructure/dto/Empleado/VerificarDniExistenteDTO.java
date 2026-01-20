package com.dev.CsiContratistas.infrastructure.dto.Empleado;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VerificarDniExistenteDTO {

    @NotBlank(message = "Debes ingresar el dni de forma correcta")
    private String dni;

}