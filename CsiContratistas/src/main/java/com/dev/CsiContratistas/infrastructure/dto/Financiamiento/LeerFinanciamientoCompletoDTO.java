package com.dev.CsiContratistas.infrastructure.dto.Financiamiento;

import com.dev.CsiContratistas.infrastructure.dto.FinanciamientoEmpleado.LeerFinanciamientoEmpleadoDTO;
import com.dev.CsiContratistas.infrastructure.dto.FinanciamientoMaterial.LeerFinanciamientoMaterialDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeerFinanciamientoCompletoDTO {
    private LeerFinanciamientoDTO financiamiento;
    private List<LeerFinanciamientoMaterialDTO> materiales;
    private List<LeerFinanciamientoEmpleadoDTO> empleados;
}
