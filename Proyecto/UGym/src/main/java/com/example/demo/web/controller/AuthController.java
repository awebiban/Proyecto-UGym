package com.example.demo.web.controller;

import com.example.demo.model.dto.UsuarioDTO;
import com.example.demo.service.UsuarioServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
    private UsuarioServiceImpl usuarioService;

	@PostMapping("/registro")
	public HttpStatus registrarUsuario(@RequestParam("nombre") String nombre,
	                                               @RequestParam("email") String email,
	                                               @RequestParam("apellido") String apellido,
	                                               @RequestParam("password") String contrasena,
	                                               @RequestParam("rol") String rol,
	                                               @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
	    
	    // Crear el DTO con los valores recibidos
	    UsuarioDTO usuarioDTO = new UsuarioDTO();
	    usuarioDTO.setNombre(nombre);
	    usuarioDTO.setEmail(email);
	    usuarioDTO.setApellido(apellido);
	    usuarioDTO.setPassword(contrasena);
	    usuarioDTO.setRol(rol);
	    
	    // Registrar el usuario
	    usuarioService.registro(usuarioDTO, file);
	    
	    return HttpStatus.OK;
	}



    @PostMapping("/login")
    public ResponseEntity<String> loginUsuario(@RequestParam String email, @RequestParam String contraseña) {
        try {
            String usuarioId = usuarioService.login(email, contraseña);
            return ResponseEntity.ok(usuarioId);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Credenciales invalidas");
        }
    }
}
