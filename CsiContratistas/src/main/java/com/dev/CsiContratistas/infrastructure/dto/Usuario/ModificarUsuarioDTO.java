package com.dev.CsiContratistas.infrastructure.dto.Usuario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModificarUsuarioDTO {

    private Integer id_empleado;
    private String correo;
    private String clave_acceso;
    private LocalDateTime fecha_asignacion;
    private Boolean estado;

}