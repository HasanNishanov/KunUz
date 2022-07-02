package com.company.exps;

public class NotPermissionException extends RuntimeException {
    public NotPermissionException(String message) {
        super(message);
    }
}
