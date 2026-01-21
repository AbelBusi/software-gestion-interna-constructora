package com.dev.CsiContratistas.infrastructure.Entity;

import com.dev.CsiContratistas.domain.model.Tipo_obra;
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
@Table(name = "TIPO_SUELO",schema = "\"gestionTerreno\"")
public class TipoSueloEntidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_suelo")
    private Integer id_tipo_suelo;

    @Column(name = "nombre",length = 50)
    private String nombre;

    @Column(name = "descripcion",columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "estado")
    private Boolean estado=true;

    public static TipoSueloEntidad fromDomainModel(Tipo_suelo tipoSuelo){
        if (tipoSuelo==null){
            return null;
        }

        return new TipoSueloEntidad(
                tipoSuelo.getId_tipo_suelo(),
                tipoSuelo.getNombre(),
                tipoSuelo.getDescripcion(),
                tipoSuelo.getEstado()
        );
    }

    public Tipo_suelo toDomainModel(){

        return new Tipo_suelo(id_tipo_suelo,nombre,descripcion,estado);

    }

}