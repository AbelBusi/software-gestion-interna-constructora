package com.dev.CsiContratistas.infrastructure.Entity;

import com.dev.CsiContratistas.domain.model.Financiamiento_material;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "FINANCIAMIENTO_MATERIAL",schema = "\"gestionFinanciamiento\"")
public class FinanciamientoMaterialEntidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_financiamiento_material")
    private Integer id_financiamiento_material;

    @ManyToOne
    @JoinColumn(name = "id_financiamiento",nullable = false)
    private FinanciamientoEntidad id_financiamiento;

    @ManyToOne
    @JoinColumn(name = "id_material",nullable = false)
    private MaterialEntidad id_material;

    @Column(name = "cantidad")
    private Integer cantidad;

    @Column(name = "precio_total",precision = 10,scale = 2)
    private BigDecimal precio_total;


    public static FinanciamientoMaterialEntidad fromDomainModel(Financiamiento_material financiamientoMaterial){
        if (financiamientoMaterial==null){
            return null;
        }

        return new FinanciamientoMaterialEntidad(
                financiamientoMaterial.getId_financiamiento_material(),
                FinanciamientoEntidad.fromDomainModel(financiamientoMaterial.getId_financiamiento()),
                MaterialEntidad.fromDomainModel(financiamientoMaterial.getId_material()),
                financiamientoMaterial.getCantidad(),
                financiamientoMaterial.getPrecio_total()
        );
    }

    public Financiamiento_material toDomainModel(){


        return new Financiamiento_material(id_financiamiento_material,
                id_financiamiento.toDomainModel() != null ? id_financiamiento.toDomainModel() : null,
                id_material.toDomainModel() != null ? id_material.toDomainModel() : null,
                cantidad,precio_total);

    }

}