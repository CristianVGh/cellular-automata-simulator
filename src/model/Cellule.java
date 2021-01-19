package model;

/**
 * @author Cristian Serban
 * une cellule contenant un état de l'automate
 */
public class Cellule {
	/**
	 * l'etat de la cellule
	 */
	Etat etat;

	
	/**
	 * @param etat l'etat de la cellule (entier)
	 * le constructeur prend une valeur entière et la convertit en l'un des états
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
	 * @return l'état sous forme d'entier
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
	 * modifie l'état d'une cellule
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
