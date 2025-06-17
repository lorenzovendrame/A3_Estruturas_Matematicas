package org.example.a3estruturasmatematicas.resource;

import org.example.a3estruturasmatematicas.service.DTO.SistemaLinearDTO;
import org.example.a3estruturasmatematicas.service.SistemaLinearService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sistemas-lineares")
public class SistemaLinearResource{

    @Autowired
    private SistemaLinearService service;

    @PostMapping("/determinante")
    public ResponseEntity<Double> determinante(@RequestBody SistemaLinearDTO dto) {
        double det = service.determinante(dto.getA());
        return ResponseEntity.ok(det);
    }

    @PostMapping("/cramer")
    public ResponseEntity<double[]> cramer(@RequestBody SistemaLinearDTO dto) {
        double[] resultado = service.cramer(dto.getA(), dto.getB());
        if (resultado == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(resultado);
    }
}

