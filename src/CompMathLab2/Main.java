package CompMathLab2;

import java.io.*;
import java.util.List;
import java.util.stream.*;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        int system_id = Integer.parseInt(bufferedReader.readLine().trim());

        int number_of_unknowns = Integer.parseInt(bufferedReader.readLine().trim());

        List<Double> initial_approximations = IntStream.range(0, number_of_unknowns).mapToObj(i -> {
                    try {
                        return bufferedReader.readLine().replaceAll("\\s+$", "");
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                })
                .map(String::trim)
                .map(Double::parseDouble)
                .collect(toList());

        List<Double> result = Solution.solve_by_fixed_point_iterations(system_id, number_of_unknowns, initial_approximations);

        bufferedWriter.write(
                result.stream()
                        .map(Object::toString)
                        .collect(joining("\n"))
                        + "\n"
        );

        bufferedReader.close();
        bufferedWriter.close();
    }
}
