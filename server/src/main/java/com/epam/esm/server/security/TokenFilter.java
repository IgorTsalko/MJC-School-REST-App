package com.epam.esm.server.security;

import com.epam.esm.common.exception.ErrorDefinition;
import com.epam.esm.common.exception.TokenExpiredException;
import com.epam.esm.common.exception.TokenInvalidException;
import com.epam.esm.server.entity.ExceptionResponse;
import com.epam.esm.service.util.TokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

@Component
public class TokenFilter extends OncePerRequestFilter {

    private final static String AUTHORIZATION_HEADER = "Authorization";
    private final static String ACCEPT_LANGUAGE = "Accept-Language";

    private final ObjectMapper objectMapper;
    private final MessageSource messageSource;
    private final TokenUtil tokenUtil;

    public TokenFilter(MessageSource messageSource, TokenUtil tokenUtil) {
        this.messageSource = messageSource;
        this.tokenUtil = tokenUtil;
        objectMapper = new ObjectMapper();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(AUTHORIZATION_HEADER);

        try {
            if (token != null && tokenUtil.validateToken(token)) {
                Authentication auth = tokenUtil.getAuthentication(token);
                if (auth != null) {
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }

            filterChain.doFilter(request, response);
        } catch (TokenExpiredException | TokenInvalidException ex) {
            ErrorDefinition errorDefinition = ex.getErrorDefinition();

            String acceptLanguage = request.getHeader(ACCEPT_LANGUAGE);
            Locale locale;
            if (acceptLanguage != null) {
                locale = Locale.forLanguageTag(acceptLanguage);
            } else {
                locale = Locale.getDefault();
            }

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            if (ex.getClass() == TokenExpiredException.class) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            } else if (ex.getClass() == TokenInvalidException.class) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }

            ExceptionResponse exceptionResponse = new ExceptionResponse()
                    .setErrorCode(errorDefinition.getErrorCode())
                    .setDetails(List.of(messageSource.getMessage(
                            errorDefinition.getErrorMessageTemplate(),
                            null,
                            locale)));

            response.getWriter().println(objectMapper.writeValueAsString(exceptionResponse));
        }
    }
}
