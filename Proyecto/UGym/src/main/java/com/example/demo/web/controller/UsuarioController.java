package com.example.demo.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.example.demo.model.dto.UsuarioDTO;
import com.example.demo.service.UsuarioService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/usuarios") 
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    //alta usuario
    @PostMapping("/altaEmpleado")
    public ResponseEntity<UsuarioDTO> altaUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        UsuarioDTO nuevoUsuario = usuarioService.altaEmpleado(usuarioDTO);
        return ResponseEntity.ok(nuevoUsuario);
    }

    //modificar usuario
    @PutMapping("/modificar/{id}")
    public ResponseEntity<UsuarioDTO> modificarUsuario(@PathVariable Long id, @RequestBody UsuarioDTO usuarioDTO) {
        UsuarioDTO usuarioActualizado = usuarioService.modificarUsuario(id, usuarioDTO);
        return ResponseEntity.ok(usuarioActualizado);
    }

    //listar usuarios
    @GetMapping("/listar")
    public ResponseEntity<List<UsuarioDTO>> listarUsuarios() {
        List<UsuarioDTO> usuarios = usuarioService.listarUsuarios();
        return ResponseEntity.ok(usuarios);
    }
    
 // obtener un usuario específico
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> obtenerUsuarioPorId(@PathVariable Long id) {
        UsuarioDTO usuario = usuarioService.findById(id);
        return ResponseEntity.ok(usuario);
    }
    
    // borrado logico
    @PutMapping("/borrar/{id}")
    public ResponseEntity<String> borrarUsuario(@PathVariable Long id) {
        usuarioService.deleteById(id);
        return ResponseEntity.ok("Usuario actualizado");
    }
    
    // reactivar usuario
    @PutMapping("/activar/{id}")
    public ResponseEntity<String> activarUsuario(@PathVariable Long id) {
        usuarioService.activarUsuario(id);
        return ResponseEntity.ok("Usuario actualizado");
    }
    
    
    @GetMapping("/imagen/{nombreArchivo}")
    public ResponseEntity<Resource> obtenerImagen(@PathVariable String nombreArchivo) {
        try {
            String rutaBase = System.getProperty("user.dir");
            String rutaCompleta = rutaBase + File.separator + "uploads/fotos_perfil";
            Path rutaArchivo = Paths.get(rutaCompleta).resolve(nombreArchivo).normalize();
            Resource recurso = new UrlResource(rutaArchivo.toUri());

            if (!recurso.exists()) {
                throw new RuntimeException("No se pudo encontrar el archivo: " + nombreArchivo);
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(recurso);
        } catch (Exception e) {
            return ResponseEntity.status(404).build();
        }
    }
    
    
    //Actualizar la Foto de Perfil
    @PostMapping("/actualizarFotoPerfil/{id}")
    public ResponseEntity<Map<String, String>> actualizarFotoPerfil(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            usuarioService.actualizarFotoPerfil(id, file);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Foto de perfil actualizada exitosamente");
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
        	
            Map<String, String> response = new HashMap<>();
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
        	
            Map<String, String> response = new HashMap<>();
            response.put("message", "Error al actualizar la foto de perfil");
            return ResponseEntity.status(500).body(response);
        }
    }
    
    
    // borrar fotoperfil
    @PutMapping("/borrarImg/{id}")
    public ResponseEntity<Map<String, String>> borrarFotoP(@PathVariable Long id) {
        Map<String, String> response = new HashMap<>();
        try {
            usuarioService.borrarImagen(id);
            response.put("message", "Foto de perfil eliminada exitosamente");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            response.put("message", "Error al eliminar la foto de perfil");
            return ResponseEntity.status(500).body(response);
        }
    }

    //Obtener todos los empleados
    @GetMapping("/empleados")
    public List<UsuarioDTO> getAllEmpleados() {
        return usuarioService.findAllEmpleados();
    }
}