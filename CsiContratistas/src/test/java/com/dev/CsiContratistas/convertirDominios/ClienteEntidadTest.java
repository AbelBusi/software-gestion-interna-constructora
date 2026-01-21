package com.dev.CsiContratistas.convertirDominios;

import com.dev.CsiContratistas.domain.model.Cliente;
import com.dev.CsiContratistas.infrastructure.Entity.ClienteEntidad;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClienteEntidadTest {

    @Test
    void debeConvertirDeDomainAEntidadCorrectamente() {
        System.out.println("Iniciando test: convertir Cliente (dominio → entidad)");

        // Arrange
        Cliente cliente = new Cliente(
                1,
                "Natural",
                "87654321",
                "María López",
                "Razon Social S.A.",
                "12345678901",
                "Comercial López",
                "maria.lopez@email.com",
                "987654321",
                true
        );

        // Act
        ClienteEntidad entidad = ClienteEntidad.fromDomainModel(cliente);

        // Assert
        assertNotNull(entidad);
        assertEquals(1, entidad.getId_cliente());
        assertEquals("Natural", entidad.getTipo_cliente());
        assertEquals("87654321", entidad.getDni());
        assertEquals("María López", entidad.getNombre());
        assertEquals("Razon Social S.A.", entidad.getRazon_social());
        assertEquals("12345678901", entidad.getRuc());
        assertEquals("Comercial López", entidad.getNombre_comercial());
        assertEquals("maria.lopez@email.com", entidad.getCorreo());
        assertEquals("987654321", entidad.getTelefono());
        assertTrue(entidad.getEstado());
        assertNull(entidad.getClientes(), "La lista de clientes debería ser null en este caso");

        System.out.println("Conversión dominio → entidad exitosa \n");
    }

    @Test
    void debeConvertirDeEntidadADomainCorrectamente() {
        System.out.println("Iniciando test: convertir ClienteEntidad (entidad → dominio)");

        // Arrange
        ClienteEntidad entidad = new ClienteEntidad(
                1,
                "Jurídico",
                "12345678",
                "Empresa SAC",
                "Importadora SAC",
                "10987654321",
                "Importadora Express",
                "empresa@email.com",
                "912345678",
                true,
                null
        );

        // Act
        Cliente cliente = entidad.toDomainModel();

        // Assert
        assertNotNull(cliente);
        assertEquals(1, cliente.getId_cliente());
        assertEquals("Jurídico", cliente.getTipo_cliente());
        assertEquals("12345678", cliente.getDni());
        assertEquals("Empresa SAC", cliente.getNombre());
        assertEquals("Importadora SAC", cliente.getRazon_social());
        assertEquals("10987654321", cliente.getRuc());
        assertEquals("Importadora Express", cliente.getNombre_comercial());
        assertEquals("empresa@email.com", cliente.getCorreo());
        assertEquals("912345678", cliente.getTelefono());
        assertTrue(cliente.getEstado());

        System.out.println("Conversión entidad → dominio exitosa \n");
    }
}
