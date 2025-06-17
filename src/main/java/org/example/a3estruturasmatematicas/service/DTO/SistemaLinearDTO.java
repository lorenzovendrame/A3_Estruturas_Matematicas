package org.example.a3estruturasmatematicas.service.DTO;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SistemaLinearDTO {
    private double[][] A;
    private double[] B;

    @JsonCreator
    public SistemaLinearDTO(@JsonProperty("A") double[][] A,@JsonProperty("B") double[] B) {
        this.A = A;
        this.B = B;
    }

    // getters e setters
    public double[][] getA() { return A; }
    public void setA(double[][] a) { A = a; }
    public double[] getB() { return B; }
    public void setB(double[] b) { this.B = b; }
}

