import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Cidades {
    String[] cidades;
    List<int[]> distancias;
    //int tamanho;

    public Cidades(String[] cidades) {
        this.cidades = cidades;
        distancias = new ArrayList<>();
        //this.tamanho = cidades.length;
    }

    public void adicionarVetorDistancia(int[] distancia) {
        this.distancias.add(distancia);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Cidades=" + Arrays.toString(cidades) + "\n");
        for (int i = 0; i < distancias.size(); i++) {
            stringBuilder.append("Linha " + i + "{");
            int[] vetor = distancias.get(i);

            for (int j = 0; j < distancias.get(i).length -1 ; j++) {
                stringBuilder.append(vetor[j] + ", ");
            }

            stringBuilder.append(vetor[distancias.get(i).length -1 ] + "}\n");
        }

        return stringBuilder.toString();
    }
}
