package com.example.demo.repository.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.repository.entity.Bono;
import com.example.demo.repository.entity.TipoBono;

import jakarta.transaction.Transactional;

@Transactional
@Repository
public interface BonoRepository extends JpaRepository<Bono, Long> {

	List<Bono> findByUsuarioId(Long usuarioId);

	Optional<Bono> findByUsuarioIdAndTipoBono(Long usuarioId, TipoBono tipoBono);

}
