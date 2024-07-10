package com.posicionCaja.posicionCaja.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.geom.Vector;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.EventType;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;
import com.itextpdf.kernel.pdf.canvas.parser.data.IEventData;
import com.itextpdf.kernel.pdf.canvas.parser.data.TextRenderInfo;
import com.itextpdf.kernel.pdf.canvas.parser.listener.LocationTextExtractionStrategy;
import com.posicionCaja.posicionCaja.model.datosRecibir;
import com.posicionCaja.posicionCaja.model.datosRetornar;

@RestController
@RequestMapping("/datosUbicacion")
public class transformarDatosUbicacion {
    private Vector lastEndPoint = null; 

    @PostMapping("/ubicacion")
    public datosRetornar ubicacion(@RequestBody datosRecibir datosR) {
        String base64Pdf = datosR.getDocpdf();
        String textBuscar = datosR.getTextoBuscar();
        String posicionTexto = datosR.getPosicionTexto();
        int padding = datosR.getPadding();
        int altura = datosR.getAltura();
        int anchura = datosR.getAnchura();

        datosRetornar resultado = new datosRetornar();
        try {
            byte[] pdfBytes = Base64.getDecoder().decode(base64Pdf);

            try (PdfReader pdfReader = new PdfReader(new ByteArrayInputStream(pdfBytes))) {
                PdfDocument pdfDocument = new PdfDocument(pdfReader);
                int numPages = pdfDocument.getNumberOfPages();

                System.out.println("Número de páginas: " + numPages);

                for (int pageNum = 1; pageNum <= numPages; pageNum++) {
                    final int currentPage = pageNum;

                    LocationTextExtractionStrategy strategy = new LocationTextExtractionStrategy() {
                        @Override
                        public void eventOccurred(IEventData data, EventType type) {
                            if (data instanceof TextRenderInfo) {
                                TextRenderInfo textRenderInfo = (TextRenderInfo) data;

                                Rectangle rect = textRenderInfo.getBaseline().getBoundingRectangle();
                                float[] coordinates = calcularCoordenadas(rect, posicionTexto, padding, altura,
                                        anchura);
                                resultado.setPaginaFirma(currentPage);
                                resultado.setCoordenadas(new int[] { (int) coordinates[0], (int) coordinates[1] });
                                resultado.setTamanyo(new int[] { anchura, altura });

                                System.out.println("Texto encontrado en la página " + currentPage);
                                System.out.println("Coordenadas: " + coordinates[0] + ", " + coordinates[1]);
                            }
                            super.eventOccurred(data, type);
                        }
                    };

                    String pageText = PdfTextExtractor.getTextFromPage(pdfDocument.getPage(pageNum));

                    // Buscar el texto
                    if (pageText.toLowerCase().contains(textBuscar.toLowerCase())) {
                        Rectangle rect = new Rectangle(0, 0);
                        float[] coordinates = calcularCoordenadas(rect, posicionTexto, padding, altura, anchura);
                        resultado.setPaginaFirma(currentPage);
                        resultado.setCoordenadas(new int[] { (int) coordinates[0], (int) coordinates[1] });
                        resultado.setTamanyo(new int[] { anchura, altura });

                        System.out.println("Texto encontrado en la página " + currentPage);
                        System.out.println("Coordenadas: " + coordinates[0] + ", " + coordinates[1]);
                    }

                    // Si encontramos el texto salir del bucle
                    if (resultado.getPaginaFirma() != 0) {
                        break;
                    }
                }

                pdfDocument.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return resultado;
    }

    private float[] calcularCoordenadas(Rectangle rect, String posicionTexto, int padding, int altura, int anchura) {
        float x = 0, y = 0;
        switch (posicionTexto.toUpperCase()) {
            case "LEFT":
                x = rect.getLeft() - anchura - padding;
                y = rect.getBottom();
                break;
            case "RIGHT":
                x = rect.getRight() + padding;
                y = rect.getBottom();
                break;
            case "UP":
                x = rect.getLeft();
                y = rect.getTop() + padding;
                break;
            case "DOWN":
                x = rect.getLeft();
                y = rect.getBottom() - altura - padding;
                break;
            default:
                // Posición por defecto
                x = rect.getLeft();
                y = rect.getBottom();
                break;
        }
        return new float[] { x, y };
    }
}
