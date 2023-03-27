public enum Modalidades {
    PEQUENO_PORTE(1, 4.87), MEDIO_PORTE(4,11.92), GRANDE_PORTE(10,27.44);

    private final int peso;
    private final double valor;

    Modalidades(int peso, double valor) {
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
