package com.dev.CsiContratistas.infrastructure.Vista;

import com.dev.CsiContratistas.domain.model.Tipo_material;
import com.dev.CsiContratistas.application.Services.TipoMaterialServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/administrador/tipos-material")
public class TipoMaterialVistaControlador {

    private final TipoMaterialServicio tipoMaterialServicio;

    @GetMapping("")
    public String listarTiposMaterial(Model model) {
        List<Tipo_material> tipos = tipoMaterialServicio.leerObjetos()
                .stream()
                .filter(Tipo_material::getEstado)
                .collect(Collectors.toList());
        model.addAttribute("tiposMaterial", tipos);
        return "materiales/tiposMaterial";
    }

    @PostMapping("/guardar")
    public String guardarTipoMaterial(@ModelAttribute Tipo_material tipoMaterial,
            RedirectAttributes redirectAttributes) {
        if (tipoMaterial.getNombre() == null || tipoMaterial.getNombre().trim().length() < 3) {
            redirectAttributes.addFlashAttribute("error", "El nombre debe tener al menos 3 caracteres.");
            return "redirect:/administrador/tipos-material";
        }
        if (tipoMaterialServicio.tipoMaterialExiste(tipoMaterial.getNombre())) {
            redirectAttributes.addFlashAttribute("error", "Ya existe un tipo de material con ese nombre.");
            return "redirect:/administrador/tipos-material";
        }
        tipoMaterial.setEstado(true);
        tipoMaterialServicio.crearObjeto(tipoMaterial);
        redirectAttributes.addFlashAttribute("success", "Tipo de material creado correctamente.");
        return "redirect:/administrador/tipos-material";
    }

    @PostMapping("/editar")
    public String editarTipoMaterial(@ModelAttribute Tipo_material tipoMaterial,
            RedirectAttributes redirectAttributes) {
        Optional<Tipo_material> optionalOriginal = tipoMaterialServicio.leerObjeto(tipoMaterial.getIdtipomaterial());

        if (!optionalOriginal.isPresent()) {
            redirectAttributes.addFlashAttribute("error", "El tipo de material no existe.");
            return "redirect:/administrador/tipos-material";
        }
        Tipo_material original = optionalOriginal.get();
        boolean sinCambios = original.getNombre().equals(tipoMaterial.getNombre())
                && original.getDescripcion().equals(tipoMaterial.getDescripcion())
                && original.getClasificacion().equals(tipoMaterial.getClasificacion());

        if (sinCambios) {
            redirectAttributes.addFlashAttribute("info", "No se realizaron cambios en el tipo de material.");
            return "redirect:/administrador/tipos-material";
        }
        if (!original.getNombre().equals(tipoMaterial.getNombre())
                && tipoMaterialServicio.tipoMaterialExiste(tipoMaterial.getNombre())) {
            redirectAttributes.addFlashAttribute("error", "Ya existe un tipo de material con ese nombre.");
            return "redirect:/administrador/tipos-material";
        }
        tipoMaterial.setEstado(true);
        tipoMaterialServicio.modificarObjeto(tipoMaterial.getIdtipomaterial(), tipoMaterial);
        redirectAttributes.addFlashAttribute("success", "Tipo de material editado correctamente.");
        return "redirect:/administrador/tipos-material";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarTipoMaterial(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            tipoMaterialServicio.eliminarTipoMaterialLogicamente(id);
            redirectAttributes.addFlashAttribute("success", "Tipo de material eliminado correctamente.");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/administrador/tipos-material";
    }
}
