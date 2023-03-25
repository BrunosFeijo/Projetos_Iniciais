public enum Caminhoes {
    PEQUENO(1), MEDIO(4), GRANDE(10);

    private int peso;

    Caminhoes(int peso) {
        this.peso = peso;
    }

    public int getPeso() {
        return peso;
    }
}
