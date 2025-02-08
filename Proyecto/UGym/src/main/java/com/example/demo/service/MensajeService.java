package com.example.demo.service;

import com.example.demo.model.dto.MensajeDTO;

import java.util.List;

public interface MensajeService {
	public MensajeDTO findById(Long id);
    MensajeDTO crearMensaje(MensajeDTO mensajeDTO);
    List<MensajeDTO> listarMensajesPorUsuario(Long idUsuario);
    void borrarMensaje(Long id);
}
