import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class Main {
    static List<String> cidadesConsulta = new ArrayList<>();
    static List<Produtos> produtos = new ArrayList<>(); // permitir que o usuário liste vários itens
    static List<Produtos> produtosEntregues = new ArrayList<>();// guardar produtos entregues e mais tarde comparar pesos
    static List<Integer> qtdProdutos = new ArrayList<>(); // permitir que o usuário liste as suas quantidades
    static List<Integer> qtdProdutosEntregues = new ArrayList<>(); // permitir que o usuário liste as suas quantidades
    static List<Double> pesos = new ArrayList<>();
    static List<String> trechos = new ArrayList<>(); // permitir que o usuário liste várias cidades
    static Set<String> evitarDuplicados = new HashSet<>(); // utilizar Set para evitar duplicidade de cidades

    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        String caminho = "C:\\Users\\bruno\\Desktop\\Teste PUC_DELL\\DNIT-Distancias.csv";
        Trechos cidades = null; // criado fora do try para ser usado posteriormente
        int opcao;
        double peso = 0;
        int distancia = 0;
        String caminhoesNecessarios = "";
        double custoTotal = 0;
        String cidade1 = null;
        String cidade2 = null;


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
        opcao = menuPrincipal();

        while (opcao != 0) {
            switch (opcao) {
                case 1:
                    escolherCidadeParaConsulta(t1, cidades.getCidades());
                    System.out.println(t1.consultarCustoTrecho(cidadesConsulta.get(0), cidadesConsulta.get(1), Modalidades.MEDIO_PORTE));
                    break;
                case 2:
                    distancia = escolherCidade(t1, cidades.getCidades()); //método para escolher cidades do trajeto retorna a distância
                    break;
                case 3:
                    peso = menuProdutos();// menu de produtos retorna o peso
                    pesos.add(peso);
                    caminhoesNecessarios = t1.modalidadeAdequada(peso);//verificação das modalidades de transporte retorna uma String
                    break;
                case 4:
                    entregarCarga();
                    break;
                case 5:
                    if (trechos.size() == 0 || produtos.size() == 0) {
                        System.out.println("Pedido incompleto");
                    } else {
                        t1.custoFinal(trechos, pesos.get(0), pesos);
                        System.out.println(textoTransporteFinal(trechos, distancia, produtos,
                                qtdProdutos, peso, caminhoesNecessarios, t1));
                    }
                    break;
            }
            opcao = menuPrincipal();
        }


    }

    public static int menuPrincipal() {
        Scanner entrada = new Scanner(System.in);
        int opcao = -1;
        boolean opcaoValida = true;

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n---------------------------Menu---------------------------\n");
        stringBuilder.append("1- Consultar custo de trecho unitário (valor de carga média)\n");
        stringBuilder.append("2- Informar trajeto\n");
        stringBuilder.append("3- Informar produtos e quantidades\n");
        stringBuilder.append("4- Informar ordem de entrega e quantidade\n");
        stringBuilder.append("5- Resumo pedido\n");
        stringBuilder.append("0- Cancelar e Sair\n");
        stringBuilder.append("-------------------------------------------------------------\n");
        stringBuilder.append("Digite a opção desejada: ");


        while (opcaoValida) {
            System.out.print(stringBuilder.toString());
            opcao = entrada.nextInt();
            opcaoValida = false;
            if (opcao < 0 || opcao > 5) {
                System.out.println("Digite uma opção válida");
                opcaoValida = true;
            }
        }
        return opcao;
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
            System.out.println("0 - Finalizar");
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

    public static void entregarCarga() {
        if (produtos.size() == 0) {
            System.out.println("Preencha a lista de produtos antes de preencher a lista de entrega");
        } else {
            Scanner entrada = new Scanner(System.in);
            Produtos produto;
            double peso = 0;
            int opcao;// opção para menu de produtos
            int qtd; // quantidade a ser definida para cada produto

            System.out.println("Estão cadastradas " + (trechos.size() - 1) + " paradas para entrega.");
            System.out.println("Digite na ordem das entregas quais produtos devem ser descarregados. ");
            for (int i = 0; i < trechos.size() - 1; i++) {
                opcao = -1;
                while (opcao != 0) {
                    System.out.println("----------Menu de Produtos----------");
                    System.out.println("1 - Celular");
                    System.out.println("2 - Geladeira");
                    System.out.println("3 - Freezer");
                    System.out.println("4 - Cadeira");
                    System.out.println("5 - Luminária");
                    System.out.println("6 - Lavadora de Roupa");
                    System.out.println("0 - Finalizar");
                    System.out.println("------------------------------------");
                    System.out.print("Quais produtos devem ser entregues em " + trechos.get(i + 1) + ": ");

                    opcao = entrada.nextInt();
                    if (opcao > 0 && opcao < 7) {// verificar se opção é válida
                        System.out.print("Escolha a quantidade: ");
                        qtd = entrada.nextInt();
                        if (verificarListaDeProdutos(definirProduto(opcao), qtd)) {// verificar se opção existe no pedido
                            produtosEntregues.add(definirProduto(opcao)); // adicionar produto selecionado na lista
                            qtdProdutosEntregues.add(qtd);
                            peso += calculoPesoProdutos(produtosEntregues, qtdProdutosEntregues);
                            produtosEntregues.clear();
                            qtdProdutosEntregues.clear();
                        } else {
                            System.out.println("Item ou quantidade não foi adicionado no pedido");
                        }
                    } else if (opcao != 0) { // se não...
                        System.out.println("Opção inválida. Tente novamente!");
                        System.out.println("\n");
                    }
                }
                pesos.add(peso);
            }
        }
    }

    public static boolean verificarListaDeProdutos(Produtos produto, int qtd) {
        if (produtos.indexOf(produto) == -1) { // se o produto não for encontrato na lista de pedidos já feitos, retorna falso
            return false;
        } else {
            //se a quantidade encontrada for maior ou igual que a solicitada retorna verdadeiro
            return qtdProdutos.get(produtos.indexOf(produto)) >= qtd;
        }
    }

    public static int escolherCidadeParaConsulta(Transporte transporte, String[] listaCidades) {
        Scanner entrada = new Scanner(System.in);
        trechos.clear(); // caso seja necessário solicitar mais de uma entrega
        int opcao = -1;

        //Ajustando interface gráfica
        JFrame frame = new JFrame("Selecione uma cidade"); //janela
        JPanel panel = new JPanel(); // painel
        JLabel label = new JLabel("Cidades: "); // rótulo do combobox
        JComboBox<String> combo = new JComboBox<>(listaCidades); // inserir lista de cidades no combobox

        while (opcao != 0 && cidadesConsulta.size() < 2) {

            combo.addActionListener(event -> {
                String cidadeSelecionada = (String) combo.getSelectedItem();
                if (evitarDuplicados.add(cidadeSelecionada)) { //testar duplicidade das cidades usando HashSet (auxiliar)
                    cidadesConsulta.add(cidadeSelecionada); //adicionado na lista real
                }
                frame.dispose();
            });

            panel.add(label);
            panel.add(combo);
            frame.add(panel);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            System.out.print("Selecione qualquer número para continuar ou 0 para sair: ");
            opcao = entrada.nextInt();
        }
        return transporte.distanciaTotalDoTrecho(trechos);
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
                if (evitarDuplicados.add(cidadeSelecionada)) { //testar duplicidade das cidades usando HashSet (auxiliar)
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
        return transporte.distanciaTotalDoTrecho(trechos);
    }

    public static String textoTransporteFinal(List<String> listaCidades, int distancia, List<Produtos> produtos,
                                              List<Integer> qtdProdutos, double peso, String caminhoes, Transporte t1) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n-----------------Resumo-----------------\n");
        stringBuilder.append("Trajeto: ");

        //listar cidades selecionadas (menos a última para não colocar a vírgula no final)
        for (int i = 0; i < listaCidades.size() - 1; i++) {
            stringBuilder.append(listaCidades.get(i)).append(", ");
        }
        stringBuilder.append(listaCidades.get(listaCidades.size() - 1)).append("\n"); // agora sim, é a última

        stringBuilder.append("Distância Total: ").append(distancia).append("km\n");
        stringBuilder.append("Produtos(qtd): ");

        //listar produtos selecionados e as suas quantidades (menos a última para não colocar a vírgula no final)
        for (int i = 0; i < produtos.size() - 1; i++) {
            stringBuilder.append(produtos.get(i)).append("(").append(qtdProdutos.get(i)).append("), ");
        }
        stringBuilder.append(produtos.get(produtos.size() - 1))
                .append("(").append(qtdProdutos.get(qtdProdutos.size() - 1)).append(")\n"); // agora sim, é o último
        stringBuilder.append("Peso Total: ").append(peso).append("kg\n");
        stringBuilder.append("Veículos necessários para menor custo: ").append(caminhoes).append("\n");
        stringBuilder.append("Custo por Trecho: ");
//        for (int i = 0; 0 < trechos.size(); i++) {
//            stringBuilder.append((i + 1)).append("° entrega custou: ").append(t1.getCustos().get(i)).append("\n");
//        }

        return stringBuilder.toString();
    }
}
