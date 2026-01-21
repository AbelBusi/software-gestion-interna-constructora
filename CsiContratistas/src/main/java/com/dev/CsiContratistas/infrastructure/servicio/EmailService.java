package com.dev.CsiContratistas.infrastructure.servicio;

import com.dev.CsiContratistas.domain.model.CodigoVerificacion;
import com.dev.CsiContratistas.infrastructure.dto.Email.EnviarCodigoRequestDTO;
import com.dev.CsiContratistas.infrastructure.dto.Email.EnviarCodigoResponseDTO;
import com.dev.CsiContratistas.infrastructure.dto.Email.VerificarCodigoRequestDTO;
import com.dev.CsiContratistas.infrastructure.dto.Email.VerificarCodigoResponseDTO;
import com.dev.CsiContratistas.infrastructure.mapper.VerificacionMapper;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    // Almacén en memoria
    private final Map<String, CodigoVerificacion> codigos = new ConcurrentHashMap<>();
    private static final Duration TTL = Duration.ofMinutes(10);

    public EnviarCodigoResponseDTO enviarCodigo(EnviarCodigoRequestDTO dto) {
        String codigo = String.format("%06d", ThreadLocalRandom.current().nextInt(1_000_000));

        // 1. Mapear DTO ➜ dominio
        CodigoVerificacion verif = VerificacionMapper.toDomain(dto, codigo, TTL);
        codigos.put(dto.getEmail(), verif);

        // 2. Crear email con HTML estilo CSI
        try {
            String html =
                    """
                    <div style="font-family:Arial,Helvetica,sans-serif;max-width:600px;margin:0 auto;
                                border:1px solid #e0e0e0;border-radius:8px;overflow:hidden">
                      <div style="background:#0c4c8a;padding:20px;text-align:center">
                        <h1 style="color:white;margin:0">CSI Contratistas</h1>
                      </div>
                      <div style="padding:24px;color:#333">
                        <h2 style="margin-top:0">Código de verificación</h2>
                        <p>Hola,</p>
                        <p>Para continuar con tu solicitud, usa el siguiente código:</p>
                        <div style="text-align:center;margin:32px 0">
                          <span style="font-size:42px;font-weight:bold;letter-spacing:6px;color:#0c4c8a;">
                            %s
                          </span>
                        </div>
                        <p style="margin-bottom:0">Este código expira en 10 minutos.</p>
                      </div>
                      <div style="background:#f5f5f5;padding:16px;text-align:center;font-size:12px;color:#666">
                        © 2025 CSI Contratistas
                      </div>
                    </div>
                    """.formatted(codigo);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
            helper.setTo(dto.getEmail());
            helper.setSubject("Tu código de verificación");
            helper.setText(html, true); // true = HTML
            mailSender.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException("Error al enviar el correo de verificación", e);
        }

        // 3. Devolver DTO respuesta
        return VerificacionMapper.toEnviarResponse(verif);
    }


    public VerificarCodigoResponseDTO verificarCodigo(VerificarCodigoRequestDTO dto) {
        CodigoVerificacion verif = codigos.get(dto.getEmail());

        boolean valido = verif != null
                && verif.getCodigo().equals(dto.getCodigo())
                && Instant.now().isBefore(verif.getGeneradoEn().plus(verif.getTtl()));

        if (valido) codigos.remove(dto.getEmail());

        return VerificacionMapper.toVerificarResponse(valido);
    }
}

