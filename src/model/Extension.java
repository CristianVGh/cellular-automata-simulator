package model;

/**
 * @author Cristian Serban
 * classe abstraite dont d�rivent tous les types d'extension
 */
public abstract class Extension {
	public abstract Cellule getElement(int x, int y, Grille g);
}
