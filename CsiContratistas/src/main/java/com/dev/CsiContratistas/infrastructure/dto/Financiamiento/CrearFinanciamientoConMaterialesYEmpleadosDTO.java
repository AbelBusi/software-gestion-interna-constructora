package com.dev.CsiContratistas.infrastructure.dto.Financiamiento;

import com.dev.CsiContratistas.infrastructure.dto.FinanciamientoEmpleado.CrearFinanciamientoEmpleadolDTO;
import com.dev.CsiContratistas.infrastructure.dto.FinanciamientoMaterial.CrearFinanciamientoMaterialDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CrearFinanciamientoConMaterialesYEmpleadosDTO {

    private CrearFinanciamientoDTO financiamiento;
    private List<CrearFinanciamientoMaterialDTO> materiales;
    private List<CrearFinanciamientoEmpleadolDTO> empleados;

}
