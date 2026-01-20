package com.dev.CsiContratistas.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Tipo_construccion implements Serializable {

    private Integer id_tipo_construccion;
    private String nombre;
    private String descripcion;
    private Boolean estado;

}