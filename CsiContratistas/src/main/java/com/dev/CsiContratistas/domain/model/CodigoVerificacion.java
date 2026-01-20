package com.dev.CsiContratistas.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.Instant;

@AllArgsConstructor
@Getter
@Setter
public class CodigoVerificacion {
    private String email;
    private String codigo;
    private Instant generadoEn;
    private Duration ttl;
}
