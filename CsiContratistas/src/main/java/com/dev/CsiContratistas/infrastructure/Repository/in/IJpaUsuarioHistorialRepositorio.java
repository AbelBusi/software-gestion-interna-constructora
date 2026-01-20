package com.dev.CsiContratistas.infrastructure.Repository.in;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dev.CsiContratistas.infrastructure.Entity.UsuarioHistorialEntidad;

@Repository
public interface IJpaUsuarioHistorialRepositorio extends JpaRepository<UsuarioHistorialEntidad, Integer> {
    List<UsuarioHistorialEntidad> findByIdUsuarioOrderByFechaDesc(Integer idUsuario);
    List<UsuarioHistorialEntidad> findAllByOrderByFechaDesc();
} 