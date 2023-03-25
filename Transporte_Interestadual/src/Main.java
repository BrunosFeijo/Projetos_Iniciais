import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        String caminho = "C:\\Users\\bruno\\Desktop\\Teste PUC_DELL\\DNIT-Distancias.csv";

        // Inicilizar BufferReader e FileReader no try para que sejam desalocados automaticamente no final
        // sem necessitar de blobo finally
        try (BufferedReader arquivo = new BufferedReader(new FileReader(caminho))) {
            String linha = arquivo.readLine(); // ler primeira linha
            String[] cidades = linha.split(";"); // capturar nome das cidades na primeira linha
            int[] distancias = null;
            int i = 0; // será usado para contar colunas da matriz

            linha = arquivo.readLine();
            String[] aux = linha.split(";"); // necessário para usar map
            distancias = Arrays.stream(aux).mapToInt(Integer::parseInt).toArray();

            while (linha != null) {

            }
        } catch (IOException e) {
            System.out.println("Erro " + e.getMessage());
        }
    }
}
