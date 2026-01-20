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
public class Usuario implements Serializable {

    private Integer id_usuario;
    private Empleado id_empleado;
    private String correo;
    private String clave_acceso;
    private LocalDateTime fecha_asignacion;
    private Boolean estado;


}
