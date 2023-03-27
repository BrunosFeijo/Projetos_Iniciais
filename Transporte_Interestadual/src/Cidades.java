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
        stringBuilder.append("Cidades{" + "cidades=" + Arrays.toString(cidades) + "\n");
        for (int i = 0; i < distancias.size(); i++) {
            stringBuilder.append("Linha " + i + "{ ");
            for (int j = 0; j < distancias.indexOf(i); j++) {
                stringBuilder.append(distancias.indexOf(i).);
            }
            stringBuilder.append("}\n");
        }

        return stringBuilder.toString();
    }
}
