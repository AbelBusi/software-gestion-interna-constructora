package com.dev.CsiContratistas.infrastructure.Entity;

import com.dev.CsiContratistas.domain.model.Tipo_construccion;
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
@Table(name = "TIPO_CONSTRUCCION",schema = "\"gestionObras\"")
public class TipoConstruccionEntidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_construccion")
    private Integer id_tipo_construccion;

    @Column(name = "nombre",length = 50)
    private String nombre;

    @Column(name = "descripcion",columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "estado")
    private Boolean estado=true;

    @OneToMany(mappedBy = "id_tipo_construccion",cascade = CascadeType.ALL)
    private List<ObraEntidad> tipoConstruccion = new ArrayList<>();

    public static TipoConstruccionEntidad fromDomainModel(Tipo_construccion tipoConstruccion){
        if (tipoConstruccion==null){
            return null;
        }

        return new TipoConstruccionEntidad(
                tipoConstruccion.getId_tipo_construccion(),
                tipoConstruccion.getNombre(),
                tipoConstruccion.getDescripcion(),
                tipoConstruccion.getEstado(),
                null
        );
    }

    public Tipo_construccion toDomainModel(){

        return new Tipo_construccion(id_tipo_construccion,nombre,descripcion,estado);

    }

}