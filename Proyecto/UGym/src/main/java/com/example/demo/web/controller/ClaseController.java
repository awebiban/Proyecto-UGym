package com.example.demo.web.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.example.demo.model.dto.ClaseDTO;
import com.example.demo.model.dto.UsuarioDTO;
import com.example.demo.service.ClaseService;



@RestController
@RequestMapping("/api/clases")
public class ClaseController {
	
	@Autowired
	private ClaseService claseService;

	//listar clases
	@GetMapping("/listar")
	public ResponseEntity<List<ClaseDTO>> listarClases() {
		List<ClaseDTO> listaClasesDTO = claseService.listarClases();
		
		return ResponseEntity.ok(listaClasesDTO);
	}
	
	@GetMapping("/{id}")
    public ResponseEntity<ClaseDTO> obtenerClasePorId(@PathVariable Long id) {
        ClaseDTO clase = claseService.findById(id);
        return ResponseEntity.ok(clase);
    }
	
	@PostMapping("/alta")
	public ResponseEntity<ClaseDTO> altaClase(@RequestBody ClaseDTO claseDTO) {
		System.out.println(claseDTO);
		ClaseDTO newClaseDTO = claseService.altaClase(claseDTO);
		return ResponseEntity.ok(newClaseDTO);
	}
	
	@PutMapping("/modificar")
	public ResponseEntity<ClaseDTO> modificarClase(@RequestBody ClaseDTO claseDTO) {
		ClaseDTO modifiedClass = claseService.modificarClase(claseDTO);
		return ResponseEntity.ok(modifiedClass);
	}
	
	//desactivar o eliminar metodo por no utilizacion!!!!!!
	/*
	@PutMapping("/desactivar/{id}")
	public void desactivar(@PathVariable Long id) {
		claseService.desactivarClase(id);
	}
	*/
}
