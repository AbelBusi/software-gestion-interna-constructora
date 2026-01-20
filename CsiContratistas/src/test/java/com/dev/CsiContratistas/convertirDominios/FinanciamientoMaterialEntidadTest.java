package com.dev.CsiContratistas.convertirDominios;

import com.dev.CsiContratistas.domain.model.Financiamiento_material;
import com.dev.CsiContratistas.domain.model.Financiamiento;
import com.dev.CsiContratistas.domain.model.Material;
import com.dev.CsiContratistas.infrastructure.Entity.FinanciamientoMaterialEntidad;
import com.dev.CsiContratistas.infrastructure.Entity.FinanciamientoEntidad;
import com.dev.CsiContratistas.infrastructure.Entity.MaterialEntidad;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class FinanciamientoMaterialEntidadTest {

    @Test
    void debeConvertirDeDomainAEntidadCorrectamente() {
        // Arrange
        Financiamiento financiamiento = new Financiamiento();
        financiamiento.setId_financiamiento(1);

        Material material = new Material();
        material.setId_material(10);

        Financiamiento_material domain = new Financiamiento_material(
                5,
                financiamiento,
                material,
                100,
                new BigDecimal("1500.50")
        );

        // Act
        FinanciamientoMaterialEntidad entidad = FinanciamientoMaterialEntidad.fromDomainModel(domain);

        // Assert
        assertNotNull(entidad);
        assertEquals(5, entidad.getId_financiamiento_material());
        assertNotNull(entidad.getId_financiamiento());
        assertEquals(1, entidad.getId_financiamiento().getId_financiamiento());
        assertNotNull(entidad.getId_material());
        assertEquals(10, entidad.getId_material().getIdmaterial());
        assertEquals(100, entidad.getCantidad());
        assertEquals(new BigDecimal("1500.50"), entidad.getPrecio_total());
    }

    @Test
    void debeConvertirDeEntidadADomainCorrectamente() {
        // Arrange
        FinanciamientoEntidad financiamientoEntidad = new FinanciamientoEntidad();
        financiamientoEntidad.setId_financiamiento(2);

        MaterialEntidad materialEntidad = new MaterialEntidad();
        materialEntidad.setIdmaterial(20);

        FinanciamientoMaterialEntidad entidad = new FinanciamientoMaterialEntidad(
                3,
                financiamientoEntidad,
                materialEntidad,
                50,
                new BigDecimal("750.00")
        );

        // Act
        Financiamiento_material domain = entidad.toDomainModel();

        // Assert
        assertNotNull(domain);
        assertEquals(3, domain.getId_financiamiento_material());
        assertNotNull(domain.getId_financiamiento());
        assertEquals(2, domain.getId_financiamiento().getId_financiamiento());
        assertNotNull(domain.getId_material());
        assertEquals(20, domain.getId_material().getId_material());
        assertEquals(50, domain.getCantidad());
        assertEquals(new BigDecimal("750.00"), domain.getPrecio_total());
    }

    @Test
    void fromDomainModelDebeRetornarNullSiParametroEsNull() {
        // Act
        FinanciamientoMaterialEntidad entidad = FinanciamientoMaterialEntidad.fromDomainModel(null);

        // Assert
        assertNull(entidad);
    }
}
