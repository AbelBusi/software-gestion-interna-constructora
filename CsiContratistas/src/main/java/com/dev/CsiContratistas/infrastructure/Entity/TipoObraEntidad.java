package com.dev.CsiContratistas.infrastructure.Entity;

import com.dev.CsiContratistas.domain.model.Empleado;
import com.dev.CsiContratistas.domain.model.Tipo_obra;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TIPO_OBRA",schema = "\"gestionTerreno\"")
public class TipoObraEntidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_obra")
    private Integer id_tipo_obra;

    @Column(name = "nombre",length = 50)
    private String nombre;

    @Column(name = "descripcion",columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "estado")
    private Boolean estado=true;

    @OneToMany(mappedBy = "id_tipo_obra",cascade = CascadeType.ALL)
    private List<ModeloObraEntidad> modelos = new ArrayList<>();

    public static TipoObraEntidad fromDomainModel(Tipo_obra tipoObra){
        if (tipoObra==null){
            return null;
        }

        return new TipoObraEntidad(
                tipoObra.getId_tipo_obra(),
                tipoObra.getNombre(),
                tipoObra.getDescripcion(),
                tipoObra.getEstado(),
                null
        );
    }

    public Tipo_obra toDomainModel(){

        return new Tipo_obra(id_tipo_obra,nombre,descripcion,estado);

    }

}