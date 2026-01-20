package com.dev.CsiContratistas.infrastructure.Entity;

import com.dev.CsiContratistas.domain.model.Forma_terreno;
import com.dev.CsiContratistas.domain.model.Terreno;
import com.dev.CsiContratistas.domain.model.Tipo_material;
import com.dev.CsiContratistas.domain.model.Tipo_suelo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TERRENO", schema = "\"gestionTerreno\"")
public class TerrenoEntidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_terreno")
    private Integer id_terreno;

    @ManyToOne
    @JoinColumn(name = "id_tipo_suelo",nullable = false)
    private TipoSueloEntidad id_tipo_suelo;

    @ManyToOne
    @JoinColumn(name = "id_forma_terreno",nullable = false)
    private FormaTerrenoEntidad id_forma_terreno;

    @Column(name = "area_total",precision = 10,scale = 2)
    private BigDecimal area_total;

    @Column(name = "area_util",precision = 10,scale = 2)
    private BigDecimal area_util;

    @Column(name = "frente_metros",precision = 10,scale = 2)
    private BigDecimal frente_metros;

    @Column(name = "fondo_metros",precision = 10,scale = 2)
    private BigDecimal fondo_metros;

    @Column(name = "zonificacion",length = 100)
    private String zonificacion;

    @Column(name = "servicios_basicos")
    private Boolean servicios_basicos=true;

    @Column(name = "acceso_vial",length = 30)
    private String acceso_vial;

    @Column(name = "observaciones",columnDefinition = "TEXT")
    private String observaciones;

    @Column(name = "coordenadas",columnDefinition = "TEXT")
    private String coordenadas;

    @Column(name = "fecha_asignacion")
    private LocalDateTime fecha_asignacion;

    @Column(name = "estado")
    private Boolean estado;

    @OneToMany(mappedBy = "id_terreno",cascade = CascadeType.ALL)
    private List<ObraEntidad> modelos_obras = new ArrayList<>();

    public static TerrenoEntidad fromDomainModel(Terreno terreno){
        if (terreno == null) {
            return null;
        }
        return new TerrenoEntidad(
                terreno.getId_terreno(),
                TipoSueloEntidad.fromDomainModel(terreno.getId_tipo_suelo()),
                FormaTerrenoEntidad.fromDomainModel(terreno.getId_forma_terreno()),
                terreno.getArea_total(),
                terreno.getArea_util(),
                terreno.getFrente_metros(),
                terreno.getFondo_metros(),
                terreno.getZonificacion(),
                terreno.getServicios_basicos(),
                terreno.getAcceso_vial(),
                terreno.getObservaciones(),
                terreno.getCoordenadas(),
                terreno.getFecha_asignacion(),
                terreno.getEstado(),
                null
        );
    }

    public Terreno toDomainModel() {
        return new Terreno(
                id_terreno,
                id_tipo_suelo != null ? id_tipo_suelo.toDomainModel() : null,
                id_forma_terreno != null ? id_forma_terreno.toDomainModel() : null,
                area_total,
                area_util,
                frente_metros,
                fondo_metros,
                zonificacion,
                servicios_basicos,
                acceso_vial,
                observaciones,
                coordenadas,
                fecha_asignacion,
                estado
        );
    }


}