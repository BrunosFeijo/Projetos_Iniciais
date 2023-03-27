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
            Cidades cidades = new Cidades(linha.split(";")); // capturar nome das cidades na primeira linha
            int i = 0; // será usado para contar colunas da matriz

            linha = arquivo.readLine();
            while (linha != null) {
                //adicionar as linhas restantes da lista de distâncias dentro do objeto 'cidades'
                //assim criamos uma 'matriz' a ser consultada com as distâncias via índice da List
                cidades.adicionarVetorDistancia(Arrays.stream(linha.split(";")).mapToInt(Integer::parseInt).toArray());

                linha = arquivo.readLine();
            }

            System.out.println(cidades);
        } catch (IOException e) {
            System.out.println("Erro " + e.getMessage());
        }

    }
}
