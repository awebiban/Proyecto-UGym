package com.example.demo.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.dto.HorarioDTO;
import com.example.demo.service.HorarioService;

@RestController
@RequestMapping("/api/horario")
public class HorarioController {

	@Autowired
	private HorarioService horarioService;

	// modificar horario
	@PutMapping("/modificarHorario/{id}")
	public ResponseEntity<HorarioDTO> modificarHorario(@PathVariable Long id, @RequestBody HorarioDTO horarioDTO) {
		HorarioDTO modifiedTimetable = horarioService.modificarHorario(id, horarioDTO);
		return ResponseEntity.ok(modifiedTimetable);
	}

	// listar horario
	@GetMapping("/listarHorario")
	public ResponseEntity<List<HorarioDTO>> listarHorario() {
		List<HorarioDTO> listaHorariosDTO = horarioService.listarHorarios();
		return ResponseEntity.ok(listaHorariosDTO);
	}

	//crear horario
	@PostMapping("/crear")
	public HorarioDTO crearHorarioPrivado(@RequestBody HorarioDTO horarioDTO) {
		System.out.println(horarioDTO);
		return horarioService.crearHorarioPrivado(horarioDTO);
	}
}
