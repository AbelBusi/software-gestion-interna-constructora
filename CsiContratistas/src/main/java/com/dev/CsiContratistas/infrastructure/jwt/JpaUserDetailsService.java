package com.dev.CsiContratistas.infrastructure.jwt;

import com.dev.CsiContratistas.infrastructure.Entity.UsuarioEntidad;
import com.dev.CsiContratistas.infrastructure.Repository.in.IJpaUsuarioRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {

    private final IJpaUsuarioRepositorio usuarioRepositorio;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Buscando usuario con correo: '" + username + "'");
        Optional<UsuarioEntidad> optionalUsuario = usuarioRepositorio.findByCorreo(username);

        if (optionalUsuario.isEmpty()) {
            System.out.println("No se encontró el usuario con correo: '" + username + "'");
            throw new UsernameNotFoundException("Usuario no encontrado");
        }

        System.out.println("Usuario encontrado: " + optionalUsuario.get().getCorreo());
        return optionalUsuario.get();
    }



}
