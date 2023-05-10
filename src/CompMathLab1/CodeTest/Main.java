package CompMathLab1.CodeTest;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.*;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.joining;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        int n = Integer.parseInt(bufferedReader.readLine().trim());

        int matrixRows = n;
        int matrixColumns = n+1;

        List<List<Double>> matrix = new ArrayList<>();

        IntStream.range(0, matrixRows).forEach(i -> {
            try {
                matrix.add(
                        Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "")
                                .replaceAll(",", ".").split(" "))
                                .map(Double::parseDouble)
                                .collect(toList())
                );
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        double epsilon = Double.parseDouble(bufferedReader.readLine().replaceAll(",", ".").trim());

        List<Double> result = Result.solveByGaussSeidel(n, matrix, epsilon);

        if (Result.isMethodApplicable){
            bufferedWriter.write(
                    result.stream()
                            .map(Object::toString)
                            .collect(joining("\n"))
                            + "\n"
            );
        } else {
            bufferedWriter.write(Result.errorMessage + "\n");
        }

        bufferedReader.close();
        bufferedWriter.close();
    }
}

