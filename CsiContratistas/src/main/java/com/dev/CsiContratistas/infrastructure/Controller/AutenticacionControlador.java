package com.dev.CsiContratistas.infrastructure.Controller;

import com.dev.CsiContratistas.application.Services.EmpleadoServicio;
import com.dev.CsiContratistas.application.Services.UsuarioServicio;
import com.dev.CsiContratistas.domain.model.Usuario;
import com.dev.CsiContratistas.infrastructure.Seguridad.AuthResponse;
import com.dev.CsiContratistas.infrastructure.dto.Empleado.ResultadoDTO;
import com.dev.CsiContratistas.infrastructure.dto.Usuario.AuthRequest;
import com.dev.CsiContratistas.infrastructure.dto.Usuario.CambiarCorreoDTO;
import com.dev.CsiContratistas.infrastructure.dto.Usuario.VerificarCorreoDniExistenteDTO;
import com.dev.CsiContratistas.infrastructure.jwt.JpaUserDetailsService;
import com.dev.CsiContratistas.infrastructure.jwt.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/autenticacion")
@CrossOrigin("http://localhost:3000/")
@RequiredArgsConstructor
public class AutenticacionControlador {


    private final AuthenticationManager authenticationManager;
    private final JpaUserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final EmpleadoServicio empleadoServicio;
    private final UsuarioServicio usuarioServicio;
    private final PasswordEncoder passwordEncoder;


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        Authentication auth = new UsernamePasswordAuthenticationToken(
                request.getCorreo(), request.getClave_acceso());
        authenticationManager.authenticate(auth);

        UserDetails user = userDetailsService.loadUserByUsername(request.getCorreo());
        String token = jwtService.generateToken(user);

        ResponseCookie cookie = ResponseCookie.from("token", token)
        .httpOnly(true)
        .secure(true)
        .maxAge(3600)
        .path("/")
        .build();

        return ResponseEntity.ok()
        .header(HttpHeaders.SET_COOKIE, cookie.toString())
        .body(new AuthResponse(token));
    }


    @PostMapping("/empleado")
    public ResponseEntity<?> empleadoExistente(
            @Valid @RequestBody VerificarCorreoDniExistenteDTO dto,
            BindingResult br) {

        if (br.hasErrors()) {
            String errores = br.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining("; "));
            return ResponseEntity.badRequest()
                    .body(Map.of("mensaje", errores));
        }

        // Buscar en el servicio correcto

        boolean dniExiste    = empleadoServicio.obtenerPorParametro(dto.getDni());
        boolean correoExiste = usuarioServicio.obtenerPorParametro(dto.getCorreo());

        ResultadoDTO resultado = new ResultadoDTO(dniExiste && correoExiste);

        if (!resultado.isResultado()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resultado);
        }
        return ResponseEntity.ok(resultado);

    }

    @PatchMapping("/cambiarClaveUsuario")
    public ResponseEntity<?> cambiarClaveCorreo(@Valid @RequestBody CambiarCorreoDTO dto, BindingResult br)
    {

        boolean validarCorreoExistente =usuarioServicio.obtenerPorParametro(dto.getCorreo());
        String clave = dto.getClave();
        String claveConfirmacion= dto.getClaveConfirmacion();
        Usuario usuario = new Usuario();

        if (!validarCorreoExistente){

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El correo no existe");

        }

        if (claveConfirmacion.isEmpty() || clave.isEmpty()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Debes agregar valor a las claves de acceso");
        }

        if (!clave.equalsIgnoreCase(claveConfirmacion)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No coinciden las claves de acceso");
        }

        usuarioServicio.actualizarPassword(dto.getCorreo(),clave);

        return ResponseEntity.status(HttpStatus.OK).body("Clave de acceso cambiado correctamente");

    }

}