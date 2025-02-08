package com.example.demo.model.dto;

import com.example.demo.repository.entity.Horario;
import com.example.demo.repository.entity.Usuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HorarioDTO {

    private Long id;
    private Usuario empleado;
    private LocalTime horaInicio;
    private Integer duracionMinutos;
    private String diaSemana;
    private LocalDate fecha;
    private Integer plazasDisponibles;
    @SuppressWarnings("unused")
	private LocalTime horaFin;

    public LocalTime getHoraFin() {
        return (horaInicio != null && duracionMinutos != null)
                ? horaInicio.plusMinutes(duracionMinutos)
                : null;
    }

    public static HorarioDTO convertToDto(Horario horario) {
        
        return HorarioDTO.builder()
                .id(horario.getId())
                .empleado(horario.getEmpleado())
                .horaInicio(horario.getHoraInicio())
                .duracionMinutos(horario.getDuracionMinutos())
                .diaSemana(horario.getDiaSemana())
                .fecha(horario.getFecha())
                .plazasDisponibles(horario.getPlazasDisponibles())
                .horaFin(horario.getHoraFin())
                .build();
        
    }

    public static Horario convertToEntity(HorarioDTO dto) {
        
        return Horario.builder()
                .id(dto.getId())
                .empleado(dto.getEmpleado())
                .horaInicio(dto.getHoraInicio())
                .duracionMinutos(dto.getDuracionMinutos())
                .diaSemana(dto.getDiaSemana())
                .fecha(dto.getFecha())
                .plazasDisponibles(dto.getPlazasDisponibles())
                .horaFin(dto.getHoraFin())
                .build();
        
    }
}
