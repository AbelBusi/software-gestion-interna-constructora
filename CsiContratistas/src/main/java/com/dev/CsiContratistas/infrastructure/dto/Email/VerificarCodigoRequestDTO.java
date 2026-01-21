package com.dev.CsiContratistas.infrastructure.dto.Email;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class VerificarCodigoRequestDTO {
    private String email;
    private String codigo;
}