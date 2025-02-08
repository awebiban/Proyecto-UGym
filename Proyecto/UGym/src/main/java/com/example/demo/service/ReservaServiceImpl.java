package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.dto.HorarioDTO;
import com.example.demo.model.dto.ReservaDTO;
import com.example.demo.model.dto.UsuarioDTO;
import com.example.demo.repository.dao.HorarioRepository;
import com.example.demo.repository.dao.ReservaRepository;
import com.example.demo.repository.dao.UsuarioRepository;
import com.example.demo.repository.entity.Horario;
import com.example.demo.repository.entity.Reserva;
import com.example.demo.repository.entity.Usuario;

import java.util.List;
import java.util.Optional;

@Service
public class ReservaServiceImpl implements ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private HorarioRepository horarioRepository;

    // Crear una nueva reserva
    public ReservaDTO crearReserva(ReservaDTO reservaDTO) {
        Reserva reserva = new Reserva();

        
        // Buscar usuario y horario por su ID
     // Buscar usuario y horario por su ID usando findById()
        Optional<Usuario> usuario = usuarioRepository.findById(reservaDTO.getUsuarioDTO().getId());
        Optional<Horario> horario = horarioRepository.findById(reservaDTO.getHorarioDTO().getId());


        // Verificar si el usuario y el horario existen
        if (usuario.isPresent() && horario.isPresent()) {
            reserva.setUsuario(usuario.get());
            reserva.setHorario(horario.get());
            reserva.setFechaReserva(reservaDTO.getFechaReserva());
            reserva.setEstado(1);
            
            System.out.println(reserva);


            // Guardar la reserva
            reserva = reservaRepository.save(reserva);

            // Convertir a DTO y devolver
            reservaDTO.setId(reserva.getId());
            // Convertir los objetos Usuario y Horario a sus respectivos DTOs completos
            reservaDTO.setUsuarioDTO(UsuarioDTO.convertToDto(reserva.getUsuario()));
            reservaDTO.setHorarioDTO(HorarioDTO.convertToDto(reserva.getHorario()));
            return reservaDTO;
        }
        return null; // O lanzar una excepción si es necesario
    }

    // Obtener todas las reservas
    public List<ReservaDTO> obtenerReservas() {
        List<Reserva> reservas = reservaRepository.findAll(); 
        return reservas.stream().map(reserva -> {
            // Convertimos la entidad a DTO
            ReservaDTO dto = new ReservaDTO();
            dto.setId(reserva.getId());
            dto.setFechaReserva(reserva.getFechaReserva());
            dto.setEstado(String.valueOf(reserva.getEstado()));
            // Convertimos los objetos Usuario y Horario a sus respectivos DTOs completos
            dto.setUsuarioDTO(UsuarioDTO.convertToDto(reserva.getUsuario()));
            dto.setHorarioDTO(HorarioDTO.convertToDto(reserva.getHorario()));
            return dto;
        }).toList();
    }

    // Obtener una reserva por su ID
    public Optional<ReservaDTO> obtenerReservaPorId(Long id) {
        Optional<Reserva> reserva = reservaRepository.findById(id);
        if (reserva.isPresent()) {
            Reserva r = reserva.get();
            // Convertir la entidad a DTO
            ReservaDTO dto = new ReservaDTO();
            dto.setId(r.getId());
            dto.setFechaReserva(r.getFechaReserva());
            dto.setEstado(String.valueOf(r.getEstado()));
            // Convertimos los objetos Usuario y Horario a sus respectivos DTOs completos
            dto.setUsuarioDTO(UsuarioDTO.convertToDto(r.getUsuario()));
            dto.setHorarioDTO(HorarioDTO.convertToDto(r.getHorario()));
            return Optional.of(dto);
        }
        return Optional.empty();
    }

    public ReservaDTO actualizarReserva(Long id, ReservaDTO reservaDTO) {
        Optional<Reserva> reservaOpt = reservaRepository.findById(id);
        if (reservaOpt.isPresent()) {
            Reserva reserva = reservaOpt.get();

            // Actualizar los campos de la reserva con los valores proporcionados en el DTO
            if (reservaDTO.getFechaReserva() != null) {
                reserva.setFechaReserva(reservaDTO.getFechaReserva());
            }
            if (reservaDTO.getEstado() != null) {
            	reserva.setEstado(Integer.parseInt(reservaDTO.getEstado()));
            }
            if (reservaDTO.getUsuarioDTO() != null) {
            	
            	Optional <Usuario> buscarUsuario = usuarioRepository.findById(reservaDTO.getUsuarioDTO().getId());
            	if (buscarUsuario.isPresent()) {  // Comprueba que lo que devuelve el repo
            									  // no es nulo y dentro geteamos el usuario
            		Usuario usuarioEncontrado = buscarUsuario.get();
            		
            		reservaDTO.setUsuarioDTO(UsuarioDTO.convertToDto(usuarioEncontrado));
            		
            		System.out.println(reservaDTO.getUsuarioDTO().toString());
                    reserva.setUsuario(UsuarioDTO.convertToEntity(reservaDTO.getUsuarioDTO()));
            	}
            }
            if (reservaDTO.getHorarioDTO() != null) {
            	Optional <Horario> buscarHorario = horarioRepository.findById(reservaDTO.getUsuarioDTO().getId());
            	if (buscarHorario.isPresent()) {  // Comprueba que lo que devuelve el repo
            									  // no es nulo y dentro geteamos el usuario
            		Horario horarioEncontrado = buscarHorario.get();
            		
            		reservaDTO.setHorarioDTO(HorarioDTO.convertToDto(horarioEncontrado));
            		
            		System.out.println(reservaDTO.getHorarioDTO().toString());
                    reserva.setHorario(HorarioDTO.convertToEntity(reservaDTO.getHorarioDTO()));
            	}
            	reserva.setHorario(HorarioDTO.convertToEntity(reservaDTO.getHorarioDTO()));
            }

            // Guardar los cambios en la base de datos
            reserva = reservaRepository.save(reserva);

            // Convertir la entidad actualizada a DTO y devolverla
            ReservaDTO dto = new ReservaDTO();
            dto.setId(reserva.getId());
            dto.setFechaReserva(reserva.getFechaReserva());
            dto.setEstado(String.valueOf(reserva.getEstado()));
            dto.setUsuarioDTO(UsuarioDTO.convertToDto(reserva.getUsuario()));
            dto.setHorarioDTO(HorarioDTO.convertToDto(reserva.getHorario()));
            return dto;
        }
        return null; // O lanzar una excepción si la reserva no se encuentra
    }


 // Eliminar una reserva por ID
    public void cancelarReserva(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
        reserva.setEstado(0);
        reservaRepository.save(reserva);
        
    }
    
    @Override
    public List<ReservaDTO> obtenerReservasPorUsuario(Long idUsuario) {
        List<Reserva> reservas = reservaRepository.findAllByUsuarioId(idUsuario);

        return reservas.stream().map(reserva -> {
            ReservaDTO dto = new ReservaDTO();
            dto.setId(reserva.getId());
            dto.setFechaReserva(reserva.getFechaReserva());
            dto.setEstado(String.valueOf(reserva.getEstado()));
            dto.setUsuarioDTO(UsuarioDTO.convertToDto(reserva.getUsuario()));
            dto.setHorarioDTO(HorarioDTO.convertToDto(reserva.getHorario()));
            return dto;
        }).toList();
    }

}

