package com.example.demo.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.dto.ClaseDTO;
import com.example.demo.model.dto.ReservaDTO;
import com.example.demo.service.ReservaService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    // Crear una reserva
    @PostMapping("/reserva/add")
    public ResponseEntity<ReservaDTO> crearReserva(@RequestBody ReservaDTO reservaDTO) {
    	System.out.println(reservaDTO);
        if (reservaDTO.getUsuarioDTO() == null || reservaDTO.getUsuarioDTO().getId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (reservaDTO.getHorarioDTO() == null || reservaDTO.getHorarioDTO().getId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        ReservaDTO nuevaReserva = reservaService.crearReserva(reservaDTO);
        return new ResponseEntity<>(nuevaReserva, HttpStatus.CREATED);
    }
    
    //Borrar Reserva
    @PutMapping("/desactivar/{id}")
	public HttpStatus eliminarReserva(@PathVariable Long id) {
		reservaService.cancelarReserva(id);
		
		return HttpStatus.OK;
	}
    
    //Actualizar Reserva
    @PutMapping("/modificar/{id}")
	public ResponseEntity<ReservaDTO> modificarReserva(@PathVariable Long id, @RequestBody ReservaDTO reservaDTO) {
    	System.out.println("Hola");
		ReservaDTO modifiedClass = reservaService.actualizarReserva(id, reservaDTO);
		return ResponseEntity.ok(modifiedClass);
	}
    
    // Obtener todas las reservas
    @GetMapping("/reserva")
    public ResponseEntity<List<ReservaDTO>> obtenerReservas() {
        List<ReservaDTO> reservas = reservaService.obtenerReservas();
        System.out.println(reservas.toString());
        return new ResponseEntity<>(reservas, HttpStatus.OK);
    }

    // Obtener una reserva por ID
    @GetMapping("reserva/{id}")
    public ResponseEntity<ReservaDTO> obtenerReservaPorId(@PathVariable("id") Long id) {
        Optional<ReservaDTO> reservaDTO = reservaService.obtenerReservaPorId(id);
        return reservaDTO.map(reserva -> new ResponseEntity<>(reserva, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<ReservaDTO>> obtenerReservasPorUsuario(@PathVariable Long idUsuario) {
        List<ReservaDTO> reservas = reservaService.obtenerReservasPorUsuario(idUsuario);
        return new ResponseEntity<>(reservas, HttpStatus.OK);
    }
}