package br.org.jogo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Ajuda extends JDialog {

	private static final long serialVersionUID = 1L;

	private JButton botaoFechar;
	private JLabel lbAjuda;
	private JPanel mainPanel;

	private JPanel infPanel;

	/** Construtor da classe Ajuda */
	public Ajuda(JFrame parent) {
		super(parent, true);

		setTitle("Ajuda!");

		iniciaComponentes();

		// permite interagir com a janela pai enquanto essa esta aberta.
		setModal(false);

		pack();

		centralizarTela(parent);
	}

	/**
	 * Centraliza esse componente na tela.
	 * 
	 * @param parent
	 *            o componente pai.
	 */
	private void centralizarTela(JFrame parent) {
		Rectangle parentBounds = parent.getBounds();
		Dimension size = getSize();
		int x = Math.max(0, parentBounds.x + (parentBounds.width - size.width)
				/ 2);
		int y = Math.max(0, parentBounds.y
				+ (parentBounds.height - size.height) / 2);

		setLocation(new Point(x, y));
	}

	/**
	 * Este m�todo � chamado dentro do construtor para inicializar os
	 * componentes.
	 * 
	 */
	private void iniciaComponentes() {

		mainPanel = new JPanel();
		infPanel = new JPanel();
		lbAjuda = new JLabel();
		botaoFechar = new JButton();

		getContentPane().setLayout(new GridLayout());

		mainPanel.setLayout(new BorderLayout(5, 5));

		mainPanel.setBorder(new EmptyBorder(new Insets(15, 10, 5, 10)));

		mainPanel.setBackground(new Color(250, 249, 249));
		infPanel.setBackground(new Color(229, 224, 255));

		lbAjuda.setText(getHTML());

		lbAjuda.setFocusable(false);

		mainPanel.add(lbAjuda, BorderLayout.CENTER);

		botaoFechar.setMnemonic('C');
		botaoFechar.setText("Fechar");
		botaoFechar.setPreferredSize(new Dimension(75, 25));
		botaoFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				botaoFecharActionPerformed(evt);
			}
		});

		infPanel.add(botaoFechar);
		mainPanel.add(infPanel, BorderLayout.SOUTH);

		getContentPane().add(mainPanel);

	}

	/**
	 * A��o do bot�o.
	 * 
	 * @param evt
	 */
	private void botaoFecharActionPerformed(ActionEvent evt) {
		setVisible(false);
		dispose();
	}

	/**
	 * Gera o c�digo html que ser� exibido na tela.
	 * 
	 * @return html a string contendo o texto.
	 */
	private String getHTML() {
		String html = "<HTML>"
				+ "<BODY style=\"margin: 10px;background-color:rgb(229,224,255)font-family:Segoe UI; font-size:12pt\">"
				+ "<h2>Campo Minado: No��es b�sicas</h2>"
				+ "<h3>O objetivo</h3>"
				+ "<p>Descobrir todos os quadrados que n�o cont�m bombas.</p>"
				+ "<h3>N�veis de dificuldade</h3>"
				+ "<p>O Campo Minado possui tr�s n�veis de dificuldade.</p>"
				+ "<ul>"
				+ "<li>"
				+ "<p>F�cil: 8 x 8 e 12 bombas</p>"
				+ "</li>"
				+ "<li>"
				+ "<p>M�dio: 15 x 20 e 60 bombas</p>"
				+ "</li>"
				+ "<li>"
				+ "<p>Dif�cil: 20 x 30 e 120 bombas</p>"
				+ "</li>"
				+ "</ul>"
				+ "<p>Voc� pode escolher o n�vel clicando no menu Arquivo e em Op��es."
				+ "<p>Tamb�m � poss�vel criar um n�vel personalizado de qualquer tamanho entre 20 linhas e 30 colunas.</p>"
				+ "<p>Em todo caso a quantidade de bombas ser� 20% do total de posi��es do grid.</p>"
				+ "<h3>Como jogar</h3>"
				+ "<p>As regras do Campo Minado s�o simples:</p>"
				+ "<ul>"
				+ "<li>"
				+ "<p>Se voc� clicar em uma bomba, o jogo acaba.</p>"
				+ "</li>"
				+ "<li>"
				+ "<p>Se clicar um quadrado vazio, todos os quadrados adjacentes s�o mostrados(j� que n�o podem conter bombas).</p>"
				+ "</li>"
				+ "<li>"
				+ "<p>Se aparecer um n�mero, ele informar� quantas bombas est�o escondidas nos oito quadrados adjacentes."
				+ "</li>"
				+ "<li>"
				+ "<p>Quando n�o tiver op��es onde clicar, clique em alguma parte inexplorada.</p>"
				+ "</li>"
				+ "<li>"
				+ "<p>Evite clicar em quadrados que voc� suspeita conter bomba.</p>"
				+ "</li>"
				+ "<li>"
				+ "<p>Se voc� suspeitar que um quadrado tem uma bomba, clique nele com o bot�o direito do mouse.</p>"
				+ "<p>Isso marca o quadrado com uma bandeira.</p>"
				+ "<p>Se n�o tiver certeza, clique com o bot�o direito do mouse novamente para inserir um ponto de interroga��o.</p>"
				+ "</li>" + "</BODY>" + "</HTML>";

		return html;
	}
}