package com.example.demo.model.dto;

import java.time.LocalDate;


import com.example.demo.repository.entity.Reserva;

import lombok.Data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservaDTO {

    private Long id;
    private UsuarioDTO usuarioDTO;
    private HorarioDTO horarioDTO;
    private LocalDate fechaReserva;
    private String estado;
    
    public static ReservaDTO convertToDto(Reserva reserva) {
        return ReservaDTO.builder()
                .id(reserva.getId())
                .usuarioDTO(UsuarioDTO.convertToDto(reserva.getUsuario())) 
                .horarioDTO(HorarioDTO.convertToDto(reserva.getHorario()))
                .fechaReserva(reserva.getFechaReserva())
                .estado(String.valueOf(reserva.getEstado()))
                .build();
    }

    public static Reserva convertToEntity(ReservaDTO reservaDTO) {
        return Reserva.builder()
                .id(reservaDTO.getId())
                .fechaReserva(reservaDTO.getFechaReserva())
                .estado(Integer.parseInt(reservaDTO.getEstado()))
                .build();
    }
    
   
}
