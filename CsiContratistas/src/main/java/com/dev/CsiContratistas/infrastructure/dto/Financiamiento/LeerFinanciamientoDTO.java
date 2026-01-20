package com.dev.CsiContratistas.infrastructure.dto.Financiamiento;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeerFinanciamientoDTO {

    private Integer id_financiamiento;
    private Integer id_obra;
    private Integer id_tipo_financiamiento;
    private Integer id_cliente;
    private String codigo_financiamiento;
    private BigDecimal sub_total;
    private BigDecimal igv;
    private BigDecimal total;
    private LocalDateTime fecha_financiamiento;
    private String forma_pago;
    private Boolean estado;

}