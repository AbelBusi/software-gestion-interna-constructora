package com.dev.CsiContratistas.convertirDominios;

import com.dev.CsiContratistas.domain.model.Tipo_obra;
import com.dev.CsiContratistas.infrastructure.Entity.TipoObraEntidad;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TipoObraEntidadTest {

    @Test
    void debeConvertirDeDomainAEntidadCorrectamente() {
        System.out.println("Test: conversión de dominio → entidad (Tipo_obra)");

        Tipo_obra tipoObra = new Tipo_obra(1, "Residencial", "Construcción de viviendas", true);

        TipoObraEntidad entidad = TipoObraEntidad.fromDomainModel(tipoObra);

        assertNotNull(entidad, "La entidad no debe ser null");
        assertEquals(1, entidad.getId_tipo_obra(), "ID incorrecto");
        assertEquals("Residencial", entidad.getNombre(), "Nombre incorrecto");
        assertEquals("Construcción de viviendas", entidad.getDescripcion(), "Descripción incorrecta");
        assertTrue(entidad.getEstado(), "Estado incorrecto");
        assertNull(entidad.getModelos(), "Los modelos deben ser null por defecto");

        System.out.println("Conversión exitosa: dominio → entidad\n");
    }

    @Test
    void debeConvertirDeEntidadADomainCorrectamente() {
        System.out.println("Test: conversión de entidad → dominio (TipoObraEntidad)");

        TipoObraEntidad entidad = new TipoObraEntidad(2, "Industrial", "Obras de uso industrial", false, null);

        Tipo_obra tipoObra = entidad.toDomainModel();

        assertNotNull(tipoObra, "El modelo de dominio no debe ser null");
        assertEquals(2, tipoObra.getId_tipo_obra(), "ID incorrecto");
        assertEquals("Industrial", tipoObra.getNombre(), "Nombre incorrecto");
        assertEquals("Obras de uso industrial", tipoObra.getDescripcion(), "Descripción incorrecta");
        assertFalse(tipoObra.getEstado(), "Estado incorrecto");

        System.out.println("Conversión exitosa: entidad → dominio\n");
    }
}
