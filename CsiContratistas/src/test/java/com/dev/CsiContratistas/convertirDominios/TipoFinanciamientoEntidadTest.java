package com.dev.CsiContratistas.convertirDominios;

import com.dev.CsiContratistas.domain.model.Tipo_financiamiento;
import com.dev.CsiContratistas.infrastructure.Entity.TipoFinanciamientoEntidad;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TipoFinanciamientoEntidadTest {

    @Test
    void debeConvertirDeDomainAEntidadCorrectamente() {
        Tipo_financiamiento tipoFinanciamiento = new Tipo_financiamiento(
                1,
                "Bancario",
                "Financiamiento a través de entidades bancarias",
                true
        );

        TipoFinanciamientoEntidad entidad = TipoFinanciamientoEntidad.fromDomainModel(tipoFinanciamiento);

        assertNotNull(entidad);
        assertEquals(1, entidad.getId_tipo_financiamiento());
        assertEquals("Bancario", entidad.getNombre());
        assertEquals("Financiamiento a través de entidades bancarias", entidad.getDescripcion());
        assertTrue(entidad.getEstado());
        assertNull(entidad.getFinanciamientos());
    }

    @Test
    void debeConvertirDeEntidadADomainCorrectamente() {
        TipoFinanciamientoEntidad entidad = new TipoFinanciamientoEntidad(
                2,
                "Cooperativa",
                "Financiamiento mediante cooperativas",
                false,
                null
        );

        Tipo_financiamiento tipoFinanciamiento = entidad.toDomainModel();

        assertNotNull(tipoFinanciamiento);
        assertEquals(2, tipoFinanciamiento.getId_tipo_financiamiento());
        assertEquals("Cooperativa", tipoFinanciamiento.getNombre());
        assertEquals("Financiamiento mediante cooperativas", tipoFinanciamiento.getDescripcion());
        assertFalse(tipoFinanciamiento.getEstado());
    }
}
