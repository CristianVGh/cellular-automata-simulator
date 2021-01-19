package model;

/**
 * @author Cristian Serban
 * Le voisinage Moore qui contient les 8 cellules adjacentes
 */
public class Moore extends Voisinage {
	/**
	 * @return renvoie une tableau de tous les voisins
	 * utilise la classe d'extension pour calculer les voisins des cellules situées aux marges
	 */
	@Override
	public Cellule[] getVoisins(Grille g, int x, int y, Extension extension) {
		Cellule[] voisins = new Cellule[8];

		voisins[0] = extension.getElement(x - 1, y - 1, g);
		voisins[1] = extension.getElement(x - 1, y, g);
		voisins[2] = extension.getElement(x - 1, y + 1, g);
		voisins[3] = extension.getElement(x, y - 1, g);
		voisins[4] = extension.getElement(x, y + 1, g);
		voisins[5] = extension.getElement(x + 1, y - 1, g);
		voisins[6] = extension.getElement(x + 1, y, g);
		voisins[7] = extension.getElement(x + 1, y + 1, g);

		return voisins;
	}

}
