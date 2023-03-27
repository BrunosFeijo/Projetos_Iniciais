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
            System.out.print("Linha " + i + ": ");
            for (int j = 0; j < distancias.indexOf(i); j++) {
                System.out.println();
            }
        }

        return stringBuilder.toString();
    }
}
