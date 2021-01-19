package model;

/**
 * @author Cristian Serban
 * une cellule contenant un �tat de l'automate
 */
public class Cellule {
	/**
	 * l'etat de la cellule
	 */
	Etat etat;

	
	/**
	 * @param etat l'etat de la cellule (entier)
	 * le constructeur prend une valeur enti�re et la convertit en l'un des �tats
	 */
	public Cellule(int etat) {
		switch (etat % 3) {
		case 0:
			this.etat = Etat.MORT;
			break;
		case 1:
			this.etat = Etat.VIVANT;
			break;
		case 2:
			this.etat = Etat.MOURANT;
			break;
		default:
			this.etat = Etat.MORT;
			break;
		}
	}

	/**
	 * @return l'�tat sous forme d'entier
	 */
	public int getEtat() {
		switch (etat) {
		case MORT:
			return 0;
		case VIVANT:
			return 1;
		case MOURANT:
			return 2;
		default:
			return -1;
		}
	}

	/**
	 * @param etat le nouveau etat de la cellule
	 * modifie l'�tat d'une cellule
	 */
	public void setEtat(int etat) {
		switch (etat % 3) {
		case 0:
			this.etat = Etat.MORT;
			break;
		case 1:
			this.etat = Etat.VIVANT;
			break;
		case 2:
			this.etat = Etat.MOURANT;
			break;
		default:
			this.etat = Etat.MORT;
			break;
		}
	}
}
