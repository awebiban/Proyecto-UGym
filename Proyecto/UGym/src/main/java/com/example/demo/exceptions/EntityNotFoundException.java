package com.example.demo.exceptions;

public class EntityNotFoundException extends RuntimeException {

    // Constructor sin parámetros
    public EntityNotFoundException() {
        super("Entidad no encontrada en la base de datos.");
    }

    // Constructor con mensaje personalizado
    public EntityNotFoundException(String message) {
        super(message);
    }

    // Constructor con mensaje personalizado y causa (otra excepción)
    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    // Constructor con causa
    public EntityNotFoundException(Throwable cause) {
        super(cause);
    }
}
