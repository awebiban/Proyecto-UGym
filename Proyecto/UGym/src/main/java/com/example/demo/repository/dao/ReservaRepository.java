package com.example.demo.repository.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.repository.entity.Reserva;

import jakarta.transaction.Transactional;

@Transactional
@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    
	List<Reserva> findAllByUsuarioId(Long usuarioId);
	
}
