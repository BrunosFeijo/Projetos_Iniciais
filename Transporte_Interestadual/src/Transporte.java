public class Transporte {

    private Trechos trecho;

    public Transporte(Trechos trecho) {
        this.trecho = trecho;
    }
    public double custoTrecho(String cidade1, String cidade2, Modalidades modalidade){

        return trecho.distanciaEntreCidades(cidade1, cidade2) * modalidade.getValor();
    }
    public String modalidadeAdequada(double peso){
        int qtdGrande = 0;
        int qtdMedio = 0;
        int qtdPequeno = 0;
        double sobra = peso;

        StringBuilder stringBuilder = new StringBuilder();

        if ((peso % Modalidades.GRANDE_PORTE.getPeso()) >= 0){

        }

        return stringBuilder.toString();
    }
    public String consultarCustoTrecho(String cidade1, String cidade2, Modalidades modalidade){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("De " + cidade1 + " para " + cidade2 + " utilizando um " + modalidade.toString() +
                " a distância é de " + trecho.distanciaEntreCidades(cidade1,cidade2) + "km" +
                " e o custo será de R$" + custoTrecho(cidade1, cidade2, modalidade));

        return stringBuilder.toString();
    }

}
