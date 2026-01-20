package com.dev.CsiContratistas.domain.ports.in;

import com.dev.CsiContratistas.domain.model.Financiamiento;
import com.dev.CsiContratistas.infrastructure.dto.FinanciamientoMaterial.CrearFinanciamientoMaterialDTO;
import jakarta.transaction.Transactional;

import java.util.List;

public interface CrearFinanciamientoConMaterialesCasoUso {
    void ejecutar(Financiamiento financiamiento, List<CrearFinanciamientoMaterialDTO> materialesDTO);
}
