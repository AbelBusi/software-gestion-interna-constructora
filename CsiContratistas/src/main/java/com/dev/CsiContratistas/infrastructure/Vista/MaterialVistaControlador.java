package com.dev.CsiContratistas.infrastructure.Vista;

import com.dev.CsiContratistas.domain.model.Material;
import com.dev.CsiContratistas.infrastructure.reportes.MaterialReporteExcelService;
import com.dev.CsiContratistas.infrastructure.reportes.MaterialReportePdfService;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.dev.CsiContratistas.application.Services.MaterialServicio;
import com.dev.CsiContratistas.application.Services.TipoMaterialServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("administrador/materiales")
public class MaterialVistaControlador {

    private final MaterialServicio materialServicio;
    private final TipoMaterialServicio tipoMaterialServicio;
    private final MaterialReportePdfService reportePdfService;
    private final MaterialReporteExcelService reporteExcelService;

    @GetMapping("")
    public String listarMateriales(Model model, @RequestParam(defaultValue = "0") int page) {
        int pageSize = 15;
        Page<Material> materialesPage = materialServicio.leerObjetosPaginados(PageRequest.of(page, pageSize));
        model.addAttribute("materialesPage", materialesPage);
        model.addAttribute("tiposMaterial", tipoMaterialServicio.leerObjetos().stream());
        model.addAttribute("tiposMaterialObj", tipoMaterialServicio.leerObjetos().stream()
            .filter(tipo -> tipo.getEstado() != null && tipo.getEstado())
            .toList());

    
        List<Material> materiales = materialesPage.getContent();
        int totalMateriales = (int) materialesPage.getTotalElements();
        int stockTotal = materiales.stream().mapToInt(m -> m.getStock_actual() != null ? m.getStock_actual() : 0).sum();
        String materialMayorStock = materiales.stream().max(Comparator.comparingInt(m -> m.getStock_actual() != null ? m.getStock_actual() : 0))
            .map(Material::getNombre).orElse("-");
        String materialMenorStock = materiales.stream().min(Comparator.comparingInt(m -> m.getStock_actual() != null ? m.getStock_actual() : Integer.MAX_VALUE))
            .map(Material::getNombre).orElse("-");
        double promedioPrecio = materiales.stream()
            .mapToDouble(m -> m.getPrecio_unitario() != null ? m.getPrecio_unitario().doubleValue() : 0)
            .average()
            .orElse(0);
        double valorInventario = materiales.stream()
            .map(m -> {
                Integer stock = m.getStock_actual() != null ? m.getStock_actual() : 0;
                BigDecimal precio = m.getPrecio_unitario() != null ? m.getPrecio_unitario() : BigDecimal.ZERO;
                return precio.multiply(BigDecimal.valueOf(stock));
            })
            .reduce(BigDecimal.ZERO, BigDecimal::add)
            .doubleValue();

        model.addAttribute("totalMateriales", totalMateriales);
        model.addAttribute("stockTotal", stockTotal);
        model.addAttribute("materialMayorStock", materialMayorStock);
        model.addAttribute("materialMenorStock", materialMenorStock);
        model.addAttribute("promedioPrecio", String.format("%.2f", promedioPrecio));
        model.addAttribute("valorInventario", String.format("%.2f", valorInventario));

        return "materiales/materiales";
    }

    @PostMapping("/guardar")
    public String guardarMaterial(@ModelAttribute Material material , RedirectAttributes redirectAttributes) {
        if (materialServicio.materialExiste(material.getNombre())) {
            redirectAttributes.addFlashAttribute("error", "El material ya existe.");
            return "redirect:/administrador/materiales";
        }
        materialServicio.crearObjeto(material);
        redirectAttributes.addFlashAttribute("success", "Material creado correctamente.");
        return "redirect:/administrador/materiales";
    }

    @PostMapping("/editar")
    public String editarMaterial(@ModelAttribute Material material, RedirectAttributes redirectAttributes) {
        materialServicio.modificarObjeto(material.getId_material(), material);
        redirectAttributes.addFlashAttribute("success", "Material editado correctamente.");
        return "redirect:/administrador/materiales";
    }

    @PostMapping("/eliminar")
    public String eliminarMaterial(@RequestParam("id_material") Integer id, RedirectAttributes redirectAttributes) {
        materialServicio.eliminarObjeto(id);
        redirectAttributes.addFlashAttribute("success", "Material eliminado correctamente.");   
        return "redirect:/administrador/materiales";
    }
    @GetMapping("/reporte/pdf")
    public void generarReportePdf(HttpServletResponse response) throws IOException {
        try {
            List<Material> materiales = materialServicio.leerObjetos();
            
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=reporte_materiales.pdf");
            
            byte[] pdfBytes = reportePdfService.generarReporte(materiales);
            response.getOutputStream().write(pdfBytes);
            response.getOutputStream().flush();
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
                "Error al generar el reporte: " + e.getMessage());
        }
    }
    @GetMapping("/reporte/excel")
    public void generarReporteExcel(HttpServletResponse response) throws IOException {
        try {
            List<Material> materiales = materialServicio.leerObjetos();

            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=reporte_materiales.xlsx");

            byte[] excelBytes = reporteExcelService.generarReporte(materiales);
            response.getOutputStream().write(excelBytes);
            response.getOutputStream().flush();
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Error al generar el reporte Excel: " + e.getMessage());
        }
    }
}
