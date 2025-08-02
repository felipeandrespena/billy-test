package com.billy.billy_test.services;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.billy.billy_test.services.model.ProcessedDocument;

public class HTMLGeneratorService {


	public void generateHTMLReport(List<ProcessedDocument> processedDocs, String outputFile) throws IOException {
	    
	    Map<String, Integer> countByMedioPago = new HashMap<>();
	    Map<String, Double> sumByMedioPago = new HashMap<>();
	    double totalAmount = 0.0;
	    int totalDocs = processedDocs.size();
	    
	    // Single loop instead of multiple streams
	    for (ProcessedDocument doc : processedDocs) {
	        String medioPago = doc.getMedioPago();
	        if (medioPago != null) {
	            countByMedioPago.merge(medioPago, 1, Integer::sum);
	            
	            Double amount = doc.getTotalAPagar();
	            if (amount != null) {
	                sumByMedioPago.merge(medioPago, amount, Double::sum);
	                totalAmount += amount;
	            }
	        }
	    }
	    
	    StringBuilder html = new StringBuilder(8192); // Start with 8KB capacity
	    
	    DecimalFormat numberFormat = new DecimalFormat("#,##0");
	    DecimalFormat currencyFormat = new DecimalFormat("$#,##0.00");
	    
	    String cssStyles = """
	        <style>
	        body { font-family: Arial, sans-serif; margin: 20px; background-color: #f5f5f5; }
	        .container { max-width: 1200px; margin: 0 auto; background: white; padding: 20px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
	        h1 { color: #2c3e50; text-align: center; margin-bottom: 30px; }
	        .summary { background: #ecf0f1; padding: 15px; border-radius: 5px; margin-bottom: 20px; }
	        table { width: 100%; border-collapse: collapse; margin: 20px 0; }
	        th, td { padding: 12px; text-align: right; border: 1px solid #bdc3c7; }
	        th { background-color: #3498db; color: white; font-weight: bold; }
	        .medio-pago { text-align: left; font-weight: bold; }
	        .totales { background-color: #2ecc71; color: white; font-weight: bold; }
	        .metric-row:nth-child(even) { background-color: #f8f9fa; }
	        .number { font-family: monospace; }
	        .timestamp { text-align: center; color: #7f8c8d; margin-top: 20px; font-size: 0.9em; }
	        </style>
	        """;
	    
	    // 5. OPTIMIZATION: Build HTML header in fewer append calls
	    html.append("""
	        <html lang="es">
	        <head>
	            <meta charset="UTF-8">
	            <meta name="viewport" content="width=device-width, initial-scale=1.0">
	            <title>Reporte de Documentos por Medio de Pago</title>
	        """)
	        .append(cssStyles)
	        .append("""
	        </head>
	        <body>
	            <div class="container">
	                <h1>Reporte de Documentos por Medio de Pago</h1>
	                <div class="summary">
	                    <strong>Resumen General:</strong> """)
	        .append(numberFormat.format(totalDocs))
	        .append(" documentos procesados | Total General: ")
	        .append(currencyFormat.format(totalAmount))
	        .append("""
	                </div>
	                <table>
	                    <thead>
	                        <tr>
	                            <th class="medio-pago">METRICAS</th>
	        """);
	    

	    List<String> mediosPago = new ArrayList<>(countByMedioPago.keySet());
	    
	    // Headers
	    for (String medioPago : mediosPago) {
	        html.append("                            <th>").append(medioPago.toUpperCase()).append("</th>\n");
	    }
	    html.append("""
	                            <th class="totales">TOTALES</th>
	                        </tr>
	                    </thead>
	                    <tbody>
	                        <tr class="metric-row">
	                            <td class="medio-pago">Cantidad Documentos</td>
	        """);
	    
	    int totalDocsSum = countByMedioPago.values().stream().mapToInt(Integer::intValue).sum();
	    double totalPaymentSum = sumByMedioPago.values().stream().mapToDouble(Double::doubleValue).sum();
	    
	    for (String medioPago : mediosPago) {
	        html.append("                            <td class=\"number\">")
	            .append(numberFormat.format(countByMedioPago.get(medioPago)))
	            .append("</td>\n");
	    }
	    html.append("                            <td class=\"number totales\">")
	        .append(numberFormat.format(totalDocsSum))
	        .append("</td>\n                        </tr>\n");
	    
	    html.append("                        <tr class=\"metric-row\">\n")
	        .append("                            <td class=\"medio-pago\">Total a Pagar</td>\n");
	    
	    for (String medioPago : mediosPago) {
	        Double sum = sumByMedioPago.get(medioPago);
	        html.append("                            <td class=\"number\">")
	            .append(currencyFormat.format(sum != null ? sum : 0.0))
	            .append("</td>\n");
	    }
	    html.append("                            <td class=\"number totales\">")
	        .append(currencyFormat.format(totalPaymentSum))
	        .append("</td>\n                        </tr>\n");
	    
	    // 8. OPTIMIZATION: Final sections in fewer append calls
	    html.append("""
	                    </tbody>
	                </table>
	                <div class="summary">
	                    <strong>Medios de Pago Encontrados:</strong> """)
	        .append(countByMedioPago.size())
	        .append(" tipos diferentes<br>\n                    <strong>Promedio por Documento:</strong> ")
	        .append(currencyFormat.format(totalAmount / totalDocs))
	        .append("""
	                </div>
	                <div class="timestamp">
	                    Reporte generado el: """)
	        .append(new java.util.Date().toString())
	        .append("""
	                </div>
	            </div>
	        </body>
	        </html>
	        """);
	    
	    // 9. OPTIMIZATION: Use BufferedWriter for file writing
	    try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputFile), StandardCharsets.UTF_8)) {
	        writer.write(html.toString());
	    }
	}
}
