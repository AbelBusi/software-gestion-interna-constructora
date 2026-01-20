package com.dev.CsiContratistas.infrastructure.dto.RolUsuario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModificarRolUsuarioDTO {

    private Integer id_rol;
    private Integer id_usuario;
    private LocalDateTime fecha_asignacion;
    private Boolean estado;

}