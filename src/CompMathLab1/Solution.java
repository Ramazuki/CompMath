package CompMathLab1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Solution {
    public static boolean isMethodApplicable = true;
    public static String errorMessage;
    static int iterations = 0;

    public static void ruleCheck (int n, List<List<Double>> matrix){
        strangeEqualityCheck(n, matrix);
        zeroCheck(n, matrix);
        if(isMethodApplicable)
            diagonalCheck(n, matrix);
    }

    private static void zeroCheck(int n, List<List<Double>> matrix){
        //check if all a_n are zeroes
        if(!isMethodApplicable)
            return;
        for (int i = 0; i < n; i++) {
            double b1 = matrix.get(i).get(n);
            if (matrix.get(i).stream()
                    .reduce(0.0, Double::sum) - b1 == 0 && b1 != 0) {
                errorMessage = "Подобное СЛАУ не может быть решено";
                isMethodApplicable = false;
            }
        }
    }

    private static void strangeEqualityCheck(int n, List<List<Double>> matrix){
        for (int i = 0; i < n-1; i++) {
            for (int j = i+1; j < n; j++) {
                List<Double> rowI = new ArrayList<>(matrix.get(i));
                double b1 = rowI.get(n);
                rowI.remove(n);
                List<Double> rowJ = new ArrayList<>(matrix.get(j));
                double b2 = rowJ.get(n);
                rowJ.remove(n);

                if (rowI.equals(rowJ) && b1 != b2){
                    isMethodApplicable = false;
                    errorMessage = "Подобное СЛАУ не может быть решено";
                    break;
                }
            }
        }


    }

    private static void columnSwap (List<List<Double>> matrix, int index1, int index2){
            int length = matrix.size();
        for (int i = 0; i < length; i++) {
            List<Double> row = new ArrayList<>(matrix.get(i));
            Collections.swap(row, index1, index2);
            matrix.set(i, row);
        }
    }

    private static void diagonalCheck (int n, List<List<Double>> matrix){
        for (int i = 0; i < n; i++) {
            double A = matrix.get(i).get(i);
            double last = matrix.get(i).get(n);

            if (Math.abs(A) < matrix.get(i).stream()
                    .map(Math::abs)
                    .reduce(0.0, Double::sum) - Math.abs(last) - Math.abs(A)) {
                List<Double> row = matrix.get(i);
                row.remove(n);
                double max = Collections.max(row);
                row.add(last);
                int maxIndex = row.indexOf(max);
                boolean foundSolution = false;

                if(max != A && maxIndex > i && Math.abs(max) >= row.stream()
                        .map(Math::abs)
                        .reduce(0.0, Double::sum) - Math.abs(max) - Math.abs(last)) {
                    columnSwap(matrix, i, maxIndex);
                    foundSolution = true;
                }

                if (!foundSolution) {
                    isMethodApplicable = false;
                    errorMessage = "Не соблюдено условие диагонального преобладания";
                    break;
                }
            }
        }
    }

    public static List<Double> solveByGaussSeidel (int n, List<List<Double>> matrix, double epsilon) {
        int b_place = n;
        List<Double> solutionVector = new ArrayList<>(Collections.nCopies(n, 0.0));
        if(!isMethodApplicable)
            return solutionVector;
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
                double error = Math.abs(solutionVector.get(i) - previous.get(i));
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
        return joinedVector;
    }

}
