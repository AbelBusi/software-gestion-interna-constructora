package com.dev.CsiContratistas.infrastructure.Repository.in;

import com.dev.CsiContratistas.infrastructure.Entity.FinanciamientoEmpleadoEntidad;
import com.dev.CsiContratistas.infrastructure.Entity.FinanciamientoEntidad;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IJpaFinanciamientoEmpleadolRepositorio extends JpaRepository<FinanciamientoEmpleadoEntidad,Integer> {

    @Modifying
    @Transactional
    @Query("DELETE FROM FinanciamientoEmpleadoEntidad fe WHERE fe.id_financiamiento = :financiamiento")
    void eliminarPorFinanciamiento(@Param("financiamiento") FinanciamientoEntidad financiamiento);

    @Query("SELECT e FROM FinanciamientoEmpleadoEntidad e WHERE e.id_financiamiento = :financiamiento")
    List<FinanciamientoEmpleadoEntidad> buscarPorFinanciamiento(@Param("financiamiento") FinanciamientoEntidad financiamiento);

}