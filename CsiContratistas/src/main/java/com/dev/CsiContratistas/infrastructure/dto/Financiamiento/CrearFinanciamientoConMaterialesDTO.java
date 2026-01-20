package com.dev.CsiContratistas.infrastructure.dto.Financiamiento;

import com.dev.CsiContratistas.infrastructure.dto.FinanciamientoMaterial.CrearFinanciamientoMaterialDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CrearFinanciamientoConMaterialesDTO {
    private CrearFinanciamientoDTO financiamiento;
    private List<CrearFinanciamientoMaterialDTO> materiales;
}
