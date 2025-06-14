package org.example.a3estruturasmatematicas.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.example.a3estruturasmatematicas.service.DTO.SistemaLinearDTO;
import org.example.a3estruturasmatematicas.service.Fracao;
import org.example.a3estruturasmatematicas.service.SistemaLinearService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sistemas-lineares")
public class SistemaLinearResource{

    @Autowired
    private SistemaLinearService service;

    @PostMapping("/eliminacao-gauss")
    public ResponseEntity<double[]> eliminacaoGauss(@RequestBody SistemaLinearDTO dto) {
        double[] resultado = service.eliminacaoGauss(dto.getA(), dto.getB());
        if (resultado == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(resultado);
    }
    /*
    @PostMapping("/gauss-teste")
    public ResponseEntity<Fracao[]> gaussTeste(@RequestBody SistemaLinearDTO dto) {
        Fracao[] resultado = service.gaussTeste(dto.getC(), dto.getD());
        if (resultado == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(resultado);
    }*/

    @PostMapping("/gauss-jordan")
    public ResponseEntity<double[]> gaussJordan(@RequestBody SistemaLinearDTO dto) {
        double[] resultado = service.gaussJordan(dto.getA(), dto.getB());
        if (resultado == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(resultado);
    }

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

    @PostMapping("/montante")
    public ResponseEntity<double[]> montante(@RequestBody SistemaLinearDTO dto) {
        double[] resultado = service.montante(dto.getA(), dto.getB());
        if (resultado == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(resultado);
    }
}

