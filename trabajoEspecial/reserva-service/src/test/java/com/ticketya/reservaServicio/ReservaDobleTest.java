package com.ticketya.reservaServicio;

import com.ticketya.reservaServicio.dto.CrearReservaDTO;
import com.ticketya.reservaServicio.service.ReservaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ReservaDobleTest {

    @Autowired
    private ReservaService reservaService;

    @Test
    public void testNoDobleVentaAsientoSimultaneo() throws InterruptedException {
        int hilosSimultaneos = 15;
        ExecutorService executorService = Executors.newFixedThreadPool(hilosSimultaneos);
        CountDownLatch latch = new CountDownLatch(1);
        AtomicInteger exitosas = new AtomicInteger(0);
        AtomicInteger fallidas = new AtomicInteger(0);

        CrearReservaDTO dto = new CrearReservaDTO();
        dto.setIdUsuario(100L);
        dto.setIdEvento(1L);
        dto.setIdSector(1L);
        dto.setFila(5);
        dto.setAsiento(12); // Asiento único exacto

        for (int i = 0; i < hilosSimultaneos; i++) {
            executorService.submit(() -> {
                try {
                    latch.await(); // Espera a que todos los hilos arranquen al mismo tiempo
                    reservaService.crearReserva(dto);
                    exitosas.incrementAndGet();
                } catch (Exception e) {
                    fallidas.incrementAndGet();
                }
            });
        }

        latch.countDown(); // ¡Dispara las peticiones en simultáneo!
        executorService.shutdown();
        while (!executorService.isTerminated()) {
            Thread.sleep(50);
        }

        System.out.println("Reservas exitosas: " + exitosas.get());
        System.out.println("Reservas rebotadas por choque: " + fallidas.get());

        // Aserción clave del TP: Solo 1 reserva debe ganar el asiento
        assertEquals(1, exitosas.get(), "Solo una reserva debe ser exitosa para el mismo asiento");
        assertEquals(hilosSimultaneos - 1, fallidas.get(), "El resto de peticiones debe fallar por asiento ocupado");
    }
}