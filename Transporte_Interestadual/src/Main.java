import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        String caminho = "C:\\Users\\bruno\\Desktop\\Teste PUC_DELL\\DNIT-Distancias.csv";
        Trechos cidades = null; // criado fora do try para ser usado posteriormente
        double peso = 0;


        // Inicilizar BufferReader e FileReader no try para que sejam desalocados automaticamente no final
        // sem necessitar de blobo finally
        try (BufferedReader arquivo = new BufferedReader(new FileReader(caminho))) {
            String linha = arquivo.readLine(); // ler primeira linha
            cidades = new Trechos(linha.split(";")); // capturar nome das cidades na primeira linha
            int i = 0; // será usado para contar colunas da matriz

            linha = arquivo.readLine();
            while (linha != null) { // verifica se a linha lida ainda contém dados ou já está vazia

                //adicionar as linhas restantes da lista de distâncias dentro do objeto 'cidades'
                //assim criamos uma 'matriz' a ser consultada com as distâncias via índice da List
                cidades.adicionarVetorDistancia(Arrays.stream(linha.split(";")).mapToInt(Integer::parseInt).toArray());

                linha = arquivo.readLine(); // nova leitura
            }

        } catch (IOException e) {
            System.out.println("Erro " + e.getMessage());
        }
        Transporte t1 = new Transporte(cidades);//inicializa o objeto 'Transporte' com a lista de distancias

        System.out.println(t1.consultarCustoTrecho("Manaus", "Curitiba", Modalidades.GRANDE_PORTE));
        escolherCidade(cidades.getCidades());

        peso = menuProdutos();
        System.out.println(t1.modalidadeAdequada(peso));
    }

    public static double menuProdutos() {
        Scanner entrada = new Scanner(System.in);
        List<Produtos> produtos = new ArrayList<>(); // permitir que o usuário liste vários itens
        List<Integer> qtdProdutos = new ArrayList<>(); // permitir que o usuário liste suas quantidades
        int opcao = -1; // opção para menu de produtos
        int qtd = 0; // quantidade a ser definida para cada produto

        while (opcao != 0) {
            System.out.println("----------Menu de Produtos----------");
            System.out.println("1 - Celular");
            System.out.println("2 - Geladeira");
            System.out.println("3 - Freezer");
            System.out.println("4 - Cadeira");
            System.out.println("5 - Luminária");
            System.out.println("6 - Lavadora de Roupa");
            System.out.println("0 - Cancelar");
            System.out.println("------------------------------------");

            System.out.print("Escolha um produto conforme seu número: ");
            opcao = entrada.nextInt();
            if (opcao > 0 && opcao < 7) {// verificar se opção é válida
                System.out.print("Escolha a quantidade: ");
                qtd = entrada.nextInt();
                produtos.add(definirProduto(opcao));
                qtdProdutos.add(qtd);
            } else if (opcao != 0) { // se não...
                System.out.println("Opção inválida. Tente novamente!");
                System.out.println("\n");
            }
        }
        return calculoPesoProdutos(produtos, qtdProdutos); // retornar peso total
    }

    public static Produtos definirProduto(int opcao) {
        Produtos produto = null;
        switch (opcao) {
            case 1 -> produto = Produtos.CELULAR;
            case 2 -> produto = Produtos.GELADEIRA;
            case 3 -> produto = Produtos.FREEZER;
            case 4 -> produto = Produtos.CADEIRA;
            case 5 -> produto = Produtos.LUMINARIA;
            case 6 -> produto = Produtos.LAVADORA_DE_ROUPA;
        }
        return produto;
    }

    public static double calculoPesoProdutos(List<Produtos> produtos, List<Integer> qtds) {
        double peso = 0;
        int i = produtos.size() - 1;

        while (i >= 0) {
            switch (produtos.get(i)) {
                case CELULAR -> peso += qtds.get(i) * Produtos.CELULAR.getPeso();
                case GELADEIRA -> peso += qtds.get(i) * Produtos.GELADEIRA.getPeso();
                case FREEZER -> peso += qtds.get(i) * Produtos.FREEZER.getPeso();
                case CADEIRA -> peso += qtds.get(i) * Produtos.CADEIRA.getPeso();
                case LUMINARIA -> peso += qtds.get(i) * Produtos.LUMINARIA.getPeso();
                case LAVADORA_DE_ROUPA -> peso += qtds.get(i) * Produtos.LAVADORA_DE_ROUPA.getPeso();
            }
            i--;
        }

        return peso;
    }

    public static void escolherCidade(String[] listaCidades) {
        Scanner entrada = new Scanner(System.in);
        String[] cidades = listaCidades; // inicializando lista de cidades para dar opções ao usuário
        Set<String> trechos = new HashSet<>(); // utilizar Set para evitar duplicidade de cidades
        int opcao = -1;

        //Ajustando interface gráfica
        JFrame frame = new JFrame("Cidades");
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Selecione uma cidade:");
        JComboBox<String> combo = new JComboBox<String>(cidades);

        System.out.print("Se deseja adicionar uma cidade ao trajeto digite 1. Ou 0 para finalizar: ");
        opcao = entrada.nextInt();
        while (opcao != 0) {
            combo.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    String cidadeSelecionada = (String) combo.getSelectedItem();
                    trechos.add(cidadeSelecionada);
                    frame.dispose();
                }
            });

            panel.add(label);
            panel.add(combo);
            frame.add(panel);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            System.out.print("Se deseja adicionar uma cidade ao trajeto digite 1. Ou 0 para finalizar: ");
            opcao = entrada.nextInt();
        }
        System.out.println(trechos.toString());
    }
}
