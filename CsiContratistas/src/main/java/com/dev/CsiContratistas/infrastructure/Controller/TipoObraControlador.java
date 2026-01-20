package com.dev.CsiContratistas.infrastructure.Controller;

import com.dev.CsiContratistas.application.Services.TipoObraServicio;
import com.dev.CsiContratistas.domain.model.Tipo_material;
import com.dev.CsiContratistas.domain.model.Tipo_obra;
import com.dev.CsiContratistas.infrastructure.dto.TipoMaterial.CrearTipoMaterialDTO;
import com.dev.CsiContratistas.infrastructure.dto.TipoMaterial.EliminarTipoMaterialDTO;
import com.dev.CsiContratistas.infrastructure.dto.TipoMaterial.LeerTipoMaterialDTO;
import com.dev.CsiContratistas.infrastructure.dto.TipoMaterial.ModificarTipoMaterialDTO;
import com.dev.CsiContratistas.infrastructure.dto.TipoObra.CrearTipoObraDTO;
import com.dev.CsiContratistas.infrastructure.dto.TipoObra.EliminarTipoObraDTO;
import com.dev.CsiContratistas.infrastructure.dto.TipoObra.LeerTipoObraDTO;
import com.dev.CsiContratistas.infrastructure.dto.TipoObra.ModificarTipoObraDTO;
import com.dev.CsiContratistas.infrastructure.mapper.TipoMaterialMapper;
import com.dev.CsiContratistas.infrastructure.mapper.TipoObraMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/tipos_obras")
@CrossOrigin ("http://localhost:3000/")
public class TipoObraControlador {

    private final TipoObraServicio tipoObraServicio;

    @PostMapping
    public ResponseEntity<CrearTipoObraDTO> crearTipoObra(@RequestBody CrearTipoObraDTO crearTipoObraDTO) {

        Tipo_obra tipoObraDomain = TipoObraMapper.crearDtoDomain(crearTipoObraDTO);

        Tipo_obra crearTipoObra = tipoObraServicio.crearObjeto(tipoObraDomain);

        return new ResponseEntity<>(crearTipoObraDTO,HttpStatus.CREATED);

    }

    @GetMapping("/{id}")
    public ResponseEntity<LeerTipoObraDTO> LeerTipoObra(@PathVariable Integer id){

        Optional<Tipo_obra> tipoObra = tipoObraServicio.leerObjeto(id);
        LeerTipoObraDTO leerTipoObraDTO=TipoObraMapper.leerDTOTipoPrueba(tipoObra.orElse(null));
        return new ResponseEntity<>(leerTipoObraDTO,HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ModificarTipoObraDTO> modificarTipoObra(@PathVariable Integer id, @RequestBody ModificarTipoObraDTO modificarTipoObraDTO) {

        Tipo_obra tipo_obra = TipoObraMapper.actualizarDtoDomain(modificarTipoObraDTO);

        Optional<Tipo_obra> modificarTipoObra = tipoObraServicio.modificarObjeto(id,tipo_obra);

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EliminarTipoObraDTO> eliminarTipoObra(@PathVariable Integer id){

        tipoObraServicio.eliminarObjeto(id);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);

    }

    @GetMapping
    public ResponseEntity<List<LeerTipoObraDTO>> mostrarTiposObras(){

        List<Tipo_obra> tipoObras= tipoObraServicio.leerObjetos();

        List<LeerTipoObraDTO> respuesta=tipoObras.stream()
                .map(TipoObraMapper::leerDTOTipoPrueba)
                .toList();

        return new ResponseEntity<>(respuesta,HttpStatus.OK);

    }

}