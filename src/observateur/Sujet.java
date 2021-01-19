package observateur;

import java.util.ArrayList;

public class Sujet {
	ArrayList<Observateur> observateurs = new ArrayList<Observateur>();

	public void notifyMesObservateurs() {
		for (Observateur o: observateurs) {
			o.misAjour();
		}
	}

	public void ajouteObservateurs(Observateur o) {
		observateurs.add(o);
	}
	
	public void supprimerObservateur(Observateur o) {
		observateurs.remove(o);
	}

}