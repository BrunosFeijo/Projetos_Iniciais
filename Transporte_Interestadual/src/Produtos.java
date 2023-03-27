public enum Produtos {
    CELULAR(0.5), GELADEIRA(60.0), FREEZER(100.0), CADEIRA(5.0), LUMINARIA(0.8),LAVADORA_DE_ROUPA(120.0);

    private final double peso;
    Produtos(double peso){
        this.peso = peso;
    }

    public double getPeso() {
        return peso;
    }
    
}
