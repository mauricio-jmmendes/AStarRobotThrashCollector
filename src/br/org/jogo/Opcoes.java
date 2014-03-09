package br.org.jogo;

import br.org.view.GameView;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class Opcoes extends JDialog implements ItemListener {

	private static final long serialVersionUID = 1L;

	private GameView gameView;

	private JButton btOk;

	private JRadioButton rbUm, rbDois, rbTres, rbQuatro;

	private ButtonGroup grupo;

	private JLabel lbCols, lbRows;
	private JTextField txtCols, txtRows;

	private JPanel mainPanel, radioPanel, tfPanel, buttonPanel;

	private int linhas, colunas;

	/** Cria um novo formulario Opcoes */
	public Opcoes(JFrame parent) {
		super(parent, true);

		gameView = (GameView) parent;

		setTitle("Opcoes");

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

		Rectangle parentBounds = new Rectangle(1280, 800);

		int x = Math.max(0, parentBounds.x + (parentBounds.width - 450) / 2);
		int y = Math.max(0, parentBounds.y + (parentBounds.height - 400) / 2);

		setLocation(x, y);
	}

	/**
	 * Este metodo e chamado dentro do construtor para inicializar os
	 * componentes.
	 * 
	 */
	private void iniciaComponentes() {

		linhas = 10;
		colunas = 10;

		lbRows = new JLabel("Linhas[8-20]:");
		lbCols = new JLabel("Colunas[8-30]:");

		txtRows = new JTextField("10");
		txtCols = new JTextField("10");

		txtRows.setPreferredSize(new Dimension(200, 25));
		txtCols.setPreferredSize(new Dimension(200, 25));

		// adiciona ouvinte para tratar eventos de focus.
		txtRows.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {

				int x;

				try {
					x = Integer.parseInt(txtRows.getText());
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null,
							"Somente numeros sao permitidos!", "Alerta!",
							JOptionPane.WARNING_MESSAGE);
					txtRows.setText("10");
					x = 10;
				}

				if ((x < 8)) {
					txtRows.setText("8");
				} else if ((x > 20)) {
					txtRows.setText("20");
				}
				setLinhas(Integer.parseInt(txtRows.getText()));
			}

			@Override
			public void focusGained(FocusEvent e) {
			}
		});

		// adiciona ouvinte para tratar eventos de focus.
		txtCols.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {

				int y;

				try {
					y = Integer.parseInt(txtCols.getText());
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null,
							"Somente n�meros s�o permitidos!", "Alerta!",
							JOptionPane.WARNING_MESSAGE);
					txtCols.setText("10");
					y = 10;
				}

				if ((y < 8)) {
					txtCols.setText("8");
				} else if ((y > 30)) {
					txtCols.setText("30");
				}
				setColunas(Integer.parseInt(txtCols.getText()));
			}

			@Override
			public void focusGained(FocusEvent e) {
			}
		});

		setFieldsEnabled(false);

		txtRows.setToolTipText("Entre com um valor dentro do intervalo(8-20).");
		txtCols.setToolTipText("Entre com um valor dentro do intervalo(8-30).");

		grupo = new ButtonGroup();

		rbUm = new JRadioButton();
		rbDois = new JRadioButton();
		rbTres = new JRadioButton();
		rbQuatro = new JRadioButton();

		rbUm.setText("Facil - 8 x 8 - 12 Bombas");
		rbDois.setText("Medio - 15 x 20 - 60 Bombas");
		rbTres.setText("Dificil - 20 x 30 - 120 Bombas");
		rbQuatro.setText("Personalizado");

		rbUm.setActionCommand("Facil");
		rbDois.setActionCommand("Medio");
		rbTres.setActionCommand("Dificil");
		rbQuatro.setActionCommand("Personalizado");

		rbUm.addItemListener(this);
		rbDois.addItemListener(this);
		rbTres.addItemListener(this);
		rbQuatro.addItemListener(this);

		grupo.add(rbUm);
		grupo.add(rbDois);
		grupo.add(rbTres);
		grupo.add(rbQuatro);

		// criar o bot�o de ok para ativar as op��es
		btOk = new JButton("OK!");
		btOk.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				gameView.reiniciarJogo();
				setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			}
		});

		// cria e seta as propriedades dos panels
		radioPanel = new JPanel();
		radioPanel.setLayout(new BoxLayout(radioPanel, BoxLayout.Y_AXIS));
		radioPanel.setBorder(new TitledBorder(null, "N�veis",
				TitledBorder.LEFT, TitledBorder.TOP));

		buttonPanel = new JPanel();
		buttonPanel.add(btOk);

		tfPanel = new JPanel();
		tfPanel.setLayout(new BoxLayout(tfPanel, BoxLayout.Y_AXIS));
		tfPanel.setBorder(new TitledBorder(null, "L x C", 1, TitledBorder.TOP));

		// Adiciona os radioButton ao painel.
		radioPanel.add(rbUm);
		radioPanel.add(rbDois);
		radioPanel.add(rbTres);
		radioPanel.add(rbQuatro);

		// adiciona os labels textfields ao painel.
		tfPanel.add(lbRows);
		tfPanel.add(txtRows);
		tfPanel.add(lbCols);
		tfPanel.add(txtCols);

		mainPanel = new JPanel();
		mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		mainPanel.setLayout(new GridBagLayout());

		// define as configura��es de cada panel a ser adicionado em mainPanel.
		GridBagConstraints gbc = new GridBagConstraints();

		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, -2, 5, -2);
		mainPanel.add(radioPanel, gbc);

		gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 0, 5, 0);
		mainPanel.add(tfPanel, gbc);

		gbc = new GridBagConstraints();
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		mainPanel.add(buttonPanel, gbc);

		// adiciona o painel principal ao painel da moldura.
		getContentPane().add(mainPanel);

	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		JRadioButton rb = (JRadioButton) e.getSource();
		String comando = rb.getActionCommand();
		if (comando == "Facil" && rb.isSelected()) {
			setFieldsEnabled(false);
			setLinhas(8);
			setColunas(8);
		} else if (comando == "Medio" && rb.isSelected()) {
			setFieldsEnabled(false);
			setLinhas(15);
			setColunas(20);
		} else if (comando == "Dificil" && rb.isSelected()) {
			setFieldsEnabled(false);
			setLinhas(20);
			setColunas(30);
		} else if (comando == "Personalizado" && rb.isSelected()) {
			setFieldsEnabled(true);
			setLinhas(Integer.parseInt(txtRows.getText()));
			setColunas(Integer.parseInt(txtCols.getText()));
		}

	}

	/**
	 * Define o estado de cada label e textfield.
	 * 
	 * @param b
	 *            valor booleano true ou false.
	 */
	private void setFieldsEnabled(boolean b) {
		lbRows.setEnabled(b);
		lbCols.setEnabled(b);
		txtRows.setEnabled(b);
		txtCols.setEnabled(b);
	}

	public int getLinhas() {
		return linhas;
	}

	public void setLinhas(int linhas) {
		this.linhas = linhas;
	}

	public int getColunas() {
		return colunas;
	}

	public void setColunas(int colunas) {
		this.colunas = colunas;
	}
}