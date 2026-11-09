package com.ticketya.eventoservicio.service;

import com.ticketya.eventoservicio.dto.CrearEventoDTO;
import com.ticketya.eventoservicio.dto.EventoDTO;
import com.ticketya.eventoservicio.dto.LugarDTO;
import com.ticketya.eventoservicio.dto.SectorDTO;
import com.ticketya.eventoservicio.models.EstadoEvento;
import com.ticketya.eventoservicio.models.Evento;
import com.ticketya.eventoservicio.models.Lugar;
import com.ticketya.eventoservicio.models.Sector;
import com.ticketya.eventoservicio.repository.EventoRepository;
import com.ticketya.eventoservicio.repository.LugarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class EventoServiceImpl implements EventoService{
    private final EventoRepository eventoRepository;
    private final LugarRepository lugarRepository;

    @Autowired
    public EventoServiceImpl(EventoRepository eventoRepository, LugarRepository lugarRepository){
        this.eventoRepository = eventoRepository;
        this.lugarRepository = lugarRepository;
    }

    @Override
    @Transactional
    public EventoDTO crearEvento(CrearEventoDTO dto){
        //check que exista el lugar donde se quiera hacer el evento desde la base usando el idLugar del dto
        Lugar lugar = lugarRepository.findById(dto.getIdLugar())
                    .orElseThrow(() -> new RuntimeException("No se encontró el lugar con ID: " + dto.getIdLugar()));

        //instanciar el evento
        Evento evento = new Evento();
        evento.setNombre(dto.getNombre());
        evento.setDescripcion(dto.getDescripcion());
        evento.setTipo(dto.getTipo());
        evento.setOrganizadorId(dto.getOrganizadorId());
        evento.setParticipantes(dto.getParticipantes());
        evento.setFechaHora(dto.getFechaHora());
        evento.setLugar(lugar);
        evento.setEstado(EstadoEvento.BORRADOR); // el evento tiene que nacer con el BORRADOR hasta que se publique

        // mapear y agregar los sectores usando el metodo helper de Evento
        if(dto.getSectores() != null){
            for(SectorDTO s: dto.getSectores()){
                Sector sector = new Sector();
                sector.setNombre(s.getNombre());
                sector.setEsNumerado(s.getEsNumerado());
                sector.setPrecio(s.getPrecio());
                sector.setCapacidadMaxima(s.getCapacidadMaxima());
                sector.setFilas(s.getFilas());
                sector.setAsientosPorFila(s.getAsientosPorFila());

                evento.agregarSector(sector);
            }
        }

        // guardar en base de datos
        Evento eventoGuardado = eventoRepository.save(evento);

        //convertimos el EventoGuardado en EventoDTO
        return convertirADTO(eventoGuardado);
    }

    @Override
    @Transactional(readOnly = true)
    public EventoDTO obtenerPorId(Long id){
        Evento e = eventoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento no encontrado con ID: " + id));
        return convertirADTO(e);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventoDTO> obtenerTodos(){
        List<Evento> eventos = eventoRepository.findAll();
        List<EventoDTO> eventosDTO = new ArrayList<>();

        for(Evento e : eventos){
            EventoDTO dto = convertirADTO(e);
            eventosDTO.add(dto);
        }

        return eventosDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventoDTO> obtenerPorIdOrganizador(Long idOrganizador){
        List<Evento> eventos = eventoRepository.findByOrganizadorId(idOrganizador);
        List<EventoDTO> eventosDTO = new ArrayList<>();

        for(Evento e : eventos){
            EventoDTO dto = convertirADTO(e);
            eventosDTO.add(dto);
        }

        return eventosDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventoDTO> obtenerPorCiudadYRangoDeFechas(String ciudad, LocalDateTime fechaInicio, LocalDateTime fechaFin){
        List<Evento> eventos = eventoRepository.buscarPorCiudadYRangoFechas(ciudad,fechaInicio,fechaFin);
        List<EventoDTO> eventosDTO = new ArrayList<>();

        for(Evento e: eventos){
            EventoDTO dto = convertirADTO(e);
            eventosDTO.add(dto);
        }

        return eventosDTO;
    }

    @Override
    @Transactional
    public EventoDTO cambiarEstado(Long id, EstadoEvento nuevoEstado){
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("No existe un evento con ID: " + id + " por lo tanto no se le puede cambiar el estado."));

        evento.setEstado(nuevoEstado);
        Evento eventoActualizado = eventoRepository.save(evento);
        return convertirADTO(eventoActualizado);

    }

    @Override
    @Transactional
    public void eliminarEvento(Long id){
        if(!eventoRepository.existsById(id)){
            throw new RuntimeException("No se puede eliminar porque no existe un evento con ese ID: " + id);
        }
        eventoRepository.deleteById(id);
    }

    private EventoDTO convertirADTO(Evento evento) {
        LugarDTO lugarDTO = null;
        if (evento.getLugar() != null) {
            Lugar l = evento.getLugar();
            lugarDTO = new LugarDTO(
                    l.getId(),
                    l.getNombre(),
                    l.getDireccion(),
                    l.getCiudad(),
                    l.getLatitud(),
                    l.getLongitud(),
                    l.getCapacidadTotal()
            );
        }

        List<SectorDTO> sectoresDTO = new ArrayList<>();
        if (evento.getSectores() != null) {
            for (Sector s : evento.getSectores()) {
                SectorDTO sDto = new SectorDTO(
                        s.getId(),
                        s.getNombre(),
                        s.getEsNumerado(),
                        s.getCapacidadMaxima(),
                        s.getPrecio(),
                        s.getFilas(),
                        s.getAsientosPorFila()
                );
                sectoresDTO.add(sDto);
            }
        }

        return new EventoDTO(
                evento.getId(),
                evento.getNombre(),
                evento.getDescripcion(),
                evento.getTipo(),
                evento.getParticipantes(),
                evento.getOrganizadorId(),
                evento.getFechaHora(),
                lugarDTO,
                evento.getEstado(),
                sectoresDTO
        );
    }
}
