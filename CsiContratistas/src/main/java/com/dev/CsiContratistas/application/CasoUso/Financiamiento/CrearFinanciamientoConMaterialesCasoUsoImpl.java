package com.dev.CsiContratistas.application.CasoUso.Financiamiento;

import com.dev.CsiContratistas.domain.model.Financiamiento;
import com.dev.CsiContratistas.domain.model.Financiamiento_material;
import com.dev.CsiContratistas.domain.model.Material;
import com.dev.CsiContratistas.domain.ports.in.CrearFinanciamientoConMaterialesCasoUso;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import com.dev.CsiContratistas.infrastructure.dto.FinanciamientoMaterial.CrearFinanciamientoMaterialDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class CrearFinanciamientoConMaterialesCasoUsoImpl implements CrearFinanciamientoConMaterialesCasoUso {

    private final ObjectRepositorioPort<Financiamiento, Integer> financiamientoRepositorio;
    private final ObjectRepositorioPort<Financiamiento_material, Integer> financiamientoMaterialRepositorio;
    private final ObjectRepositorioPort<Material, Integer> materialRepositorio;

    @Override
    public void ejecutar(Financiamiento financiamiento, List<CrearFinanciamientoMaterialDTO> materialesDTO) {
        Financiamiento guardado = financiamientoRepositorio.guardar(financiamiento);

        for (CrearFinanciamientoMaterialDTO dto : materialesDTO) {
            Material material = materialRepositorio.leer(dto.getId_material())
                    .orElseThrow(() -> new RuntimeException("Material no encontrado"));

            Financiamiento_material relacion = new Financiamiento_material();
            relacion.setId_financiamiento(guardado);
            relacion.setId_material(material);
            relacion.setCantidad(dto.getCantidad());
            relacion.setPrecio_total(dto.getPrecio_total());

            financiamientoMaterialRepositorio.guardar(relacion);

            material.setStock_actual(material.getStock_actual() - dto.getCantidad());
            materialRepositorio.guardar(material);
        }
    }
}
