
package com.ticketya.reservaServicio.service;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilaVirtualService {

    private final StringRedisTemplate redisTemplate;

    public FilaVirtualService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // Agrega al usuario una sola vez y respeta el orden de llegada.
    public Long ingresar(Long idEvento, Long idUsuario) {

        String claveFila = "fila:evento:" + idEvento;
        String claveSecuencia = "fila:secuencia:evento:" + idEvento;

        String script =
                "local existente = redis.call('ZSCORE', KEYS[1], ARGV[1]) " +
                        "if existente then return tonumber(existente) end " +
                        "local posicion = redis.call('INCR', KEYS[2]) " +
                        "redis.call('ZADD', KEYS[1], posicion, ARGV[1]) " +
                        "return posicion";

        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptText(script);
        redisScript.setResultType(Long.class);

        return redisTemplate.execute(
                redisScript,
                List.of(claveFila, claveSecuencia),
                idUsuario.toString()
        );
    }

    // Devuelve la posición actual del usuario en la fila.
    public Long consultarPosicion(Long idEvento, Long idUsuario) {

        String claveFila = "fila:evento:" + idEvento;

        Long posicion = redisTemplate.opsForZSet()
                .rank(claveFila, idUsuario.toString());

        if (posicion == null) {
            return null;
        }

        return posicion + 1;
    }

    // Habilita los primeros usuarios de la fila para comprar.
    public List<String> habilitarTanda(Long idEvento, int cantidad) {

        if (cantidad <= 0) {
            throw new IllegalArgumentException(
                    "La cantidad debe ser mayor a cero."
            );
        }

        String claveFila = "fila:evento:" + idEvento;
        String claveHabilitados = "fila:habilitados:evento:" + idEvento;

        long vencimiento = System.currentTimeMillis() + 10 * 60 * 1000;

        String script =
                "local usuarios = redis.call('ZRANGE', KEYS[1], 0, ARGV[1] - 1) " +
                        "for _, usuario in ipairs(usuarios) do " +
                        "redis.call('ZREM', KEYS[1], usuario) " +
                        "redis.call('ZADD', KEYS[2], ARGV[2], usuario) " +
                        "end " +
                        "return usuarios";

        DefaultRedisScript<List> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptText(script);
        redisScript.setResultType(List.class);

        return redisTemplate.execute(
                redisScript,
                List.of(claveFila, claveHabilitados),
                String.valueOf(cantidad),
                String.valueOf(vencimiento)
        );
    }

    // Comprueba si el usuario todavía tiene permiso para comprar.
    public boolean estaHabilitado(Long idEvento, Long idUsuario) {

        String claveHabilitados = "fila:habilitados:evento:" + idEvento;
        long ahora = System.currentTimeMillis();

        redisTemplate.opsForZSet()
                .removeRangeByScore(claveHabilitados, 0, ahora);

        Double vencimiento = redisTemplate.opsForZSet()
                .score(claveHabilitados, idUsuario.toString());

        return vencimiento != null && vencimiento > ahora;
    }

    // Consume el permiso cuando el usuario logra crear su reserva.
    public void consumirHabilitacion(Long idEvento, Long idUsuario) {

        String claveHabilitados = "fila:habilitados:evento:" + idEvento;

        redisTemplate.opsForZSet()
                .remove(claveHabilitados, idUsuario.toString());
    }
}
