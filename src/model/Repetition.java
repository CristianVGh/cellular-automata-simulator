package model;

/**
 * @author Cristian Serban
 * l'extension par répétition répète la cellule la plus proche
 */
public class Repetition extends Extension {

	
	/**
	 * @param x la ligne sur laquelle se trouve la cellule
	 * @param y la colonne sur laquelle se trouve la cellule
	 * @param g la grille de l'automate
	 * @return renvoie la cellule demandée
	 */
	public Cellule getElement(int x, int y, Grille g) {
		Cellule[][] grille = g.getCellules();
		int a = x;
		int b = y;

		if (a < 0)
			a = 0;
		if (a >= g.getHeight())
			a = g.getHeight() - 1;
		if (b < 0)
			b = 0;
		if (b >= g.getWidth())
			b = g.getWidth() - 1;

		return grille[a][b];
	}

}
