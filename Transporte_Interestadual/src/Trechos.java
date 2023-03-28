import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Trechos {
    String[] cidades;
    List<int[]> valores;

    public Trechos(String[] cidades) {
        this.cidades = cidades;
        valores = new ArrayList<>();
    }

    public void adicionarVetorDistancia(int[] distancia) {
        this.valores.add(distancia);
    }

    public int indiceCidade(String nome) {
        for (int i = 0; i < cidades.length; i++) {
            if (cidades[i].equalsIgnoreCase(nome)) return i;
        }

        return -1;
    }

    public int distanciaEntreCidades(String cidade1, String cidade2) {
        int[] distancias = valores.get(indiceCidade(cidade1));

        return distancias[indiceCidade(cidade2)];
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Cidades=" + Arrays.toString(cidades) + "\n");
        for (int i = 0; i < valores.size(); i++) {
            stringBuilder.append("Linha " + i + "{");
            int[] vetor = valores.get(i);

            for (int j = 0; j < valores.get(i).length - 1; j++) {
                stringBuilder.append(vetor[j] + ", ");
            }

            stringBuilder.append(vetor[valores.get(i).length - 1] + "}\n");
        }

        return stringBuilder.toString();
    }
}
