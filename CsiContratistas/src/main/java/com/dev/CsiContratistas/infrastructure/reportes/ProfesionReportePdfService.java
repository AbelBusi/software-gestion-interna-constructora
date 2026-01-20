package com.dev.CsiContratistas.infrastructure.reportes;

import org.springframework.stereotype.Service;
import com.dev.CsiContratistas.domain.model.Profesiones;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;
import org.knowm.xchart.style.CategoryStyler;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ProfesionReportePdfService {

    private static final com.lowagie.text.Font TITLE_FONT = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, Color.DARK_GRAY);
    private static final com.lowagie.text.Font SUBTITLE_FONT = FontFactory.getFont(FontFactory.HELVETICA, 12, Color.GRAY);
    private static final com.lowagie.text.Font HEADER_FONT = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, Color.WHITE);
    private static final com.lowagie.text.Font KPI_TITLE_FONT = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, Color.DARK_GRAY);
    private static final com.lowagie.text.Font KPI_VALUE_FONT = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, Color.BLUE);
    private static final Color HEADER_BG_COLOR = new Color(51, 102, 153);
    private static final Color KPI_BORDER_COLOR = new Color(220, 220, 220);

    public byte[] generarReporte(List<Profesiones> profesiones) throws DocumentException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4); // A4 normal, no rotado, ya que la tabla es más pequeña

        try {
            PdfWriter.getInstance(document, baos);
            document.open();

            agregarEncabezado(document);
            agregarKPIs(document, profesiones);
            agregarTablaProfesiones(document, profesiones);
            agregarGraficos(document, profesiones);

            document.close();
            return baos.toByteArray();
        } catch (Exception e) {
            throw new DocumentException("Error al generar el reporte PDF de Profesiones: " + e.getMessage());
        } finally {
            if (document.isOpen()) {
                document.close();
            }
            try {
                baos.close();
            } catch (Exception ex) {
                // Ignorar excepción al cerrar, ya está manejada por el try-catch principal
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
            // Manejar si el logo no se encuentra, pero no detener el reporte
            System.err.println("Advertencia: No se pudo cargar el logo del reporte para Profesiones. " + e.getMessage());
        }

        document.add(Chunk.NEWLINE);
        Paragraph empresa = new Paragraph("CSI CONTRATISTAS",
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, Color.BLUE));
        empresa.setAlignment(Element.ALIGN_CENTER);
        document.add(empresa);

        Paragraph titulo = new Paragraph("Reporte General de Profesiones", TITLE_FONT);
        titulo.setAlignment(Element.ALIGN_CENTER);
        document.add(titulo);

        Paragraph fecha = new Paragraph(
                "Generado el: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")),
                SUBTITLE_FONT);
        fecha.setAlignment(Element.ALIGN_CENTER);
        document.add(fecha);
        document.add(Chunk.NEWLINE);
    }

    private void agregarKPIs(Document document, List<Profesiones> profesiones) throws DocumentException {
        int total = profesiones.size();
        long activas = profesiones.stream().filter(Profesiones::getEstado).count();
        long inactivas = total - activas;

        PdfPTable kpiTable = new PdfPTable(3); // 3 KPIs para profesiones
        kpiTable.setWidthPercentage(100);
        kpiTable.setSpacingBefore(10f);
        kpiTable.setSpacingAfter(10f);

        kpiTable.addCell(crearCeldaKPI("Total Profesiones", String.valueOf(total)));
        kpiTable.addCell(crearCeldaKPI("Activas", String.valueOf(activas)));
        kpiTable.addCell(crearCeldaKPI("Inactivas", String.valueOf(inactivas)));

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

    private void agregarTablaProfesiones(Document document, List<Profesiones> profesiones) throws DocumentException {
        PdfPTable table = new PdfPTable(4); // ID, Nombre, Descripción, Estado
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);
        table.setWidths(new float[]{1, 3, 4, 1.5f}); // Ajusta anchos de columnas

        String[] headers = {"ID", "Nombre", "Descripción", "Estado"};
        for (String header : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(header, HEADER_FONT));
            cell.setBackgroundColor(HEADER_BG_COLOR);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPadding(8);
            table.addCell(cell);
        }

        for (Profesiones p : profesiones) {
            table.addCell(crearCeldaDatos(String.valueOf(p.getId_profesion())));
            table.addCell(crearCeldaDatos(p.getNombre()));
            table.addCell(crearCeldaDatos(p.getDescripcion()));

            PdfPCell estadoCell = crearCeldaDatos(p.getEstado() ? "Activo" : "Inactivo");
            estadoCell.setBackgroundColor(p.getEstado()
                    ? new Color(220, 255, 220) // Verde claro para activo
                    : new Color(255, 220, 220)); // Rojo claro para inactivo
            table.addCell(estadoCell);
        }

        document.add(table);
    }

    private PdfPCell crearCeldaDatos(String contenido) {
        PdfPCell cell = new PdfPCell(new Phrase(contenido));
        cell.setPadding(5);
        cell.setBorderColor(KPI_BORDER_COLOR);
        return cell;
    }

    private void agregarGraficos(Document document, List<Profesiones> profesiones) throws DocumentException {
        document.newPage();
        document.add(new Paragraph("Gráficos de Profesiones", TITLE_FONT));
        document.add(Chunk.NEWLINE);

        try {
            // Gráfico de distribución por estado (Activo/Inactivo)
            Map<String, Long> conteoEstado = profesiones.stream()
                    .collect(Collectors.groupingBy(p -> p.getEstado() ? "Activas" : "Inactivas", Collectors.counting()));

            com.lowagie.text.Image chartEstado = generarGraficoPie("Distribución de Profesiones por Estado", conteoEstado);
            chartEstado.setAlignment(com.lowagie.text.Image.ALIGN_CENTER);
            document.add(chartEstado);

        } catch (Exception e) {
            throw new DocumentException("Error al generar gráficos de profesiones: " + e.getMessage());
        }
    }

    // Adaptado para recibir un Map<String, Long> para casos de uso más genéricos
    private com.lowagie.text.Image generarGraficoPie(String titulo, Map<String, Long> data) throws Exception {
        PieChart chart = new PieChartBuilder()
                .width(600)
                .height(400)
                .title(titulo)
                .theme(Styler.ChartTheme.XChart)
                .build();

        chart.getStyler()
                .setLegendVisible(true)
                .setPlotContentSize(0.7)
                .setChartTitleVisible(true)
                .setChartTitleFont(new java.awt.Font(java.awt.Font.SANS_SERIF, java.awt.Font.BOLD, 14))
                .setLegendFont(new java.awt.Font(java.awt.Font.SANS_SERIF, java.awt.Font.PLAIN, 10))
                .setLegendFont(new java.awt.Font(java.awt.Font.SANS_SERIF, java.awt.Font.PLAIN, 10));

        // Asignar colores específicos para Activas/Inactivas
        if (data.containsKey("Activas") && data.containsKey("Inactivas")) {
            chart.addSeries("Activas", data.get("Activas")).setFillColor(new Color(60, 179, 113)); // MediumSeaGreen
            chart.addSeries("Inactivas", data.get("Inactivas")).setFillColor(new Color(255, 99, 71)); // Tomato
        } else {
            data.forEach((key, value) -> chart.addSeries(key, value));
        }

        BufferedImage img = BitmapEncoder.getBufferedImage(chart);
        return com.lowagie.text.Image.getInstance(img, null);
    }

    private com.lowagie.text.Image generarGraficoBarras(String titulo, List<String> categorias) throws Exception {
        Map<String, Long> conteo = categorias.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        List<Map.Entry<String, Long>> sortedEntries = new ArrayList<>(conteo.entrySet());
        sortedEntries.sort(Map.Entry.<String, Long>comparingByValue().reversed());

        List<String> keys = sortedEntries.stream().map(entry -> entry.getKey()).collect(Collectors.toList());
        List<Long> values = sortedEntries.stream().map(entry -> entry.getValue()).collect(Collectors.toList());

        CategoryChart chart = new CategoryChartBuilder()
                .width(800)
                .height(500)
                .title(titulo)
                .xAxisTitle("Categoría")
                .yAxisTitle("Cantidad")
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

        chart.addSeries(titulo, keys, values);

        BufferedImage img = BitmapEncoder.getBufferedImage(chart);
        return com.lowagie.text.Image.getInstance(img, null);
    }
}