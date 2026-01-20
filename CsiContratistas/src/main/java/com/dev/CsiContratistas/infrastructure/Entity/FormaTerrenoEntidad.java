package com.dev.CsiContratistas.infrastructure.Entity;

import com.dev.CsiContratistas.domain.model.Forma_terreno;
import com.dev.CsiContratistas.domain.model.Tipo_suelo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "FORMA_TERRENO",schema = "\"gestionTerreno\"")
public class FormaTerrenoEntidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_forma_terreno")
    private Integer id_forma_terreno;

    @Column(name = "nombre",length = 50)
    private String nombre;

    @Column(name = "descripcion",columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "estado")
    private Boolean estado=true;

    public static FormaTerrenoEntidad fromDomainModel(Forma_terreno formaTerreno){
        if (formaTerreno==null){
            return null;
        }

        return new FormaTerrenoEntidad(
                formaTerreno.getId_forma_terreno(),
                formaTerreno.getNombre(),
                formaTerreno.getDescripcion(),
                formaTerreno.getEstado()
        );
    }

    public Forma_terreno toDomainModel(){

        return new Forma_terreno(id_forma_terreno,nombre,descripcion,estado);

    }

}