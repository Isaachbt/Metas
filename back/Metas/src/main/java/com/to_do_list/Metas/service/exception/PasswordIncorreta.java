package com.to_do_list.Metas.service.exception;

public class PasswordIncorreta extends RuntimeException {
    public PasswordIncorreta(String message) {
        super(message);
    }
}
