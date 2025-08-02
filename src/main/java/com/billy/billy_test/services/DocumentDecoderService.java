package com.billy.billy_test.services;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.zip.GZIPInputStream;

import com.billy.billy_test.services.model.DecodedDocument;
import com.billy.billy_test.services.model.DocumentBatch;
import com.billy.billy_test.services.model.InputDocument;
import com.billy.billy_test.services.model.ProcessedDocument;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DocumentDecoderService {
    
    private final ObjectMapper objectMapper;
    
    public DocumentDecoderService() {
        this.objectMapper = new ObjectMapper();
    }
    
    /**
     * Procesa un lote completo de documentos (sin paralelismo)
     * @param batch lote de documentos a procesar
     * @return lista de documentos procesados
     */
    public List<ProcessedDocument> processDocumentBatch(DocumentBatch batch) {
        
    		List<ProcessedDocument> processedDocuments = batch.getDocuments()
    	        .parallelStream()
    	        .map(inputDoc -> {
    	            try {
    	                // Decodificar documento (CPU-intensive: Base64 + Gzip + JSON parsing)
    	                DecodedDocument decodedDoc = decodeDocument(inputDoc);
    	                return new ProcessedDocument(inputDoc, decodedDoc);
    	                
    	            } catch (Exception e) {
    	                System.err.println("❌ Error processing document " + inputDoc.getNroDocInterno() + ": " + e.getMessage());
    	                return null; // Return null for failed documents
    	            }
    	        })
    	        .filter(Objects::nonNull) // Remove failed documents
    	        .collect(Collectors.toList());
    	    
    	    return processedDocuments;
    }
    
    /**
     * Decodifica un documento individual: Base64 → Gzip → JSON
     * @param inputDoc documento con contenido codificado
     * @return documento decodificado
     * @throws Exception si hay error en la decodificación
     */
    public DecodedDocument decodeDocument(InputDocument inputDoc) throws Exception {
        String contentBase64 = inputDoc.getContentBase64();
        
        // Paso 1: Decodificar Base64
        byte[] gzipData = Base64.getDecoder().decode(contentBase64);
        
        // Paso 2: Descomprimir Gzip
        String jsonString = decompressGzip(gzipData);
        
        // Paso 3: Parsear JSON
        DecodedDocument decodedDoc = objectMapper.readValue(jsonString, DecodedDocument.class);
        
        return decodedDoc;
    }
    
    /**
     * Descomprime datos Gzip
     * @param gzipData datos comprimidos
     * @return string descomprimido
     * @throws IOException si hay error en la descompresión
     */
    private String decompressGzip(byte[] gzipData) throws IOException {
        try (ByteArrayInputStream bais = new ByteArrayInputStream(gzipData);
             GZIPInputStream gzis = new GZIPInputStream(bais)) {
            
            return new String(gzis.readAllBytes());
        }
    }
}