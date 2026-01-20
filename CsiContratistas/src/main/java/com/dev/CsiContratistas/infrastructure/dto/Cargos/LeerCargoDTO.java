package com.dev.CsiContratistas.infrastructure.dto.Cargos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeerCargoDTO {

    private Integer id_cargo;
    private Integer id_empleado;
    private Integer id_rama;
    private Integer id_profesion;
    private LocalDateTime fecha_asignacion;
    private Boolean estado;

}