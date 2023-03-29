import java.util.List;

public class Transporte {

    private final Trechos trecho;
    private List<Integer> distancias;
    private List<Double> custos;
    private int qtdCaminhaoGrande;
    private int qtdCaminhaoMedio;
    private int qtdCaminhaoPequeno;


    public Transporte(Trechos trecho) {
        this.trecho = trecho;
    }

    public double custoTrecho(String cidade1, String cidade2, Modalidades modalidade, int qtd) {

        return trecho.distanciaEntreCidades(cidade1, cidade2) * modalidade.getValor() * qtd;
    }

    public String modalidadeAdequada(double peso) {
        calculoQtdCaminhoes(peso); // converter peso para inteiro para facilitar o cálculo de caminhões

        StringBuilder stringBuilder = new StringBuilder();
        if (qtdCaminhaoGrande > 0) stringBuilder.append(qtdCaminhaoGrande).append(" de Porte Grande - ");
        if (qtdCaminhaoMedio > 0) stringBuilder.append(qtdCaminhaoMedio).append(" de Porte Médio - ");
        if (qtdCaminhaoPequeno > 0) stringBuilder.append(qtdCaminhaoPequeno).append(" de Porte Pequeno - ");


        return stringBuilder.toString();
    }

    private double calculoQtdCaminhoes(double peso) {
        qtdCaminhaoGrande = 0; // retorna qtd a zero para refazer o cálculo em caso de entrega de carga parcial
        qtdCaminhaoMedio = 0;
        qtdCaminhaoPequeno = 0;

        //verifica custo-benefício entre os veículos e as cargas (recursiva)
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
        stringBuilder.append("De ").append(cidade1).append(" para ").append(cidade2)
                .append(", utilizando um ").append(modalidade.toString())
                .append(", a distância é de ").append(trecho.distanciaEntreCidades(cidade1, cidade2)).append("km")
                .append(" e o custo será de R$").append(custoTrecho(cidade1, cidade2, modalidade, 1));

        return stringBuilder.toString();
    }

    public int distanciaTotalDoTrecho(List<String> listaCidades) {
        int distanciaTotal = 0;
        for (int i = 0; i < listaCidades.size() - 1; i++) {
            distancias.add(trecho.distanciaEntreCidades(listaCidades.get(i), listaCidades.get(i + 1))); // adiciona os trechos separadamente para cálculo final
            distanciaTotal += distancias.get(distancias.size() - 1);
        }
        return distanciaTotal;
    }

    private void custoFinal(List<String> listaCidades, double peso) {
        calculoQtdCaminhoes(peso); //definir quantidade de caminhões por modelo

        double custoTotalTrecho = 0;
        for (int i = 0; i < listaCidades.size() - 1; i++) {
            if (qtdCaminhaoGrande > 0) {
                custoTotalTrecho += custoTrecho(listaCidades.get(i), listaCidades.get(i + 1), Modalidades.GRANDE_PORTE, qtdCaminhaoGrande);
            }
            if (qtdCaminhaoMedio > 0) {
                custoTotalTrecho += custoTrecho(listaCidades.get(i), listaCidades.get(i + 1), Modalidades.MEDIO_PORTE, qtdCaminhaoMedio);
            }
            if (qtdCaminhaoPequeno > 0) {
                custoTotalTrecho += custoTrecho(listaCidades.get(i), listaCidades.get(i + 1), Modalidades.PEQUENO_PORTE, qtdCaminhaoPequeno);
            }
            calculoQtdCaminhoes(peso); // redefinir quantidade de caminhões após as paradas e entregas parciais.
        }
        custos.add(custoTotalTrecho);
    }
    private double entregasParciais(double peso){

        return 0;
    }
}
