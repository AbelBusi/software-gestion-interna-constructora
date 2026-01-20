package com.dev.CsiContratistas.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Permiso implements Serializable {

    private Integer id_permiso;
    private String nombre;
    private String descripcion;
    private LocalDateTime fecha_asignacion;
    private Boolean estado;

}
