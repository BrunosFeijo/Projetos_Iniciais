import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Teste {
    public static void main(String[] args) {
        String csvFile = "C:\\Users\\bruno\\Desktop\\Teste PUC_DELL\\DNIT-Distancias.csv";
        String line = "";
        String csvSeparator = ";";
        int[] intArray = null;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            // Lê a primeira linha do arquivo
            line = br.readLine();
            line = br.readLine();

            // Divide a linha em substrings e converte em inteiros
            String[] aux = line.split(";");
            intArray = Arrays.stream(aux).mapToInt(Integer::parseInt).toArray();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Imprime o array de inteiros
        System.out.println(Arrays.toString(intArray));
    }
}

