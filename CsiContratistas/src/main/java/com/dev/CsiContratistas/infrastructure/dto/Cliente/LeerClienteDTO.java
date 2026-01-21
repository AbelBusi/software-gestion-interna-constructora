package com.dev.CsiContratistas.infrastructure.dto.Cliente;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeerClienteDTO {

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