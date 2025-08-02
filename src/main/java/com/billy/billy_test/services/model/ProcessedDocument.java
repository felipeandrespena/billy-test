package com.billy.billy_test.services.model;


//TODO: posiblemente usar lombok para evitar constructores, setters y getters.
public class ProcessedDocument {
	    private String docType;           
	    private String nroDocInterno;     
	    private String id;                
	    private String name;              
	    private String type;              
	    private Double totalAPagar;       
	    private String medioPago;         
	    
	    // Constructors
	    public ProcessedDocument() {}
	    
	    public ProcessedDocument(InputDocument input, DecodedDocument decoded) {
	        this.docType = input.getDocType();
	        this.nroDocInterno = input.getNroDocInterno();
	        this.id = decoded.getId();
	        this.name = decoded.getName();
	        this.type = decoded.getType();
	        this.totalAPagar = decoded.getTotalAPagar();
	        this.medioPago = decoded.getMedioPago();
	    }
	    
	    // Getters and Setters
	    public String getDocType() {
	        return docType;
	    }
	    
	    public void setDocType(String docType) {
	        this.docType = docType;
	    }
	    
	    public String getNroDocInterno() {
	        return nroDocInterno;
	    }
	    
	    public void setNroDocInterno(String nroDocInterno) {
	        this.nroDocInterno = nroDocInterno;
	    }
	    
	    public String getId() {
	        return id;
	    }
	    
	    public void setId(String id) {
	        this.id = id;
	    }
	    
	    public String getName() {
	        return name;
	    }
	    
	    public void setName(String name) {
	        this.name = name;
	    }
	    
	    public String getType() {
	        return type;
	    }
	    
	    public void setType(String type) {
	        this.type = type;
	    }
	    
	    public Double getTotalAPagar() {
	        return totalAPagar;
	    }
	    
	    public void setTotalAPagar(Double totalAPagar) {
	        this.totalAPagar = totalAPagar;
	    }
	    
	    public String getMedioPago() {
	        return medioPago;
	    }
	    
	    public void setMedioPago(String medioPago) {
	        this.medioPago = medioPago;
	    }
	    
	    @Override
	    public String toString() {
	        return "ProcessedDocument{" +
	                "docType='" + docType + '\'' +
	                ", nroDocInterno='" + nroDocInterno + '\'' +
	                ", id='" + id + '\'' +
	                ", name='" + name + '\'' +
	                ", type='" + type + '\'' +
	                ", totalAPagar=" + totalAPagar +
	                ", medioPago='" + medioPago + '\'' +
	                '}';
	    }
	}
