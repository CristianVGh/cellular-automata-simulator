package model;

/**
 * @author Cristian Serban
 * classe abstraite dont dérivent tous les automates
 */
public abstract class Automate implements Regle{
	/**
	 * la grille de l'automate
	 */
	Grille grille;
	/**
	 * le type de voisinage
	 */
	Voisinage voisinage;
	/**
	 * le type d'extension de la grille
	 */
	Extension extension;
	
	public Automate (Grille grille, Voisinage voisinage, Extension extension) {
		this.grille = grille;
		this.voisinage = voisinage;
		this.extension = extension;
	}
	
	/**
	 * @return la grille de l'automate
	 */
	public Grille getGrille() {
		return grille;
		
	}
	
	/**
	 * @param grille remplace la grille de l'automate
	 */
	public void setGrille(Grille grille) {
		this.grille = grille;
		
	}
	
}
