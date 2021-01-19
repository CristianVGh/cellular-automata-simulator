package model;
/**
 * @author Cristian Serban
 * classe abstraite dont d�rivent tous les voisinages
 */
public abstract class Voisinage {
	public abstract Cellule[] getVoisins(Grille g, int x, int y, Extension extension);
}
