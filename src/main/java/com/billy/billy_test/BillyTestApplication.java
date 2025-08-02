package com.billy.billy_test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.billy.billy_test.services.DocumentDecoderService;
import com.billy.billy_test.services.HTMLGeneratorService;
import com.billy.billy_test.services.JsonLoaderService;
import com.billy.billy_test.services.XmlGeneratorService;
import com.billy.billy_test.services.model.DocumentBatch;
import com.billy.billy_test.services.model.ProcessedDocument;


public class BillyTestApplication {

   
    BillyTestApplication() {
    
    }

	public static void main(String... args) throws Exception {

		// Iniciar medición de tiempo total
	    long startTime = System.currentTimeMillis();
	    
	    JsonLoaderService jsonLoaderService = new JsonLoaderService();
	    DocumentDecoderService documentDecoderService = new DocumentDecoderService();
	    XmlGeneratorService xmlGenerator = new XmlGeneratorService();
	    HTMLGeneratorService htmlGenerator = new HTMLGeneratorService();

	    
	    System.out.println("=".repeat(60));
	    System.out.println("🚀 COMENZANDO PROCESO ...");
	    System.out.println("⏰ Hora inicio: " + new java.text.SimpleDateFormat("HH:mm:ss").format(new java.util.Date()));
	    System.out.println("=".repeat(60));
	    
	    String inputFile = args.length > 0 ? args[0] : "lote.ejemplo.json";
	    
	    
	    String outputFolder = "output";
	    
	    initializeOutputFolders(outputFolder);
	    
	    DocumentBatch batch = jsonLoaderService.loadDocumentsSafe(inputFile);
	    
	    List<ProcessedDocument> processedDocs = documentDecoderService.processDocumentBatch(batch);
	    
	    // Generar archivo XML
	    if (!processedDocs.isEmpty()) {
	    	
	        ExecutorService executor = Executors.newFixedThreadPool(2);
	        
	    	// Con los documentos procesados , se pueden ejecutar ambas tareas simultaneamente.
	    	CompletableFuture<Void> xmlGeneratorTask = CompletableFuture.runAsync( () -> {
				//xml generator
			    try {
					xmlGenerator.generateXmlFileWithValidation(processedDocs, outputFolder+"/output.xml");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			},executor);
			
			
			CompletableFuture<Void> htmlGeneratorTask = CompletableFuture.runAsync( () -> {
				try {
					htmlGenerator.generateHTMLReport(processedDocs, outputFolder+"/report.html");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			},executor);
			
			//Ejecucion en paralelo. 
			CompletableFuture<Void> allTasks = CompletableFuture.allOf(xmlGeneratorTask , htmlGeneratorTask);
			
			allTasks.get();
			
	    } else {
	        System.err.println("❌ No ha documentos disponibles");
	    }

	    // Calcular tiempo total
	    long endTime = System.currentTimeMillis();
	    long totalTime = endTime - startTime;
	    double totalTimeSeconds = totalTime / 1000.0;

	    System.out.println("⏰ Hora fin : " + new java.text.SimpleDateFormat("HH:mm:ss").format(new java.util.Date()));
	    System.out.println("⏱️ Tiempo total : " + String.format("%.3f", totalTimeSeconds) + " seconds (" + totalTime + "ms)");
	    System.out.println("\n🎯 PROCESO TERMINADO , revisar el directorio output!");
	    System.exit(0);
	}
	
	public static void initializeOutputFolders(String outputPath) {
		File outputDir = new File("output");
		if (!outputDir.exists()) {
		    outputDir.mkdirs();
		    System.out.println("✅ Created output folder");
		} else {
		    System.out.println("✅ Output folder already exists");
		}
    }
	
}
