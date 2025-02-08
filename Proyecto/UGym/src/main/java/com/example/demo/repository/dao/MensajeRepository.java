package com.example.demo.repository.dao;

import com.example.demo.repository.entity.Mensaje;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MensajeRepository extends JpaRepository<Mensaje, Long> {
	
	List<Mensaje> findByUsuarioId(Long usuarioId);
    
}
