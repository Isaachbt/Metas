package com.to_do_list.Metas.controller.exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException ex) throws IOException {
        StandarError error = new StandarError(
                LocalDateTime.now(),
                HttpServletResponse.SC_FORBIDDEN,
                "Acesso negado. Você não tem permissão para acessar este recurso.",
                request.getRequestURI()
        );

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule()); // Registra o módulo para tipos Java 8
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // Para formatar como ISO-8601

        response.getWriter().write(mapper.writeValueAsString(error));
    }
}
