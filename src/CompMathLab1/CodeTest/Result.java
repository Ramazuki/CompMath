package CompMathLab1.CodeTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static java.util.Collections.swap;

public class Result {
    public static boolean isMethodApplicable = true;
    public static String errorMessage;


    public static void diagonalCheck (int n, List<List<Double>> matrix){
        for (int i = 0; i < n; i++) {
            double A = matrix.get(i).get(i);
            double last = matrix.get(i).get(n);

            if (Math.abs(A) < Math.abs(matrix.get(i).stream()
                    .map(Math::abs)
                    .reduce(0.0, Double::sum) - Math.abs(last) - Math.abs(A))) {

                List<Double> row = matrix.get(i);
                //Trying to find numbers to solve diagonal predominance problem
                boolean foundSolution = false;
                for (int j = 0; j < n; j++) {
                    double B = row.get(j);
                    if(B != A){
                        if (Math.abs(B) >= Math.abs(row.stream()
                                .map(Math::abs)
                                .reduce(0.0, Double::sum) - Math.abs(last) - Math.abs(B))) {
                            swap(row, i, j);
                            matrix.set(i, row);
                            foundSolution = true;
                            break;
                        }
                    }
                }
                if (!foundSolution){
                    isMethodApplicable = false;
                    errorMessage = "The system has no diagonal dominance for this method. Method of the Gauss-Seidel is not applicable.";
                    break;
                }
            }
        }
    }

    public static List<Double> solveByGaussSeidel (int n, List<List<Double>> matrix, double epsilon){
        diagonalCheck(n, matrix);
        int b_place = n;
        List<Double> solutionVector = new ArrayList<>(Collections.nCopies(n, 0.0));
        List<Double> previous = new ArrayList<>(solutionVector);
        int itterations = 0;

        while (true) {
            for (int i = 0; i < n; i++) {
                double numerator = matrix.get(i).get(b_place); //retrieve number after "="
                double denominator = matrix.get(i).get(i); //retrieve diagonal number
                for (int j = 0; j < n; j++)
                    if (j != i)
                        numerator -= matrix.get(i).get(j) * solutionVector.get(j);
                solutionVector.set(i, numerator / denominator);
            }
            ++itterations;
            if(itterations == 1)
                continue;
            boolean isOver = true;
            for (int i = 0; i < n; i++) {
                if(Math.abs(solutionVector.get(i) - previous.get(i)) > epsilon){
                    isOver = false;
                    break;
                }
            }
            if(isOver)
                break;
            previous = new ArrayList<>(solutionVector);
        }
        System.out.println("iterations: " + itterations);
        return solutionVector;
    }

}
