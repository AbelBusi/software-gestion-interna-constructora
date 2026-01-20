package com.dev.CsiContratistas.infrastructure.Repository.in;

import com.dev.CsiContratistas.infrastructure.Entity.RolEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IJpaRolRepositorio extends JpaRepository<RolEntidad,Integer> {
    public Optional<RolEntidad> findByNombre(String nombre);
}
