package com.dev.CsiContratistas.infrastructure.dto.Cargos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CrearCargoDTO {

    private Integer id_empleado;
    private Integer id_rama;
    private Integer id_profesion;
    private LocalDateTime fecha_asignacion;
    private Boolean estado;

}
