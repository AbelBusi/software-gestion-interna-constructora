package com.dev.CsiContratistas.infrastructure.reportes;

import org.springframework.stereotype.Service;
import com.dev.CsiContratistas.domain.model.Rama;
import com.dev.CsiContratistas.domain.model.Profesiones; // Necesario para la profesión asociada
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
public class RamaReportePdfService {

    private static final com.lowagie.text.Font TITLE_FONT = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, Color.DARK_GRAY);
    private static final com.lowagie.text.Font SUBTITLE_FONT = FontFactory.getFont(FontFactory.HELVETICA, 12, Color.GRAY);
    private static final com.lowagie.text.Font HEADER_FONT = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, Color.WHITE);
    private static final com.lowagie.text.Font KPI_TITLE_FONT = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, Color.DARK_GRAY);
    private static final com.lowagie.text.Font KPI_VALUE_FONT = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, Color.BLUE);
    private static final Color HEADER_BG_COLOR = new Color(51, 102, 153);
    private static final Color KPI_BORDER_COLOR = new Color(220, 220, 220);

    public byte[] generarReporte(List<Rama> ramas) throws DocumentException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4.rotate()); // Rotado, ya que la tabla de ramas puede ser más ancha

        try {
            PdfWriter.getInstance(document, baos);
            document.open();

            agregarEncabezado(document);
            agregarKPIs(document, ramas);
            agregarTablaRamas(document, ramas);
            agregarGraficos(document, ramas);

            document.close();
            return baos.toByteArray();
        } catch (Exception e) {
            throw new DocumentException("Error al generar el reporte PDF de Ramas: " + e.getMessage());
        } finally {
            if (document.isOpen()) {
                document.close();
            }
            try {
                baos.close();
            } catch (Exception ex) {
                // Ignorar excepción al cerrar
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
            System.err.println("Advertencia: No se pudo cargar el logo del reporte para Ramas. " + e.getMessage());
        }

        document.add(Chunk.NEWLINE);
        Paragraph empresa = new Paragraph("CSI CONTRATISTAS",
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, Color.BLUE));
        empresa.setAlignment(Element.ALIGN_CENTER);
        document.add(empresa);

        Paragraph titulo = new Paragraph("Reporte General de Ramas", TITLE_FONT);
        titulo.setAlignment(Element.ALIGN_CENTER);
        document.add(titulo);

        Paragraph fecha = new Paragraph(
                "Generado el: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")),
                SUBTITLE_FONT);
        fecha.setAlignment(Element.ALIGN_CENTER);
        document.add(fecha);
        document.add(Chunk.NEWLINE);
    }

    private void agregarKPIs(Document document, List<Rama> ramas) throws DocumentException {
        int total = ramas.size();
        long activas = ramas.stream().filter(Rama::getEstado).count();
        long inactivas = total - activas;

        String profesionConMasRamas = ramas.stream()
                .filter(r -> r.getId_profesion() != null && r.getId_profesion().getNombre() != null)
                .collect(Collectors.groupingBy(r -> r.getId_profesion().getNombre(), Collectors.counting()))
                .entrySet().stream().max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey).orElse("N/A");

        PdfPTable kpiTable = new PdfPTable(4); // Total, Activas, Inactivas, Profesion Principal
        kpiTable.setWidthPercentage(100);
        kpiTable.setSpacingBefore(10f);
        kpiTable.setSpacingAfter(10f);

        kpiTable.addCell(crearCeldaKPI("Total Ramas", String.valueOf(total)));
        kpiTable.addCell(crearCeldaKPI("Activas", String.valueOf(activas)));
        kpiTable.addCell(crearCeldaKPI("Inactivas", String.valueOf(inactivas)));
        kpiTable.addCell(crearCeldaKPI("Profesión con más ramas", profesionConMasRamas));

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

    private void agregarTablaRamas(Document document, List<Rama> ramas) throws DocumentException {
        PdfPTable table = new PdfPTable(5); // ID, Nombre, Descripción, Profesión, Estado
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);
        table.setWidths(new float[]{1, 2.5f, 3.5f, 2.5f, 1.5f}); // Ajusta anchos de columnas

        String[] headers = {"ID", "Nombre", "Descripción", "Profesión Asociada", "Estado"};
        for (String header : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(header, HEADER_FONT));
            cell.setBackgroundColor(HEADER_BG_COLOR);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPadding(8);
            table.addCell(cell);
        }

        for (Rama r : ramas) {
            table.addCell(crearCeldaDatos(String.valueOf(r.getId_rama())));
            table.addCell(crearCeldaDatos(r.getNombre()));
            table.addCell(crearCeldaDatos(r.getDescripcion()));

            // Manejo de profesión asociada nula
            String profesionNombre = Optional.ofNullable(r.getId_profesion())
                    .map(Profesiones::getNombre)
                    .orElse("Sin Profesión");
            table.addCell(crearCeldaDatos(profesionNombre));

            PdfPCell estadoCell = crearCeldaDatos(r.getEstado() ? "Activo" : "Inactivo");
            estadoCell.setBackgroundColor(r.getEstado()
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

    private void agregarGraficos(Document document, List<Rama> ramas) throws DocumentException {
        document.newPage();
        document.add(new Paragraph("Gráficos de Ramas", TITLE_FONT));
        document.add(Chunk.NEWLINE);

        try {
            // Gráfico de distribución por estado (Activo/Inactivo)
            Map<String, Long> conteoEstado = ramas.stream()
                    .collect(Collectors.groupingBy(r -> r.getEstado() ? "Activas" : "Inactivas", Collectors.counting()));

            com.lowagie.text.Image chartEstado = generarGraficoPie("Distribución de Ramas por Estado", conteoEstado);
            chartEstado.setAlignment(com.lowagie.text.Image.ALIGN_CENTER);
            document.add(chartEstado);

            // Gráfico de distribución por Profesión Asociada (si hay datos)
            Map<String, Long> conteoProfesion = ramas.stream()
                    .filter(r -> r.getId_profesion() != null && r.getId_profesion().getNombre() != null)
                    .collect(Collectors.groupingBy(r -> r.getId_profesion().getNombre(), Collectors.counting()));

            if (!conteoProfesion.isEmpty()) {
                com.lowagie.text.Image chartProfesion = generarGraficoBarras("Ramas por Profesión", conteoProfesion);
                chartProfesion.setAlignment(com.lowagie.text.Image.ALIGN_CENTER);
                document.add(chartProfesion);
            }

        } catch (Exception e) {
            throw new DocumentException("Error al generar gráficos de ramas: " + e.getMessage());
        }
    }

    // Método genérico para gráficos de Pie (circular)
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

        // Asignar colores específicos para Activas/Inactivas si aplica
        if (data.containsKey("Activas") && data.containsKey("Inactivas")) {
            chart.addSeries("Activas", data.get("Activas")).setFillColor(new Color(60, 179, 113)); // MediumSeaGreen
            chart.addSeries("Inactivas", data.get("Inactivas")).setFillColor(new Color(255, 99, 71)); // Tomato
        } else {
            data.forEach((key, value) -> chart.addSeries(key, value));
        }

        BufferedImage img = BitmapEncoder.getBufferedImage(chart);
        return com.lowagie.text.Image.getInstance(img, null);
    }

    // Método genérico para gráficos de Barras
    private com.lowagie.text.Image generarGraficoBarras(String titulo, Map<String, Long> data) throws Exception {
        List<Map.Entry<String, Long>> sortedEntries = new ArrayList<>(data.entrySet());
        sortedEntries.sort(Map.Entry.<String, Long>comparingByValue().reversed()); // Ordenar para mejor visualización

        List<String> keys = sortedEntries.stream().map(Map.Entry::getKey).collect(Collectors.toList());
        List<Long> values = sortedEntries.stream().map(Map.Entry::getValue).collect(Collectors.toList());

        CategoryChart chart = new CategoryChartBuilder()
                .width(800)
                .height(500)
                .title(titulo)
                .xAxisTitle("Profesión")
                .yAxisTitle("Cantidad de Ramas")
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
