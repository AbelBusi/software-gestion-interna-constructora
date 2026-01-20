package com.dev.CsiContratistas.infrastructure.Controller;

import com.dev.CsiContratistas.infrastructure.dto.Email.EnviarCodigoRequestDTO;
import com.dev.CsiContratistas.infrastructure.dto.Email.EnviarCodigoResponseDTO;
import com.dev.CsiContratistas.infrastructure.dto.Email.VerificarCodigoRequestDTO;
import com.dev.CsiContratistas.infrastructure.dto.Email.VerificarCodigoResponseDTO;
import com.dev.CsiContratistas.infrastructure.servicio.EmailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/verificarEmail")
@CrossOrigin("http://localhost:3000/")
public class EmailControlador {

    private final EmailService emailService;

    @PostMapping("/enviar-codigo")
    public ResponseEntity<EnviarCodigoResponseDTO> enviarCodigo(
            @Valid @RequestBody EnviarCodigoRequestDTO request) {
        return ResponseEntity.ok(emailService.enviarCodigo(request));
    }

    @PostMapping("/verificar-codigo")
    public ResponseEntity<VerificarCodigoResponseDTO> verificarCodigo(
            @Valid @RequestBody VerificarCodigoRequestDTO request) {
        return ResponseEntity.ok(emailService.verificarCodigo(request));
    }

}
