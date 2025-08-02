package com.billy.billy_test.services;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import com.billy.billy_test.services.model.ProcessedDocument;

public class XmlGeneratorService {
    
    /**
     * Genera el archivo XML completo con todos los documentos procesados
     * @param processedDocuments lista de documentos procesados
     * @param outputFileName nombre del archivo de salida
     * @throws IOException si hay error escribiendo el archivo
     */
    public void generateXmlFile(List<ProcessedDocument> processedDocuments, String outputFileName) throws IOException {
        
        long startTime = System.currentTimeMillis();
        StringBuilder builder = new StringBuilder();
        
        try (BufferedWriter writer = new BufferedWriter( new FileWriter(outputFileName, StandardCharsets.ISO_8859_1))) {
            // Escribir cabecera XML
            writeXmlHeader(builder);
            
            // Escribir cada documento
            for (int i = 0; i < processedDocuments.size(); i++) {
                ProcessedDocument doc = processedDocuments.get(i);
                writeDocumentXml(builder, doc);
                
            }
            
            writeXmlFooter(builder);
        }

        Files.writeString(Paths.get(outputFileName), builder.toString(), StandardCharsets.ISO_8859_1);
    }
    
    /**
     * Escribe la cabecera del XML
     * @param writer escritor del archivo
     * @throws IOException si hay error escribiendo
     */
    private void writeXmlHeader(StringBuilder writer) throws IOException {
        writer.append("<?xml version=\"1.0\" encoding=\"ISO-8859-1\" standalone=\"no\"?>\n")
        	  .append("<DTE version=\"1.0\">\n");
    }
    
    /**
     * Escribe un documento individual en formato XML
     * @param writer escritor del archivo
     * @param doc documento a escribir
     * @throws IOException si hay error escribiendo
     */
    private void writeDocumentXml(StringBuilder writer, ProcessedDocument doc) throws IOException {
        writer.append("  <Documento ID=\"" + escapeXml(doc.getId()) + "\">\n")
              .append("    <Cliente>" + escapeXml(doc.getName()) + "</Cliente>\n")
              .append("    <Tipo>" + escapeXml(doc.getType()) + "</Tipo>\n")
              .append("    <TotalAPagar>" + (doc.getTotalAPagar() != null ? doc.getTotalAPagar() : "0") + "</TotalAPagar>\n")
              .append("    <MedioPago>" + escapeXml(doc.getMedioPago()) + "</MedioPago>\n")
              .append("  </Documento>\n");
    }
    
    /**
     * Escribe el cierre del XML
     * @param writer escritor del archivo
     * @throws IOException si hay error escribiendo
     */
    private void writeXmlFooter(StringBuilder writer) throws IOException {
        writer.append("</DTE>\n");
    }
    
    /**
     * Escapa caracteres especiales para XML
     * @param text texto a escapar
     * @return texto escapado
     */
    private String escapeXml(String text) {
        if (text == null) {
            return "";
        }
        
        return text.replace("&", "&amp;")
                   .replace("<", "&lt;")
                   .replace(">", "&gt;")
                   .replace("\"", "&quot;")
                   .replace("'", "&apos;");
    }
    
    /**
     * Genera XML solo con documentos válidos
     * @param processedDocuments lista de documentos procesados
     * @param outputFileName nombre del archivo de salida
     * @throws IOException si hay error escribiendo el archivo
     */
    public void generateXmlFileWithValidation(List<ProcessedDocument> processedDocuments, String outputFileName) throws IOException {
        // Generar XML con documentos válidos
        generateXmlFile(processedDocuments, outputFileName);
    }
    
}