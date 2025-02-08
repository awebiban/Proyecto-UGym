package com.example.demo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import com.example.demo.repository.entity.Usuario;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioDTO {

    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private String rol;
    private LocalDate fechaRegistro;
    private String password;
    private int estado;
    private String imagen;


    public static UsuarioDTO convertToDto(Usuario usuario) {
        return UsuarioDTO.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .apellido(usuario.getApellido())
                .email(usuario.getEmail())
                .rol(usuario.getRol().name())
                .fechaRegistro(usuario.getFechaRegistro())
                .estado(usuario.getEstado())
                .imagen(usuario.getImagen())
                .build();
    }

    public static Usuario convertToEntity(UsuarioDTO usuarioDTO) {
        return Usuario.builder()
                .id(usuarioDTO.getId())
                .nombre(usuarioDTO.getNombre())
                .apellido(usuarioDTO.getApellido())
                .email(usuarioDTO.getEmail())
                .rol(Usuario.Rol.valueOf(usuarioDTO.getRol()))
                .fechaRegistro(usuarioDTO.getFechaRegistro())
                .password(usuarioDTO.getPassword())
                .estado(usuarioDTO.getEstado())
                .imagen(usuarioDTO.getImagen())
                .build();
    }
}
