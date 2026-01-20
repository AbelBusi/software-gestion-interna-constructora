package com.dev.CsiContratistas.convertirDominios;

import com.dev.CsiContratistas.domain.model.Forma_terreno;
import com.dev.CsiContratistas.infrastructure.Entity.FormaTerrenoEntidad;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FormaTerrenoEntidadTest {

    @Test
    void debeConvertirDeDomainAEntidadCorrectamente() {
        // Arrange
        Forma_terreno formaTerreno = new Forma_terreno(1, "Rectangular", "Terreno de forma rectangular", true);

        // Act
        FormaTerrenoEntidad entidad = FormaTerrenoEntidad.fromDomainModel(formaTerreno);

        // Assert
        assertNotNull(entidad);
        assertEquals(1, entidad.getId_forma_terreno());
        assertEquals("Rectangular", entidad.getNombre());
        assertEquals("Terreno de forma rectangular", entidad.getDescripcion());
        assertTrue(entidad.getEstado());
    }

    @Test
    void debeConvertirDeEntidadADomainCorrectamente() {
        // Arrange
        FormaTerrenoEntidad entidad = new FormaTerrenoEntidad(2, "Triangular", "Terreno triangular", false);

        // Act
        Forma_terreno forma = entidad.toDomainModel();

        // Assert
        assertNotNull(forma);
        assertEquals(2, forma.getId_forma_terreno());
        assertEquals("Triangular", forma.getNombre());
        assertEquals("Terreno triangular", forma.getDescripcion());
        assertFalse(forma.getEstado());
    }

    @Test
    void fromDomainModelDebeRetornarNullSiParametroEsNull() {
        // Act
        FormaTerrenoEntidad entidad = FormaTerrenoEntidad.fromDomainModel(null);

        // Assert
        assertNull(entidad);
    }
}
