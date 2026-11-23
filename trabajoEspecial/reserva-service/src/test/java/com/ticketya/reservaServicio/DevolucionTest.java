package com.ticketya.reservaServicio;

import com.ticketya.reservaServicio.cliente.EventoClient;
import com.ticketya.reservaServicio.dto.EventoDTO;
import com.ticketya.reservaServicio.models.*;
import com.ticketya.reservaServicio.repository.EntradaRepository;
import com.ticketya.reservaServicio.service.ReservaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DevolucionTest {

    @Mock
    private EntradaRepository entradaRepository;

    @Mock
    private EventoClient eventoClient;

    @InjectMocks
    private ReservaService reservaService;

    private Entrada entradaMock;
    private EventoDTO eventoFuturo;
    private EventoDTO eventoCercano;

    @BeforeEach
    void setUp() {
        // Armamos una entrada falsa
        Compra compra = new Compra();
        compra.setIdUsuario(1L);
        compra.setIdEvento(10L);

        entradaMock = new Entrada();
        entradaMock.setId(100L);
        entradaMock.setCompra(compra);
        entradaMock.setEstadoEntrada(EstadoEntrada.NO_USADA);

        // Evento que pasa en 5 días (se puede devolver)
        eventoFuturo = new EventoDTO();
        eventoFuturo.setId(10L);
        eventoFuturo.setFechaHora(LocalDateTime.now().plusDays(5));

        // Evento que pasa en 1 día (NO se puede devolver)
        eventoCercano = new EventoDTO();
        eventoCercano.setId(10L);
        eventoCercano.setFechaHora(LocalDateTime.now().plusDays(1));
    }

    @Test
    void testDevolucionExitosaPorqueFaltanMasDe72Horas() {
        // Configuramos los mocks
        when(entradaRepository.findById(100L)).thenReturn(Optional.of(entradaMock));
        when(eventoClient.obtenerEventoPorId(10L)).thenReturn(eventoFuturo);

        // Ejecutamos el método
        assertDoesNotThrow(() -> reservaService.solicitarDevolucion(100L, 1L));

        // Verificamos que la entrada cambió de estado y se guardó
        assertEquals(EstadoEntrada.PENDIENTE_DEVOLUCION, entradaMock.getEstadoEntrada());
        verify(entradaRepository, times(1)).save(entradaMock);
    }

    @Test
    void testDevolucionFallidaPorqueFaltanMenosDe72Horas() {
        when(entradaRepository.findById(100L)).thenReturn(Optional.of(entradaMock));
        when(eventoClient.obtenerEventoPorId(10L)).thenReturn(eventoCercano);

        // Verificamos que lance la excepción
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            reservaService.solicitarDevolucion(100L, 1L);
        });

        assertTrue(exception.getMessage().contains("72 hs"));
        // Verificamos que NO se guardó nada en la BD
        verify(entradaRepository, never()).save(any());
    }
}