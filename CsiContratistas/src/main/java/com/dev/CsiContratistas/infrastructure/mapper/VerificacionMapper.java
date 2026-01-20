package com.dev.CsiContratistas.infrastructure.mapper;


import com.dev.CsiContratistas.domain.model.CodigoVerificacion;
import com.dev.CsiContratistas.infrastructure.dto.Email.EnviarCodigoRequestDTO;
import com.dev.CsiContratistas.infrastructure.dto.Email.EnviarCodigoResponseDTO;
import com.dev.CsiContratistas.infrastructure.dto.Email.VerificarCodigoResponseDTO;

import java.time.Duration;
import java.time.Instant;

public class VerificacionMapper {

    /** De DTO de petición a dominio  */
    public static CodigoVerificacion toDomain(
            EnviarCodigoRequestDTO dto,
            String codigoGenerado,
            Duration ttl) {

        return new CodigoVerificacion(
                dto.getEmail(),
                codigoGenerado,
                Instant.now(),
                ttl
        );
    }

    /** De dominio a DTO respuesta (envío) */
    public static EnviarCodigoResponseDTO toEnviarResponse(CodigoVerificacion model) {
        return new EnviarCodigoResponseDTO(
                "Código enviado a " + model.getEmail()
        );
    }

    /** Resultado booleano a DTO respuesta (verificación) */
    public static VerificarCodigoResponseDTO toVerificarResponse(boolean valido) {
        return new VerificarCodigoResponseDTO(valido);
    }
}
