import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        String caminho = "C:\\Users\\bruno\\Desktop\\Teste PUC_DELL";

        try (BufferedReader arquivo = new BufferedReader(new FileReader(caminho))) {
            String linha = arquivo.readLine();
            String cidades[] = linha.split(",");
            int i = 0;

            linha = arquivo.readLine();
            int distancias[] = Integer.parseInt(linha);
            while (linha != null) {
                distancias[i++]
            }
        } catch (IOException e) {
            System.out.println("Erro " + e.getMessage());
        }
    }
}
