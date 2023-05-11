package CompMathLab1;

import java.util.Arrays;

public class GaussSeidel {

    Matrix matrix;
    double accuracy;
    public GaussSeidel(Matrix matrix, double accuracy){
        this.matrix = matrix;
        this.accuracy = accuracy;
    }


    public double[] solveMatrix(){
        double[] solution;
        if(matrix.isSquare()){
            solution = solveSquareMatrix();
        }
        else {
            solution = solveSquareMatrix();
        }
        return solution;
    }
    private double[] solveSquareMatrix(){
        diagonalCheck();
        int len = matrix.getA()[0].length;
        double[] solutionVector = new double[len];
        Arrays.fill(solutionVector, 0);
        double[] previous = new double[len];
        int iterations = 0;

        while (true){
            for (int i = 0; i < len; i++) {
                double numerator = matrix.getB()[i];
                double denominator = matrix.getA()[i][i];
                for (int j = 0; j < len; j++)
                    if(i != j)
                        numerator -= matrix.getA()[i][j] * solutionVector[j];
                solutionVector[i] = numerator / denominator;
            }
            iterations++;
            if (iterations == 1)
                continue;
            boolean isOver = true;
            for (int i = 0; i < len; i++) {
                if (Math.abs(solutionVector[i] - previous[i]) > accuracy) {
                    isOver = false;
                    break;
                }
            }
            if(isOver)
                break;
            previous = solutionVector.clone();
        }
        System.out.println("Number of iterations: " + iterations);
        return solutionVector;
    }

//    private  int[] solveNotSquareMatrix(){
//
//    }

    private void diagonalCheck(){
        for (int i = 0; i < matrix.getA().length; i++) {
            double A = matrix.getA()[i][i];

            if(A == 0)
                throw new RuntimeException("This method expect not 0 values in diagonal");
            if(Math.abs(A) < Math.abs(Arrays.stream(matrix.getA()[i]).map(Math::abs).sum() - A))
                throw new RuntimeException("This method expect matrix to be [diagonally dominant]");
        }

    }
}
