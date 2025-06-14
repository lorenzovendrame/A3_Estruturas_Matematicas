package org.example.a3estruturasmatematicas.service;

public class Fracao {
    public long numerador;
    public long denominador;

    public Fracao(long numerador, long denominador) {
        if (denominador == 0) throw new ArithmeticException("Denominador não pode ser zero");
        // Normaliza sinal
        if (denominador < 0) {
            numerador = -numerador;
            denominador = -denominador;
        }
        long mdc = mdc(Math.abs(numerador), denominador);
        this.numerador = numerador / mdc;
        this.denominador = denominador / mdc;
    }

    // Construtor para inteiro
    public Fracao(long inteiro) {
        this(inteiro, 1);
    }

    // Máximo divisor comum (Euclides)
    private long mdc(long a, long b) {
        while (b != 0) {
            long t = b;
            b = a % b;
            a = t;
        }
        return a;
    }

    // Soma
    public Fracao soma(Fracao f) {
        long num = this.numerador * f.denominador + f.numerador * this.denominador;
        long den = this.denominador * f.denominador;
        return new Fracao(num, den);
    }

    // Subtração
    public Fracao subtrai(Fracao f) {
        long num = this.numerador * f.denominador - f.numerador * this.denominador;
        long den = this.denominador * f.denominador;
        return new Fracao(num, den);
    }

    // Multiplicação
    public Fracao multiplica(Fracao f) {
        return new Fracao(this.numerador * f.numerador, this.denominador * f.denominador);
    }

    // Divisão
    public Fracao divide(Fracao f) {
        if (f.numerador == 0) throw new ArithmeticException("Divisão por zero");
        return new Fracao(this.numerador * f.denominador, this.denominador * f.numerador);
    }

    @Override
    public String toString() {
        if (denominador == 1) return Long.toString(numerador);
        return numerador + "/" + denominador;
    }

    // Conversão de double para fração (aproximação)
    public static Fracao fromDouble(double valor, double epsilon, int maxDen) {
        long n = 1;
        long d = 0;
        long n1 = 0;
        long d1 = 1;
        double val = valor;
        while (true) {
            long a = (long) Math.floor(val);
            long aux = n;
            n = a * n + n1;
            n1 = aux;
            aux = d;
            d = a * d + d1;
            d1 = aux;
            val = 1 / (val - a);
            if (d > maxDen) break;
            if (Math.abs(valor - (double) n / d) < epsilon) break;
        }
        return new Fracao(n, d);
    }
}

