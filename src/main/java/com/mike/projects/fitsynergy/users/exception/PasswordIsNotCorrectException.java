package com.mike.projects.fitsynergy.users.exception;

public class PasswordIsNotCorrectException extends RuntimeException{
    public PasswordIsNotCorrectException() {
    }

    public PasswordIsNotCorrectException(String message) {
        super(message);
    }

    public PasswordIsNotCorrectException(String message, Throwable cause) {
        super(message, cause);
    }

    public PasswordIsNotCorrectException(Throwable cause) {
        super(cause);
    }
}
