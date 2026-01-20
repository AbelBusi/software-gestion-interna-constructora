package com.dev.CsiContratistas.infrastructure.Controller;

import com.dev.CsiContratistas.application.Services.FinanciamientoMaterialServicio;
import com.dev.CsiContratistas.domain.model.*;
import com.dev.CsiContratistas.infrastructure.dto.FinanciamientoMaterial.CrearFinanciamientoMaterialDTO;
import com.dev.CsiContratistas.infrastructure.dto.FinanciamientoMaterial.EliminarFinanciamientoMaterialDTO;
import com.dev.CsiContratistas.infrastructure.dto.FinanciamientoMaterial.LeerFinanciamientoMaterialDTO;
import com.dev.CsiContratistas.infrastructure.dto.FinanciamientoMaterial.ModificarFinanciamientoMaterialDTO;
import com.dev.CsiContratistas.infrastructure.mapper.FinanciamientoMaterialMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/financiamientos_materiales")
@CrossOrigin ("http://localhost:3000/")
public class FinanciamientoMaterialControlador {

    private final FinanciamientoMaterialServicio financiamientoMaterialServicio;

    @PostMapping
    public ResponseEntity<CrearFinanciamientoMaterialDTO> crearFinanciamientoMaterial(@RequestBody CrearFinanciamientoMaterialDTO crearFinanciamientoMaterialDTO) {

        Financiamiento financiamiento = new Financiamiento();
        financiamiento.setId_financiamiento(crearFinanciamientoMaterialDTO.getId_financiamiento());

        Material material = new Material();
        material.setId_material(crearFinanciamientoMaterialDTO.getId_material());

        Financiamiento_material financiamientoMaterialDomain = FinanciamientoMaterialMapper.crearDtoDomain(crearFinanciamientoMaterialDTO,financiamiento,material);

        Financiamiento_material crearFinanciamiento = financiamientoMaterialServicio.crearObjeto(financiamientoMaterialDomain);

        return new ResponseEntity<>(crearFinanciamientoMaterialDTO,HttpStatus.CREATED);

    }

    @GetMapping("/{id}")
    public ResponseEntity<LeerFinanciamientoMaterialDTO> LeerFinanciamientoMaterial(@PathVariable Integer id){

        Optional<Financiamiento_material> financiamientoMaterial = financiamientoMaterialServicio.leerObjeto(id);
        LeerFinanciamientoMaterialDTO leerFinanciamientoMaterialDTO=FinanciamientoMaterialMapper.leerDTOFinanciamientoMaterial(financiamientoMaterial.orElse(null));
        return new ResponseEntity<>(leerFinanciamientoMaterialDTO,HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ModificarFinanciamientoMaterialDTO> modificarFinanciamientoMaterial(@PathVariable Integer id, @RequestBody ModificarFinanciamientoMaterialDTO modificarFinanciamientoMaterialDTO) {

        Financiamiento financiamiento = new Financiamiento();
        financiamiento.setId_financiamiento(modificarFinanciamientoMaterialDTO.getId_financiamiento());

        Material material = new Material();
        material.setId_material(modificarFinanciamientoMaterialDTO.getId_material());

        Financiamiento_material financiamientoMaterial = FinanciamientoMaterialMapper.actualizarDtoDomain(modificarFinanciamientoMaterialDTO,financiamiento,material);

        Optional<Financiamiento_material> modificarFinanciamientoMaterial = financiamientoMaterialServicio.modificarObjeto(id,financiamientoMaterial);

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EliminarFinanciamientoMaterialDTO> eliminarFinanciamientoMaterial(@PathVariable Integer id){

        financiamientoMaterialServicio.eliminarObjeto(id);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);

    }

    @GetMapping
    public ResponseEntity<List<LeerFinanciamientoMaterialDTO>> mostrarFinanciamientosMateriales(){

        List<Financiamiento_material> financiamientoMaterials= financiamientoMaterialServicio.leerObjetos();

        List<LeerFinanciamientoMaterialDTO> respuesta=financiamientoMaterials.stream()
                .map(FinanciamientoMaterialMapper::leerDTOFinanciamientoMaterial)
                .toList();

        return new ResponseEntity<>(respuesta,HttpStatus.OK);

    }

}