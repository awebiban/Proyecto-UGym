package com.example.demo.web.controller;

import com.example.demo.model.dto.BonoDTO;
import com.example.demo.repository.entity.TipoBono;
import com.example.demo.service.BonoService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/bonos")
public class BonoController {

	@Autowired
    private BonoService bonoService;

	// Obtener los bonos de un usuario
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<BonoDTO>> obtenerBonosPorUsuario(@PathVariable Long usuarioId) {
        List<BonoDTO> bonos = bonoService.obtenerBonosPorUsuario(usuarioId);
        return ResponseEntity.ok(bonos);
    }

    // Recargar el bono
    @PutMapping("/recargar")
    public ResponseEntity<BonoDTO> recargarBono(@RequestParam Long usuarioId, @RequestParam int cantidad, @RequestBody TipoBono tipoBono) {
        BonoDTO bonoRecargado = bonoService.recargarBono(usuarioId, cantidad, tipoBono);
        return ResponseEntity.ok(bonoRecargado);
    }

    // Consumir clase de un bono privado
    @PutMapping("/consumir/{usuarioId}/{bonoId}")
    public ResponseEntity<BonoDTO> consumirBono(@PathVariable Long usuarioId, @PathVariable Long bonoId) {
    	System.out.println(usuarioId+bonoId);
    	
        BonoDTO bonoConsumido = bonoService.consumirBono(usuarioId, bonoId);
        return ResponseEntity.ok(bonoConsumido);
    }
}
