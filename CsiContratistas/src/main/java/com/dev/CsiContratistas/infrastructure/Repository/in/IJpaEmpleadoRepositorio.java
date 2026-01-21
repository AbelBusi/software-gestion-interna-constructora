package com.dev.CsiContratistas.infrastructure.Repository.in;

import com.dev.CsiContratistas.infrastructure.Entity.EmpleadoEntidad;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface IJpaEmpleadoRepositorio extends JpaRepository<EmpleadoEntidad,Integer> {
    boolean existsByDni (String Dni);
    long countByEstado(String estado);
    @Query("SELECT e FROM EmpleadoEntidad e WHERE e.estado = 'Activo' ORDER BY e.id_empleado DESC LIMIT 5")
    List<EmpleadoEntidad> findTop5ByEstadoTrueOrderById_empleadoDesc();

    @Query("SELECT e FROM EmpleadoEntidad e ORDER BY e.id_empleado ASC")
    Page<EmpleadoEntidad> listarOrdenadoPorId(Pageable pageable);


}
