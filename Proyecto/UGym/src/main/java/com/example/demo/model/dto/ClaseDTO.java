package com.example.demo.model.dto;


import com.example.demo.repository.entity.Clase;
import com.example.demo.repository.entity.Horario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@AllArgsConstructor
@Builder
public class ClaseDTO {
	
	
    private Long id;
	private String NombreClase;
	private String descripcion;
	private String categoria;
	private int duracionMinutos;
	private int capacidadMaxima;
	private int TipoClase;
	private String imagen;
	private HorarioDTO horario;
	
	public ClaseDTO() {
		this.duracionMinutos = 60;
	}
	
	public static ClaseDTO convertToDTO(Clase clase) {
		return ClaseDTO.builder()
				.id(clase.getId())
				.NombreClase(clase.getNombreClase())
				.descripcion(clase.getDescripcion())
				.categoria(clase.getCategoria())
				.duracionMinutos(clase.getDuracionMinutos())
				.capacidadMaxima(clase.getCapacidadMaxima())
				.TipoClase(clase.getTipoClase())
				.horario(HorarioDTO.convertToDto(clase.getHorario()))
				.build();
	}
	
	public static Clase convertToEntity(ClaseDTO cdto, Horario horario) {
		return Clase.builder()
				.id(cdto.getId())
				.nombreClase(cdto.getNombreClase())
				.descripcion(cdto.getDescripcion())
				.categoria(cdto.getCategoria())
				.duracionMinutos(cdto.getDuracionMinutos())
				.capacidadMaxima(cdto.getCapacidadMaxima())
				.horario(horario)
				.tipoClase(cdto.getTipoClase())
				.build();
	}
	
}
