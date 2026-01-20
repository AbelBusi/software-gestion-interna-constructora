package com.dev.CsiContratistas.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cliente  implements Serializable {

    private Integer id_cliente;
    private String tipo_cliente;
    private String dni;
    private String nombre;
    private String razon_social;
    private String ruc;
    private String nombre_comercial;
    private String correo;
    private String telefono;
    private Boolean estado;

}