package com.dev.CsiContratistas.infrastructure.Entity;

import com.dev.CsiContratistas.domain.model.Tipo_material;
import jakarta.persistence.*;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TIPO_MATERIAL", schema = "\"gestionMateriales\"")
public class TipoMaterialEntidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_material",nullable = false)
    private Integer idTipomaterial;

    @Column(name = "nombre", length = 50)
    private String nombre;

    @Column(name = "descripcion",columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "clasificacion",columnDefinition = "TEXT")
    private String clasificacion;

    @Column(name = "estado")
    private Boolean estado;

    @OneToMany(mappedBy = "tipoMaterial",cascade = CascadeType.ALL)
    List<MaterialEntidad> materiales = new ArrayList<>();

    public static TipoMaterialEntidad fromDomainModel(Tipo_material tipoMaterial){
        if (tipoMaterial == null) {
            return null;
        }
        return new TipoMaterialEntidad(
                tipoMaterial.getIdtipomaterial(),
                tipoMaterial.getNombre(),
                tipoMaterial.getDescripcion(),
                tipoMaterial.getClasificacion(),
                tipoMaterial.getEstado(),
                null
        );
    }

    public Tipo_material toDomainModel(){

        return new Tipo_material(idTipomaterial,nombre,descripcion,clasificacion,estado);
    }

}