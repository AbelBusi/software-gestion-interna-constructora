package com.dev.CsiContratistas.infrastructure.Controller;

import com.dev.CsiContratistas.application.Services.FormaTerrenoServicio;
import com.dev.CsiContratistas.domain.model.Forma_terreno;
import com.dev.CsiContratistas.domain.model.Tipo_suelo;
import com.dev.CsiContratistas.infrastructure.dto.FormaTerreno.CrearFormaTerrenoDTO;
import com.dev.CsiContratistas.infrastructure.dto.FormaTerreno.EliminarFormaTerrenoDTO;
import com.dev.CsiContratistas.infrastructure.dto.FormaTerreno.LeerFormaTerrenoDTO;
import com.dev.CsiContratistas.infrastructure.dto.FormaTerreno.ModificarFormaTerrenoDTO;
import com.dev.CsiContratistas.infrastructure.dto.TipoSuelo.CrearTipoSueloDTO;
import com.dev.CsiContratistas.infrastructure.dto.TipoSuelo.EliminarTipoSueloDTO;
import com.dev.CsiContratistas.infrastructure.dto.TipoSuelo.LeerTipoSueloDTO;
import com.dev.CsiContratistas.infrastructure.dto.TipoSuelo.ModificarTipoSueloDTO;
import com.dev.CsiContratistas.infrastructure.mapper.FormaTerrenoMapper;
import com.dev.CsiContratistas.infrastructure.mapper.TipoSueloMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/formas_terrenos")
@CrossOrigin ("http://localhost:3000/")
public class FormaTerrenoControlador {

    private final FormaTerrenoServicio formaTerrenoServicio;

    @PostMapping
    public ResponseEntity<CrearFormaTerrenoDTO> crearFormaTerreno(@RequestBody CrearFormaTerrenoDTO crearFormaTerrenoDTO) {

        Forma_terreno formaTerreno = FormaTerrenoMapper.crearDtoDomain(crearFormaTerrenoDTO);

        Forma_terreno crearFormaTerreno = formaTerrenoServicio.crearObjeto(formaTerreno);

        return new ResponseEntity<>(crearFormaTerrenoDTO,HttpStatus.CREATED);

    }

    @GetMapping("/{id}")
    public ResponseEntity<LeerFormaTerrenoDTO> LeerFormaTerreno(@PathVariable Integer id){

        Optional<Forma_terreno> formaTerreno = formaTerrenoServicio.leerObjeto(id);
        LeerFormaTerrenoDTO leerFormaTerrenoDTO=FormaTerrenoMapper.leerDTOFormaTerreno(formaTerreno.orElse(null));
        return new ResponseEntity<>(leerFormaTerrenoDTO,HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ModificarFormaTerrenoDTO> modificarFormaTerreno(@PathVariable Integer id, @RequestBody ModificarFormaTerrenoDTO modificarFormaTerrenoDTO) {

        Forma_terreno formaTerreno = FormaTerrenoMapper.actualizarDtoDomain(modificarFormaTerrenoDTO);

        Optional<Forma_terreno> modificarFormaTerreno = formaTerrenoServicio.modificarObjeto(id,formaTerreno);

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EliminarFormaTerrenoDTO> eliminarFormaTerreno(@PathVariable Integer id){

        formaTerrenoServicio.eliminarObjeto(id);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);

    }

    @GetMapping
    public ResponseEntity<List<LeerFormaTerrenoDTO>> mostrarFormaTerrenos(){

        List<Forma_terreno> formaTerrenos= formaTerrenoServicio.leerObjetos();

        List<LeerFormaTerrenoDTO> respuesta=formaTerrenos.stream()
                .map(FormaTerrenoMapper::leerDTOFormaTerreno)
                .toList();

        return new ResponseEntity<>(respuesta,HttpStatus.OK);

    }

}