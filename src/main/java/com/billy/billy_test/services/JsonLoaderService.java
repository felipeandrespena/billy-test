package com.billy.billy_test.services;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.billy.billy_test.services.model.DocumentBatch;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonLoaderService {
    
    private final ObjectMapper objectMapper;
    
    public JsonLoaderService() {
        this.objectMapper = new ObjectMapper();
    }
    
    /**
     * Carga el archivo JSON y lo convierte a DocumentBatch
     * @param filePath ruta al archivo JSON
     * @return DocumentBatch con todos los documentos
     * @throws IOException si hay error leyendo el archivo
     */
    public DocumentBatch loadDocuments(String filePath) throws IOException {
        System.out.println("Loading JSON file: " + filePath);
        
        // Verificar que el archivo existe
        File file = new File(filePath);
        if (!file.exists()) {
            throw new IOException("File not found: " + filePath);
        }
        
        // Leer y parsear el JSON
        DocumentBatch batch = objectMapper.readValue(file, DocumentBatch.class);
        
        System.out.println("Successfully loaded " + batch.size() + " documents");
        return batch;
    }
    
    /**
     * Método alternativo usando String content
     * @param jsonContent contenido JSON como String
     * @return DocumentBatch con todos los documentos
     * @throws IOException si hay error parseando el JSON
     */
    public DocumentBatch loadDocumentsFromString(String jsonContent) throws IOException {
        DocumentBatch batch = objectMapper.readValue(jsonContent, DocumentBatch.class);
        System.out.println("Successfully loaded " + batch.size() + " documents from string");
        return batch;
    }
    
    /**
     * Método usando NIO para archivos grandes
     * @param filePath ruta al archivo JSON
     * @return DocumentBatch con todos los documentos
     * @throws IOException si hay error leyendo el archivo
     */
    public DocumentBatch loadDocumentsNIO(String filePath) throws IOException {
        System.out.println("Loading JSON file with NIO: " + filePath);
        
        // Leer todo el archivo a String
        String content = Files.readString(Paths.get(filePath));
        
        // Parsear JSON
        DocumentBatch batch = objectMapper.readValue(content, DocumentBatch.class);
        
        System.out.println("Successfully loaded " + batch.size() + " documents with NIO");
        return batch;
    }
    
    /**
     * Método con manejo de errores más detallado
     * @param filePath ruta al archivo JSON
     * @return DocumentBatch con todos los documentos
     * @throws IOException 
     */
    public DocumentBatch loadDocumentsSafe(String filePath) throws IOException {
    	 try {
    	        
    	        DocumentBatch batch;

    	        int _16k = 16384;
    	        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath);
    	            
			 	// Buffer Reader is faster
			 	BufferedInputStream bufferedStream = new BufferedInputStream(inputStream, _16k)) {
    	            
    	            if (inputStream == null) {
    	                throw new IOException("Resource not found: " + filePath);
    	            }
    	            
    	            batch = objectMapper.readValue(bufferedStream, DocumentBatch.class);
    	        }
    	        
    	        return batch;
    	        
    	    } catch (IOException e) {
    	        System.err.println("ERROR loading JSON file: " + e.getMessage());
    	        e.printStackTrace();
    	        return new DocumentBatch();
    	    }
    }
}


