package com.example.demo.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.dto.MensajeDTO;
import com.example.demo.service.MensajeService;

import java.util.List;

@RestController
@RequestMapping("/api/mensajes")
public class MensajeController {

    @Autowired
    private MensajeService mensajeService;

    // listar mensajes de un usuario
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<MensajeDTO>> listarMensajesPorUsuario(@PathVariable Long usuarioId) {
        List<MensajeDTO> mensajes = mensajeService.listarMensajesPorUsuario(usuarioId);
        return ResponseEntity.ok(mensajes);
    }
    
 // obtener un mensaje específico
    @GetMapping("/ver/{mensajeId}")
    public ResponseEntity<MensajeDTO> obtenerMensajePorId(@PathVariable Long mensajeId) {
        MensajeDTO mensaje = mensajeService.findById(mensajeId);
        return ResponseEntity.ok(mensaje);
    }

    // crear un mensaje
    @PostMapping
    public ResponseEntity<MensajeDTO> crearMensaje(@RequestBody MensajeDTO mensajeDTO) {
        MensajeDTO nuevoMensaje = mensajeService.crearMensaje(mensajeDTO);
        return ResponseEntity.ok(nuevoMensaje);
    }

    // borrar un mensaje
    @DeleteMapping("/{mensajeId}")
    public ResponseEntity<Void> borrarMensaje(@PathVariable Long mensajeId) {
        mensajeService.borrarMensaje(mensajeId);
        return ResponseEntity.noContent().build();
    }
}
