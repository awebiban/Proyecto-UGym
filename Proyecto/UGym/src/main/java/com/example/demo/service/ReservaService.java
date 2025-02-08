package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.model.dto.ReservaDTO;

public interface ReservaService {
    ReservaDTO crearReserva(ReservaDTO reservaDTO);
    List<ReservaDTO> obtenerReservas();
    ReservaDTO actualizarReserva(Long id, ReservaDTO reservaDTO);
    void cancelarReserva(Long id);
    Optional<ReservaDTO> obtenerReservaPorId(Long id);
    List<ReservaDTO> obtenerReservasPorUsuario(Long idUsuario);
}
