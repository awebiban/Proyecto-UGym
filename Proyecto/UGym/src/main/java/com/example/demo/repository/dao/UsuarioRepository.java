package com.example.demo.repository.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.repository.entity.Usuario;
import com.example.demo.repository.entity.Usuario.Rol;

import jakarta.transaction.Transactional;
import java.util.List;



@Transactional
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	Optional<Usuario> findByEmail(String email);
	
    @Query("SELECT u FROM Usuario u WHERE u.rol = :rol")
    List<Usuario> findAllByRol(@Param("rol") Rol rol);
	
}