package com.dev.CsiContratistas.infrastructure.Vista;

import com.dev.CsiContratistas.application.Services.*;
import com.dev.CsiContratistas.domain.model.*;
import com.dev.CsiContratistas.infrastructure.dto.Cliente.LeerClienteDTO;
import com.dev.CsiContratistas.infrastructure.dto.FormaTerreno.LeerFormaTerrenoDTO;
import com.dev.CsiContratistas.infrastructure.dto.ModeloObra.LeerModeloObraDTO;
import com.dev.CsiContratistas.infrastructure.dto.Obra.LeerObraDTO;
import com.dev.CsiContratistas.infrastructure.dto.Terreno.LeerTerrenoDTO;
import com.dev.CsiContratistas.infrastructure.dto.TipoConstruccion.LeerTipoConstruccionDTO;
import com.dev.CsiContratistas.infrastructure.dto.TipoObra.LeerTipoObraDTO;
import com.dev.CsiContratistas.infrastructure.dto.TipoSuelo.LeerTipoSueloDTO;
import com.dev.CsiContratistas.infrastructure.mapper.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/administrador/menu")
@RequiredArgsConstructor
public class VistaDashboardControlador {

    private final FormaTerrenoServicio formaTerrenoServicio;
    private final TipoSueloServicio tipoSueloServicio;
    private final TerrenoServicio terrenoServicio;
    private final TipoObraServicio tipoObraServicio;
    private final ModeloObraServicio modeloObraServicio;
    private final TipoConstruccionServicio tipoConstruccionServicio;
    private final ClienteServicio clienteServicio;
    private final ObraServicio obraServicio;
    private final UsuarioServicio usuarioServicio;
    private final MaterialServicio materialServicio;
    private final EmpleadoServicio empleadoServicio;

    @GetMapping
    public String mostrarMenuPrincipal(Model model) {
        List <Obra> todasLasObras= obraServicio.leerObjetos();
        List<Obra> obrasActivas = todasLasObras.stream()
                .filter(Obra::getEstado)
                .collect(Collectors.toList());

        model.addAttribute("obras", obrasActivas);
        long totalUsuarios = usuarioServicio.contarUsuariosActivos();
        long totalMateriales = materialServicio.contarMaterialesActivos();
        long totalEmpleados = empleadoServicio.contarEmpleadosActivos();
        model.addAttribute("totalUsuarios", totalUsuarios);
        model.addAttribute("totalMateriales", totalMateriales);
        model.addAttribute("totalEmpleados", totalEmpleados);
        model.addAttribute("ultimosEmpleados", empleadoServicio.leerUltimosEmpleados(3));
        model.addAttribute("UltimosMateriales", materialServicio.leerUltimos(3));
        model.addAttribute("nombre", "Administrador");
        return "dashboard/index";
    }

    @GetMapping("/obras")
    public String verObras(Model model) {
        model.addAttribute("nombre", "Administrador");

        //Obras
        List<Obra> listaObras = obraServicio.leerObjetos();
        List<LeerObraDTO> dtoObrass = listaObras.stream()
                .map(ObraMapper::leerDTOObra)
                .toList();
        model.addAttribute("obras", dtoObrass);

        //Formas de terreno:
        List<Forma_terreno> lista = formaTerrenoServicio.leerObjetos();
        List<LeerFormaTerrenoDTO> dtos = lista.stream()
                .map(FormaTerrenoMapper::leerDTOFormaTerreno)
                .toList();

        model.addAttribute("formasTerreno", dtos);

        //Clientes
        List<Cliente> listaClientes = clienteServicio.leerObjetos();
        List<LeerClienteDTO> dtoClientes = listaClientes.stream()
                .map(ClienteMapper::leerDTOClienteo)
                .toList();
        model.addAttribute("clientes", dtoClientes);

        //tiposSuelos
        List<Tipo_suelo> listaTipoSuelos = tipoSueloServicio.leerObjetos();
        List<LeerTipoSueloDTO> dtoTiposSuelos = listaTipoSuelos.stream()
                .map(TipoSueloMapper::leerDTOTipoSuelo)
                .toList();
        model.addAttribute("tiposSuelo", dtoTiposSuelos);

        //terrenos:

        List<Terreno> listaTerrenos = terrenoServicio.leerObjetos();
        List<LeerTerrenoDTO> dtoTerrenos = listaTerrenos.stream()
                .map(TerrenoMapper::leerDTOTerreno)
                .toList();



        model.addAttribute("terrenos", dtoTerrenos);

        //obras
        List<Modelo_obra> listaModelosObras = modeloObraServicio.leerObjetos();
        List<LeerModeloObraDTO> dtoModeloObras = listaModelosObras.stream()
                .map(ModeloObraMapper::leerDTOModeloObra)
                .toList();



        model.addAttribute("modelosObra", dtoModeloObras);


        //Tipo de obras
        List<Tipo_obra> listaTipoObras = tipoObraServicio.leerObjetos();
        List<LeerTipoObraDTO> dtoTipoObras = listaTipoObras.stream()
                .map(TipoObraMapper::leerDTOTipoPrueba)
                .toList();



        model.addAttribute("tiposObra", dtoTipoObras);

        //Tipo de obras
        List<Tipo_construccion> listaTipoConstruccion = tipoConstruccionServicio.leerObjetos();
        List<LeerTipoConstruccionDTO> dtoTipoConstruccion = listaTipoConstruccion.stream()
                .map(TipoConstruccionMapper::leerDTOTipoConstruccion)
                .toList();



        model.addAttribute("tiposConstruccion", dtoTipoConstruccion);

        return "dashboard/obras";
    }


}