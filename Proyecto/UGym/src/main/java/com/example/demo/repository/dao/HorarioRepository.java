package com.example.demo.repository.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.repository.entity.Horario;

import jakarta.transaction.Transactional;

@Transactional
@Repository
public interface HorarioRepository extends JpaRepository<Horario, Long> {

}
