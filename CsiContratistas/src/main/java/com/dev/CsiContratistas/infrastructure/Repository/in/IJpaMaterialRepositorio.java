package com.dev.CsiContratistas.infrastructure.Repository.in;

import com.dev.CsiContratistas.domain.model.Material;
import com.dev.CsiContratistas.infrastructure.Entity.MaterialEntidad;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public interface IJpaMaterialRepositorio extends JpaRepository<MaterialEntidad,Integer> {
    boolean existsByNombre(String nombre);
    Page<MaterialEntidad> findByEstadoTrue(Pageable pageable);
    List<MaterialEntidad> findByTipoMaterial_idTipomaterial(Integer idTipomaterial);
    long countByEstadoTrue();
    @Query("SELECT m FROM MaterialEntidad m WHERE m.estado = TRUE ORDER BY m.idmaterial DESC LIMIT 5")
    List<MaterialEntidad> findTop5ByEstadoTrueOrderByIdDesc();



}