import java.util.List;

public class Transporte {

    private Trechos trecho;
    private int qtdCaminhaoGrande = 0;
    private int qtdCaminhaoMedio = 0;
    private int qtdCaminhaoPequeno = 0;
    //private int distanciaTotal = 0;


    public Transporte(Trechos trecho) {
        this.trecho = trecho;
    }

    public double custoTrecho(String cidade1, String cidade2, Modalidades modalidade) {

        return trecho.distanciaEntreCidades(cidade1, cidade2) * modalidade.getValor();
    }

    public String modalidadeAdequada(double peso) {
        calculoQtdCaminhoes((int) Math.ceil(peso)); // converter peso para inteiro para facilitar o cálculo de caminhões
        StringBuilder stringBuilder = new StringBuilder();
        if (qtdCaminhaoGrande > 0) stringBuilder.append(qtdCaminhaoGrande).append(" de Porte Grande - ");
        if (qtdCaminhaoMedio > 0) stringBuilder.append(qtdCaminhaoMedio).append(" de Porte Médio - ");
        if (qtdCaminhaoPequeno > 0) stringBuilder.append(qtdCaminhaoPequeno).append(" de Porte Pequeno - ");

//        qtdCaminhaoGrande = 0; // retorna qtd a zero
//        qtdCaminhaoMedio = 0;
//        qtdCaminhaoPequeno = 0;

        return stringBuilder.toString();
    }

    private int calculoQtdCaminhoes(int peso) {
        if (peso >= Modalidades.GRANDE_PORTE.getPeso()) {
            peso -= Modalidades.GRANDE_PORTE.getPeso();
            qtdCaminhaoGrande++;
            return calculoQtdCaminhoes(peso);
        } else if (peso >= Modalidades.GRANDE_PORTE.getPeso() - 1000) {
            peso -= peso;
            qtdCaminhaoGrande++;
            return peso;
        } else if (peso >= Modalidades.MEDIO_PORTE.getPeso()) {
            peso -= Modalidades.MEDIO_PORTE.getPeso();
            qtdCaminhaoMedio++;
            return calculoQtdCaminhoes(peso);
        } else if (peso >= Modalidades.MEDIO_PORTE.getPeso() - 1000) {
            peso -= peso;
            qtdCaminhaoMedio++;
            return peso;
        } else if (peso > 0) {
            peso -= Modalidades.PEQUENO_PORTE.getPeso();
            qtdCaminhaoPequeno++;
            return calculoQtdCaminhoes(peso);
        }
        return 0;
    }


    public String consultarCustoTrecho(String cidade1, String cidade2, Modalidades modalidade) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("De " + cidade1 + " para " + cidade2 + ", utilizando um " + modalidade.toString() +
                ", a distância é de " + trecho.distanciaEntreCidades(cidade1, cidade2) + "km" +
                " e o custo será de R$" + custoTrecho(cidade1, cidade2, modalidade));

        return stringBuilder.toString();
    }

    public int distanciaTotalDoTrecho(List<String> listaCidades) {
        int distanciaTotal= 0;
        for (int i = 0; i < listaCidades.size() - 1; i++) {
            distanciaTotal += trecho.distanciaEntreCidades(listaCidades.get(i), listaCidades.get(i + 1));
        }
        return distanciaTotal;
    }
}
