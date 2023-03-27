public class Transporte {
    private Modalidades modalidade;
    private Trechos trecho;
    private Produtos produtos;

    public Transporte(Trechos trecho) {
        this.trecho = trecho;
    }
    public double custoTrecho(String cidade1, String cidade2, Modalidades modalidade){

        return trecho.distanciaEntreCidades(cidade1, cidade2) * modalidade.getValor();
    }
    public String informarCustoTrecho(String cidade1, String cidade2, Modalidades modalidade){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("De " + cidade1 + " para " + cidade2 + " utilizando um " + modalidade.toString() +
                " a distância é de " + trecho.distanciaEntreCidades(cidade1,cidade2) + "km" +
                " e o custo será de R$" + custoTrecho(cidade1, cidade2, modalidade));

        return stringBuilder.toString();
    }
}
