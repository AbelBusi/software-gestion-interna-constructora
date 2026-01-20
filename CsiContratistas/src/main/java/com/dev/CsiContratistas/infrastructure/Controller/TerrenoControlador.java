package com.dev.CsiContratistas.infrastructure.Controller;

import com.dev.CsiContratistas.application.Services.TerrenoServicio;
import com.dev.CsiContratistas.domain.model.Forma_terreno;
import com.dev.CsiContratistas.domain.model.Terreno;
import com.dev.CsiContratistas.domain.model.Tipo_suelo;
import com.dev.CsiContratistas.infrastructure.dto.FormaTerreno.CrearFormaTerrenoDTO;
import com.dev.CsiContratistas.infrastructure.dto.FormaTerreno.EliminarFormaTerrenoDTO;
import com.dev.CsiContratistas.infrastructure.dto.FormaTerreno.LeerFormaTerrenoDTO;
import com.dev.CsiContratistas.infrastructure.dto.FormaTerreno.ModificarFormaTerrenoDTO;
import com.dev.CsiContratistas.infrastructure.dto.Terreno.CrearTerrenoDTO;
import com.dev.CsiContratistas.infrastructure.dto.Terreno.EliminarTerrenoDTO;
import com.dev.CsiContratistas.infrastructure.dto.Terreno.LeerTerrenoDTO;
import com.dev.CsiContratistas.infrastructure.dto.Terreno.ModificarTerrenoDTO;
import com.dev.CsiContratistas.infrastructure.mapper.FormaTerrenoMapper;
import com.dev.CsiContratistas.infrastructure.mapper.TerrenoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/terrenos")
@CrossOrigin ("http://localhost:3000/")
public class TerrenoControlador {

    private final TerrenoServicio terrenoServicio;

    @PostMapping
    public ResponseEntity<CrearTerrenoDTO> crearTerreno(@RequestBody CrearTerrenoDTO crearTerrenoDTO) {

        Tipo_suelo tipoSuelo = new Tipo_suelo();
        tipoSuelo.setId_tipo_suelo(crearTerrenoDTO.getId_tipo_suelo());

        Forma_terreno formaTerreno = new Forma_terreno();
        formaTerreno.setId_forma_terreno(crearTerrenoDTO.getId_forma_terreno());

        Terreno terreno = TerrenoMapper.crearDtoDomain(crearTerrenoDTO,tipoSuelo,formaTerreno);

        Terreno crearTerreno = terrenoServicio.crearObjeto(terreno);

        return new ResponseEntity<>(crearTerrenoDTO,HttpStatus.CREATED);

    }

    @GetMapping("/{id}")
    public ResponseEntity<LeerTerrenoDTO> LeerTerreno(@PathVariable Integer id){

        Optional<Terreno> terreno = terrenoServicio.leerObjeto(id);
        LeerTerrenoDTO leerTerrenoDTO=TerrenoMapper.leerDTOTerreno(terreno.orElse(null));
        return new ResponseEntity<>(leerTerrenoDTO,HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ModificarTerrenoDTO> modificarTerreno(@PathVariable Integer id, @RequestBody ModificarTerrenoDTO modificarTerrenoDTO) {

        Tipo_suelo tipoSuelo = new Tipo_suelo();
        tipoSuelo.setId_tipo_suelo(modificarTerrenoDTO.getId_tipo_suelo());

        Forma_terreno formaTerreno = new Forma_terreno();
        formaTerreno.setId_forma_terreno(modificarTerrenoDTO.getId_forma_terreno());

        Terreno terreno = TerrenoMapper.actualizarDtoDomain(modificarTerrenoDTO,tipoSuelo,formaTerreno);

        Optional<Terreno> modificarTerreno = terrenoServicio.modificarObjeto(id,terreno);

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EliminarTerrenoDTO> eliminarTerreno(@PathVariable Integer id){

        terrenoServicio.eliminarObjeto(id);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);

    }

    @GetMapping
    public ResponseEntity<List<LeerTerrenoDTO>> mostrarTerrenos(){

        List<Terreno> terrenos= terrenoServicio.leerObjetos();

        List<LeerTerrenoDTO> respuesta=terrenos.stream()
                .map(TerrenoMapper::leerDTOTerreno)
                .toList();

        return new ResponseEntity<>(respuesta,HttpStatus.OK);

    }

}