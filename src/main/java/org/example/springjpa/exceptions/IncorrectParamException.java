package org.example.springjpa.exceptions;

public class IncorrectParamException extends RuntimeException {
    public IncorrectParamException() {
        super();
    }

    public IncorrectParamException(String message) {
        super(message);
    }
}
