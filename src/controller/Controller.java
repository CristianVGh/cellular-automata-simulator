package controller;

import java.util.Arrays;

import fabrique.Fabrique;
import fabrique.TypeAutomate;
import model.Automate;
import model.Cellule;
import model.Extension;
import model.Grille;
import model.Moore;
import model.Periodicite;
import model.Repetition;
import model.Voisinage;
import model.VonNeumann;
import observateur.Sujet;
/**
 * @author Cristian Serban
 *
 */
public class Controller extends Sujet {
	/**
	 * la grille de l'automate
	 */
	Grille grille;
	/**
	 * le type de voisinage de l'automate
	 */
	Voisinage voisinage;
	/**
	 * le type d'extension de l'automate
	 */
	Extension extension;
	/**
	 * le patron fabrique
	 */
	Fabrique fabrique;
	/**
	 * l'automate
	 */
	Automate automate;

	/**
	 * constructeur par défaut qui initialise une grille vide
	 */
	public Controller() {
		grille = new Grille();
		fabrique = Fabrique.getInstance();
	}

	/**
	 * @param width la largeur de la grille
	 * @param height la taille de la grille
	 * crée une nouvelle grille avec les dimensions données
	 */
	public void creerGrille(int width, int height) {
		grille = new Grille(width, height);
	}

	/**
	 * @param type le type de l'extension
	 * crée une nouvelle extension basée sur le type donné
	 */
	public void creerExtension(String type) {
		switch (type) {
		case "Repetition":
			extension = new Repetition();
			break;
		case "Periodicite":
			extension = new Periodicite();
			break;
		default:
			break;
		}
	}

	/**
	 * @param type le type du voisinage
	 * crée un nouvel voisinage basé sur le type donné
	 */
	public void creerVoisinage(String type) {
		switch (type) {
		case "Moore":
			voisinage = new Moore();
			break;
		case "VonNeumann":
			voisinage = new VonNeumann();
			break;
		default:
			break;
		}
	}

	
	/**
	 * @param type le type de l'automate
	 * crée un nouvel automate et lui assigne la grille déjà existante, le type d'extension et le voisinage
	 */
	public void creerAutomate(String type) {
		switch (type) {
		case "Conway":
			creerVoisinage("Moore");
			automate = fabrique.creerAutomat(TypeAutomate.CONWAY, grille, voisinage, extension);
			break;
		case "Brian":
			creerVoisinage("Moore");
			automate = fabrique.creerAutomat(TypeAutomate.BRIAN, grille, voisinage, extension);
			break;
		case "Fredkin":
			creerVoisinage("VonNeumann");
			automate = fabrique.creerAutomat(TypeAutomate.FREDKIN, grille, voisinage, extension);
			break;
		default:
			creerVoisinage("Moore");
			automate = fabrique.creerAutomat(null, grille, voisinage, extension);
			break;
		}

	}

	
	/**
	 * calcule l'état suivant de l'automate
	 */
	public void nextIteration() {
		automate.nextIteration();
		grille = automate.getGrille();
	}

	
	/**
	 * @param x la ligne sur laquelle se trouve la cellule
	 * @param y la colonne sur laquelle se trouve la cellule
	 * @return la nouvelle grille
	 * utilisé par la vue dans l'événement click
	 * modifie de force l'état d'une cellule lorsqu'elle a été cliquée
	 */
	public Grille nextEtat(int x, int y) {
		Cellule[][] cellules = grille.getCellules();
		int e = cellules[x][y].getEtat();
		cellules[x][y].setEtat(e + 1);
		grille.setCellules(cellules);
		return grille;
	}
	
	
	/**
	 * réinitialise la grille
	 */
	public void clearGrille() {
		int width = grille.getWidth();
		int height = grille.getHeight();
		Grille newGrille = new Grille(width, height);
		
		grille.setCellules(newGrille.getCellules());
	}

	/**
	 * @return renvoie la grille
	 */
	public Grille getGrille() {
		return grille;
	}

}
