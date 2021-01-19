package model;

/**
 * @author Cristian Serban
 * l'extension par peridiocite reflète les éléments de la grille
 */
public class Periodicite extends Extension {
	/**
	 * @param x la ligne sur laquelle se trouve la cellule
	 * @param y la colonne sur laquelle se trouve la cellule
	 * @param g la grille de l'automate
	 * @return renvoie la cellule demandée
	 */
	@Override
	public Cellule getElement(int x, int y, Grille g) {
		Cellule[][] grille = g.getCellules();
		int a = x < 0 ? x += g.getHeight() : x % g.getHeight();
		int b = y < 0 ? y += g.getWidth() : y % g.getWidth();

		return grille[a][b];
	}

}
