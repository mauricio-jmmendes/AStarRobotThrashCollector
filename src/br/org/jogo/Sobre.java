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

public class Sobre extends JDialog {

	private static final long serialVersionUID = 1L;

	private JButton botaoFechar;
	private JLabel lbMensagem;
	private JPanel mainPanel;

	private JPanel infPanel;

	/** Cria um novo formulario Sobre */
	public Sobre(JFrame parent) {
		super(parent, true);

		setTitle("Sobre!");

		iniciaComponentes();

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
	 * Este metodo e chamado dentro do construtor para inicializar os
	 * componentes.
	 * 
	 */
	private void iniciaComponentes() {

		mainPanel = new JPanel();
		infPanel = new JPanel();
		lbMensagem = new JLabel();
		botaoFechar = new JButton();

		getContentPane().setLayout(new GridLayout());

		mainPanel.setLayout(new BorderLayout(5, 5));

		mainPanel.setBorder(new EmptyBorder(new Insets(15, 10, 5, 10)));

		lbMensagem.setBackground(new Color(229, 224, 255));

		lbMensagem.setText(getHTML());

		lbMensagem.setFocusable(false);

		mainPanel.add(lbMensagem, BorderLayout.CENTER);

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

	private void botaoFecharActionPerformed(ActionEvent evt) {
		setVisible(false);
		dispose();
	}

	private String getHTML() {
		String html = "<HTML>"
				+ "<BODY style=\"margin: 10px;background-color:rgb(229,224,255)font-family:Segoe UI; font-size:12pt\">"
				+ "<h2>Sobre Campo Minado</h2>"
				+ "<p>Implementacao do jogo Campo Minado.</p>" + "<br />"
				+ "<p>Autores:</p>" + "<ul>" + "<li>" + "<p>Heber Barbosa</p>"
				+ "</li>" + "<li>" + "<p>Juliano Avancci</p>" + "</li>"
				+ "<li>" + "<p>Mauricio Mendes</p>" + "</li>" + "<li>"
				+ "<p>Tiago Lobo</p>" + "</li>" + "</ul>" + "</BODY>"
				+ "</HTML>";

		return html;
	}
}