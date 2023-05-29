package CompMathLab2;

import java.util.*;
import java.util.function.*;

import static java.lang.StrictMath.sin;
import static java.lang.StrictMath.pow;

class SNAEFunctions {

    private static double first_function(List<Double> args) {
        return sin(args.get(0));
    }

    private static double second_function(List<Double> args) {
        return args.get(0) * args.get(1) / 2;
    }

    private static double third_function(List<Double> args) {
        return
                pow(args.get(0), 2) * pow(args.get(1), 2) - 3 * pow(args.get(0), 3) - 6 * pow(args.get(1),
                        3) + 8;
    }

    private static double fourth_function(List<Double> args) {
        return pow(args.get(0), 4) - 9 * args.get(1) + 2;
    }

    private static double fifth_function(List<Double> args) {
        return args.get(0) + pow(args.get(0), 2) - 2 * args.get(1) * args.get(2) - 0.1;
    }

    private static double six_function(List<Double> args) {
        return args.get(1) + pow(args.get(1), 2) + 3 * args.get(0) * args.get(2) + 0.2;
    }


    private static double seven_function(List<Double> args) {
        return args.get(2) + pow(args.get(2), 2) + 2 * args.get(0) * args.get(1) - 0.3;
    }

    private static double default_function(List<Double> args) {
        return 0.0;
    }

    /*
     * How to use this function:
     *    List<Function<Double, List<Double>> funcs = get_functions(4);
     *    funcs[0].apply(List.of(0.0001, 0.002));
     */
    public static List<Function<List<Double>, Double>> get_functions(int n) {
        return switch (n) {
            case (1) -> List.of(SNAEFunctions::first_function, SNAEFunctions::second_function);
            case (2) -> List.of(SNAEFunctions::third_function, SNAEFunctions::fourth_function);
            case (3) ->
                    List.of(SNAEFunctions::fifth_function, SNAEFunctions::six_function, SNAEFunctions::seven_function);
            default -> List.of(SNAEFunctions::default_function);
        };
    }
}

public class Solution {

    public static List<Double> solve_by_fixed_point_iterations(int systemId, int numberOfUnknowns, List<Double> initialApproximations) {
        double eps = 1E-5;
        double[] step = new double[numberOfUnknowns];
        Arrays.fill(step,1E-3);
        List<Function<List<Double>, Double>> baseFunctions = SNAEFunctions.get_functions(systemId);
        List<Double> previousVector = new ArrayList<>(initialApproximations);
        List<Double> calculatedX = new ArrayList<>();
        List<Double> funcResult1 = new ArrayList<>();
        List<Double> funcResult2 = new ArrayList<>();
        int iterations = 0;
        while (true) {
            boolean isOver = true;
            for (int i = 0; i < numberOfUnknowns; i++) {
                int finalI = i;
                List<Double> finalPreviousVector = previousVector;
                Function<List<Double>, Double> gx = (xi) -> finalPreviousVector.get(finalI) + step[finalI] * baseFunctions.get(finalI).apply(xi);
                calculatedX.add(gx.apply(previousVector));
                funcResult1.add(baseFunctions.get(i).apply(previousVector));
            }
            for (int i = 0; i < numberOfUnknowns; i++) {
                funcResult2.add(baseFunctions.get(i).apply(calculatedX));
                if (Math.abs(funcResult1.get(i)) < Math.abs(funcResult2.get(i))){
                    step[i] = step[i] > 0 ? -step[i] : -step[i]/2;
                }
            }


            iterations++;
            if (iterations == 1)
                continue;
            for (int i = 0; i < numberOfUnknowns; i++) {
                if (Math.abs(calculatedX.get(i) - previousVector.get(i)) > eps) {
                    isOver = false;
                    break;
                }
            }
            if (isOver)
                break;

            previousVector = calculatedX;
            calculatedX = new ArrayList<>();
            funcResult1 = new ArrayList<>();
            funcResult2 = new ArrayList<>();
        }

        return calculatedX;
    }

}
