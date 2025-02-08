package com.example.demo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.example.demo.repository.entity.Mensaje;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MensajeDTO {

    private Long id;
    private Long idUsuario;
    private String titulo;
    private String contenido;
    private LocalDate fechaEnvio;

    public static MensajeDTO convertToDto(Mensaje mensaje) {
        return MensajeDTO.builder()
                .id(mensaje.getId())
                .idUsuario(mensaje.getUsuario().getId())
                .titulo(mensaje.getTitulo())
                .contenido(mensaje.getContenido())
                .fechaEnvio(mensaje.getFechaEnvio())
                .build();
    }

    public static Mensaje convertToEntity(MensajeDTO mensajeDTO) {
        return Mensaje.builder()
                .id(mensajeDTO.getId())
                .titulo(mensajeDTO.getTitulo())
                .contenido(mensajeDTO.getContenido())
                .fechaEnvio(mensajeDTO.getFechaEnvio())
                .build();
    }
}
