package org.example.exceptions;

public class DiagnosisNotFoundException extends RuntimeException{
    public DiagnosisNotFoundException(String message) {
        super(message);
    }
}
