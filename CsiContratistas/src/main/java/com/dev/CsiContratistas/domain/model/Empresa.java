package com.dev.CsiContratistas.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Empresa {
    private Integer id_empresa;
    private String razon_social;
    private String ruc;
    private String representante_legal;
    private String nombre_comercial;
}