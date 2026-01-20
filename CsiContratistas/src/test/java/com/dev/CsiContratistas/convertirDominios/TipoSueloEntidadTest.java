package com.dev.CsiContratistas.convertirDominios;

import com.dev.CsiContratistas.domain.model.Tipo_suelo;
import com.dev.CsiContratistas.infrastructure.Entity.TipoSueloEntidad;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TipoSueloEntidadTest {

    @Test
    void debeConvertirDeDomainAEntidadCorrectamente() {
        System.out.println("Test: conversión de dominio → entidad");

        Tipo_suelo tipoSuelo = new Tipo_suelo(1, "Arcilloso", "Suelo con mucha arcilla", true);

        TipoSueloEntidad entidad = TipoSueloEntidad.fromDomainModel(tipoSuelo);

        assertNotNull(entidad, "La entidad no debería ser null");
        assertEquals(1, entidad.getId_tipo_suelo(), "ID incorrecto");
        assertEquals("Arcilloso", entidad.getNombre(), "Nombre incorrecto");
        assertEquals("Suelo con mucha arcilla", entidad.getDescripcion(), "Descripción incorrecta");
        assertTrue(entidad.getEstado(), "Estado incorrecto");

        System.out.println("Conversión de dominio a entidad exitosa \n");
    }

    @Test
    void debeConvertirDeEntidadADomainCorrectamente() {
        System.out.println("Test: conversión de entidad → dominio");

        TipoSueloEntidad entidad = new TipoSueloEntidad(2, "Arenoso", "Suelo con mucha arena", false);

        Tipo_suelo tipoSuelo = entidad.toDomainModel();

        assertNotNull(tipoSuelo, "El modelo de dominio no debería ser null");
        assertEquals(2, tipoSuelo.getId_tipo_suelo(), "ID incorrecto");
        assertEquals("Arenoso", tipoSuelo.getNombre(), "Nombre incorrecto");
        assertEquals("Suelo con mucha arena", tipoSuelo.getDescripcion(), "Descripción incorrecta");
        assertFalse(tipoSuelo.getEstado(), "Estado incorrecto");

        System.out.println("Conversión de entidad a dominio exitosa \n");
    }
}
