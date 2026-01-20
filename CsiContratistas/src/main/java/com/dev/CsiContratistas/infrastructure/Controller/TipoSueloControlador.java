package com.dev.CsiContratistas.infrastructure.Controller;

import com.dev.CsiContratistas.application.Services.TipoSueloServicio;
import com.dev.CsiContratistas.domain.model.Tipo_obra;
import com.dev.CsiContratistas.domain.model.Tipo_suelo;
import com.dev.CsiContratistas.infrastructure.dto.TipoObra.EliminarTipoObraDTO;
import com.dev.CsiContratistas.infrastructure.dto.TipoObra.LeerTipoObraDTO;
import com.dev.CsiContratistas.infrastructure.dto.TipoObra.ModificarTipoObraDTO;
import com.dev.CsiContratistas.infrastructure.dto.TipoSuelo.CrearTipoSueloDTO;
import com.dev.CsiContratistas.infrastructure.dto.TipoSuelo.EliminarTipoSueloDTO;
import com.dev.CsiContratistas.infrastructure.dto.TipoSuelo.LeerTipoSueloDTO;
import com.dev.CsiContratistas.infrastructure.dto.TipoSuelo.ModificarTipoSueloDTO;
import com.dev.CsiContratistas.infrastructure.mapper.TipoObraMapper;
import com.dev.CsiContratistas.infrastructure.mapper.TipoSueloMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/tipos_suelos")
@CrossOrigin ("http://localhost:3000/")
public class TipoSueloControlador {

    private final TipoSueloServicio tipoSueloServicio;

    @PostMapping
    public ResponseEntity<CrearTipoSueloDTO> crearTipoSuelo(@RequestBody CrearTipoSueloDTO crearTipoSueloDTO) {

        Tipo_suelo tipoSueloDomain = TipoSueloMapper.crearDtoDomain(crearTipoSueloDTO);

        Tipo_suelo crearTipoSuelo =tipoSueloServicio.crearObjeto(tipoSueloDomain);

        return new ResponseEntity<>(crearTipoSueloDTO,HttpStatus.CREATED);

    }

    @GetMapping("/{id}")
    public ResponseEntity<LeerTipoSueloDTO> LeerTipoSuelo(@PathVariable Integer id){

        Optional<Tipo_suelo> tipoSuelo = tipoSueloServicio.leerObjeto(id);
        LeerTipoSueloDTO leerTipoSueloDTO=TipoSueloMapper.leerDTOTipoSuelo(tipoSuelo.orElse(null));
        return new ResponseEntity<>(leerTipoSueloDTO,HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ModificarTipoSueloDTO> modificarTipoSuelo(@PathVariable Integer id, @RequestBody ModificarTipoSueloDTO modificarTipoSueloDTO) {

        Tipo_suelo tiposuelo = TipoSueloMapper.actualizarDtoDomain(modificarTipoSueloDTO);

        Optional<Tipo_suelo> modificarTipoSuelo = tipoSueloServicio.modificarObjeto(id,tiposuelo);

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EliminarTipoSueloDTO> eliminarTipoObra(@PathVariable Integer id){

        tipoSueloServicio.eliminarObjeto(id);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);

    }

    @GetMapping
    public ResponseEntity<List<LeerTipoSueloDTO>> mostrarTiposObras(){

        List<Tipo_suelo> tipoObras= tipoSueloServicio.leerObjetos();

        List<LeerTipoSueloDTO> respuesta=tipoObras.stream()
                .map(TipoSueloMapper::leerDTOTipoSuelo)
                .toList();

        return new ResponseEntity<>(respuesta,HttpStatus.OK);

    }

}