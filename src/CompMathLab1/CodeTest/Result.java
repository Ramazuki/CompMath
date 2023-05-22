package CompMathLab1.CodeTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Result {
    public static boolean isMethodApplicable = true;
    public static String errorMessage;
    static int iterations = 0;

    public static void ruleCheck (int n, List<List<Double>> matrix){
        matrixCheck(n, matrix);
        if(isMethodApplicable)
            diagonalCheck(n, matrix);
    }

    public static void matrixCheck(int n, List<List<Double>> matrix){
        errorMessage = "Input data cannot be considered as matrix";
        int expectedRowSize = matrix.get(0).size();
        for (int i = 1; i < n; i++) {
            int curRowSize = matrix.get(i).size();
            if (curRowSize != expectedRowSize) {
                isMethodApplicable = false;
                break;
            }
        }
        if(isMethodApplicable) {
            for (int i = 0; i < n; i++) {
                double b = matrix.get(i).get(n);
                if (matrix.get(i).stream()
                        .reduce(0.0, Double::sum) - b == 0 && b != 0) {
                    isMethodApplicable = false;
                }
            }
        }
    }
    public static void diagonalCheck (int n, List<List<Double>> matrix){
        for (int i = 0; i < n; i++) {
            double A = matrix.get(i).get(i);
            double last = matrix.get(i).get(n);

            if (Math.abs(A) < matrix.get(i).stream()
                    .map(Math::abs)
                    .reduce(0.0, Double::sum) - Math.abs(last) - Math.abs(A)) {

                List<Double> row = matrix.get(i);
                //Trying to find numbers to solve diagonal predominance problem
                boolean foundSolution = false;
                row.remove(n);
                double B = Collections.max(row);
                row.add(last);
                if(B != A && Math.abs(B) >= row.stream()
                        .map(Math::abs)
                        .reduce(0.0, Double::sum) - Math.abs(B) - Math.abs(last)) {
                    int index = row.indexOf(B);
                    Collections.swap(row, i, index);
                    matrix.set(i, row);
                    foundSolution = true;
                }
                if (!foundSolution) {
                    isMethodApplicable = false;
                    errorMessage = "The system has no diagonal dominance for this method. Method of the Gauss-Seidel is not applicable.";
                    break;
                }
            }
        }
    }

    public static List<Double> solveByGaussSeidel (int n, List<List<Double>> matrix, double epsilon) {
        ruleCheck(n, matrix);
        int b_place = n;
        List<Double> solutionVector = new ArrayList<>(Collections.nCopies(n, 0.0));
        List<Double> previous = new ArrayList<>(solutionVector);
        List<Double> errors = new ArrayList<>(solutionVector);


        while (true) {
            for (int i = 0; i < n; i++) {
                double numerator = matrix.get(i).get(b_place); //retrieve number after "="
                double denominator = matrix.get(i).get(i); //retrieve diagonal number
                for (int j = 0; j < n; j++)
                   if (j != i)
                        numerator -= matrix.get(i).get(j) * solutionVector.get(j);


                solutionVector.set(i, numerator / denominator);
            }
            ++iterations;
            if(iterations == 1)
                continue;
            boolean isOver = true;
            for (int i = 0; i < n; i++) {
                double error = Math.abs(solutionVector.get(i) - previous.get(i)) / Math.abs(solutionVector.get(i));
                errors.set(i, error);
                if(error > epsilon){
                    isOver = false;
                }
            }
            if(isOver)
                break;
            previous = new ArrayList<>(solutionVector);
        }
        List<Double> joinedVector = new ArrayList<>(solutionVector);
        joinedVector.addAll(errors);
        System.out.println("iterations: " + iterations);
        return joinedVector;
    }

}
