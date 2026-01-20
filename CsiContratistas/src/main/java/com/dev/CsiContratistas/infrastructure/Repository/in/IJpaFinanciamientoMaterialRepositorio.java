package com.dev.CsiContratistas.infrastructure.Repository.in;

import com.dev.CsiContratistas.infrastructure.Entity.FinanciamientoEntidad;
import com.dev.CsiContratistas.infrastructure.Entity.FinanciamientoMaterialEntidad;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IJpaFinanciamientoMaterialRepositorio extends JpaRepository<FinanciamientoMaterialEntidad,Integer> {
    @Modifying
    @Transactional
    @Query("DELETE FROM FinanciamientoMaterialEntidad fm WHERE fm.id_financiamiento = :financiamiento")
    void eliminarPorFinanciamiento(@Param("financiamiento") FinanciamientoEntidad financiamiento);

    @Query("SELECT f FROM FinanciamientoMaterialEntidad f WHERE f.id_financiamiento = :financiamiento")
    List<FinanciamientoMaterialEntidad> buscarPorFinanciamiento(@Param("financiamiento") FinanciamientoEntidad financiamiento);


}
