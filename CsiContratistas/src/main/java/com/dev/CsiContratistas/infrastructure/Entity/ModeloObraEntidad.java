package com.dev.CsiContratistas.infrastructure.Entity;

import com.dev.CsiContratistas.domain.model.Cargo;
import com.dev.CsiContratistas.domain.model.Modelo_obra;
import com.dev.CsiContratistas.domain.model.Tipo_obra;
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
@Table(name = "MODELO_OBRA",schema ="\"gestionObras\"")
public class ModeloObraEntidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_modelo_obra")
    private Integer id_modelo_obra;

    @ManyToOne
    @JoinColumn(name = "id_tipo_obra",nullable = false)
    private TipoObraEntidad id_tipo_obra;

    @Column(name = "ur_modelo3d", columnDefinition = "TEXT")
    private String url_modelo3d;

    @Column(name = "nombre",length = 50)
    private String nombre;

    @Column(name = "descripcion",columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "uso_destinado",columnDefinition = "TEXT")
    private String uso_destinado;

    @Column(name = "pisos")
    private Integer pisos;

    @Column(name = "ambientes")
    private Integer ambientes;

    @Column(name = "area_total",precision = 5,scale = 2)
    private BigDecimal area_total;

    @Column(name = "capacidad_personas")
    private Integer capacidad_personas;

    @Column(name="precio_preferencial",precision = 10,scale = 2)
    private BigDecimal precio_preferencial;

    @Column(name = "estado")
    private Boolean estado;


    @OneToMany(mappedBy = "id_modelo_obra",cascade = CascadeType.ALL)
    private List<ObraEntidad> modelo_obras = new ArrayList<>();


    public static ModeloObraEntidad fromDomainModel(Modelo_obra modeloObra) {

        if (modeloObra==null){
            return null;
        }
        return new ModeloObraEntidad(

                modeloObra.getId_modelo_obra(),
                TipoObraEntidad.fromDomainModel(modeloObra.getId_tipo_obra()),
                modeloObra.getUrl_modelo3d(),
                modeloObra.getNombre(),
                modeloObra.getDescripcion(),
                modeloObra.getUso_destinado(),
                modeloObra.getPisos(),
                modeloObra.getAmbientes(),
                modeloObra.getArea_total(),
                modeloObra.getCapacidad_personas(),
                modeloObra.getPrecio_preferencial(),
                modeloObra.getEstado(),
                null

        );
    }


    public Modelo_obra toDomainModel() {
        return new Modelo_obra(
                id_modelo_obra,
                id_tipo_obra != null ? id_tipo_obra.toDomainModel() : null,
                url_modelo3d,
                nombre,
                descripcion,
                uso_destinado,
                pisos,
                ambientes,
                area_total,
                capacidad_personas,
                precio_preferencial,
                estado
        );
    }


}