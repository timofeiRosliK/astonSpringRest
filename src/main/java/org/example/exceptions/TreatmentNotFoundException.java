package org.example.exceptions;

public class TreatmentNotFoundException extends RuntimeException{
    public TreatmentNotFoundException(String message) {
        super(message);
    }
}
