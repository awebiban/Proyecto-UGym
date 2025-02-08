package com.example.demo.service;

import com.example.demo.model.dto.HorarioDTO;
import com.example.demo.repository.dao.HorarioRepository;
import com.example.demo.repository.entity.Horario;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HorarioServiceImpl implements HorarioService {

    @Autowired
    private HorarioRepository horarioRepository;

    // Método para listar horarios
    @Override
    public List<HorarioDTO> listarHorarios() {
        return horarioRepository.findAll()
                .stream()
                .map(HorarioDTO::convertToDto)
                .collect(Collectors.toList());
    }

    // Método para modificar un horario
    @Override
    public HorarioDTO modificarHorario(Long id, HorarioDTO horarioDTO) {
        Horario horario = horarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Horario no encontrado con ID: " + id));

        horario.setEmpleado(horarioDTO.getEmpleado());
        horario.setHoraInicio(horarioDTO.getHoraInicio());
        horario.setDuracionMinutos(horarioDTO.getDuracionMinutos());
        horario.setHoraFin(horarioDTO.getHoraFin());
        horario.setDiaSemana(horarioDTO.getDiaSemana());
        horario.setFecha(horarioDTO.getFecha());
        horario.setPlazasDisponibles(horarioDTO.getPlazasDisponibles());

        Horario horarioActualizado = horarioRepository.save(horario);
        return HorarioDTO.convertToDto(horarioActualizado);
    }

    @Override
    public HorarioDTO crearHorarioPrivado(HorarioDTO horarioDTO) {
        Horario nuevoHorario = new Horario();
        nuevoHorario.setEmpleado(horarioDTO.getEmpleado());
        nuevoHorario.setHoraInicio(horarioDTO.getHoraInicio());
        nuevoHorario.setHoraFin(horarioDTO.getHoraFin());
        nuevoHorario.setDuracionMinutos(horarioDTO.getDuracionMinutos());
        nuevoHorario.setDiaSemana(horarioDTO.getDiaSemana());
        nuevoHorario.setFecha(horarioDTO.getFecha());
        nuevoHorario.setPlazasDisponibles(1);

        Horario horarioGuardado = horarioRepository.save(nuevoHorario);
        return HorarioDTO.convertToDto(horarioGuardado);
    }
}
