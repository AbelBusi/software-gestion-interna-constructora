package com.dev.CsiContratistas.infrastructure.dto.Usuario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {

    private String correo;
    private String clave_acceso;

}
