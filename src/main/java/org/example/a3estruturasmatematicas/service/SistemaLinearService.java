package org.example.a3estruturasmatematicas.service;
import org.springframework.stereotype.Service;

import static java.lang.Math.abs;

@Service
public class SistemaLinearService {

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
}

