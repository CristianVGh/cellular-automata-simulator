package model;
/**
 * @author Cristian Serban
 * Le voisinage Von Neumann qui contient le 4 cellules adjacentes situees au Nord, Sud, Ouest et Est
 */
public class VonNeumann extends Voisinage {
	/**
	 * @return renvoie une tableau de tous les voisins
	 * utilise la classe d'extension pour calculer les voisins des cellules situées aux marges
	 */
	@Override
	public Cellule[] getVoisins(Grille g, int x, int y, Extension extension) {
		Cellule[] voisins = new Cellule[4];

		voisins[0] = extension.getElement(x - 1, y, g);
		voisins[1] = extension.getElement(x, y - 1, g);
		voisins[2] = extension.getElement(x, y + 1, g);
		voisins[3] = extension.getElement(x + 1, y, g);

		return voisins;
	}

}
