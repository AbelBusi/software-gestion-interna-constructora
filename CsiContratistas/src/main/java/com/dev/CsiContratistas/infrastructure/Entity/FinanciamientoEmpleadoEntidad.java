package com.dev.CsiContratistas.infrastructure.Entity;

import com.dev.CsiContratistas.domain.model.Financiamiento_empleado;
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
@Table(name = "FINANCIAMIENTO_EMPLEADO",schema = "\"gestionFinanciamiento\"")
public class FinanciamientoEmpleadoEntidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_financiamiento_empleado")
    private Integer id_financiamiento_empleado;

    @ManyToOne
    @JoinColumn(name = "id_financiamiento",nullable = false)
    private FinanciamientoEntidad id_financiamiento;

    @ManyToOne
    @JoinColumn(name = "id_empleado",nullable = false)
    private EmpleadoEntidad id_empleado;

    @Column(name = "dias_participacion")
    private Integer dias_participacion;

    @Column(name = "costo_total",precision = 10,scale = 2)
    private BigDecimal costo_total;


    public static FinanciamientoEmpleadoEntidad fromDomainModel(Financiamiento_empleado financiamientoEmpleado){
        if (financiamientoEmpleado==null){
            return null;
        }

        return new FinanciamientoEmpleadoEntidad(
                financiamientoEmpleado.getId_financiamiento_empleado(),
                FinanciamientoEntidad.fromDomainModel(financiamientoEmpleado.getId_financiamiento()),
                EmpleadoEntidad.fromDomainModel(financiamientoEmpleado.getId_empleado()),
                financiamientoEmpleado.getDias_participacion(),
                financiamientoEmpleado.getCosto_total()
        );
    }

    public Financiamiento_empleado toDomainModel(){


        return new Financiamiento_empleado(id_financiamiento_empleado,
                id_financiamiento.toDomainModel() != null ? id_financiamiento.toDomainModel() : null,
                id_empleado.toDomainModel() != null ? id_empleado.toDomainModel() : null,
                dias_participacion,costo_total);

    }

}