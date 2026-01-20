package com.dev.CsiContratistas.infrastructure.Entity;

import com.dev.CsiContratistas.domain.model.*;
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
@Table(name = "OBRA", schema = "\"gestionObras\"")
public class ObraEntidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_obra")
    private Integer id_obra;

    @ManyToOne
    @JoinColumn(name = "id_responsable") // corregido: "is_responsable" debe ser "id_responsable"
    private EmpleadoEntidad id_responsable;

    @ManyToOne
    @JoinColumn(name = "id_modelo_obra")
    private ModeloObraEntidad id_modelo_obra;

    @ManyToOne
    @JoinColumn(name = "id_terreno")
    private TerrenoEntidad id_terreno;

    @ManyToOne
    @JoinColumn(name = "id_tipo_construccion")
    private TipoConstruccionEntidad id_tipo_construccion;

    @Column(name = "fecha_inicio")
    private LocalDateTime fecha_inicio;

    @Column(name = "fecha_finalizacion")
    private LocalDateTime fecha_finalizacion;

    @Column(name = "estado")
    private Boolean estado = true;

    @Column(name = "nombre")
    private String nombre;

    @OneToMany(mappedBy = "id_financiamiento_obra",cascade = CascadeType.ALL)
    private List<FinanciamientoEntidad> financiamientoObra = new ArrayList<>();

    public static ObraEntidad fromDomainModel(Obra obra) {
        if (obra == null) return null;
        return new ObraEntidad(
                obra.getId_obra(),
                EmpleadoEntidad.fromDomainModel(obra.getId_responsable()),
                ModeloObraEntidad.fromDomainModel(obra.getId_modelo_obra()),
                TerrenoEntidad.fromDomainModel(obra.getId_terreno()),
                TipoConstruccionEntidad.fromDomainModel(obra.getId_tipo_construccion()),
                obra.getFecha_inicio(),
                obra.getFecha_finalizacion(),
                obra.getEstado(),
                obra.getNombre(),
                null
        );
    }

    public Obra toDomainModel() {
        return new Obra(
                id_obra,
                id_responsable != null ? id_responsable.toDomainModel() : null,
                id_modelo_obra != null ? id_modelo_obra.toDomainModel() : null,
                id_terreno != null ? id_terreno.toDomainModel() : null,
                id_tipo_construccion != null ? id_tipo_construccion.toDomainModel() : null,
                fecha_inicio,
                fecha_finalizacion,
                estado,
                nombre
        );
    }
}
