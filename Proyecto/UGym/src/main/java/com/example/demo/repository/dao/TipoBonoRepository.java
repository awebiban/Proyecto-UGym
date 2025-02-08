package com.example.demo.repository.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.repository.entity.TipoBono;

@Repository
public interface TipoBonoRepository extends JpaRepository<TipoBono, Long> {
	
    TipoBono findByNombre(String nombre);
}
