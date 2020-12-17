package com.isi.arcgis.api.exceptions;

public class PasswordRegisterException extends RuntimeException {

    public PasswordRegisterException(String message) {
        super(message);
    }

    public PasswordRegisterException(String message, Throwable e) {
        super(message, e);
    }
}
