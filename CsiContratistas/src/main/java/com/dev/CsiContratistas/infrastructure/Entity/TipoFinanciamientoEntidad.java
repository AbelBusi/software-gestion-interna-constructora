package com.dev.CsiContratistas.infrastructure.Entity;

import com.dev.CsiContratistas.domain.model.Tipo_construccion;
import com.dev.CsiContratistas.domain.model.Tipo_financiamiento;
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
@Table(name = "TIPO_FINANCIAMIENTO",schema = "\"gestionFinanciamiento\"")
public class TipoFinanciamientoEntidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_financiamiento")
    private Integer id_tipo_financiamiento;

    @Column(name = "nombre",length = 50)
    private String nombre;

    @Column(name = "descripcion",columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "estado")
    private Boolean estado=true;

    @OneToMany(mappedBy = "id_tipo_financiamiento",cascade = CascadeType.ALL)
    private List<FinanciamientoEntidad> financiamientos = new ArrayList<>();


    public static TipoFinanciamientoEntidad fromDomainModel(Tipo_financiamiento tipoFinanciamiento){
        if (tipoFinanciamiento==null){
            return null;
        }

        return new TipoFinanciamientoEntidad(
                tipoFinanciamiento.getId_tipo_financiamiento(),
                tipoFinanciamiento.getNombre(),
                tipoFinanciamiento.getDescripcion(),
                tipoFinanciamiento.getEstado(),
                null
        );
    }

    public Tipo_financiamiento toDomainModel(){


        return new Tipo_financiamiento(id_tipo_financiamiento,nombre,descripcion,estado);

    }

}