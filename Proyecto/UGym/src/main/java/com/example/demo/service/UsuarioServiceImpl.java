package com.example.demo.service;

import com.example.demo.model.dto.MensajeDTO;
import com.example.demo.model.dto.UsuarioDTO;
import com.example.demo.repository.dao.BonoRepository;
import com.example.demo.repository.dao.MensajeRepository;
import com.example.demo.repository.dao.TipoBonoRepository;
import com.example.demo.repository.dao.UsuarioRepository;
import com.example.demo.repository.entity.Bono;
import com.example.demo.repository.entity.TipoBono;
import com.example.demo.repository.entity.Usuario;
import com.example.demo.repository.entity.Usuario.Rol;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.nio.file.Files;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements UsuarioService, UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private MensajeService mensajeService;
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private BonoRepository bonoRepository;
    
    @Autowired
    private TipoBonoRepository tipoBonoRepository;

    @Value("${ruta.fotos.perfil}")
    private String rutaFotos;
    
    @Override
	public UsuarioDTO findById(Long id) {
    	
		return usuarioRepository.findById(id)
				.map(UsuarioDTO::convertToDto)
				.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
	}
    
    
    
    @Override
    public UsuarioDTO altaEmpleado(UsuarioDTO usuarioDTO) {
    	
    	System.out.println(usuarioDTO);
    	
        Usuario usuario = Usuario.builder()
                .nombre(usuarioDTO.getNombre())
                .apellido(usuarioDTO.getApellido())
                .email(usuarioDTO.getEmail())
                .password(passwordEncoder.encode(usuarioDTO.getPassword()))
                .rol(Rol.EMPLEADO)
                .fechaRegistro(java.time.LocalDate.now())
                .estado(usuarioDTO.getEstado())
                .imagen(usuarioDTO.getImagen())
                .build();
        Usuario savedUsuario = usuarioRepository.save(usuario);
        return UsuarioDTO.builder()
                .id(savedUsuario.getId())
                .nombre(savedUsuario.getNombre())
                .apellido(savedUsuario.getApellido())
                .email(savedUsuario.getEmail())
                .rol(savedUsuario.getRol().name())
                .fechaRegistro(savedUsuario.getFechaRegistro())
                .estado(savedUsuario.getEstado())
                .imagen(usuario.getImagen())
                .build();
    }
    
    

    @Override
    public UsuarioDTO modificarUsuario(Long id, UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setApellido(usuarioDTO.getApellido());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setRol(Usuario.Rol.valueOf(usuarioDTO.getRol()));
        usuario.setEstado(usuarioDTO.getEstado());
        usuario.setImagen(usuarioDTO.getImagen());
        Usuario updatedUsuario = usuarioRepository.save(usuario);
        return UsuarioDTO.builder()
                .id(updatedUsuario.getId())
                .nombre(updatedUsuario.getNombre())
                .apellido(updatedUsuario.getApellido())
                .email(updatedUsuario.getEmail())
                .rol(updatedUsuario.getRol().name())
                .fechaRegistro(updatedUsuario.getFechaRegistro())
                .estado(updatedUsuario.getEstado())
                .imagen(updatedUsuario.getImagen())
                .build();
    }

    @Override
    public List<UsuarioDTO> listarUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(usuario -> UsuarioDTO.builder()
                        .id(usuario.getId())
                        .nombre(usuario.getNombre())
                        .apellido(usuario.getApellido())
                        .email(usuario.getEmail())
                        .rol(usuario.getRol().name())
                        .fechaRegistro(usuario.getFechaRegistro())
                        .estado(usuario.getEstado())
                        .imagen(usuario.getImagen())
                        .build())
                .collect(Collectors.toList());
    }
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        return User.builder()
                .username(usuario.getEmail())
                .password(usuario.getPassword())
                .roles(usuario.getRol().name())
                .build();
    }

    public UsuarioDTO registro(UsuarioDTO usuarioDTO, MultipartFile file) throws IOException {

        if (usuarioRepository.findByEmail(usuarioDTO.getEmail()).isPresent()) {
            throw new RuntimeException("El email ya esta registrado");
        }

        Usuario usuario = UsuarioDTO.convertToEntity(usuarioDTO);
        
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        usuario.setFechaRegistro(java.time.LocalDate.now());
        
        usuario.setEstado(1);
       
        usuario.setRol(Rol.USUARIO);

        if (file != null && !file.isEmpty()) {
            String nombreArchivo = usuario.getId() + "_" + file.getOriginalFilename();
            
            File directorio = new File("uploads/fotos_perfil");
            if (!directorio.exists()) {
                directorio.mkdirs();
            }
            
            Path ruta = Paths.get(directorio.getPath() + File.separator + nombreArchivo);
            
            Files.write(ruta, file.getBytes());
            
            usuario.setImagen(nombreArchivo);
        }
        
        Usuario nuevoUsuario = usuarioRepository.save(usuario);
        
        
        TipoBono tipoGeneral = tipoBonoRepository.findByNombre("General");
        TipoBono tipoPrivado = tipoBonoRepository.findByNombre("Privado");
        List<Bono> bonos = new ArrayList<>();
        
        // Bono general
        Bono bonoGeneral = new Bono();
        bonoGeneral.setUsuario(usuario);
        bonoGeneral.setTipoBono(tipoGeneral); 
        bonoGeneral.setSaldoClases(0);
        bonoGeneral.setFechaInicio(null);
        bonoGeneral.setFechaFin(null);
        bonos.add(bonoGeneral);

        // Bono privado
        Bono bonoPrivado = new Bono();
        bonoPrivado.setUsuario(usuario);
        bonoPrivado.setTipoBono(tipoPrivado);
        bonoPrivado.setSaldoClases(0);
        bonoPrivado.setFechaInicio(null);
        bonoPrivado.setFechaFin(null);
        bonos.add(bonoPrivado);

        bonoRepository.saveAll(bonos);

        MensajeDTO mensajeDTO = MensajeDTO.builder()
                .idUsuario(nuevoUsuario.getId())
                .titulo("Bienvenido a UGym")
                .contenido("¡Bienvenido al gimnasio UGym! Estamos felices de tenerte con nosotros.")
                .fechaEnvio(LocalDate.now())
                .build();
        
        mensajeService.crearMensaje(mensajeDTO);
        
        return UsuarioDTO.convertToDto(nuevoUsuario);
    }


    public String login(String email, String contraseña) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, contraseña));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return String.valueOf(usuario.getId());
    }

    
    @Override
    public void deleteById(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuario.setEstado(0);
        usuarioRepository.save(usuario);
    }
    
    @Override
    public void activarUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuario.setEstado(1);
        usuarioRepository.save(usuario);
    }
    
    @Override
    public void actualizarFotoPerfil(Long usuarioId, MultipartFile file) throws IOException {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        String contentType = file.getContentType();
        if (contentType == null || (!contentType.equals("image/jpeg") && !contentType.equals("image/png"))) {
            throw new IllegalArgumentException("El archivo debe ser una imagen con formato JPG o PNG");
        }

        String rutaBase = System.getProperty("user.dir");
        String rutaCompleta = rutaBase + File.separator + rutaFotos;

        File directorio = new File(rutaCompleta);
        if (!directorio.exists()) {
            directorio.mkdirs();
        }

        String nombreArchivo = usuarioId + "_" + file.getOriginalFilename();
        Path rutaArchivo = Paths.get(rutaCompleta + File.separator + nombreArchivo);

        Files.write(rutaArchivo, file.getBytes());

        usuario.setImagen(nombreArchivo);
        usuarioRepository.save(usuario);
    }

    
    
    @Override
    public void borrarImagen(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuario.setImagen(null);
        usuarioRepository.save(usuario);
    }
    
    @Override
    public List<UsuarioDTO> findAllEmpleados() {
    	 return usuarioRepository.findAllByRol(Usuario.Rol.EMPLEADO)
                 .stream()
                 .map(UsuarioDTO::convertToDto)
                 .collect(Collectors.toList());
     }
}
