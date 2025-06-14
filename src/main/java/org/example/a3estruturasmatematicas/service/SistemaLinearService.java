package org.example.a3estruturasmatematicas.service;
import org.springframework.stereotype.Service;

import static java.lang.Math.abs;

@Service
public class SistemaLinearService {

    public double[] eliminacaoGauss(double[][] A, double[] b) {
        int n = A.length;
        for (int k = 0; k < n; k++) {
            // Pivoteamento parcial
            int maxRow = k;
            for (int i = k + 1; i < n; i++) {
                if (abs(A[i][k]) > abs(A[maxRow][k])) {
                    maxRow = i;
                }
            }
            if (abs(A[maxRow][k]) < 1e-12) {
                return null; // Sistema singular
            }
            // Troca linhas
            if (maxRow != k) {
                double[] temp = A[k];
                A[k] = A[maxRow];
                A[maxRow] = temp;

                double tempB = b[k];
                b[k] = b[maxRow];
                b[maxRow] = tempB;
            }
            // Eliminação
            for (int i = k + 1; i < n; i++) {
                double fator = A[i][k] / A[k][k];
                for (int j = k; j < n; j++) {
                    A[i][j] -= fator * A[k][j];
                }
                b[i] -= fator * b[k];
            }
        }
        // Substituição retroativa
        double[] x = new double[n];
        for (int i = n - 1; i >= 0; i--) {
            double soma = 0;
            for (int j = i + 1; j < n; j++) {
                soma += A[i][j] * x[j];
            }
            x[i] = (b[i] - soma) / A[i][i];
        }
        return x;
    }

    public Fracao[] gaussTeste(Fracao[][] A, Fracao[] b) {//Deletar
        int n = A.length;
        for (int k = 0; k < n; k++) {
            // Pivoteamento parcial omitido para simplicidade
            if (A[k][k].numerador == 0) return null; // sistema singular

            for (int i = k + 1; i < n; i++) {
                Fracao fator = A[i][k].divide(A[k][k]);
                for (int j = k; j < n; j++) {
                    A[i][j] = A[i][j].subtrai(fator.multiplica(A[k][j]));
                }
                b[i] = b[i].subtrai(fator.multiplica(b[k]));
            }
        }
        Fracao[] x = new Fracao[n];
        for (int i = n - 1; i >= 0; i--) {
            Fracao soma = new Fracao(0,1);
            for (int j = i + 1; j < n; j++) {
                soma = soma.soma(A[i][j].multiplica(x[j]));
            }
            x[i] = b[i].subtrai(soma).divide(A[i][i]);
        }
        return x;
    }//Deletar


    public double[] gaussJordan(double[][] A, double[] b) {
        int n = A.length;
        if (n != A[0].length) return null;

        // Matriz aumentada
        double[][] M = new double[n][n + 1];
        for (int i = 0; i < n; i++) {
            System.arraycopy(A[i], 0, M[i], 0, n);
            M[i][n] = b[i];
        }

        for (int i = 0; i < n; i++) {
            // Pivoteamento
            int maxRow = i;
            for (int r = i + 1; r < n; r++) {
                if (abs(M[r][i]) > abs(M[maxRow][i])) {
                    maxRow = r;
                }
            }
            if (abs(M[maxRow][i]) < 1e-12) return null;
            if (maxRow != i) {
                double[] temp = M[i];
                M[i] = M[maxRow];
                M[maxRow] = temp;
            }
            // Normaliza linha
            double piv = M[i][i];
            for (int k = 0; k <= n; k++) {
                M[i][k] /= piv;
            }
            // Zera outras linhas
            for (int j = 0; j < n; j++) {
                if (j != i) {
                    double fator = M[j][i];
                    for (int k = 0; k <= n; k++) {
                        M[j][k] -= fator * M[i][k];
                    }
                }
            }
        }
        double[] x = new double[n];
        for (int i = 0; i < n; i++) {
            x[i] = M[i][n];
        }
        return x;
    }

    public double determinante(double[][] M) {
        int n = M.length;
        double[][] A = new double[n][n];
        for (int i = 0; i < n; i++) {
            System.arraycopy(M[i], 0, A[i], 0, n);
        }
        double det = 1;
        for (int i = 0; i < n; i++) {
            int maxRow = i;
            for (int r = i + 1; r < n; r++) {
                if (abs(A[r][i]) > abs(A[maxRow][i])) {
                    maxRow = r;
                }
            }
            if (abs(A[maxRow][i]) < 1e-12) return 0;
            if (maxRow != i) {
                double[] temp = A[i];
                A[i] = A[maxRow];
                A[maxRow] = temp;
                det *= -1;
            }
            det *= A[i][i];
            for (int j = i + 1; j < n; j++) {
                double fator = A[j][i] / A[i][i];
                for (int k = i; k < n; k++) {
                    A[j][k] -= fator * A[i][k];
                }
            }
        }
        return det;
    }

    public double[] cramer(double[][] A, double[] b) {
        int n = A.length;
        if (n != A[0].length) return null;

        double detA = determinante(A);
        if (abs(detA) < 1e-12) return null;

        double[] x = new double[n];
        for (int i = 0; i < n; i++) {
            double[][] Ai = new double[n][n];
            for (int r = 0; r < n; r++) {
                System.arraycopy(A[r], 0, Ai[r], 0, n);
                Ai[r][i] = b[r];
            }
            double detAi = determinante(Ai);
            x[i] = detAi / detA;
        }
        return x;
    }

    public double[] montante(double[][] A, double[] b) {
        int n = A.length;
        if (n != A[0].length) return null;

        double[][] M = new double[n][n + 1];
        for (int i = 0; i < n; i++) {
            System.arraycopy(A[i], 0, M[i], 0, n);
            M[i][n] = b[i];
        }

        double p = 1;
        for (int k = 0; k < n; k++) {
            double piv = M[k][k];
            if (abs(piv) < 1e-12) return null;
            for (int i = 0; i < n; i++) {
                if (i != k) {
                    for (int j = k + 1; j <= n; j++) {
                        M[i][j] = (M[k][k] * M[i][j] - M[i][k] * M[k][j]) / p;
                    }
                    M[i][k] = 0;
                }
            }
            p = piv;
        }
        double[] x = new double[n];
        for (int i = 0; i < n; i++) {
            x[i] = M[i][n] / M[i][i];
        }
        return x;
    }
}

