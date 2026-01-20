package com.dev.CsiContratistas.infrastructure.Controller;

import com.dev.CsiContratistas.application.Services.MaterialServicio;
import com.dev.CsiContratistas.domain.model.Material;
import com.dev.CsiContratistas.domain.model.Tipo_material;
import com.dev.CsiContratistas.infrastructure.dto.Material.CrearMaterialDTO;
import com.dev.CsiContratistas.infrastructure.dto.Material.EliminarMaterialDTO;
import com.dev.CsiContratistas.infrastructure.dto.Material.LeerMaterialDTO;
import com.dev.CsiContratistas.infrastructure.dto.Material.ModificarMaterialDTO;
import com.dev.CsiContratistas.infrastructure.mapper.MaterialMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/materiales")
@CrossOrigin ("http://localhost:3000/")
public class MaterialControlador {

    private final MaterialServicio materialServicio;

    @PostMapping
    public ResponseEntity<CrearMaterialDTO> crearMaterial(@RequestBody CrearMaterialDTO crearMaterialDTO) {

        Tipo_material tipoMaterial= new Tipo_material();

        tipoMaterial.setIdtipomaterial(crearMaterialDTO.getTipo_material());

        Material materialDomain = MaterialMapper.crearDtoDomain(crearMaterialDTO,tipoMaterial);

        Material crearMaterial = materialServicio.crearObjeto(materialDomain);

        return new ResponseEntity<>(crearMaterialDTO,HttpStatus.CREATED);

    }


    @GetMapping("/{id}")
    public ResponseEntity<LeerMaterialDTO> LeerMaterial(@PathVariable Integer id){

        Optional<Material> material = materialServicio.leerObjeto(id);
        LeerMaterialDTO leerMaterialDTO=MaterialMapper.leerDTOMaterial(material.orElse(null));
        return new ResponseEntity<>(leerMaterialDTO,HttpStatus.OK);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ModificarMaterialDTO> modificarMaterial(@PathVariable Integer id, @RequestBody ModificarMaterialDTO modificarMaterialDTO) {


        Tipo_material tipoMaterial= new Tipo_material();
        tipoMaterial.setIdtipomaterial(modificarMaterialDTO.getTipo_material());
        Material material = MaterialMapper.actualizarDtoDomain(modificarMaterialDTO,tipoMaterial);

        Optional<Material> modificarMaterial = materialServicio.modificarObjeto(id,material);

        return new ResponseEntity<>(HttpStatus.OK);

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<EliminarMaterialDTO> eliminarMaterial(@PathVariable Integer id){

        materialServicio.eliminarObjeto(id);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);

    }

    @GetMapping
    public ResponseEntity<List<LeerMaterialDTO>> mostrarRamas(){

        List<Material>materials= materialServicio.leerObjetos();

        List<LeerMaterialDTO> respuesta=materials.stream()
                .map(MaterialMapper::leerDTOMaterial)
                .toList();

        return new ResponseEntity<>(respuesta,HttpStatus.OK);

    }
}