package br.org.model;

import java.util.Random;

/**
 * Classe responsavel pela logica do jogo.
 * 
 * Cria o @grid, de tamanho @linhas+2 x @colunas+2, distribui as @bombas,
 * 
 */
public class GameModel {

	/**
	 * Quantidade de linhas do @grid, 10 � valor padr�o, atribu�do pelo
	 * construtor.
	 */
	private int linhas;

	/**
	 * Quantidade de colunas do @grid, 10 � valor padr�o, atribu�do pelo
	 * construtor.
	 */
	private int colunas;

	/**
	 * 
	 * O @grid � criado de forma que possamos deixar uma margem,
	 * 
	 * afim de simplificar diversas opera��es,
	 * 
	 * como por exemplo, o preenchimento da quantidade de @bombas.
	 * 
	 * Ex.: Um Grid de 5 x 5 � criado como 7 x 7.
	 * 
	 * Deixamos de utilizar o �ndice 0 e o �ndice 6, e utilizamos do 1 ao 5.
	 * 
	 */
	private int[][] grid;

	private int qtdBombas;

	/**
	 * Constante usada para representar um bomba.
	 */
	private static final int BOMBA = 9;

	/**
	 * Construtor padr�o.
	 */
	public GameModel() {
		this(10, 10);
	}

	/**
	 * Construtor com parametros
	 * 
	 * @param rows
	 *            � quantidade de linhas que ter� o @grid.
	 * @param cols
	 *            � quantidade de colunas que ter� o @grid.
	 */
	public GameModel(int rows, int cols) {
		this.linhas = rows;
		this.colunas = cols;
		grid = new int[linhas + 2][colunas + 2];
	}

	/**
	 * M�todo respons�vel por definir o jogo
	 * 
	 * com a quantidade de @bomba passada no parametro.
	 * 
	 * @param qtdBombas
	 *            � a quantidade de @bomba que ser�o colocadas no @grid.
	 */
	public void definirJogo(int qtdBombas) {
		for (int i = 0; i < qtdBombas; i++) {
			plantarBomba();
		}

		preencherGrid();
	}

	/**
	 * M�todo respons�vel por distribuir as @bombas no @grid.
	 * 
	 * possui verifica��o afim de evitar que uma @bomba
	 * 
	 * seja colocada nas extremidades do @grid
	 * 
	 * ou em uma posi��o que j� tenha @bomba.
	 */
	private int plantarBomba() {

		Random gera = new Random();

		int x = 0;
		int y = 0;

		// Gera as coordenadas x e y, com a condi��o de que sejam diferentes
		// de 0 e que n�o tenha bomba na posi��o dada pelas coordenadas.
		do {
			x = gera.nextInt(linhas);
			y = gera.nextInt(colunas);

		} while ((x == 0) || (y == 0) || grid[x][y] == BOMBA);

		// se foi encontrada um boa posi��o coloca uma bomba.
		grid[x][y] = BOMBA;

		return qtdBombas++;

	}

	/**
	 * M�todo respons�vel por percorrer o @grid
	 * 
	 * e preencher a quantidade de @bombas em cada posi��o.
	 * 
	 */
	private void preencherGrid() {

		for (int i = 1; i <= linhas; i++) {
			for (int j = 1; j <= colunas; j++) {
				if (grid[i][j] == BOMBA) {

					preencherQtdBombas(i, j);

				}
			}
		}
	}

	/**
	 * M�todo respons�vel por preencher a quantidade de @bombas
	 * 
	 * ao redor de uma determinada posi��o passada no argumento.
	 * 
	 * @param rows
	 *            � o valor da linha.
	 * @param cols
	 *            � o valor da coluna.
	 */
	private void preencherQtdBombas(int rows, int cols) {

		if (!temBomba(++rows, cols))
			grid[rows][cols]++;
		if (!temBomba(rows, ++cols))
			grid[rows][cols]++;
		if (!temBomba(--rows, cols))
			grid[rows][cols]++;
		if (!temBomba(--rows, cols))
			grid[rows][cols]++;
		if (!temBomba(rows, --cols))
			grid[rows][cols]++;
		if (!temBomba(rows, --cols))
			grid[rows][cols]++;
		if (!temBomba(++rows, cols))
			grid[rows][cols]++;
		if (!temBomba(++rows, cols))
			grid[rows][cols]++;

	}

	/**
	 * Verifica se tem @bomba na posi��o passada no parametro.
	 * 
	 * @param x
	 *            � valor da linha.
	 * @param y
	 *            � valor da coluna.
	 * 
	 * @return true se tiver @bomba, caso contr�rio retorna false.
	 */
	private boolean temBomba(int x, int y) {
		if (grid[x][y] == 9)
			return true;
		return false;

	}

	/*--------------------------/
	 * 							*
	 *    M�todos Gets e Sets   *
	 * 							*
	 *------------------------- */

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

	public int[][] getGrid() {
		return grid;
	}

}
