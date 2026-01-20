package com.dev.CsiContratistas.infrastructure.Controller;

import com.dev.CsiContratistas.application.Services.TipoFinanciamientoServicio;
import com.dev.CsiContratistas.domain.model.Tipo_financiamiento;
import com.dev.CsiContratistas.infrastructure.dto.TipoConstruccion.LeerTipoConstruccionDTO;
import com.dev.CsiContratistas.infrastructure.dto.TipoFinanciamiento.CrearTipoFinanciamientoDTO;
import com.dev.CsiContratistas.infrastructure.dto.TipoFinanciamiento.EliminarTipoFinanciamientoDTO;
import com.dev.CsiContratistas.infrastructure.dto.TipoFinanciamiento.LeerTipoFinanciamientoDTO;
import com.dev.CsiContratistas.infrastructure.dto.TipoFinanciamiento.ModificarTipoFinanciamientoDTO;
import com.dev.CsiContratistas.infrastructure.mapper.TipoFinanciamientoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/tipos_financiamientos")
@CrossOrigin ("http://localhost:3000/")
public class TipoFinanciamientoControlador {

    private final TipoFinanciamientoServicio tipoFinanciamientoServicio;

    @PostMapping
    public ResponseEntity<CrearTipoFinanciamientoDTO> crearTipoFinanciamiento(@RequestBody CrearTipoFinanciamientoDTO crearTipoFinanciamientoDTO) {

        Tipo_financiamiento tipoFinanciamientoDomain = TipoFinanciamientoMapper.crearDtoDomain(crearTipoFinanciamientoDTO);

        Tipo_financiamiento crearTipoFinanciamiento = tipoFinanciamientoServicio.crearObjeto(tipoFinanciamientoDomain);

        return new ResponseEntity<>(crearTipoFinanciamientoDTO,HttpStatus.CREATED);

    }

    @GetMapping("/{id}")
    public ResponseEntity<LeerTipoFinanciamientoDTO> LeerTipoFinanciamiento(@PathVariable Integer id){

        Optional<Tipo_financiamiento> tipoFinanciamiento = tipoFinanciamientoServicio.leerObjeto(id);
        LeerTipoFinanciamientoDTO leerTipoFinanciamientoDTO=TipoFinanciamientoMapper.leerDTOTipoFinanciamiento(tipoFinanciamiento.orElse(null));
        return new ResponseEntity<>(leerTipoFinanciamientoDTO,HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ModificarTipoFinanciamientoDTO> modificarTipoFinanciamiento(@PathVariable Integer id, @RequestBody ModificarTipoFinanciamientoDTO modificarTipoFinanciamientoDTO) {

        Tipo_financiamiento tipoFinanciamiento = TipoFinanciamientoMapper.actualizarDtoDomain(modificarTipoFinanciamientoDTO);

        Optional<Tipo_financiamiento> modificarTipoFinanciamiento = tipoFinanciamientoServicio.modificarObjeto(id,tipoFinanciamiento);

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EliminarTipoFinanciamientoDTO> eliminarTipoFinanciamiento(@PathVariable Integer id){

        tipoFinanciamientoServicio.eliminarObjeto(id);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);

    }

    @GetMapping
    public ResponseEntity<List<LeerTipoFinanciamientoDTO>> mostrarTiposFinanciamientos(){

        List<Tipo_financiamiento> tipoFinanciamientos= tipoFinanciamientoServicio.leerObjetos();

        List<LeerTipoFinanciamientoDTO> respuesta=tipoFinanciamientos.stream()
                .map(TipoFinanciamientoMapper::leerDTOTipoFinanciamiento)
                .toList();

        return new ResponseEntity<>(respuesta,HttpStatus.OK);

    }

}