package com.bank.userservice.common.exception;

public class CustomException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    // Mensaje que describe el error
    private String message;

    // Constructor que recibe el mensaje de la excepción
    public CustomException(String message) {
        super(message);  // Llama al constructor de RuntimeException
        this.message = message;
    }

    // Constructor que recibe el mensaje y la causa
    public CustomException(String message, Throwable cause) {
        super(message, cause);  // Llama al constructor de RuntimeException
        this.message = message;
    }

    // Obtener el mensaje de la excepción
    @Override
    public String getMessage() {
        return message;
    }
}
