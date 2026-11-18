package com.ticketya.reservaServicio.dto;

public class ValidarQrResponseDTO {
        private boolean valida;
        private String mensaje;
        private Long idEntrada;
        private Long idSector;
        private Integer fila;
        private Integer asiento;

        public ValidarQrResponseDTO(boolean valida, String mensaje, Long idEntrada, Long idSector, Integer fila, Integer asiento) {
            this.valida = valida;
            this.mensaje = mensaje;
            this.idEntrada = idEntrada;
            this.idSector = idSector;
            this.fila = fila;
            this.asiento = asiento;
        }
}
