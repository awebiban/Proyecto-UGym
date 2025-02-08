package com.example.demo.repository.entity;

import java.time.LocalDate;

import com.example.demo.repository.entity.Usuario.Rol;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "reservas")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reserva {

		@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
     
	    @ManyToOne
	    @JoinColumn(name = "id_usuario", referencedColumnName = "id", nullable = false)
	    private Usuario usuario;

	    @ManyToOne
	    @JoinColumn(name = "id_horario", referencedColumnName = "id", nullable = false)
	    private Horario horario;

	    @Column(name = "fecha_reserva", nullable = false)
	    private LocalDate fechaReserva;

	    @Column(name = "estado", nullable = false)
	    private int estado;
}
	    
	  