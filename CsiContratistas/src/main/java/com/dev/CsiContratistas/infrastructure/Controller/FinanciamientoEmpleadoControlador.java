package com.dev.CsiContratistas.infrastructure.Controller;

import com.dev.CsiContratistas.application.Services.FinanciamientoEmpleadoServicio;
import com.dev.CsiContratistas.domain.model.Financiamiento;
import com.dev.CsiContratistas.domain.model.Financiamiento_empleado;
import com.dev.CsiContratistas.domain.model.Empleado;
import com.dev.CsiContratistas.infrastructure.dto.FinanciamientoEmpleado.CrearFinanciamientoEmpleadolDTO;
import com.dev.CsiContratistas.infrastructure.dto.FinanciamientoEmpleado.EliminarFinanciamientoEmpleadoDTO;
import com.dev.CsiContratistas.infrastructure.dto.FinanciamientoEmpleado.LeerFinanciamientoEmpleadoDTO;
import com.dev.CsiContratistas.infrastructure.dto.FinanciamientoEmpleado.ModificarFinanciamientoEmpleadoDTO;
import com.dev.CsiContratistas.infrastructure.dto.FinanciamientoMaterial.CrearFinanciamientoMaterialDTO;
import com.dev.CsiContratistas.infrastructure.dto.FinanciamientoMaterial.EliminarFinanciamientoMaterialDTO;
import com.dev.CsiContratistas.infrastructure.dto.FinanciamientoMaterial.LeerFinanciamientoMaterialDTO;
import com.dev.CsiContratistas.infrastructure.dto.FinanciamientoMaterial.ModificarFinanciamientoMaterialDTO;
import com.dev.CsiContratistas.infrastructure.mapper.FinanciamientoEmpleadoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/financiamientos_empleados")
@CrossOrigin ("http://localhost:3000/")
public class FinanciamientoEmpleadoControlador {

    private final FinanciamientoEmpleadoServicio financiamientoEmpleadoServicio;

    @PostMapping
    public ResponseEntity<CrearFinanciamientoEmpleadolDTO> crearFinanciamientoEmpleado(@RequestBody CrearFinanciamientoEmpleadolDTO crearFinanciamientoEmpleadolDTO) {

        Financiamiento financiamiento = new Financiamiento();
        financiamiento.setId_financiamiento(crearFinanciamientoEmpleadolDTO.getId_financiamiento());

        Empleado empleado = new Empleado();
        empleado.setId_empleado(crearFinanciamientoEmpleadolDTO.getId_empleado());

        Financiamiento_empleado financiamientoEmpleadoDomain = FinanciamientoEmpleadoMapper.crearDtoDomain(crearFinanciamientoEmpleadolDTO,financiamiento,empleado);

        Financiamiento_empleado crearFinanciamiento = financiamientoEmpleadoServicio.crearObjeto(financiamientoEmpleadoDomain);

        return new ResponseEntity<>(crearFinanciamientoEmpleadolDTO,HttpStatus.CREATED);

    }

    @GetMapping("/{id}")
    public ResponseEntity<LeerFinanciamientoEmpleadoDTO> LeerFinanciamientoMaterial(@PathVariable Integer id){

        Optional<Financiamiento_empleado> financiamientoEmpleado = financiamientoEmpleadoServicio.leerObjeto(id);
        LeerFinanciamientoEmpleadoDTO leerFinanciamientoEmpleadoDTO=FinanciamientoEmpleadoMapper.leerDTOFinanciamientoEmpleado(financiamientoEmpleado.orElse(null));
        return new ResponseEntity<>(leerFinanciamientoEmpleadoDTO,HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ModificarFinanciamientoEmpleadoDTO> modificarFinanciamientoEmpleado(@PathVariable Integer id, @RequestBody ModificarFinanciamientoEmpleadoDTO modificarFinanciamientoEmpleadoDTO) {

        Financiamiento financiamiento = new Financiamiento();
        financiamiento.setId_financiamiento(modificarFinanciamientoEmpleadoDTO.getId_financiamiento());

        Empleado empleado = new Empleado();
        empleado.setId_empleado(modificarFinanciamientoEmpleadoDTO.getId_empleado());

        Financiamiento_empleado financiamientoEmpleado = FinanciamientoEmpleadoMapper.actualizarDtoDomain(modificarFinanciamientoEmpleadoDTO,financiamiento,empleado);

        Optional<Financiamiento_empleado> modificarFinanciamientoEmpleado = financiamientoEmpleadoServicio.modificarObjeto(id,financiamientoEmpleado);

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EliminarFinanciamientoEmpleadoDTO> eliminarFinanciamientoEmpleado(@PathVariable Integer id){

        financiamientoEmpleadoServicio.eliminarObjeto(id);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);

    }

    @GetMapping
    public ResponseEntity<List<LeerFinanciamientoEmpleadoDTO>> mostrarFinanciamientosEmpleados(){

        List<Financiamiento_empleado> financiamientoEmpleados= financiamientoEmpleadoServicio.leerObjetos();

        List<LeerFinanciamientoEmpleadoDTO> respuesta=financiamientoEmpleados.stream()
                .map(FinanciamientoEmpleadoMapper::leerDTOFinanciamientoEmpleado)
                .toList();

        return new ResponseEntity<>(respuesta,HttpStatus.OK);

    }

}