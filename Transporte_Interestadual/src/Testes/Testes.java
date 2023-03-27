package Testes;

public class Testes {
    public static void main(String[] args) {
        int peso = 1200;
        int carga = 1000;
        int caixas = peso / carga;
        double sobra = peso % carga;

        System.out.println(caixas);
        System.out.println(sobra);
    }
}
