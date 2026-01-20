package com.dev.CsiContratistas.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tipo_financiamiento implements Serializable {

    private Integer id_tipo_financiamiento;
    private String nombre;
    private String descripcion;
    private Boolean estado;

}
