package com.example.demo.web.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.dto.PaymentRequestDTO;
import com.example.demo.service.BonoService;
import com.example.demo.service.PayPalService;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

	@Autowired
	private PayPalService payPalService;

	@Autowired
	private BonoService bonoService;

	@PostMapping("/pay")
	public ResponseEntity createPayment(@RequestBody PaymentRequestDTO paymentRequestDTO) throws Exception {
		try {
			String approvalLink = payPalService.createPayment(paymentRequestDTO.getAmount(), "EUR",
					"http://localhost:4200/bonos/confirm-payment?status=success&usuarioId="
							+ paymentRequestDTO.getUsuarioId() + "&tipoBonoId=" + paymentRequestDTO.getTipoBonoId()
							+ "&montoPago=" + paymentRequestDTO.getAmount(),
					"http://localhost:4200/bonos/confirm-payment?status=cancel&usuarioId="
							+ paymentRequestDTO.getUsuarioId() + "&tipoBonoId=" + paymentRequestDTO.getTipoBonoId());

			return ResponseEntity.ok(Map.of("approvalLink", approvalLink));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
		}
	}

	@GetMapping("/confirm-payment")
	public ResponseEntity<?> confirmPayment(@RequestParam String paymentId, @RequestParam Long usuarioId,
	                                        @RequestParam int tipoBonoId, @RequestParam Double montoPago) {
	    try {
	        System.out.println(usuarioId);
	        boolean paymentVerified = payPalService.verifyPayment(paymentId);

	        Map<String, String> response = new LinkedHashMap<>();

	        if (paymentVerified) {
	            boolean bonoUpdated = bonoService.updateBonosAfterPayment(usuarioId, tipoBonoId, montoPago);

	            if (bonoUpdated) {
	                response.put("status", "success");
	                response.put("message", "Pago confirmado y bonos actualizados");
	                return ResponseEntity.ok(response);
	            } else {
	                response.put("status", "error");
	                response.put("message", "Error al actualizar los bonos");
	                return ResponseEntity.status(400).body(response);
	            }
	        } else {
	            response.put("status", "error");
	            response.put("message", "Pago no verificado");
	            return ResponseEntity.status(400).body(response);
	        }
	    } catch (Exception e) {
	        Map<String, String> response = new LinkedHashMap<>();
	        response.put("status", "error");
	        response.put("message", "Error al confirmar el pago: " + e.getMessage());
	        return ResponseEntity.status(500).body(response);
	    }
	}

}
