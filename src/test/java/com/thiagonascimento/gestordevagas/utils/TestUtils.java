package com.thiagonascimento.gestordevagas.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class TestUtils {

    public static String objectToJson(Object object) {
        final ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String generateToken(UUID companyId, String secret) {
        var expires_in = Instant.now().plus(Duration.ofHours(2));

        return JWT.create().withIssuer("gestordevagas")
                .withExpiresAt(expires_in)
                .withSubject(companyId.toString())
                .withClaim("role", List.of("COMPANY"))
                .sign(Algorithm.HMAC256(secret));
    }
}
