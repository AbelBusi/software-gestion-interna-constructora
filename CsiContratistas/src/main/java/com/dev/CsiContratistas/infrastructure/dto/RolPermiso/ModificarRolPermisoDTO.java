package com.dev.CsiContratistas.infrastructure.dto.RolPermiso;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModificarRolPermisoDTO {

    private Integer id_rol;
    private Integer id_permiso;
    private LocalDateTime fecha_asignacion;
    private Boolean estado;

}