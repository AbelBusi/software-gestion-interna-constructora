package com.dev.CsiContratistas.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Obra implements Serializable {

    private Integer id_obra;
    private Empleado id_responsable;
    private Modelo_obra id_modelo_obra;
    private Terreno id_terreno;
    private Tipo_construccion id_tipo_construccion;
    private LocalDateTime fecha_inicio;
    private LocalDateTime fecha_finalizacion;
    private Boolean estado;
    private String nombre;
    public Integer getAvance() {
        if (fecha_inicio == null || fecha_finalizacion == null || estado == null || !estado) {
            return 0;
        }
        LocalDateTime ahora = LocalDateTime.now();
        if (ahora.isBefore(fecha_inicio)) {
            return 0;
        }
        if (ahora.isAfter(fecha_finalizacion) || ahora.isEqual(fecha_finalizacion)) {
            return 100;
        }
        long duracionTotalDias = ChronoUnit.DAYS.between(fecha_inicio, fecha_finalizacion);

        long diasTranscurridos = ChronoUnit.DAYS.between(fecha_inicio, ahora);

        if (duracionTotalDias <= 0) {
            return 0;
        }
        double avanceDecimal = (double) diasTranscurridos / duracionTotalDias;
        Integer avancePorcentaje = (int) Math.round(avanceDecimal * 100);

        return Math.min(avancePorcentaje, 100);
    }

}