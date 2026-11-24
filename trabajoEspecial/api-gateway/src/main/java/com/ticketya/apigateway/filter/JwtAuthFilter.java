package com.ticketya.apigateway.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Component
public class JwtAuthFilter implements GlobalFilter, Ordered {

    private final SecretKey key;

    public JwtAuthFilter(@Value("${jwt.secret}") String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
        HttpMethod method = request.getMethod();

        // Borra los encabezados que el cliente podría falsificar
        ServerHttpRequest.Builder builder = request.mutate()
                .headers(h -> {
                    h.remove("X-User-Id");
                    h.remove("X-User-Rol");
                });

        if (esPublica(path, method)) {
            return chain.filter(exchange.mutate().request(builder.build()).build());
        }

        String auth = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (auth == null || !auth.startsWith("Bearer ")) {
            return rechazar(exchange, HttpStatus.UNAUTHORIZED);
        }

        try {
            Claims claims = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(auth.substring(7))
                    .getPayload();

            String rol = claims.get("rol", String.class);
            if (rol == null || !tienePermiso(rol, path, method)) {
                return rechazar(exchange, HttpStatus.FORBIDDEN);
            }

            builder.header("X-User-Id", claims.getSubject())
                    .header("X-User-Rol", rol);
            return chain.filter(exchange.mutate().request(builder.build()).build());

        } catch (JwtException | IllegalArgumentException e) {
            // firma inválida, token vencido o mal formado
            return rechazar(exchange, HttpStatus.UNAUTHORIZED);
        }
    }

    // Rutas que no requieren token
    private boolean esPublica(String path, HttpMethod method) {
        return HttpMethod.OPTIONS.equals(method)
                || path.contains("/auth/")                  // <-- Permite cualquier endpoint de auth (/login, /register, etc.)
                || path.startsWith("/api/eventos")          // <-- Permite /api/eventos, /api/eventos/, /api/eventos/buscar
                || (path.equals("/api/usuarios") && HttpMethod.POST.equals(method))
                || path.contains("swagger")
                || path.contains("api-docs");
    }

    // Matriz de ejemplo: hay que acordarla con el grupo
    private boolean tienePermiso(String rol, String path, HttpMethod method) {
        String rolLimpio = rol.startsWith("ROLE_") ? rol.substring(5) : rol;

        if (path.startsWith("/api/lugares") && !HttpMethod.GET.equals(method)) {
            return rolLimpio.equals("ORGANIZADOR") || rolLimpio.equals("ADMINISTRADOR");
        }

        if (path.startsWith("/api/eventos") && !HttpMethod.GET.equals(method)) {
            return rolLimpio.equals("ORGANIZADOR") || rolLimpio.equals("ADMINISTRADOR");
        }

        if (path.contains("/devoluciones/") && (path.endsWith("/aprobar") || path.endsWith("/rechazar"))) {
            return rolLimpio.equals("ADMINISTRADOR");
        }

        if (path.contains("/validar")) {
            return rolLimpio.equals("PERSONAL_ACCESO") || rolLimpio.equals("ADMINISTRADOR");
        }
        if (path.startsWith("/api/fila/eventos/")
                && path.endsWith("/habilitar")
                && HttpMethod.POST.equals(method)) {

            return rolLimpio.equals("ORGANIZADOR")
                    || rolLimpio.equals("ADMINISTRADOR");
        }

        return true;
    }

    private Mono<Void> rechazar(ServerWebExchange exchange, HttpStatus status) {
        exchange.getResponse().setStatusCode(status);
        return exchange.getResponse().setComplete();
    }

    @Override
    public int getOrder() {
        return -1;
    }
}