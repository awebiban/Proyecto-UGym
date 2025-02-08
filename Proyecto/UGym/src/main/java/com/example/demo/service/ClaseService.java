package com.example.demo.service;

import java.util.List;

import com.example.demo.model.dto.ClaseDTO;

public interface ClaseService {

	ClaseDTO findById(Long id);
	
	List<ClaseDTO> listarClases();
	
	ClaseDTO altaClase(ClaseDTO claseDTO);
	
	ClaseDTO modificarClase(ClaseDTO claseDTO);
	
	//void desactivarClase(Long id, ClaseDTO claseDTO);
}
