import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {
    static List<Produtos> produtos = new ArrayList<>(); // permitir que o usuário liste vários itens
    static List<Integer> qtdProdutos = new ArrayList<>(); // permitir que o usuário liste as suas quantidades
    static List<String> trechos = new ArrayList<>(); // permitir que o usuário liste várias cidades
    static Set<String> evitarDuplicados = new HashSet<>(); // utilizar Set para evitar duplicidade de cidades
    public static void main(String[] args) {
        String caminho = "C:\\Users\\bruno\\Desktop\\Teste PUC_DELL\\DNIT-Distancias.csv";
        Trechos cidades = null; // criado fora do try para ser usado posteriormente
        double peso;


        // Inicilizar BufferReader e FileReader no try para que sejam desalocados automaticamente no final
        // sem necessitar de blobo finally
        try (BufferedReader arquivo = new BufferedReader(new FileReader(caminho))) {
            String linha = arquivo.readLine(); // ler primeira linha
            cidades = new Trechos(linha.split(";")); // capturar nome das cidades na primeira linha

            linha = arquivo.readLine();
            while (linha != null) { // verifica se a linha lida ainda contém dados ou já está vazia

                //adicionar as linhas restantes da lista de distâncias no objeto 'cidades'
                //assim criamos uma 'matriz' a ser consultada com as distâncias via índice da List
                cidades.adicionarVetorDistancia(Arrays.stream(linha.split(";")).mapToInt(Integer::parseInt).toArray());

                linha = arquivo.readLine(); // nova leitura
            }

        } catch (IOException e) {
            System.out.println("Erro " + e.getMessage());
        }
        Transporte t1 = new Transporte(cidades);//inicializa o objeto 'Transporte' com a matriz de distâncias

        System.out.println(t1.consultarCustoTrecho("Manaus", "Curitiba", Modalidades.MEDIO_PORTE));

        System.out.println("Distância ToTal: " + escolherCidade(t1,cidades.getCidades()) + "km");
        peso = menuProdutos();
        System.out.println("Peso Total: " + peso);
        System.out.println(t1.modalidadeAdequada(peso));
    }

    public static double menuProdutos() {
        Scanner entrada = new Scanner(System.in);
        produtos.clear(); // caso haja um novo pedido no mesmo carrinho
        qtdProdutos.clear(); // caso haja um novo pedido no mesmo carrinho
        int opcao = -1; // opção para menu de produtos
        int qtd; // quantidade a ser definida para cada produto

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
                produtos.add(definirProduto(opcao)); // adicionar produto selecionado na lista
                qtdProdutos.add(qtd);
            } else if (opcao != 0) { // se não...
                System.out.println("Opção inválida. Tente novamente!");
                System.out.println("\n");
            }
        }
        return calculoPesoProdutos(produtos, qtdProdutos); // retornar peso total
    }

    public static Produtos definirProduto(int opcao) {
        //Converter opção de inteiro para enumerador
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
        //Iterar todos os produtos da lista e gerar peso final para definir tamanho adequado de transporte
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

    public static int escolherCidade(Transporte transporte, String[] listaCidades) {
        Scanner entrada = new Scanner(System.in);
        trechos.clear(); // caso seja necessário solicitar mais de uma entrega
        int opcao;

        //Ajustando interface gráfica
        JFrame frame = new JFrame("Selecione uma cidade"); //janela
        JPanel panel = new JPanel(); // painel
        JLabel label = new JLabel("Cidades: "); // rótulo do combobox
        JComboBox<String> combo = new JComboBox<>(listaCidades); // inserir lista de cidades no combobox

        System.out.print("Se deseja adicionar uma cidade ao trajeto digite 1. Ou 0 para finalizar: ");
        opcao = entrada.nextInt();
        while (opcao != 0 || trechos.size() < 2) {
            combo.addActionListener(event -> {
                String cidadeSelecionada = (String) combo.getSelectedItem();
                if (evitarDuplicados.add(cidadeSelecionada)){ //testar duplicidade das cidades usando HashSet (auxiliar)
                    trechos.add(cidadeSelecionada); //adicionado na lista real
                }
                frame.dispose();
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

        return transporte.distanciaTotalDoTrecho(trechos);
    }
    public static String textoTransporteFinal(List<String> listaCidades, int distancia, List<Produtos> produtos,
                                              List<Integer> qtdProdutos,String caminhoes, double custoTotal){

        StringBuilder stringBuilder =new StringBuilder();


        return stringBuilder.toString();
    }
}
