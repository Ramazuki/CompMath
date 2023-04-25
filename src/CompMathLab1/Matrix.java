package CompMathLab1;

import java.util.Random;

public class Matrix {
    int[][] A;
    int[] B;
    private final int RANDOM_SIZE = 20;

    public Matrix() {
        int range = 10;
        this.A = createRandomMatrix(range);
        this.B = createRandomVector(range);
    }
    public Matrix (int[][] unknown, int[] solutions){
        this.A = unknown;
        this.B = solutions;
    }

    private int[][] createRandomMatrix(int range) {
        int[][] matrix = new int[RANDOM_SIZE][RANDOM_SIZE];
        Random random = new Random();
        for (int i = 0; i < RANDOM_SIZE; i++) {
            for (int j = 0; j < RANDOM_SIZE; j++) {
                matrix[i][j] = random.nextInt(range * 2) + (-range);
            }
        }
        return matrix;
    }

    private int[] createRandomVector(int range){
        int[] vector = new int[RANDOM_SIZE];
        Random random = new Random();
        for (int i = 0; i  < vector.length; i++) {
            vector[i] = random.nextInt(range * 2) + (-range);
        }
        return vector;
    }
}
