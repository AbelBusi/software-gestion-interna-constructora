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
public class Rol_Permiso implements Serializable {

    private Integer id_rol_Permiso;
    private Rol id_rol;
    private Permiso id_permiso;
    private LocalDateTime fecha_asignacion;
    private Boolean estado;

}