public enum Modalidades {
    PEQUENO_PORTE(1, 4.87, "Caminhão de Pequeno Porte"),
    MEDIO_PORTE(4,11.92, "Caminhão de Médio Porte"),
    GRANDE_PORTE(10,27.44, "Caminhão de Grande Porte");

    private final int peso;
    private final double valor;
    private final String nome;

    Modalidades(int peso, double valor, String nome) {
        this.peso = peso;
        this.valor = valor;
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public int getPeso() {
        return peso;
    }
    public double getValor(){
        return valor;
    }

    @Override
    public String toString() {
        return getNome();
    }
}
