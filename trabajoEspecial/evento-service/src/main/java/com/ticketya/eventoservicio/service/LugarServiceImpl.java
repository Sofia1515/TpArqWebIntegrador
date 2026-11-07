package com.ticketya.eventoservicio.service;

import com.ticketya.eventoservicio.dto.LugarDTO;
import com.ticketya.eventoservicio.models.Lugar;
import com.ticketya.eventoservicio.repository.LugarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class LugarServiceImpl implements LugarService{

    private LugarRepository lugarRepository;

    @Autowired
    public LugarServiceImpl(LugarRepository lugarRepository){
        this.lugarRepository = lugarRepository;
    }

    @Override
    @Transactional
    public LugarDTO crearLugar(LugarDTO dto){
        Lugar lugar = new Lugar();
        lugar.setNombre(dto.getNombre());
        lugar.setDireccion(dto.getDireccion());
        lugar.setCiudad(dto.getCiudad());
        lugar.setLatitud(dto.getLatitud());
        lugar.setLongitud(dto.getLongitud());
        lugar.setCapacidadTotal(dto.getCapacidadTotal());

        Lugar lugarGuardado = lugarRepository.save(lugar);

        return convertirADTO(lugarGuardado);
    }

    @Override
    @Transactional(readOnly = true)
    public LugarDTO obtenerPorId(Long id){
        Lugar lugar = lugarRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("No existe un lugar con el ID: " + id));

        return convertirADTO(lugar);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LugarDTO> obtenerTodos(){
        List<Lugar> lugares = lugarRepository.findAll();
        List<LugarDTO> lugaresDTO = new ArrayList<>();

        for(Lugar l: lugares){
            LugarDTO dto = convertirADTO(l);
            lugaresDTO.add(dto);
        }

        return lugaresDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public List<LugarDTO> obtenerPorCiudad(String ciudad){
        List<Lugar> lugares = lugarRepository.findByCiudadIgnoreCase(ciudad);
        List<LugarDTO> lugaresDTO = new ArrayList<>();

        for(Lugar l: lugares){
            LugarDTO dto = convertirADTO(l);
            lugaresDTO.add(dto);
        }

        return lugaresDTO;
    }

    private LugarDTO convertirADTO(Lugar lugar) {
        return new LugarDTO(
                lugar.getId(),
                lugar.getNombre(),
                lugar.getDireccion(),
                lugar.getCiudad(),
                lugar.getLatitud(),
                lugar.getLongitud(),
                lugar.getCapacidadTotal()
        );
    }
}
