package com.dev.CsiContratistas.convertirDominios;

import com.dev.CsiContratistas.domain.model.*;
import com.dev.CsiContratistas.infrastructure.Entity.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CargoEntidadTest {

    @Test
    void debeConvertirDeDomainAEntidadCorrectamente() {
        Empleado empleado = new Empleado(1, "12345678", "Juan", "Pérez",
                null, "Masculino", "987654321", "Soltero", "Dirección X", "Activo");
        Rama rama = new Rama(2, new Profesiones(5, null, null, null, true),
                "Backend", null, null, true);
        Profesiones profesion = new Profesiones(5, "Ingeniería de Datos", null, null, true);

        Cargo cargoDomain = new Cargo(
                10, empleado, rama, profesion,
                LocalDateTime.of(2023, 5, 20, 14, 30), true);

        CargoEntidad entidad = CargoEntidad.fromDomainModel(cargoDomain);

        assertNotNull(entidad);
        assertEquals(5, entidad.getId_profesion().getId_profesion());
        assertEquals(2, entidad.getId_rama().getId_rama());
        assertEquals(1, entidad.getId_empleado().getId_empleado());
    }

    @Test
    void debeConvertirDeEntidadADomainCorrectamente() {
        EmpleadoEntidad empleadoEntidad = new EmpleadoEntidad();
        empleadoEntidad.setId_empleado(1);

        RamaEntidad ramaEntidad = new RamaEntidad();
        ramaEntidad.setId_rama(2);

        ProfesionEntidad profesionEntidad = new ProfesionEntidad();
        profesionEntidad.setId_profesion(5);

        CargoEntidad entidad = new CargoEntidad(
                10, empleadoEntidad, ramaEntidad, profesionEntidad,
                LocalDateTime.of(2023, 5, 20, 14, 30), true);

        Cargo dominio = entidad.toDomainModel();

        assertNotNull(dominio);
        assertEquals(5, dominio.getId_profesion().getId_profesion());
        assertEquals(2, dominio.getId_rama().getId_rama());
        assertEquals(1, dominio.getId_empleado().getId_empleado());
    }
}
