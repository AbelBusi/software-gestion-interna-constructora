package com.dev.CsiContratistas.infrastructure.Repository.in;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dev.CsiContratistas.infrastructure.Entity.UsuarioEntidad;

@Repository
public interface IJpaUsuarioRepositorio extends JpaRepository<UsuarioEntidad, Integer> {
    Optional<UsuarioEntidad> findByCorreo(String correo);
    boolean existsByCorreo(String correo);

    @Query(value = "SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END " +
           "FROM UsuarioEntidad u WHERE u.id_empleado.id_empleado = :idEmpleado")
    boolean existsByEmpleadoId(@Param("idEmpleado") Integer idEmpleado);

    @Query(value = "SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END " +
           "FROM UsuarioEntidad u WHERE u.id_empleado.id_empleado = :idEmpleado AND u.id_usuario != :idUsuario")
    boolean existsByEmpleadoIdAndNotUsuario(@Param("idEmpleado") Integer idEmpleado, @Param("idUsuario") Integer idUsuario);

    @Query(value = "SELECT DISTINCT u FROM UsuarioEntidad u " +
       "LEFT JOIN FETCH u.id_empleado e " +
       "LEFT JOIN FETCH u.Usuarios ru " +
       "LEFT JOIN FETCH ru.id_rol r")
    List<UsuarioEntidad> findAllWithEmpleadoAndRoles();
    
    @Query(value = "SELECT u FROM UsuarioEntidad u " +
           "LEFT JOIN FETCH u.id_empleado " +
           "WHERE u.estado = true " +
           "ORDER BY u.fechaActualizacion ASC")
    List<UsuarioEntidad> findAllActiveWithEmpleado();
    
    @Query(value = "SELECT DISTINCT u FROM UsuarioEntidad u " +
       "LEFT JOIN FETCH u.id_empleado e " +
       "LEFT JOIN FETCH u.Usuarios ru " +
       "LEFT JOIN FETCH ru.id_rol r " +
       "WHERE u.estado = true " +
       "ORDER BY u.fechaActualizacion DESC")
    List<UsuarioEntidad> findAllActiveWithEmpleadoAndRoles();
    @Query("SELECT u FROM UsuarioEntidad u " +
            "LEFT JOIN FETCH u.id_empleado e " +
            "ORDER BY u.fecha_asignacion DESC, u.fechaActualizacion DESC " +
            "LIMIT :limit")
    List<UsuarioEntidad> findTopNByOrderByFechaRegistroDesc(@Param("limit") int limit);
    long countByEstadoTrue();
    
}
