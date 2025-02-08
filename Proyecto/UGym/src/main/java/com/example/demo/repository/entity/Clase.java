package com.example.demo.repository.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@Entity
@Table(name = "clases")
@AllArgsConstructor
@Builder
public class Clase {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(name = "nombre_clase", nullable = false)
	private String nombreClase;
	
	@Column(nullable = true)
	private String descripcion;
	
	@Column(nullable = false)
	private String categoria;
	
	@Column(name = "duracion_minutos", nullable = false)
	private int duracionMinutos;
	
	@Column(name = "capacidad_maxima", nullable = false)
	private int capacidadMaxima;
	
	@Column(name = "tipo_clase", nullable = false)
	private int tipoClase;
	
	@Column(nullable = true)
	private String imagen;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_horario", nullable = true)
    private Horario horario;
	
	public Clase() {
		this.duracionMinutos = 60;
	}
	
	
	
	
}
