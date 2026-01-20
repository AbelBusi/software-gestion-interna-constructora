package com.dev.CsiContratistas.infrastructure.Repository.on;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.dev.CsiContratistas.domain.model.Usuario;
import com.dev.CsiContratistas.domain.ports.out.CambiarCorreoClaveRepositorioPort;
import com.dev.CsiContratistas.domain.ports.out.ObjectRepositorioPort;
import com.dev.CsiContratistas.domain.ports.out.ObjectValidRepositorioPort;
import com.dev.CsiContratistas.infrastructure.Entity.UsuarioEntidad;
import com.dev.CsiContratistas.infrastructure.Repository.in.IJpaUsuarioRepositorio;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JpaUsuarioRepositorioAdapter implements ObjectRepositorioPort<Usuario,Integer>, ObjectValidRepositorioPort<String>, CambiarCorreoClaveRepositorioPort {

    private final IJpaUsuarioRepositorio iJpaUsuarioRepositorio;
    private static final Logger logger = LoggerFactory.getLogger(JpaUsuarioRepositorioAdapter.class);

    private final PasswordEncoder passwordEncoder;

    @Override
    public Usuario guardar(Usuario objeto) {

        objeto.setClave_acceso(passwordEncoder.encode(objeto.getClave_acceso()));

        UsuarioEntidad usuarioEntidad = UsuarioEntidad.fromDomainModel(objeto);

        UsuarioEntidad guardarUsuario = iJpaUsuarioRepositorio.save(usuarioEntidad);

        return guardarUsuario.toDomainModel();

    }

    @Override
    public Integer eliminar(Integer integer) {

        Optional<UsuarioEntidad> clienteEntidadOptional = iJpaUsuarioRepositorio.findById(integer);

        if (!clienteEntidadOptional.isPresent()){
            return 0;
        }

        UsuarioEntidad clienteEntidad= clienteEntidadOptional.get();

        clienteEntidad.setEstado(false);

        UsuarioEntidad actualizarClienteEntidad=iJpaUsuarioRepositorio.save(clienteEntidad);

        return 1;

    }

    @Override

    public Optional<Usuario> leer(Integer id) {

        return iJpaUsuarioRepositorio.findById(id).map(UsuarioEntidad::toDomainModel);

    }

    @Override

    public List<Usuario> leerObjetos() {

        return iJpaUsuarioRepositorio.findAll().stream()

                .map(UsuarioEntidad::toDomainModel)

                .collect(Collectors.toList());

    }

    @Override
    public Optional<Usuario> modificar(Integer id, Usuario usuario) {
        if (!iJpaUsuarioRepositorio.existsById(id)) {
            return Optional.empty();
        }


        try {
            if (existByIdEmpleadoExcludingUsuario(usuario.getId_empleado().getId_empleado(), id)) {
                return Optional.empty();
            }
        } catch (Exception e) {
            logger.error("Error al verificar empleado", e);
            return Optional.empty();
        }


        Optional<Usuario> usuarioActual = leer(id);
        if (usuarioActual.isPresent() &&
                !usuario.getCorreo().equals(usuarioActual.get().getCorreo()) &&
                existEmail(usuario.getCorreo())) {
            return Optional.empty();
        }


        try {
            UsuarioEntidad usuarioEntidad = UsuarioEntidad.fromDomainModel(usuario);
            usuarioEntidad.setId_usuario(id);
            usuarioEntidad.setFechaActualizacion(LocalDateTime.now());
            return Optional.of(iJpaUsuarioRepositorio.save(usuarioEntidad).toDomainModel());
        } catch (Exception e) {
            logger.error("Error al actualizar usuario", e);
            return Optional.empty();
        }


    }
    public boolean existEmail(String correo) {
        return iJpaUsuarioRepositorio.existsByCorreo( correo);
    }
    public boolean existByIdEmpleado(Integer idEmpleado ) {
        return iJpaUsuarioRepositorio.existsByEmpleadoId(idEmpleado);
    }
    public boolean existByIdEmpleadoExcludingUsuario(Integer idEmpleado, Integer idUsuario) {
        try {
            return iJpaUsuarioRepositorio.existsByEmpleadoIdAndNotUsuario(idEmpleado, idUsuario);
        } catch (Exception e) {
            logger.error("Error al verificar existencia de empleado", e);
            return false;
        }
    }


    @Override
    public boolean leerParametro(String correo) {

        boolean validacion= iJpaUsuarioRepositorio.existsByCorreo(correo);
        if (!validacion){
            return false;
        }
        return true;
    }

    @Override
    public boolean actualizarPassword(String correo, String nuevaPassword) {
        try {
            Optional<UsuarioEntidad> usuarioOpt = iJpaUsuarioRepositorio.findByCorreo(correo);

            if (usuarioOpt.isEmpty()) {
                logger.warn("Intento de cambio de contraseña para correo inexistente: {}", correo);
                return false;
            }

            UsuarioEntidad usuario = usuarioOpt.get();

            usuario.setClave_acceso(passwordEncoder.encode(nuevaPassword));

            iJpaUsuarioRepositorio.save(usuario);
            return true;

        } catch (Exception e) {
            logger.error("Error al actualizar password para correo {}", correo, e);
            return false;
        }
    }
    public List<Usuario> leerUsuariosConEmpleadoYRoles() {
        return iJpaUsuarioRepositorio.findAllWithEmpleadoAndRoles()
            .stream()
            .sorted(Comparator.comparing(
                u -> Optional.ofNullable(u.getFechaActualizacion()).orElse(u.getFecha_asignacion()),
                Comparator.nullsLast(Comparator.reverseOrder())
            ))
            .map(UsuarioEntidad::toDomainModel)
            .collect(Collectors.toList());
    }
    
    public List<Usuario> leerUsuariosActivosConEmpleadoYRoles() {
    return iJpaUsuarioRepositorio.findAllActiveWithEmpleadoAndRoles()
            .stream()
            .map(UsuarioEntidad::toDomainModel)
            .collect(Collectors.toList());
    }
    public Optional<Usuario> buscarPorCorreo(String correo) {
        return iJpaUsuarioRepositorio.findByCorreo(correo).map(UsuarioEntidad::toDomainModel);
    }
    public List<Usuario> findTopNUsuariosRecientes(int limit) {
        return iJpaUsuarioRepositorio.findTopNByOrderByFechaRegistroDesc(limit)
                .stream()
                .map(UsuarioEntidad::toDomainModel)
                .collect(Collectors.toList());
    }
    public long countUsuariosActivos() {
        return iJpaUsuarioRepositorio.countByEstadoTrue();
    }

}