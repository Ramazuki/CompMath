package CompMathLab1;

import java.util.Random;
import java.util.Arrays;

public class Matrix {
    double[][] A;
    double[] B;
    private final int RANDOM_SIZE = 20;

    public Matrix() {
        int range = 10;
        this.A = createRandomMatrix(range);
        this.B = createRandomVector(range);
    }
    public Matrix (double[][] unknown, double[] solutions){
        this.A = unknown;
        this.B = solutions;
        isValid();
    }

    private double[][] createRandomMatrix(int range) {
        double[][] matrix = new double[RANDOM_SIZE][RANDOM_SIZE];
        Random random = new Random();
        for (int i = 0; i < RANDOM_SIZE; i++) {
            for (int j = 0; j < RANDOM_SIZE; j++) {
                matrix[i][j] = random.nextDouble(range * 2) + (-range);
            }
        }
        for (int i = 0; i < RANDOM_SIZE; i++) {
            matrix[i][i] = Arrays.stream(matrix[i]).map(Math::abs).sum() * random.nextInt(2, 4);
        }
        return matrix;
    }

    private double[] createRandomVector(int range){
        double[] vector = new double[RANDOM_SIZE];
        Random random = new Random();
        for (int i = 0; i  < vector.length; i++) {
            vector[i] = random.nextInt(range * 2) + (-range);
        }
        return vector;
    }

    public double[][] getA() {
        return A;
    }

    public double[] getB() {
        return B;
    }

    public boolean isSquare (){
        double rows = A.length;
        double columns = A[0].length;
        return rows == columns;
    }

    private void isValid (){
        if (A.length == 0){
            throw new IllegalArgumentException("Matrix cannot be empty");
        }
        int columnLen = A[0].length;
        for (double[] columns : A) {
            if (columns.length != columnLen) {
                throw new IllegalArgumentException("Columns in matrix must be same");
            }
        }
    }
}
