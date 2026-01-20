package com.dev.CsiContratistas.infrastructure.dto.Obra;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeerObraDTO {

    private Integer id_obra;
    private Integer id_responsable;
    private Integer id_modelo_obra;
    private Integer id_terreno;
    private Integer id_tipo_construccion;
    private LocalDateTime fecha_inicio;
    private LocalDateTime fecha_finalizacion;
    private Boolean estado;
    private String nombre;

}