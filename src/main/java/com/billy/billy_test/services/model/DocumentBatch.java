package com.billy.billy_test.services.model;

import java.util.ArrayList;
import java.util.List;

public class DocumentBatch {
	    private List<InputDocument> documents;
	    
	    // Constructors
	    public DocumentBatch() {
	        this.documents = new ArrayList<>();
	    }
	    
	    public DocumentBatch(List<InputDocument> documents) {
	        this.documents = documents;
	    }
	    
	    // Getters and Setters
	    public List<InputDocument> getDocuments() {
	        return documents;
	    }
	    
	    public void setDocuments(List<InputDocument> documents) {
	        this.documents = documents;
	    }
	    
	    public int size() {
	        return documents != null ? documents.size() : 0;
	    }
	    
	    @Override
	    public String toString() {
	        return "DocumentBatch{" +
	                "documents=" + (documents != null ? documents.size() : 0) + " items" +
	                '}';
	    }
	}