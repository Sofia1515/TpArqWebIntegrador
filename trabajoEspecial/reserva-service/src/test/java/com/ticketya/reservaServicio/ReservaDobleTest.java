package com.ticketya.reservaServicio;

import com.ticketya.reservaServicio.cliente.EventoClient;
import com.ticketya.reservaServicio.dto.CrearReservaDTO;
import com.ticketya.reservaServicio.dto.EventoDTO;
import com.ticketya.reservaServicio.service.ReservaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.server.ResponseStatusException;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class ReservaDobleTest {

    @Autowired
    private ReservaService reservaService;

    @MockBean
    private EventoClient eventoClient;  // Mockeamos OpenFeign para no depender de evento-service

    @BeforeEach
    void setUp() {
        // Simulamos que el evento 1 existe y está PUBLICADO
        EventoDTO eventoMock = new EventoDTO();
        eventoMock.setId(1L);
        eventoMock.setNombre("Evento Test");
        eventoMock.setEstado("PUBLICADO");
        eventoMock.setSectores(java.util.List.of()); // Sin sectores, usa el precio por defecto

        when(eventoClient.obtenerEventoPorId(1L)).thenReturn(eventoMock);
    }

    @Test
    public void testNoDobleVentaAsientoSimultaneo() throws InterruptedException {
        int hilosSimultaneos = 15;
        ExecutorService executorService = Executors.newFixedThreadPool(hilosSimultaneos);
        CountDownLatch latch = new CountDownLatch(1);
        AtomicInteger exitosas = new AtomicInteger(0);
        AtomicInteger conflictos = new AtomicInteger(0);
        AtomicInteger otrosErrores = new AtomicInteger(0);

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
                } catch (ResponseStatusException e) {
                    if (e.getStatusCode().value() == 409) {
                        conflictos.incrementAndGet();
                    } else {
                        otrosErrores.incrementAndGet();
                        System.err.println("Error HTTP inesperado: " + e.getStatusCode() + " - " + e.getReason());
                    }
                } catch (Exception e) {
                    otrosErrores.incrementAndGet();
                    System.err.println("Error inesperado: " + e.getMessage());
                }
            });
        }

        latch.countDown(); // ¡Dispara las peticiones en simultáneo!
        executorService.shutdown();
        executorService.awaitTermination(2, TimeUnit.MINUTES);

        System.out.println("=== RESULTADOS TEST DE CONCURRENCIA ===");
        System.out.println("Reservas exitosas: " + exitosas.get());
        System.out.println("Conflictos (409): " + conflictos.get());
        System.out.println("Otros errores: " + otrosErrores.get());

        // Aserción clave del TP: Solo 1 reserva debe ganar el asiento
        assertEquals(1, exitosas.get(), "Solo una reserva debe ser exitosa para el mismo asiento");
        assertEquals(hilosSimultaneos - 1, conflictos.get() + otrosErrores.get(), "El resto de peticiones debe fallar");
    }
}