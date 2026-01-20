package com.dev.CsiContratistas.infrastructure.Repository.in;

import com.dev.CsiContratistas.infrastructure.Entity.CargoEntidad;
import com.dev.CsiContratistas.infrastructure.Entity.ClienteEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IJpaClienteRepositorio extends JpaRepository<ClienteEntidad,Integer> {
}
