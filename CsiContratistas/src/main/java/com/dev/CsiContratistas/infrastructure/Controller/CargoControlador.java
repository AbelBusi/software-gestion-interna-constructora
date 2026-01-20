package com.dev.CsiContratistas.infrastructure.Controller;

import com.dev.CsiContratistas.application.Services.CargoServicio;
import com.dev.CsiContratistas.application.Services.ProfesionServicio;
import com.dev.CsiContratistas.domain.model.Cargo;
import com.dev.CsiContratistas.domain.model.Empleado;
import com.dev.CsiContratistas.domain.model.Profesiones;
import com.dev.CsiContratistas.domain.model.Rama;
import com.dev.CsiContratistas.infrastructure.dto.Cargos.CrearCargoDTO;
import com.dev.CsiContratistas.infrastructure.dto.Cargos.EliminarCargoDTO;
import com.dev.CsiContratistas.infrastructure.dto.Cargos.LeerCargoDTO;
import com.dev.CsiContratistas.infrastructure.dto.Cargos.ModificarCargoDTO;
import com.dev.CsiContratistas.infrastructure.mapper.CargoMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/cargos")
@CrossOrigin ("http://localhost:3000/")
public class CargoControlador {

    private final CargoServicio cargoServicio;

    private static final Logger logger = LoggerFactory.getLogger(CargoControlador.class);


    @PostMapping
    public ResponseEntity<CrearCargoDTO> crearCargo(@RequestBody CrearCargoDTO crearCargoDTO) {

        Empleado empleados= new Empleado();
        empleados.setId_empleado(crearCargoDTO.getId_empleado());

        Rama ramas = new Rama();
        ramas.setId_rama(crearCargoDTO.getId_rama());

        Profesiones profesiones = new Profesiones();
        profesiones.setId_profesion(crearCargoDTO.getId_profesion());


        Cargo cargoDomain = CargoMapper.crearDtoDomain(crearCargoDTO,empleados,ramas,profesiones);


        Cargo crearCargo = cargoServicio.crearObjeto(cargoDomain);


        return new ResponseEntity<>(crearCargoDTO,HttpStatus.CREATED);

    }

    @GetMapping("/{id}")
    public ResponseEntity<LeerCargoDTO> LeerCargo(@PathVariable Integer id){

        Optional<Cargo> cargo = cargoServicio.leerObjeto(id);
        LeerCargoDTO leerCargoDTO=CargoMapper.leerDTOCargo(cargo.orElse(null));
        return new ResponseEntity<>(leerCargoDTO,HttpStatus.OK);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ModificarCargoDTO> modificarCargo(@PathVariable Integer id, @RequestBody ModificarCargoDTO modificarCargoDTO) {


        Empleado empleados= new Empleado();
        empleados.setId_empleado(modificarCargoDTO.getId_empleado());

        Rama ramas = new Rama();
        ramas.setId_rama(modificarCargoDTO.getId_rama());

        Profesiones profesiones= new Profesiones();
        profesiones.setId_profesion(modificarCargoDTO.getId_profesion());


        Cargo cargo = CargoMapper.actualizarDtoDomain(modificarCargoDTO,empleados,ramas,profesiones);

        Optional<Cargo> modificarCargo = cargoServicio.modificarObjeto(id,cargo);

        return new ResponseEntity<>(HttpStatus.OK);

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<EliminarCargoDTO> eliminarCargo(@PathVariable Integer id){

        cargoServicio.eliminarObjeto(id);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);

    }


    @GetMapping
    public ResponseEntity<List<LeerCargoDTO>> mostrarCargos(){

        List<Cargo> cargos= cargoServicio.leerObjetos();

        List<LeerCargoDTO> respuesta=cargos.stream()
                .map(CargoMapper::leerDTOCargo)
                .toList();

        return new ResponseEntity<>(respuesta,HttpStatus.OK);

    }
}