import java.util.List;

public class Transporte {

    private Trechos trecho;
    private int qtdCaminhaoGrande = 0;
    private int qtdCaminhaoMedio = 0;
    private int qtdCaminhaoPequeno = 0;


    public Transporte(Trechos trecho) {
        this.trecho = trecho;
    }

    public double custoTrecho(String cidade1, String cidade2, Modalidades modalidade) {

        return trecho.distanciaEntreCidades(cidade1, cidade2) * modalidade.getValor();
    }

    public String modalidadeAdequada(double peso) {
        calculoQtdCaminhoes((int) Math.ceil(peso));
        StringBuilder stringBuilder = new StringBuilder();
        if (qtdCaminhaoGrande > 0) stringBuilder.append(qtdCaminhaoGrande).append(" de Porte Grande - ");
        if (qtdCaminhaoMedio > 0) stringBuilder.append(qtdCaminhaoMedio).append(" de Porte Médio - ");
        if (qtdCaminhaoPequeno > 0) stringBuilder.append(qtdCaminhaoPequeno).append(" de Porte Pequeno - ");

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
        } else if (peso >= Modalidades.PEQUENO_PORTE.getPeso()) {
            peso -= Modalidades.PEQUENO_PORTE.getPeso();
            qtdCaminhaoPequeno++;
            return calculoQtdCaminhoes(peso);
        }
        return 0;
    }

    public double calculoPesoProdutos(List<Produtos> produtos, List<Double> qtds) {
        double peso = 0;
        int i = 0;

        while(produtos.isEmpty()){
            switch (produtos.get(i)){
                case CELULAR -> peso += qtds.get(i) * Produtos.CELULAR.getPeso();
                case GELADEIRA -> peso += qtds.get(i) * Produtos.GELADEIRA.getPeso();
                case FREEZER -> peso += qtds.get(i) * Produtos.FREEZER.getPeso();
                case CADEIRA -> peso += qtds.get(i) * Produtos.CADEIRA.getPeso();
                case LUMINARIA -> peso += qtds.get(i) * Produtos.LUMINARIA.getPeso();
                case LAVADORA_DE_ROUPA -> peso += qtds.get(i) * Produtos.LAVADORA_DE_ROUPA.getPeso();
            }
            i++;
        }

        return peso;
    }

    public String consultarCustoTrecho(String cidade1, String cidade2, Modalidades modalidade) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("De " + cidade1 + " para " + cidade2 + ", utilizando um " + modalidade.toString() +
                ", a distância é de " + trecho.distanciaEntreCidades(cidade1, cidade2) + "km" +
                " e o custo será de R$" + custoTrecho(cidade1, cidade2, modalidade));

        return stringBuilder.toString();
    }
}
