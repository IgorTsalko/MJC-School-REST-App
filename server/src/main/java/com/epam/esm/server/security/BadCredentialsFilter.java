package com.epam.esm.server.security;

import com.epam.esm.common.exception.ErrorDefinition;
import com.epam.esm.common.exception.TokenExpiredException;
import com.epam.esm.common.exception.TokenInvalidException;
import com.epam.esm.server.entity.ExceptionResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;

@Component
public class BadCredentialsFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;
    private final MessageSource messageSource;

    public BadCredentialsFilter(ObjectMapper objectMapper, MessageSource messageSource) {
        this.objectMapper = objectMapper;
        this.messageSource = messageSource;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (TokenExpiredException | TokenInvalidException ex) {
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());

            ErrorDefinition errorDefinition = ex.getErrorDefinition();
            String lang = request.getHeader(HttpHeaders.ACCEPT_LANGUAGE);
            Locale locale = lang != null ? new Locale(lang) : Locale.getDefault();

            ExceptionResponse exceptionResponse = new ExceptionResponse()
                    .setErrorCode(errorDefinition.getErrorCode())
                    .setDetails(List.of(messageSource.getMessage(
                            errorDefinition.getErrorMessageTemplate(), null, locale)));

            response.getWriter().println(objectMapper.writeValueAsString(exceptionResponse));
            response.setStatus(errorDefinition.getHttpStatus().value());
        }
    }
}
