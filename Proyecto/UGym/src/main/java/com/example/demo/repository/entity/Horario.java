package com.example.demo.repository.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "Horarios")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Horario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_empleado", nullable = true)
    private Usuario empleado;

    @Column(name = "hora_inicio", nullable = false)
    private LocalTime horaInicio;

    @Column(name = "duracion_minutos", nullable = false)
    private Integer duracionMinutos;

    @Column(name = "dia_semana", nullable = false, length = 50)
    private String diaSemana;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Column(name = "plazas_disponibles", nullable = false)
    private Integer plazasDisponibles;
    
    @Column(name = "hora_fin")
    private LocalTime horaFin;

    // metodo para calcular la hora final
    public LocalTime getHoraFin() {
        return horaInicio.plusMinutes(duracionMinutos);
    }
}
