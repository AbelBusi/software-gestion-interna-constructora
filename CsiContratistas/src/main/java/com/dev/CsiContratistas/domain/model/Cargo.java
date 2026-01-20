package com.dev.CsiContratistas.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cargo implements Serializable {

    private Integer id_cargo;
    private Empleado id_empleado;
    private Rama id_rama;
    private Profesiones id_profesion;
    private LocalDateTime fecha_asignacion;
    private Boolean estado;



}