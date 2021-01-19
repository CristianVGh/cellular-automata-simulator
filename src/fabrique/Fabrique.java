package fabrique;

import model.Automate;
import model.Brian;
import model.Conway;
import model.Extension;
import model.Fredkin;
import model.Grille;
import model.Voisinage;

public class Fabrique {
	TypeAutomate type;
	Automate automate;
	
	private static Fabrique fabrique = null;
	
	public Automate creerAutomat(TypeAutomate type, Grille grille, Voisinage voisinage, Extension extension) {
		switch(type) {
			case CONWAY:
				automate = new Conway(grille, voisinage, extension);
				return automate;
			case FREDKIN:
				automate = new Fredkin(grille, voisinage, extension);
				return automate;
			case BRIAN:
				automate = new Brian(grille, voisinage, extension);
				return automate;
			default:
				automate = null;
				return automate;				
		}
	}
	
	public static Fabrique getInstance() {
		if(fabrique == null)
			fabrique = new Fabrique();
		return fabrique;
	}
}
