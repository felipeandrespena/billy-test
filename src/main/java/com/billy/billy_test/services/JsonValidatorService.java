package com.billy.billy_test.services;

import com.billy.billy_test.services.model.DocumentBatch;
import com.billy.billy_test.services.model.InputDocument;

public class JsonValidatorService {
    
    /**
     * Valida la estructura del DocumentBatch cargado
     * @param batch el batch a validar
     * @return true si es válido
     */
    public boolean validateBatch(DocumentBatch batch) {
        if (batch == null) {
            System.err.println("❌ Batch is null");
            return false;
        }
        
        if (batch.getDocuments() == null) {
            System.err.println("❌ Documents list is null");
            return false;
        }
        
        if (batch.getDocuments().isEmpty()) {
            System.err.println("❌ Documents list is empty");
            return false;
        }
        
        // Validar cada documento
        for (int i = 0; i < batch.getDocuments().size(); i++) {
            InputDocument doc = batch.getDocuments().get(i);
            if (!validateDocument(doc, i)) {
                return false;
            }
        }
        
        System.out.println("✅ Batch validation passed: " + batch.size() + " documents");
        return true;
    }
    
    /**
     * Valida un documento individual
     * @param doc el documento a validar
     * @param index índice del documento (para logging)
     * @return true si es válido
     */
    private boolean validateDocument(InputDocument doc, int index) {
        if (doc == null) {
            System.err.println("❌ Document at index " + index + " is null");
            return false;
        }
        
        if (doc.getDocType() == null || doc.getDocType().trim().isEmpty()) {
            System.err.println("❌ Document at index " + index + " has empty DocType");
            return false;
        }
        
        if (doc.getNroDocInterno() == null || doc.getNroDocInterno().trim().isEmpty()) {
            System.err.println("❌ Document at index " + index + " has empty NroDocInterno");
            return false;
        }
        
        if (doc.getContentBase64() == null || doc.getContentBase64().trim().isEmpty()) {
            System.err.println("❌ Document at index " + index + " has empty ContentBase64");
            return false;
        }
        
        // Validar que el contentBase64 parece válido (longitud mínima)
        if (doc.getContentBase64().length() < 10) {
            System.err.println("❌ Document at index " + index + " has suspiciously short Base64 content");
            return false;
        }
        
        return true;
    }
    
    /**
     * Muestra estadísticas del batch cargado
     * @param batch el batch a analizar
     */
    public void showBatchStats(DocumentBatch batch) {
        if (batch == null || batch.getDocuments() == null) {
            System.out.println("No batch data to analyze");
            return;
        }
        
        System.out.println("\n📊 BATCH STATISTICS:");
        System.out.println("Total documents: " + batch.size());
        
        // Contar por tipo de documento
        batch.getDocuments().stream()
            .collect(java.util.stream.Collectors.groupingBy(
                InputDocument::getDocType,
                java.util.stream.Collectors.counting()
            ))
            .forEach((type, count) -> 
                System.out.println("  - " + type + ": " + count + " documents")
            );
        
        // Estadísticas de tamaño de Base64
        java.util.IntSummaryStatistics base64Stats = batch.getDocuments().stream()
            .mapToInt(doc -> doc.getContentBase64().length())
            .summaryStatistics();
            
        System.out.println("Base64 content length:");
        System.out.println("  - Min: " + base64Stats.getMin());
        System.out.println("  - Max: " + base64Stats.getMax());
        System.out.println("  - Average: " + String.format("%.2f", base64Stats.getAverage()));
        System.out.println();
    }
}