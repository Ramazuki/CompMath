package CompMathLab1;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.*;

import static CompMathLab1.Solution.*;
import static java.util.stream.Collectors.toList;


public class Menu {
    static List<List<Double>> matrix = new ArrayList<>();
    static double epsilon = 0;

    public static void main(String[] args) throws IOException {
        boolean exportInFile = inputMenu();
        List<Double> solutionVector = solveByGaussSeidel(matrix.get(0).size() - 1, matrix, epsilon);
        export(solutionVector, exportInFile);
    }
    public static boolean inputMenu() throws IOException {
        System.out.println("Выберите режим ввода:\n1:Случайная матрица\n2:Ручной ввод\n3:Ввод из файла");
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
        int inputMode = Integer.parseInt(consoleReader.readLine().trim());
        System.out.println("Выберите режим вывода:\n1: В консоль\n2: В файл");
        int exportMode = Integer.parseInt(consoleReader.readLine().trim());

        switch (inputMode) {
            case 1 -> {
                System.out.println("Введите значение погрешности");
                epsilon = Double.parseDouble(consoleReader.readLine().trim());
                createRandom();
            }
            case 2 -> {
                handMode();
            }
            case 3 -> {
                readFromFile();
            }
            default -> {
                throw new RuntimeException("No such mode, try 1, 2 or 3");
            }
        }


        return exportMode == 2;
    }

    public static void export(List<Double> toWrite, boolean exportInFile) throws IOException {
        int length = toWrite.size();
        if (exportInFile) {
            String fileName = "./src/CompMathLab1/CodeTest/files/result.txt";
            File file = new File(fileName);
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write("Iterations: " + iterations + "\n");
            if (Solution.isMethodApplicable){
                for (int i = 0; i < length / 2; i++) {
                    double x = toWrite.get(i);
                    double eps = toWrite.get(i+(length / 2));
                    bufferedWriter.write("x" + (i+1) + ": " + x + "| err: " + eps + "\n");
                }
            } else {
                bufferedWriter.write(Solution.errorMessage + "\n");
            }
            bufferedWriter.close();

        }
        else {
            if (isMethodApplicable){
                System.out.println("Iterations: " + iterations);
                for (int i = 0; i < length / 2; i++) {
                    double x = toWrite.get(i);
                    double eps = toWrite.get(i+(length / 2));
                    System.out.println("x" + (i+1) + ": " + x + "| err: " + eps);
                }
            } else {
                System.out.println(errorMessage + "\n");
            }
        }
    }

    private static void handMode() throws IOException{
        System.out.print("Введите размерность матрицы >> ");
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
        int matrixRows = Integer.parseInt(consoleReader.readLine().trim());
        System.out.println("""
                Введите матрицу в следующем виде:
                a_11 a_12 ... a_1n b_1
                a_21 a_22 ... a_2n b_2
                .
                .
                a_n1 a_n2 ... a_nn b_n""");

        //noinspection DuplicatedCode
        IntStream.range(0, matrixRows).forEach(i -> {
            try {
                matrix.add(
                        Stream.of(consoleReader.readLine().replaceAll("\\s+$", "")
                                        .replaceAll(",", ".").split(" "))
                                .map(Double::parseDouble)
                                .collect(toList())
                );
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        ruleCheck(matrixRows, matrix);
        if(isMethodApplicable) {
            System.out.print("Введите значение максимальной погрешности >> ");
            epsilon = Double.parseDouble(consoleReader.readLine().trim());
        }
        consoleReader.close();
    }
    private static void readFromFile() throws IOException{
        System.out.print("Поместите файл в папку files и введите его название >> ");
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
        String fileName = "./src/CompMathLab1/CodeTest/files/" + consoleReader.readLine();
        consoleReader.close();
        File file = new File(fileName);
        BufferedReader fileReader = new BufferedReader(new FileReader(file));
        int matrixRows = Integer.parseInt(fileReader.readLine().trim());

        //noinspection DuplicatedCode
        IntStream.range(0, matrixRows).forEach(i ->{
           try {
               matrix.add (
               Stream.of(fileReader.readLine().replaceAll("\\s+$", "")
                       .replaceAll(",", ".").split(" "))
                       .map(Double::parseDouble)
                       .collect(toList())
               );
           }
           catch (IOException ex) {
               throw new RuntimeException(ex);
           }
        });
        ruleCheck(matrixRows, matrix);
        if(isMethodApplicable) {
            epsilon = Double.parseDouble(fileReader.readLine().trim());
            fileReader.close();
        }
    }

    private static void createRandom() {
        final int RANDOM_SIZE = 20;
        int range = 32;
        Random random = new Random();
        for (int i = 0; i < RANDOM_SIZE; i++) {
            List<Double> row = new ArrayList<>();
            for (int j = 0; j < RANDOM_SIZE; j++) {
                double ranNumber = random.nextDouble(range * 2) + (-range);
                row.add(ranNumber);
            }
            double diagonal = row.stream().map(Math::abs)
                    .reduce(0.0, Double::sum) * random.nextInt(2, 3);
            row.set(i, diagonal);
            row.add(random.nextDouble());
            matrix.add(row);
        }
    }
}
