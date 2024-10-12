package com.to_do_list.Metas.service.exception;

public class TarefaNotFoundException extends RuntimeException {
    public TarefaNotFoundException(String message) {
        super(message);
    }
}
