package com.bank.userservice.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidacionUtil {

    // Expresión regular para validar emails
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    // Método para validar el formato de un correo electrónico
    public static boolean validarEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }

        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // Método para validar si un valor es nulo o vacío
    public static boolean esNuloOValido(String valor) {
        return valor != null && !valor.trim().isEmpty();
    }

    // Método para validar contraseñas, por ejemplo, una longitud mínima
    public static boolean validarPassword(String password) {
        return password != null && password.length() >= 8;  // Contraseña con al menos 8 caracteres
    }
}
