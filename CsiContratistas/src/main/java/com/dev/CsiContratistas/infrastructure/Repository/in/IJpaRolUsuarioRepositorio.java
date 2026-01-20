package com.dev.CsiContratistas.infrastructure.Repository.in;

import com.dev.CsiContratistas.infrastructure.Entity.RolUsuarioEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IJpaRolUsuarioRepositorio extends JpaRepository<RolUsuarioEntidad,Integer> {
}
