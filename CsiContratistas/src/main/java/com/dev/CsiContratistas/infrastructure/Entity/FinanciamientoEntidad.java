package com.dev.CsiContratistas.infrastructure.Entity;

import com.dev.CsiContratistas.domain.model.Cargo;
import com.dev.CsiContratistas.domain.model.Financiamiento;
import com.dev.CsiContratistas.domain.model.Financiamiento_material;
import com.dev.CsiContratistas.domain.model.Tipo_financiamiento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "FINANCIAMIENTO",schema ="\"gestionFinanciamiento\"")
public class FinanciamientoEntidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_financiamiento")
    private Integer id_financiamiento;

    @ManyToOne
    @JoinColumn(name = "id_financiamiento_obra")
    private ObraEntidad id_financiamiento_obra;

    @ManyToOne
    @JoinColumn(name = "id_tipo_financiamiento",nullable = false)
    private TipoFinanciamientoEntidad id_tipo_financiamiento;

    @ManyToOne
    @JoinColumn(name = "id_cliente",nullable = false)
    private ClienteEntidad id_cliente;

    @Column(name = "codigo_financiamiento", length = 10)
    private String codigo_financiamiento;

    @Column(name = "sub_total",precision = 10,scale = 2)
    private BigDecimal sub_total;

    @Column(name = "igv",precision = 10,scale = 2)
    private BigDecimal igv;

    @Column(name = "total",precision = 10,scale = 2)
    private BigDecimal total;

    @Column(name = "fecha_financiamiento")
    private LocalDateTime fecha_financiamiento;

    @Column(name = "forma_pago")
    private String forma_pago;

    @Column(name = "estado")
    private Boolean estado = true;

    @OneToMany(mappedBy = "id_financiamiento",cascade = CascadeType.ALL)
    private List<FinanciamientoMaterialEntidad> financiamientoMaterialEntidads = new ArrayList<>();



    public static FinanciamientoEntidad fromDomainModel(Financiamiento financiamiento) {
        return new FinanciamientoEntidad(
                financiamiento.getId_financiamiento(),
                financiamiento.getId_obra() != null ? ObraEntidad.fromDomainModel(financiamiento.getId_obra()) : null,
                financiamiento.getId_tipo_financiamiento() != null ? TipoFinanciamientoEntidad.fromDomainModel(financiamiento.getId_tipo_financiamiento()) : null,
                financiamiento.getId_cliente() != null ? ClienteEntidad.fromDomainModel(financiamiento.getId_cliente()) : null,
                financiamiento.getCodigo_financiamiento(),
                financiamiento.getSub_total(),
                financiamiento.getIgv(),
                financiamiento.getTotal(),
                financiamiento.getFecha_financiamiento(),
                financiamiento.getForma_pago(),
                financiamiento.getEstado(),
                null
        );
    }


    public Financiamiento toDomainModel() {
        return new Financiamiento(
                id_financiamiento,
                id_financiamiento_obra != null ? id_financiamiento_obra.toDomainModel() : null,
                id_tipo_financiamiento != null ? id_tipo_financiamiento.toDomainModel() : null,
                id_cliente != null ? id_cliente.toDomainModel() : null,
                codigo_financiamiento,
                sub_total,
                igv,
                total,
                fecha_financiamiento,
                forma_pago,
                estado
        );
    }


}