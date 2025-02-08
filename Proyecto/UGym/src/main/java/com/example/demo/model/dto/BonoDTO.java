package com.example.demo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import com.example.demo.repository.entity.Bono;
import com.example.demo.repository.entity.TipoBono;
import com.example.demo.repository.entity.Usuario;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BonoDTO {
    private Long id;
    private UsuarioDTO usuarioDTO;
    private TipoBono tipoBono;
    private Integer saldoClases;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    public static BonoDTO convertToDto(Bono bono) {
        return BonoDTO.builder()
                .id(bono.getId())
                .usuarioDTO(UsuarioDTO.convertToDto(bono.getUsuario()))
                .tipoBono(bono.getTipoBono())
                .saldoClases(bono.getSaldoClases())
                .fechaInicio(bono.getFechaInicio())
                .fechaFin(bono.getFechaFin())
                .build();
    }

    public static Bono convertToEntity(BonoDTO bonoDTO, Usuario usuario) {
        return Bono.builder()
                .id(bonoDTO.getId())
                .usuario(usuario)
                .tipoBono(bonoDTO.getTipoBono())
                .saldoClases(bonoDTO.getSaldoClases())
                .fechaInicio(bonoDTO.getFechaInicio())
                .fechaFin(bonoDTO.getFechaFin())
                .build();
    }
}
