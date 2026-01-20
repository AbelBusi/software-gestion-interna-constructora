package com.dev.CsiContratistas.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Rol_usuario {

    private Integer id_rol_usuario;
    private Rol id_rol;
    private Usuario id_usuario;
    private LocalDateTime fecha_asignacion;
    private Boolean estado;

}
