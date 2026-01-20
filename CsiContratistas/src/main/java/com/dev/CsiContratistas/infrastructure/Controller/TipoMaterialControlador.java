package com.dev.CsiContratistas.infrastructure.Controller;

import com.dev.CsiContratistas.application.Services.TipoMaterialServicio;
import com.dev.CsiContratistas.domain.model.Tipo_material;
import com.dev.CsiContratistas.infrastructure.dto.TipoMaterial.CrearTipoMaterialDTO;
import com.dev.CsiContratistas.infrastructure.dto.TipoMaterial.EliminarTipoMaterialDTO;
import com.dev.CsiContratistas.infrastructure.dto.TipoMaterial.LeerTipoMaterialDTO;
import com.dev.CsiContratistas.infrastructure.dto.TipoMaterial.ModificarTipoMaterialDTO;
import com.dev.CsiContratistas.infrastructure.mapper.TipoMaterialMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/tipos_material")
@CrossOrigin ("http://localhost:3000/")
public class TipoMaterialControlador {

    private final TipoMaterialServicio tipoMaterialServicio;

    @PostMapping
    public ResponseEntity<?> crearTipoMaterial(@Valid  @RequestBody CrearTipoMaterialDTO crearTipoMaterialDTO) {
        if(tipoMaterialServicio.tipoMaterialExiste(crearTipoMaterialDTO.getNombre())){
            return new ResponseEntity<>("El tipo de material ya se encuentra registrado",HttpStatus.BAD_REQUEST);
        }
        Tipo_material tipoMaterialDomain = TipoMaterialMapper.crearDtoDomain(crearTipoMaterialDTO);

        Tipo_material crearTipoMaterial = tipoMaterialServicio.crearObjeto(tipoMaterialDomain);

        return new ResponseEntity<>(crearTipoMaterialDTO,HttpStatus.CREATED);

    }

    @GetMapping("/{id}")
    public ResponseEntity<LeerTipoMaterialDTO> LeerTipoMaterial(@PathVariable Integer id){

        Optional<Tipo_material> tipoMaterial = tipoMaterialServicio.leerObjeto(id);
        LeerTipoMaterialDTO leerTipoMaterialDTO=TipoMaterialMapper.leerDTOtipoMaterial(tipoMaterial.orElse(null));
        return new ResponseEntity<>(leerTipoMaterialDTO,HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> modificarTipoMaterial(@PathVariable Integer id, @RequestBody ModificarTipoMaterialDTO modificarTipoMaterialDTO) {

        Tipo_material tipoMaterial = TipoMaterialMapper.actualizarDtoDomain(modificarTipoMaterialDTO);

        Optional<Tipo_material> modificarTipoMaterial = tipoMaterialServicio.modificarObjeto(id,tipoMaterial);

        return new ResponseEntity<>("Modificacion correcta ",HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarTipoMaterial(@PathVariable Integer id){

        try {
            tipoMaterialServicio.eliminarTipoMaterialLogicamente(id);
            return new ResponseEntity<>("Tipo de material eliminado lógicamente", HttpStatus.ACCEPTED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping
    public ResponseEntity<List<LeerTipoMaterialDTO>> mostrarTiposMateriales(){

        List<Tipo_material> tipoMaterials= tipoMaterialServicio.leerObjetos();

        List<LeerTipoMaterialDTO> respuesta=tipoMaterials.stream()
                .map(TipoMaterialMapper::leerDTOtipoMaterial)
                .toList();

        return new ResponseEntity<>(respuesta,HttpStatus.OK);

    }

}