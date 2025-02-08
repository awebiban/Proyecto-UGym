package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.dto.ClaseDTO;
import com.example.demo.model.dto.UsuarioDTO;
import com.example.demo.repository.dao.ClaseRepository;
import com.example.demo.repository.dao.HorarioRepository;
import com.example.demo.repository.entity.Clase;
import com.example.demo.repository.entity.Horario;

@Service
public class ClaseServiceImpl implements ClaseService{
	
	private static final Logger log = LoggerFactory.getLogger(ClaseServiceImpl.class);
	
	@Autowired
	private ClaseRepository claseRepository;
	
	@Autowired
	private HorarioRepository horarioRepository;

	@Override
	public ClaseDTO findById(Long id) {
    	
		return claseRepository.findById(id)
				.map(ClaseDTO::convertToDTO)
				.orElseThrow(() -> new RuntimeException("Clase no encontrada"));
	}
	
	public List<ClaseDTO> listarClases() {
		
		log.info("LISTANDO CLASES - listarClases - " + ClaseServiceImpl.class);
		
		List<Clase> listaClases = claseRepository.findAll();
		List<ClaseDTO> listaClasesDTO = new ArrayList<>();
		
		for (Clase c : listaClases) {
			ClaseDTO cdto = ClaseDTO.convertToDTO(c);
			listaClasesDTO.add(cdto);
		}
		return listaClasesDTO;
		
	}

	public ClaseDTO altaClase(ClaseDTO claseDTO) {
		
		log.info("CREANDO CLASE - altaClase - " + ClaseServiceImpl.class);
		
		Horario horario = Horario.builder().build();
		
		Optional<Horario> opHorario = horarioRepository.findById(claseDTO.getHorario().getId());
		
		if (opHorario.isPresent()) {
			horario = opHorario.get();
		}
		
		Clase newClase = ClaseDTO.convertToEntity(claseDTO, horario);
		claseRepository.save(newClase);
		
		return ClaseDTO.convertToDTO(newClase);
	}

	public ClaseDTO modificarClase(ClaseDTO claseDTO) {
		
		log.info("MODIFICANDO CLASE SELECCIONADA - modificarClase - " + ClaseServiceImpl.class);
		
		Optional<Clase> claseAModificar = claseRepository.findById(claseDTO.getId());
		Clase claseModificable = new Clase();
		
			if (claseAModificar.isPresent()) {
				
				claseModificable = claseAModificar.get();
				
				claseModificable.setId(claseDTO.getId());
				claseModificable.setNombreClase(claseDTO.getNombreClase());
				claseModificable.setDescripcion(claseDTO.getDescripcion());
				claseModificable.setDuracionMinutos(claseDTO.getDuracionMinutos());
				claseModificable.setCapacidadMaxima(claseDTO.getCapacidadMaxima());
				claseModificable.setCategoria(claseDTO.getCategoria());
				claseModificable.setTipoClase(claseDTO.getTipoClase());
				
				log.info("GUARDANDO CAMBIOS MODIFICADOS - modificarClase - " + ClaseServiceImpl.class);
				claseRepository.save(claseModificable);
			}
			
		return ClaseDTO.convertToDTO(claseModificable);
	}
	/*
	public void desactivarClase(Long id, ClaseDTO claseDTO) {
		
		log.info("BORRANDO CLASE SELECCIONADA - borrarClase - " + ClaseServiceImpl.class);
		
		
		Clase newClase = ClaseDTO.convertToEntity(claseDTO);
		
		claseRepository.save(newClase);
		//claseRepository.deleteById(newClase.getId());
	}
	*/
	
	
	

}
