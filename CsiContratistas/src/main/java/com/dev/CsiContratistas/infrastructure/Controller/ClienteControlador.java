package com.dev.CsiContratistas.infrastructure.Controller;

import com.dev.CsiContratistas.application.Services.ClienteServicio;
import com.dev.CsiContratistas.domain.model.Cliente;
import com.dev.CsiContratistas.infrastructure.dto.Cliente.CrearClienteDTO;
import com.dev.CsiContratistas.infrastructure.dto.Cliente.EliminarClienteDTO;
import com.dev.CsiContratistas.infrastructure.dto.Cliente.LeerClienteDTO;
import com.dev.CsiContratistas.infrastructure.dto.Cliente.ModificarClienteDTO;
import com.dev.CsiContratistas.infrastructure.dto.FormaTerreno.CrearFormaTerrenoDTO;
import com.dev.CsiContratistas.infrastructure.dto.FormaTerreno.EliminarFormaTerrenoDTO;
import com.dev.CsiContratistas.infrastructure.dto.FormaTerreno.LeerFormaTerrenoDTO;
import com.dev.CsiContratistas.infrastructure.dto.FormaTerreno.ModificarFormaTerrenoDTO;
import com.dev.CsiContratistas.infrastructure.mapper.ClienteMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/clientes")
@CrossOrigin ("http://localhost:3000/")
public class ClienteControlador {

    private final ClienteServicio clienteServicio;

    @PostMapping
    public ResponseEntity<CrearClienteDTO> crearFormaCliente(@RequestBody CrearClienteDTO crearClienteDTO) {

        Cliente clienteDomain = ClienteMapper.crearDtoDomain(crearClienteDTO);

        Cliente crearCliente = clienteServicio.crearObjeto(clienteDomain);

        return new ResponseEntity<>(crearClienteDTO,HttpStatus.CREATED);

    }

    @GetMapping("/{id}")
    public ResponseEntity<LeerClienteDTO> LeerCliente(@PathVariable Integer id){

        Optional<Cliente> cliente = clienteServicio.leerObjeto(id);
        LeerClienteDTO leerClienteDTO=ClienteMapper.leerDTOClienteo(cliente.orElse(null));
        return new ResponseEntity<>(leerClienteDTO,HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ModificarClienteDTO> modificarCliente(@PathVariable Integer id, @RequestBody ModificarClienteDTO modificarClienteDTO) {

        Cliente cliente = ClienteMapper.actualizarDtoDomain(modificarClienteDTO);

        Optional<Cliente> modificarCliente = clienteServicio.modificarObjeto(id,cliente);

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EliminarClienteDTO> eliminarCliente(@PathVariable Integer id){

        clienteServicio.eliminarObjeto(id);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);

    }

    @GetMapping
    public ResponseEntity<List<LeerClienteDTO>> mostrarClientes(){

        List<Cliente> clientes= clienteServicio.leerObjetos();

        List<LeerClienteDTO> respuesta=clientes.stream()
                .map(ClienteMapper::leerDTOClienteo)
                .toList();

        return new ResponseEntity<>(respuesta,HttpStatus.OK);

    }

}