package com.example.demo.service;

import java.util.List;

import com.example.demo.model.dto.HorarioDTO;

public interface HorarioService {

	public HorarioDTO modificarHorario(Long id, HorarioDTO horarioDTO);
	public List<HorarioDTO> listarHorarios();
	HorarioDTO crearHorarioPrivado(HorarioDTO horarioDTO);
	
}
