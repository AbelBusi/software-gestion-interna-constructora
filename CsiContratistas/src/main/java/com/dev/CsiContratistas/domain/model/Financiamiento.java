package com.dev.CsiContratistas.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Financiamiento implements Serializable {

    private Integer id_financiamiento;
    private Obra id_obra;
    private Tipo_financiamiento id_tipo_financiamiento;
    private Cliente id_cliente;
    private String codigo_financiamiento;
    private BigDecimal sub_total;
    private BigDecimal igv;
    private BigDecimal total;
    private LocalDateTime fecha_financiamiento;
    private String forma_pago;
    private Boolean estado;

}
