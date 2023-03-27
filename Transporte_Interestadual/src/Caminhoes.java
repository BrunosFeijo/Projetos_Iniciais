public enum Caminhoes {
    PEQUENO(1, 4.87), MEDIO(4,11.92), GRANDE(10,27.44);

    private final int peso;
    private final double valor;

    Caminhoes(int peso, double valor) {
        this.peso = peso;
        this.valor = valor;
    }

    public int getPeso() {
        return peso;
    }
    public double getValor(){
        return valor;
    }
}
