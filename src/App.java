import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class App {
    private Scanner entrada = null; // Atributo para entrada de dados
    // Construtor
    public App() {
        try {
            BufferedReader streamEntrada = new BufferedReader(new FileReader("dados.csv"));
            entrada = new Scanner(streamEntrada); // Usa como entrada um arquivo
            PrintStream streamSaida = new PrintStream (new File("resultado.csv"), Charset.forName("UTF-8"));
            System.setOut(streamSaida); // Usa como saida um arquivo
        } catch (Exception e) {
            System.err.println(e);
        }
        entrada.useLocale(Locale.ENGLISH);
    }

    public void executa() {
        //variaveis auxiliares para todos os passos do programa
        Acervo acervo = new Acervo();
        ArrayList<AudioVisual> auxAv = acervo.getColecao();

        lerAudiovisuais(entrada, acervo); // feature 1
        infoItens(auxAv); // feature 2
        qtdRpgsCadastrados(auxAv); // feature 3
        impostoMaisProxMedia(auxAv); // feature 4
    }

    /**
     * Lê todos os dados de audiovisuais e escreve a quantidade de
     * itens carregados com sucesso
     * @param entrada arquivo de entrada a ser lido
     * @param acervo colecao de audiovisuaus a ser populada
     */
    private void lerAudiovisuais(Scanner entrada, Acervo acervo) {
        int qtdCadastrados = 0;
        while (entrada.hasNextLine()) {
            try {
                String linha = entrada.nextLine();
                String[] tokens = linha.split(";");
                AudioVisual audioVisual;

                // o formato de dados.csv é: titulo;precobase;tipo;duração_categoria
                // tokens[] vai ser um array no formato: [titulo, precobase, tipo, duracao_categoria]
                // logo: tokens[2] vai ser o tipo de AudioVisual (1 = bluray e 2 = game)
                int tipoAux = Integer.parseInt(tokens[2]);
                String titulo = tokens[0];
                double precoBase = Double.parseDouble(tokens[1]);

                if (tipoAux == 1) { // checagem do tipo de audiovisual
                    int duracao = Integer.parseInt(tokens[3]);
                    audioVisual = new BluRay(titulo, precoBase, duracao);
                } else {
                    Categoria categoria = Categoria.valueOf(tokens[3]);
                    audioVisual = new Game(titulo, precoBase, categoria);
                }

                // adiciona o audio visual na colecao
                if (acervo.addAudioVisual(audioVisual))
                    qtdCadastrados++;

            } catch (ArrayIndexOutOfBoundsException e) {
                System.err.println("ERRO: Numero invalido de itens em linha");
                e.printStackTrace();
            }
        }
        System.out.printf("1;%d\n", qtdCadastrados);
    }

    /**
     * Escreve algumas informações para cada item
     * carregado com sucesso no sistema
     * @param auxAv colecao de audiovisuais já populada
     */
    private void infoItens(ArrayList<AudioVisual> auxAv) {
        for (AudioVisual av : auxAv) {
            String titulo = av.getTitulo();
            double precoVenda = av.calculaPrecoVenda();
            double imposto = av.calculaImposto();

            System.out.printf("2;%s;%.2f;%.2f\n", titulo, precoVenda, imposto);
        }
    }

    /**
     * Escreve a quantidade de games da categoria RPG que
     * foram carregados no sistema
     * @param auxAv colecao de audiovisuais já populada
     */
    private void qtdRpgsCadastrados(ArrayList<AudioVisual> auxAv) {
        int contRPG = 0; // feature 3
        for (AudioVisual av : auxAv) {
            if (av instanceof Game && ((Game) av).getCategoria() == Categoria.RPG)
                contRPG++;
        }
        System.out.printf("3;%d\n", contRPG);
    }

    /**
     * Calcula a média dos valores de imposto de BluRays
     * carregados e escreve qual BluRay possui o valor
     * de imposto mais próximo da média calculada
     * @param auxAv colecao de audiovisuais já populada
     */
    private void impostoMaisProxMedia(ArrayList<AudioVisual> auxAv) {
        double contBluRay = 0;
        double auxMedia = 0.0;
        ArrayList<AudioVisual> auxBluRay = new ArrayList<>();
        for (AudioVisual av : auxAv) {
            if (av instanceof BluRay) {
                auxMedia += av.calculaImposto();
                contBluRay++;
                auxBluRay.add(av);
            }
        }

        double media = auxMedia/contBluRay;
        double maisProximo = Double.MAX_VALUE;
        double menorDiferenca = Math.abs(maisProximo - media);
        String tituloMenorDif = "";
        for (AudioVisual av : auxBluRay) {
            String titulo = av.getTitulo();
            double imposto = av.calculaImposto();
            double diferenca = Math.abs(imposto - media);
            if (diferenca < menorDiferenca) {
                menorDiferenca = diferenca;
                maisProximo = imposto;
                tituloMenorDif = titulo;
            }
        }
        System.out.printf("4;%.2f;%s", media, tituloMenorDif);

    }
}