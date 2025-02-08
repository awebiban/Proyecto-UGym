package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.dto.BonoDTO;
import com.example.demo.repository.dao.BonoRepository;
import com.example.demo.repository.dao.TipoBonoRepository;
import com.example.demo.repository.dao.UsuarioRepository;
import com.example.demo.repository.entity.Bono;
import com.example.demo.repository.entity.TipoBono;
import com.example.demo.repository.entity.Usuario;

@Service
public class BonoServiceImpl implements BonoService {

	@Autowired
    private BonoRepository bonoRepository;
	
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private TipoBonoRepository tipoBonoRepository;
    
    private static final int PRECIO_POR_CLASE_PRIVADA = 10;  // 10€ por clase


    @Override
    public BonoDTO recargarBono(Long usuarioId, int cantidad, TipoBono tipoBono) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Bono bono = bonoRepository.findByUsuarioIdAndTipoBono(usuarioId, tipoBono)
                .orElseGet(() -> Bono.builder()
                        .usuario(usuario)
                        .tipoBono(tipoBono)
                        .saldoClases(0)
                        .fechaInicio(tipoBono.getDuracionDias() != null ? LocalDate.now() : null)
                        .fechaFin(tipoBono.getDuracionDias() != null ? LocalDate.now().plusDays(tipoBono.getDuracionDias()) : null)
                        .build());

        if (tipoBono.getNombre().equalsIgnoreCase("General")) {
            bono.setSaldoClases(1);
            bono.setFechaInicio(LocalDate.now());
            bono.setFechaFin(LocalDate.now().plusDays(tipoBono.getDuracionDias()));
        } else if (tipoBono.getNombre().equalsIgnoreCase("Privado")) {
            bono.setSaldoClases(bono.getSaldoClases() + cantidad);
        }

        Bono bonoGuardado = bonoRepository.save(bono);
        return BonoDTO.convertToDto(bonoGuardado);
    }
    
    @Override
    public List<BonoDTO> obtenerBonosPorUsuario(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        List<Bono> bonosUsuario = bonoRepository.findByUsuarioId(usuarioId);

        return bonosUsuario.stream()
                .map(BonoDTO::convertToDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public BonoDTO consumirBono(Long usuarioId, Long bonoId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Bono bono = bonoRepository.findById(bonoId)
                .orElseThrow(() -> new RuntimeException("Bono no encontrado"));

        if (bono.getTipoBono().getNombre().equalsIgnoreCase("Privado")) {
            if (bono.getSaldoClases() > 0) {
                bono.setSaldoClases(bono.getSaldoClases() - 1);
                bonoRepository.save(bono);
            } else {
                throw new RuntimeException("No hay clases disponibles en este bono.");
            }
        }

        return BonoDTO.convertToDto(bono);
    }
    
   
    @Override
    public boolean updateBonosAfterPayment(Long usuarioId, int tipoBonoId, Double montoPago) {
        TipoBono tipoBono = tipoBonoRepository.findById((long) tipoBonoId)
                .orElseThrow(() -> new RuntimeException("Tipo de bono no encontrado"));

        if (tipoBono.getNombre().equalsIgnoreCase("General")) {
            Bono bonoGeneral = bonoRepository.findByUsuarioIdAndTipoBono(usuarioId, tipoBono)
                    .orElse(new Bono());
            bonoGeneral.setFechaInicio(LocalDate.now());
            bonoGeneral.setFechaFin(LocalDate.now().plusDays(tipoBono.getDuracionDias()));
            bonoGeneral.setSaldoClases(1);
            bonoRepository.save(bonoGeneral);
        } else if (tipoBono.getNombre().equalsIgnoreCase("Privado")) {
            Bono bonoPrivado = bonoRepository.findByUsuarioIdAndTipoBono(usuarioId, tipoBono)
                    .orElse(new Bono());
            int clasesAdicionales = (int) (montoPago / PRECIO_POR_CLASE_PRIVADA);
            bonoPrivado.setSaldoClases(bonoPrivado.getSaldoClases() + clasesAdicionales);
            System.out.println(bonoPrivado);
            bonoRepository.save(bonoPrivado);
        }
        return true;
    }
}

