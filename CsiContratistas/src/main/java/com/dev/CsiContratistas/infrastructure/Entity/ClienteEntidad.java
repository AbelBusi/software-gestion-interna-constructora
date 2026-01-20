package com.dev.CsiContratistas.infrastructure.Entity;

import com.dev.CsiContratistas.domain.model.Cliente;
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
@Table(name = "CLIENTE",schema = "\"gestionFinanciamiento\"")
public class ClienteEntidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_cliente;

    @Column(name = "tipo_cliente")
    private String tipo_cliente;

    @Column(name = "dni",length = 8,unique = true)
    private String dni;

    @Column(name = "nombre_completo", length = 50)
    private String nombre;
    @Column(name = "razon_social",columnDefinition = "TEXT")
    private String razon_social;

    @Column(name = "ruc", length = 20)
    private String ruc;

    @Column(name = "nombre_comercial", length = 50)
    private String nombre_comercial;

    @Column(name = "correo",length = 50)
    private String correo;

    @Column(name = "telefono",length = 9)
    private String telefono;

    @Column(name = "estado")
    private Boolean estado = true;

    @OneToMany(mappedBy = "id_cliente",cascade = CascadeType.ALL)
    private List<FinanciamientoEntidad> clientes = new ArrayList<>();


    public static ClienteEntidad fromDomainModel(Cliente cliente){
        if (cliente==null){
            return null;
        }

        return new ClienteEntidad(
                cliente.getId_cliente(),
                cliente.getTipo_cliente(),
                cliente.getDni(),
                cliente.getNombre(),
                cliente.getRazon_social(),
                cliente.getRuc(),
                cliente.getNombre_comercial(),
                cliente.getCorreo(),
                cliente.getTelefono(),
                cliente.getEstado(),
                null
        );
    }

    public Cliente toDomainModel(){

        return new Cliente(id_cliente,tipo_cliente,dni,nombre,razon_social,ruc,nombre_comercial,correo,telefono,estado);

    }

}