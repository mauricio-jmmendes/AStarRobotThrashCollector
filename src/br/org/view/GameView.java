package br.org.view;

import br.org.model.GameModel;
import br.org.model.Quadrado;
import br.org.algoritmo.AStar;
import br.org.jogo.Ajuda;
import br.org.jogo.Opcoes;
import br.org.jogo.Sobre;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * Classe responsavel pela dinamica e visualizacao do jogo.
 *
 */
public class GameView extends JFrame {

    /**
     *
     */
    private static final long serialVersionUID = -3489753441876873331L;

    /**
     * Matriz de inteiros que representa o grid ja preenchido.
     *
     */
    private int[][] grid;

    /**
     * Matriz de JButton para apresentar o grid na tela.
     *
     */
    private JButton[][] gridButton;

	// ------------------ Menus e Items de menu----------------------
    private JMenuBar menuBar;
    private JMenu menuArquivo, menuAjuda;
    private JMenuItem menuItemNovo, menuItemOpcoes, menuItemSair;
    private JMenuItem menuItemExibirAjuda, menuItemSobre;

	// --------------------------------------------------------------
    /**
     * Container da Janela principal.
     *
     */
    private Container janela;

    /**
     * Panel para os JButton.
     *
     */
    private JPanel panelButton;

    /**
     * Refer�ncia para um objeto GameModel, respons�vel pela l�gica do jogo.
     *
     */
    private GameModel game;

    /**
     * Refer�ncia para um objeto Opcoes, respons�vel pelas op��es do jogo.
     *
     */
    private Opcoes opcoes;

    /**
     * Quantidade de Linhas do grid.
     */
    private int linhas;

    /**
     * Quantidade de colunasndo grid.
     */
    private int colunas;

    /**
     * A quantidade de bombas � definida em fun��o
     *
     * da quantidade de posi��es do @grid e equivale a 20% do total desta.
     *
     */
    private int qtdBombas;

    /**
     * Usado como um Flag para indicar o final de jogo.
     *
     */
    private boolean fim = false;

    /**
     * Construtor da classe. Seta as propriedades e inicia os componentes.
     *
     */
    public GameView() {

        super("Campo Minado");

        setSize(500, 500);
        centralizarTela(this);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        iniciarComponentes();

        pack();
    }

    /**
     * M�todo respons�vel por inicializar os componentes.
     *
     */
    private void iniciarComponentes() {

        // Seta opc�es do jogo
        opcoes = new Opcoes(this);

        // Inicia um novo jogo.
        this.iniciarJogo();

        // Cria os menus
        this.criarMenus();

        // Cria os Pain�is
        this.criarPanels();

    }

    /**
     * M�todo respons�vel por iniciar um novo jogo.
     *
     */
    private void iniciarJogo() {

        linhas = opcoes.getLinhas();
        colunas = opcoes.getColunas();

        qtdBombas = Math.round((((linhas * colunas) * 20) / 100));

        game = new GameModel(linhas, colunas);
        game.definirJogo(1);

        // seta a matriz de inteiros.
        grid = game.getGrid();

        Quadrado[][] grade = new Quadrado[10][10];
        for (int i = 0; i < grade.length; i++) {
            for (int j = 0; j < grade[i].length; j++) {
                grade[i][j] = new Quadrado(i, j);
            }
        }

        //configura grade 
        Quadrado origem = grade[1][5];
        Quadrado destino = grade[9][5];

        AStar aStar = new AStar(grade, origem, destino);
        aStar.addBloqueio(grade[4][3]);
        aStar.addBloqueio(grade[4][4]);
        aStar.addBloqueio(grade[4][5]);
        aStar.addBloqueio(grade[4][6]);
        aStar.addBloqueio(grade[4][7]);
        aStar.addBloqueio(grade[8][4]);
        aStar.addBloqueio(grade[9][4]);

        long tempo1 = System.nanoTime();
        boolean pesquisaOk = aStar.iniciarPesquisa();
        long tempo2 = System.nanoTime();
        System.out.println("Tempo de pesquisa: " + (tempo2 - tempo1) + "ns");

    }

    /**
     * M�todo respons�vel por criar os panels e definir suas propriedades.
     *
     */
    private void criarPanels() {

        // Seta os containers
        janela = getContentPane();
        panelButton = new JPanel();

        // Seta os Layouts
        janela.setLayout(new BorderLayout());
        panelButton.setLayout(new GridLayout(linhas, colunas, 1, 1));

        // cria o grid de bot�es
        this.criarGridButton();

        panelButton.setBorder(new EmptyBorder(new Insets(15, 15, 15, 15)));

        janela.add(panelButton, BorderLayout.SOUTH);
    }

    /**
     * M�todo respons�vel por criar o grid de bot�es.
     *
     */
    private void criarGridButton() {

        // Seta o tamanho da matriz de bot�es.
        gridButton = new JButton[linhas][colunas];

        // looping for para setar os bot�es e adicionar no painel de bot�es.
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {

                gridButton[i][j] = new JButton("");
                gridButton[i][j].setPreferredSize(new Dimension(35, 35));
                gridButton[i][j].setIcon(criarIcone("N10.jpg"));
                gridButton[i][j].setFocusable(false);

                gridButton[i][j].addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        if ((e.getModifiers() & InputEvent.BUTTON1_MASK) != 0) {
                            clickBotaoEsquerdoActionPerformed(e);
                        } else if ((e.getModifiers() & InputEvent.BUTTON3_MASK) != 0) {
                            clickBotaoDireitoActionPerformed(e);
                        }

                    }
                });

                panelButton.add(gridButton[i][j]);
            }
        }
    }

    /**
     * M�todo respons�vel por criar os menus.
     *
     */
    private void criarMenus() {
        menuBar = new JMenuBar();
        menuArquivo = new JMenu("Arquivo");
        menuAjuda = new JMenu("Ajuda");
        menuItemNovo = new JMenuItem("Novo");
        menuItemOpcoes = new JMenuItem("Op��es");
        menuItemSair = new JMenuItem("Sair");
        menuItemSobre = new JMenuItem("Sobre");
        menuItemExibirAjuda = new JMenuItem("Exibir ajuda");

        menuArquivo.setMnemonic('A');

        menuItemNovo.setMnemonic('N');
        menuItemNovo.setToolTipText("Iniciar um novo Jogo!");
        menuItemNovo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuItemNovoActionPerformed(e);
            }
        });

        menuArquivo.add(menuItemNovo);

        menuItemOpcoes.setMnemonic('O');
        menuItemOpcoes.setToolTipText("Op��es do jogo!");
        menuItemOpcoes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuItemOpcoesActionPerformed(e);
            }
        });

        menuArquivo.add(menuItemOpcoes);

        menuItemSair.setMnemonic('S');
        menuItemSair.setToolTipText("Sair do jogo!");
        menuItemSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemSairActionPerformed(evt);
            }
        });

        menuArquivo.add(menuItemSair);

        menuItemExibirAjuda.setMnemonic('E');
        menuItemExibirAjuda.setToolTipText("Exibir ajuda!");
        menuItemExibirAjuda.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuItemExibirAjudaActionPerformed(e);
            }
        });

        menuAjuda.add(menuItemExibirAjuda);

        menuItemSobre.setMnemonic('B');
        menuItemSobre.setToolTipText("Informa��es sobre o jogo!");
        menuItemSobre.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuItemSobreActionPerformed(e);
            }
        });

        menuAjuda.add(menuItemSobre);

        menuBar.add(menuArquivo);
        menuBar.add(menuAjuda);

        this.setJMenuBar(menuBar);

    }

    protected void menuItemExibirAjudaActionPerformed(ActionEvent e) {
        new Ajuda(this).setVisible(true);

    }

    protected void menuItemOpcoesActionPerformed(ActionEvent e) {
        opcoes.setVisible(true);
    }

    protected void menuItemNovoActionPerformed(ActionEvent e) {
        reiniciarJogo();
    }

    protected void menuItemSairActionPerformed(ActionEvent evt) {
        System.exit(0);
    }

    protected void menuItemSobreActionPerformed(ActionEvent e) {
        new Sobre(this).setVisible(true);
    }

    /**
     * Trata um evento disparado por algum bot�o.
     *
     * @param e evento passado como par�metro.
     */
    protected void clickBotaoEsquerdoActionPerformed(MouseEvent e) {

        if (!fim) {

            // flag usado para evitar repeti��es desnecess�rias.
            boolean flag = false;

            // percorre a matriz em busca do bot�o que gerou o evento.
            for (int i = 0; i < linhas; i++) {
                if (flag) {
                    break;
                }

                for (int j = 0; j < colunas; j++) {
                    if (e.getSource() == gridButton[i][j]) {

						// Se j� encontrei o bot�o n�o � necess�rio continuar o
                        // loop
                        flag = true;

                        String nomeIcone = obterNomeIcone(i, j);

                        gridButton[i][j].setIcon(criarIcone(nomeIcone));
                        gridButton[i][j].setSelected(true);

						// Se � um quadrado em branco checa se as posi��es
                        // adjacentes podem ser liberadas.
                        if (grid[i + 1][j + 1] == 0) {
                            this.checaPosicao(i, j);
                        }

                        // Se tem bomba e o jogo n�o acabou
                        if (grid[i + 1][j + 1] == 9) {
                            this.explodir();
                        }

                        // Checa o status do jogo.
                        checarStatusGame();
                        break;
                    }
                }
            }
        }
    }

    protected void clickBotaoDireitoActionPerformed(MouseEvent e) {
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                if (e.getSource() == gridButton[i][j]
                        && !gridButton[i][j].isSelected()) {

                    ImageIcon ic = (ImageIcon) gridButton[i][j].getIcon();
                    String description = ic.getDescription();
                    if (description.equals("N10.jpg")) {
                        gridButton[i][j].setIcon(criarIcone("N11.jpg"));
                    } else if (description.equals("N11.jpg")) {
                        gridButton[i][j].setIcon(criarIcone("N12.jpg"));
                    } else if (description.equals("N12.jpg")) {
                        gridButton[i][j].setIcon(criarIcone("N10.jpg"));
                    }
                }
            }
        }
    }

    /**
     * M�todo utilizado para centralizar a tela.
     *
     * @param frame que ser� cetralizado.
     */
    private void centralizarTela(JFrame frame) {

        Dimension paneSize = frame.getSize();
        Dimension screenSize = frame.getToolkit().getScreenSize();
        frame.setLocation((screenSize.width - paneSize.width) / 2,
                (screenSize.height - paneSize.height) / 2);

    }

    /**
     * Cria um ImageIcon com base no nome fornecido.
     *
     * @param nomeImagem � o nome da imagem jpg.
     * @return um ImageIcon
     */
    public ImageIcon criarIcone(String nomeImagem) {

        String caminho = "/resources/images/" + nomeImagem;
        Class<? extends GameView> classe = getClass();
        URL url = classe.getResource(caminho);

        System.out.println("Caminho - " + url.toString());

        return new ImageIcon(url, nomeImagem);
    }

    /**
     * M�todo chamado quando o usu�rio clica em uma bomba.
     *
     * Exibe uma mensagem de derrota e mostra o grid.
     *
     */
    private void explodir() {
        JOptionPane.showMessageDialog(janela, "Voc� perdeu!", "Perdeu!",
                JOptionPane.INFORMATION_MESSAGE);
        this.mostrarGrid();
    }

    /**
     * Obt�m o nome do icone que ser� colocado
     *
     * na posi��o determinada por @x e @y.
     *
     * @param x � a coordenada x da posi��o.
     *
     * @param y � a coordenada y da posi��o.
     * @return uma String com o nome do �cone da posi��o dada.
     */
    private String obterNomeIcone(int x, int y) {

        x++;
        y++;

        for (int i = 0; i < 10; i++) {
            if ((grid[x][y]) == i) {
                return "N" + i + ".jpg";
            }

        }
        return "N.jpg";
    }

    /**
     * Exibe todas as posi��es do grid para o usu�rio.
     *
     * � chamado quando o usu�rio ganha ou perde o jogo.
     *
     */
    private void mostrarGrid() {

        fim = true;

        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {

                String nameIcon = obterNomeIcone(i, j);

                gridButton[i][j].setIcon(criarIcone(nameIcon));
                gridButton[i][j].setSelected(true);

            }

        }
    }

    /**
     * Checa o status do jogo.
     *
     * Por exemplo a quantidade de posi��es liberadas
     *
     * e se o usu�rio ganhou o jogo.
     *
     */
    private void checarStatusGame() {
        int qtdPosLiberadas = 0;
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                if (gridButton[i][j].isSelected()) {
                    qtdPosLiberadas++;
                }

                if ((((linhas * colunas) - qtdBombas) == qtdPosLiberadas)
                        && (!fim)) {
                    JOptionPane.showMessageDialog(janela,
                            "Parab�ns! Voc� ganhou...", "Ganhou!",
                            JOptionPane.INFORMATION_MESSAGE);
                    this.mostrarGrid();
                }
            }
        }
    }

    /**
     * Reinicia o jogo.
     */
    public void reiniciarJogo() {
        if (JOptionPane.showConfirmDialog(null,
                "Tem certeza que deseja come�ar um novo jogo", "Reiniciar!",
                JOptionPane.YES_NO_OPTION) == 0) {

            // remove tudo da janela para que n�o fique um panel sobre outro.
            janela.removeAll();

            // inicio um novo jogo.
            iniciarJogo();

            // cria novos panels para mostrar o novo jogo.
            criarPanels();

            // reorganiza novamente os componentes.
            pack();

            // devemos setar fim do jogo como false.
            fim = false;
        }
    }

    /**
     * Confere se as coordenadas passadas no par�metro
     *
     * est�o corretas e se poss�vel libera as posi��es adjacentes.
     *
     * @param i � a coordenada i da posi��o.
     * @param j � a coordenada j da posi��o.
     */
    private void checaPosicao(int i, int j) {
        if ((i >= 0 && i <= linhas) && (j >= 0 && j <= colunas)) {

            liberaPosicao(++i, j);
            liberaPosicao(i, ++j);
            liberaPosicao(--i, j);
            liberaPosicao(--i, j);
            liberaPosicao(i, --j);
            liberaPosicao(i, --j);
            liberaPosicao(++i, j);
            liberaPosicao(++i, j);

        }

    }

    /**
     * Libera a posi��o dada pelas coordenadas i e j.
     *
     * @param i � a coordenada i da posi��o.
     * @param j � a coordenada j da posi��o.
     */
    private void liberaPosicao(int i, int j) {
        if ((i >= 0 && i < linhas) && (j >= 0 && j < colunas)) {

            if (!gridButton[i][j].isSelected()) {
                String nameIcon = obterNomeIcone(i, j);

                gridButton[i][j].setIcon(criarIcone(nameIcon));
                gridButton[i][j].setSelected(true);

                if (grid[i + 1][j + 1] == 0) {
                    checaPosicao(i, j);
                }
            }
        }
    }

}
