package com.bank.userservice.common.exception;

public class UsuarioNoEncontradoException extends CustomException {

    private static final long serialVersionUID = 1L;

    // Constructor que recibe un mensaje
    public UsuarioNoEncontradoException(String message) {
        super(message);  // Llama al constructor de CustomException con el mensaje
    }

    // Constructor que recibe un mensaje y la causa (otra excepción)
    public UsuarioNoEncontradoException(String message, Throwable cause) {
        super(message, cause);  // Llama al constructor de CustomException con el mensaje y la causa
    }
}
