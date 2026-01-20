package com.dev.CsiContratistas.infrastructure.reportes;

import org.springframework.stereotype.Service;
import com.dev.CsiContratistas.domain.model.Material;
import com.dev.CsiContratistas.domain.model.Tipo_material;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;
import org.knowm.xchart.style.CategoryStyler;
import org.knowm.xchart.style.PieStyler;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.math.BigDecimal;

@Service
public class MaterialReportePdfService {

    private static final com.lowagie.text.Font TITLE_FONT = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, Color.DARK_GRAY);
    private static final com.lowagie.text.Font SUBTITLE_FONT = FontFactory.getFont(FontFactory.HELVETICA, 12, Color.GRAY);
    private static final com.lowagie.text.Font HEADER_FONT = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, Color.WHITE);
    private static final com.lowagie.text.Font KPI_TITLE_FONT = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, Color.DARK_GRAY);
    private static final com.lowagie.text.Font KPI_VALUE_FONT = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, Color.BLUE);
    private static final Color HEADER_BG_COLOR = new Color(51, 102, 153);
    private static final Color KPI_BORDER_COLOR = new Color(220, 220, 220);

    public byte[] generarReporte(List<Material> materiales) throws DocumentException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4.rotate());
        
        try {
            PdfWriter.getInstance(document, baos);
            document.open();

            agregarEncabezado(document);
            agregarKPIs(document, materiales);
            agregarTablaMateriales(document, materiales);
            agregarGraficos(document, materiales);

            document.close();
            return baos.toByteArray();
        } catch (Exception e) {
            throw new DocumentException("Error al generar el reporte PDF: " + e.getMessage());
        } finally {
            if (document.isOpen()) {
                document.close();
            }
            try {
                baos.close();
            } catch (Exception ex) {
            }
        }
    }

    private void agregarEncabezado(Document document) throws DocumentException {
        try {
            com.lowagie.text.Image logo = com.lowagie.text.Image.getInstance(getClass().getResource("/static/img/logo-report.png"));
            logo.scaleToFit(80, 80);
            logo.setAlignment(Element.ALIGN_CENTER);
            document.add(logo);
        } catch (Exception e) {
        }

        document.add(Chunk.NEWLINE);
        Paragraph empresa = new Paragraph("CSI CONTRATISTAS",
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, Color.BLUE));
        empresa.setAlignment(Element.ALIGN_CENTER);
        document.add(empresa);

        Paragraph titulo = new Paragraph("Reporte General de Materiales", TITLE_FONT);
        titulo.setAlignment(Element.ALIGN_CENTER);
        document.add(titulo);

        Paragraph fecha = new Paragraph(
                "Generado el: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")),
                SUBTITLE_FONT);
        fecha.setAlignment(Element.ALIGN_CENTER);
        document.add(fecha);
        document.add(Chunk.NEWLINE);
    }

    private void agregarKPIs(Document document, List<Material> materiales) throws DocumentException {
        int total = materiales.size();
        int stockTotal = materiales.stream()
                .mapToInt(m -> m.getStock_actual() != null ? m.getStock_actual() : 0)
                .sum();
        
        String materialMayorStock = materiales.stream()
                .max(Comparator.comparingInt(m -> m.getStock_actual() != null ? m.getStock_actual() : 0))
                .map(Material::getNombre)
                .orElse("N/A");
        
        String materialMenorStock = materiales.stream()
                .min(Comparator.comparingInt(m -> m.getStock_actual() != null ? m.getStock_actual() : Integer.MAX_VALUE))
                .map(Material::getNombre)
                .orElse("N/A");
        
        double promedioPrecio = materiales.stream()
                .mapToDouble(m -> m.getPrecio_unitario() != null ? m.getPrecio_unitario().doubleValue() : 0)
                .average()
                .orElse(0);
        
        BigDecimal valorInventario = materiales.stream()
                .map(m -> {
                    Integer stock = m.getStock_actual() != null ? m.getStock_actual() : 0;
                    BigDecimal precio = m.getPrecio_unitario() != null ? m.getPrecio_unitario() : BigDecimal.ZERO;
                    return precio.multiply(BigDecimal.valueOf(stock));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        PdfPTable kpiTable = new PdfPTable(5);
        kpiTable.setWidthPercentage(100);
        kpiTable.setSpacingBefore(10f);
        kpiTable.setSpacingAfter(10f);
        
        kpiTable.addCell(crearCeldaKPI("Total Materiales", String.valueOf(total)));
        kpiTable.addCell(crearCeldaKPI("Stock Total", String.valueOf(stockTotal)));
        kpiTable.addCell(crearCeldaKPI("Material con Mayor Stock", materialMayorStock));
        kpiTable.addCell(crearCeldaKPI("Precio Promedio", String.format("S/ %.2f", promedioPrecio)));
        kpiTable.addCell(crearCeldaKPI("Valor de Inventario", String.format("S/ %.2f", valorInventario)));

        document.add(kpiTable);
        document.add(Chunk.NEWLINE);
    }

    private PdfPCell crearCeldaKPI(String titulo, String valor) {
        Paragraph p = new Paragraph();
        p.add(new Chunk(titulo + "\n", KPI_TITLE_FONT));
        p.add(new Chunk(valor, KPI_VALUE_FONT));
        
        PdfPCell cell = new PdfPCell(p);
        cell.setPadding(10);
        cell.setBorderColor(KPI_BORDER_COLOR);
        cell.setBackgroundColor(Color.WHITE);
        cell.setBorderWidth(1.5f);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        
        return cell;
    }

    private void agregarTablaMateriales(Document document, List<Material> materiales) throws DocumentException {
        PdfPTable table = new PdfPTable(6); 
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);
        table.setWidths(new float[]{1, 3, 2, 2, 2, 2});

        String[] headers = {"ID", "Nombre", "Tipo", "Stock Actual", "Precio Unitario", "Valor Total"};
        for (String header : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(header, HEADER_FONT));
            cell.setBackgroundColor(HEADER_BG_COLOR);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPadding(8);
            table.addCell(cell);
        }

        for (Material m : materiales) {
            table.addCell(crearCeldaDatos(String.valueOf(m.getId_material())));
            table.addCell(crearCeldaDatos(m.getNombre()));
            
            String tipoMaterial = m.getTipo_material() != null && m.getTipo_material().getNombre() != null 
                ? m.getTipo_material().getNombre() 
                : "-";
            table.addCell(crearCeldaDatos(tipoMaterial));
            
            table.addCell(crearCeldaDatos(String.valueOf(m.getStock_actual() != null ? m.getStock_actual() : 0)));
            
            table.addCell(crearCeldaDatos(m.getPrecio_unitario() != null 
                ? String.format("S/ %.2f", m.getPrecio_unitario()) 
                : "S/ 0.00"));
            
            BigDecimal valorTotal = BigDecimal.ZERO;
            if (m.getStock_actual() != null && m.getPrecio_unitario() != null) {
                valorTotal = m.getPrecio_unitario().multiply(BigDecimal.valueOf(m.getStock_actual()));
            }
            table.addCell(crearCeldaDatos(String.format("S/ %.2f", valorTotal)));
        }

        document.add(table);
    }

    private PdfPCell crearCeldaDatos(String contenido) {
        PdfPCell cell = new PdfPCell(new Phrase(contenido));
        cell.setPadding(5);
        cell.setBorderColor(KPI_BORDER_COLOR);
        return cell;
    }

    private void agregarGraficos(Document document, List<Material> materiales) throws DocumentException {
        document.newPage();
        document.add(new Paragraph("Gráficos", TITLE_FONT));
        document.add(Chunk.NEWLINE);

        try {
            com.lowagie.text.Image chartTipoMaterial = generarGraficoPie(
                "Distribución por Tipo de Material", 
                materiales.stream()
                    .map(m -> m.getTipo_material() != null ? m.getTipo_material().getNombre() : "Sin Tipo")
                    .collect(Collectors.toList())
            );
            chartTipoMaterial.setAlignment(com.lowagie.text.Image.ALIGN_CENTER);
            document.add(chartTipoMaterial);

            com.lowagie.text.Image chartTopMateriales = generarGraficoBarrasTopMateriales(
                "Top 10 Materiales con Mayor Stock", 
                materiales
            );
            chartTopMateriales.setAlignment(com.lowagie.text.Image.ALIGN_CENTER);
            document.add(chartTopMateriales);
        } catch (Exception e) {
            throw new DocumentException("Error al generar gráficos: " + e.getMessage());
        }
    }

    private com.lowagie.text.Image generarGraficoPie(String titulo, List<String> categorias) throws Exception {
        Map<String, Long> conteo = categorias.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        PieChart chart = new PieChartBuilder()
                .width(600)
                .height(400)
                .title(titulo)
                .theme(Styler.ChartTheme.XChart)
                .build();

        PieStyler styler = chart.getStyler();
        styler.setLegendVisible(true);
        styler.setPlotContentSize(0.7);
        styler.setChartTitleVisible(true);
        styler.setChartTitleFont(new java.awt.Font(java.awt.Font.SANS_SERIF, java.awt.Font.BOLD, 14));
        styler.setLegendFont(new java.awt.Font(java.awt.Font.SANS_SERIF, java.awt.Font.PLAIN, 10));
        styler.setDecimalPattern("#");

        conteo.forEach((key, value) -> chart.addSeries(key, value));

        BufferedImage img = BitmapEncoder.getBufferedImage(chart);
        return com.lowagie.text.Image.getInstance(img, null);
    }

    private com.lowagie.text.Image generarGraficoBarrasTopMateriales(String titulo, List<Material> materiales) throws Exception {
        List<Material> topMateriales = materiales.stream()
                .sorted(Comparator.comparingInt((Material m) -> m.getStock_actual() != null ? m.getStock_actual() : 0).reversed())
                .limit(10)
                .collect(Collectors.toList());

        List<String> nombres = topMateriales.stream()
                .map(Material::getNombre)
                .collect(Collectors.toList());
        
        List<Integer> stocks = topMateriales.stream()
                .map(m -> m.getStock_actual() != null ? m.getStock_actual() : 0)
                .collect(Collectors.toList());

        CategoryChart chart = new CategoryChartBuilder()
                .width(800)
                .height(500)
                .title(titulo)
                .xAxisTitle("Material")
                .yAxisTitle("Stock")
                .theme(Styler.ChartTheme.XChart)
                .build();

        CategoryStyler styler = chart.getStyler();
        styler.setLegendVisible(false);
        styler.setHasAnnotations(true);
        styler.setPlotGridVerticalLinesVisible(false);
        styler.setPlotGridHorizontalLinesVisible(true);
        styler.setAxisTitlesVisible(true);
        styler.setChartTitleFont(new java.awt.Font(java.awt.Font.SANS_SERIF, java.awt.Font.BOLD, 14));
        styler.setAxisTitleFont(new java.awt.Font(java.awt.Font.SANS_SERIF, java.awt.Font.PLAIN, 12));
        styler.setAxisTickLabelsFont(new java.awt.Font(java.awt.Font.SANS_SERIF, java.awt.Font.PLAIN, 10));
        styler.setXAxisLabelRotation(45);

        chart.addSeries("Stock", nombres, stocks);

        BufferedImage img = BitmapEncoder.getBufferedImage(chart);
        return com.lowagie.text.Image.getInstance(img, null);
    }
}