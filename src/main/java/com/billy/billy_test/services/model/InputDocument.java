package com.billy.billy_test.services.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InputDocument {
	    
		@JsonProperty("DocType")
		private String docType;
		
		@JsonProperty("NroDocInterno")
	    private String nroDocInterno;
		
		@JsonProperty("ContentBase64")
	    private String contentBase64;
	    
	    // Constructors
	    public InputDocument() {}
	    
	    public InputDocument(String docType, String nroDocInterno, String contentBase64) {
	        this.docType = docType;
	        this.nroDocInterno = nroDocInterno;
	        this.contentBase64 = contentBase64;
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
	    
	    public String getContentBase64() {
	        return contentBase64;
	    }
	    
	    public void setContentBase64(String contentBase64) {
	        this.contentBase64 = contentBase64;
	    }
	    
	    @Override
	    public String toString() {
	        return "InputDocument{" +
	                "docType='" + docType + '\'' +
	                ", nroDocInterno='" + nroDocInterno + '\'' +
	                ", contentBase64='" + contentBase64.substring(0, Math.min(50, contentBase64.length())) + "...'" +
	                '}';
	    }
	}