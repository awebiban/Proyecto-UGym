package com.example.demo.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.dto.UsuarioDTO;

public interface UsuarioService {
	public UsuarioDTO findById(Long id);
    UsuarioDTO altaEmpleado(UsuarioDTO usuarioDTO);
    UsuarioDTO modificarUsuario(Long id, UsuarioDTO usuarioDTO);
    List<UsuarioDTO> listarUsuarios();
	void deleteById(Long id);
	void actualizarFotoPerfil(Long usuarioId, MultipartFile file) throws IOException;
	void activarUsuario(Long id);
	void borrarImagen(Long id);
	List<UsuarioDTO> findAllEmpleados();
    
}