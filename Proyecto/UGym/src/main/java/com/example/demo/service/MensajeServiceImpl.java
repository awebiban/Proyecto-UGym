package com.example.demo.service;

import com.example.demo.model.dto.MensajeDTO;
import com.example.demo.repository.dao.MensajeRepository;
import com.example.demo.repository.dao.UsuarioRepository;
import com.example.demo.repository.entity.Mensaje;
import com.example.demo.repository.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MensajeServiceImpl implements MensajeService {

    @Autowired
    private MensajeRepository mensajeRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public MensajeDTO crearMensaje(MensajeDTO mensajeDTO) {
        Usuario usuario = usuarioRepository.findById(mensajeDTO.getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Mensaje mensaje = Mensaje.builder()
                .usuario(usuario)
                .titulo(mensajeDTO.getTitulo())
                .contenido(mensajeDTO.getContenido())
                .fechaEnvio(LocalDate.now())
                .build();
        
        Mensaje savedMensaje = mensajeRepository.save(mensaje);
        
        return MensajeDTO.builder()
                .id(savedMensaje.getId())
                .idUsuario(savedMensaje.getUsuario().getId())
                .titulo(savedMensaje.getTitulo())
                .contenido(savedMensaje.getContenido())
                .fechaEnvio(savedMensaje.getFechaEnvio())
                .build();
    }

    @Override
    public List<MensajeDTO> listarMensajesPorUsuario(Long idUsuario) {
        List<Mensaje> mensajes = mensajeRepository.findByUsuarioId(idUsuario);
        return mensajes.stream()
                .map(mensaje -> MensajeDTO.builder()
                        .id(mensaje.getId())
                        .idUsuario(mensaje.getUsuario().getId())
                        .titulo(mensaje.getTitulo())
                        .contenido(mensaje.getContenido())
                        .fechaEnvio(mensaje.getFechaEnvio())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public void borrarMensaje(Long id) {
        if (!mensajeRepository.existsById(id)) {
            throw new RuntimeException("Mensaje no encontrado");
        }
        mensajeRepository.deleteById(id);
    }

	@Override
	public MensajeDTO findById(Long id) {
		
		return mensajeRepository.findById(id)
                .map(MensajeDTO::convertToDto)
                .orElse(null);
	}
}
