package model;

import observateur.Sujet;

/**
 * @author Cristian Serban
 * La grille finite de l'automat
 */
public class Grille extends Sujet {
	/**
	 * la largeur de la grille
	 */
	int width;
	/**
	 * la taille de la grille
	 */
	int height;

	/**
	 * le tableau de cellules qui contient toutes les cellules de la grille
	 */
	Cellule[][] cellules;

	/**
	 * constructeur sans arguments
	 */
	public Grille() {
	}

	/**
	 * @param width la largeur de la grille
	 * @param height la taille de la grille
	 * le constructeur prend les dimensions de la grille et construit un tableau de cellules défini sur l'état initial de 0 (mort)
	 */
	public Grille(int width, int height) {
		this.width = width;
		this.height = height;
		cellules = new Cellule[height][width];
		for (int i = 0; i < height; i++)
			for (int j = 0; j < width; j++) {
				cellules[i][j] = new Cellule(0);
			}
	}

	/**
	 * @return le tableau de cellules
	 */
	public Cellule[][] getCellules() {
		return cellules;
	}

	/**
	 * @return la largeur
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @return la taille
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param cells tableau de cellules
	 * modifie le tableau des cellules et notifie l'observateur
	 */
	public void setCellules(Cellule[][] cells) {
		this.cellules = cells;
		notifyMesObservateurs();
	}

	/**
	 * @param x la ligne
	 * @param y la colonne
	 * @param etat le nouveau etat
	 * modifie une cellule trouvée à une certaine position
	 */
	public void setCellule(int x, int y, int etat) {
		cellules[x][y].setEtat(etat);
	}

}
