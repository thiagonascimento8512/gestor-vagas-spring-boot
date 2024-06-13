package com.thiagonascimento.gestordevagas.security;

import com.thiagonascimento.gestordevagas.providers.JWTCandidateProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityCandidateFilter extends OncePerRequestFilter {

    @Autowired
    private JWTCandidateProvider jwtCandidateProvider;

    private static final String CANDIDATE_PATH = "/candidate";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!request.getRequestURI().startsWith(CANDIDATE_PATH)) {
            filterChain.doFilter(request, response);
            return;
        }

        SecurityContextHolder.getContext().setAuthentication(null);

        String header = request.getHeader("Authorization");


        if (header != null) {
            var token = jwtCandidateProvider.validateToken(header);

            if (token == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            request.setAttribute("candidate_id", token.getSubject());
            var roles = token.getClaim("roles").asList(String.class);

            var grants = roles.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.toUpperCase())).toList();

            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(
                            token.getSubject(),
                            null,
                            grants);

            SecurityContextHolder.getContext().setAuthentication(auth);
        }


        filterChain.doFilter(request, response);
    }
}
