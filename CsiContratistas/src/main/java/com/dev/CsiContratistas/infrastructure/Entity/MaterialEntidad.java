package com.dev.CsiContratistas.infrastructure.Entity;

import com.dev.CsiContratistas.domain.model.Material;
import com.dev.CsiContratistas.domain.model.Tipo_material;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "MATERIAL", schema = "\"gestionMateriales\"")
public class MaterialEntidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_material")
    private Integer idmaterial;

    @ManyToOne
    @JoinColumn(name = "id_tipo_material")
    private TipoMaterialEntidad tipoMaterial;

    @Column(name = "nombre", length = 50,unique = true)
    private String nombre;

    @Column(name = "precio_unitario",precision = 10,scale = 2)
    private BigDecimal precio_unitario;

    @Column(name = "stock_actual")
    private Integer stock_actual;

    @Column(name = "masa_especifica",precision = 10,scale = 2)
    private BigDecimal masa_especifica;

    @Column(name = "longitud",precision = 10,scale = 2)
    private BigDecimal longitud;

    @Column(name = "temperatura_termodinamica",precision = 5,scale = 2)
    private BigDecimal temperatura_termodinamica;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "estado")
    private Boolean estado=true;

    @OneToMany(mappedBy = "id_material",cascade = CascadeType.ALL)
    private List<FinanciamientoMaterialEntidad> financiamientoMaterialEntidads= new ArrayList<>();

    public static MaterialEntidad fromDomainModel(Material material){
        if (material == null) {
            return null;
        }
        return new MaterialEntidad(
                material.getId_material(),
                TipoMaterialEntidad.fromDomainModel(material.getTipo_material()),
                material.getNombre(),
                material.getPrecio_unitario(),
                material.getStock_actual(),
                material.getMasa_especifica(),
                material.getLongitud(),
                material.getTemperatura_termodinamica(),
                material.getDescripcion(),
                material.getEstado(),
                null
        );
    }

    public Material toDomainModel() {
        return new Material(
                idmaterial,
                tipoMaterial != null ? tipoMaterial.toDomainModel() : null,
                nombre,
                precio_unitario,
                stock_actual,
                masa_especifica,
                longitud,
                temperatura_termodinamica,
                descripcion,
                estado
        );
    }

}