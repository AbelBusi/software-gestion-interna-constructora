package com.dev.CsiContratistas.convertirDominios;

import com.dev.CsiContratistas.domain.model.Material;
import com.dev.CsiContratistas.domain.model.Tipo_material;
import com.dev.CsiContratistas.infrastructure.Entity.MaterialEntidad;
import com.dev.CsiContratistas.infrastructure.Entity.TipoMaterialEntidad;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class MaterialEntidadTest {

    @Test
    void debeConvertirDeDomainAEntidadCorrectamente() {
        // Arrange
        Tipo_material tipo = new Tipo_material(1, "Hormigón", "Material de construcción", "Pesado", true);
        Material material = new Material(
                1, tipo, "Bloque", new BigDecimal("12.50"), 100,
                new BigDecimal("2.45"), new BigDecimal("1.20"),
                new BigDecimal("35.00"), "Bloques resistentes", true
        );

        // Act
        MaterialEntidad entidad = MaterialEntidad.fromDomainModel(material);

        // Assert
        assertNotNull(entidad);
        assertEquals("Bloque", entidad.getNombre());
        assertEquals(new BigDecimal("12.50"), entidad.getPrecio_unitario());
        assertEquals(new BigDecimal("2.45"), entidad.getMasa_especifica());
        assertEquals(new BigDecimal("1.20"), entidad.getLongitud());
        assertEquals(new BigDecimal("35.00"), entidad.getTemperatura_termodinamica());
        assertEquals("Bloques resistentes", entidad.getDescripcion());
        assertNotNull(entidad.getTipoMaterial());
        assertEquals("Hormigón", entidad.getTipoMaterial().getNombre());
    }

    @Test
    void debeConvertirDeEntidadADomainCorrectamente() {
        // Arrange
        TipoMaterialEntidad tipoEntidad = new TipoMaterialEntidad(2, "Acero", "Metal de construcción", "Estructural", true, null);
        MaterialEntidad entidad = new MaterialEntidad(
                2, tipoEntidad, "Viga", new BigDecimal("22.10"), 200,
                new BigDecimal("7.85"), new BigDecimal("6.00"),
                new BigDecimal("20.00"), "Vigas de acero estructural", true, null
        );

        // Act
        Material domain = entidad.toDomainModel();

        // Assert
        assertNotNull(domain);
        assertEquals("Viga", domain.getNombre());
        assertEquals(200, domain.getStock_actual());
        assertEquals("Vigas de acero estructural", domain.getDescripcion());
        assertEquals("Acero", domain.getTipo_material().getNombre());
    }

    @Test
    void fromDomainModelDebeRetornarNullSiParametroEsNull() {
        // Act
        MaterialEntidad entidad = MaterialEntidad.fromDomainModel(null);

        // Assert
        assertNull(entidad);
    }
}
