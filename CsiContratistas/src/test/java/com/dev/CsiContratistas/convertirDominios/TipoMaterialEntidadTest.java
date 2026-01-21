package com.dev.CsiContratistas.convertirDominios;

import com.dev.CsiContratistas.domain.model.Tipo_material;
import com.dev.CsiContratistas.infrastructure.Entity.TipoMaterialEntidad;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TipoMaterialEntidadTest {

    @Test
    void debeConvertirDeDomainAEntidadCorrectamente() {
        Tipo_material tipoMaterial = new Tipo_material(
                1,
                "Cemento",
                "Material aglutinante utilizado en construcción",
                "Material de construcción",
                true
        );

        TipoMaterialEntidad entidad = TipoMaterialEntidad.fromDomainModel(tipoMaterial);

        assertNotNull(entidad);
        assertEquals(1, entidad.getIdTipomaterial());
        assertEquals("Cemento", entidad.getNombre());
        assertEquals("Material aglutinante utilizado en construcción", entidad.getDescripcion());
        assertEquals("Material de construcción", entidad.getClasificacion());
        assertTrue(entidad.getEstado());
        assertNull(entidad.getMateriales());
    }

    @Test
    void debeConvertirDeEntidadADomainCorrectamente() {
        TipoMaterialEntidad entidad = new TipoMaterialEntidad(
                2,
                "Ladrillo",
                "Bloque de construcción de arcilla",
                "Material de construcción",
                false,
                null
        );

        Tipo_material tipoMaterial = entidad.toDomainModel();

        assertNotNull(tipoMaterial);
        assertEquals(2, tipoMaterial.getIdtipomaterial());
        assertEquals("Ladrillo", tipoMaterial.getNombre());
        assertEquals("Bloque de construcción de arcilla", tipoMaterial.getDescripcion());
        assertEquals("Material de construcción", tipoMaterial.getClasificacion());
        assertFalse(tipoMaterial.getEstado());
    }
}
