package com.example.demo.service;

import java.util.List;

import com.example.demo.model.dto.BonoDTO;
import com.example.demo.repository.entity.TipoBono;

public interface BonoService {
	 BonoDTO recargarBono(Long usuarioId, int cantidad, TipoBono tipoBono);

	List<BonoDTO> obtenerBonosPorUsuario(Long usuarioId);

	BonoDTO consumirBono(Long usuarioId, Long bonoId);

	boolean updateBonosAfterPayment(Long usuarioId, int tipoBonoId, Double montoPago);
}
