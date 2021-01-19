package model;

/**
 * @author Cristian Serban
 * Le automate le jeu de la vie
 */
public class Conway extends Automate {

	public Conway(Grille grille, Voisinage voisinage, Extension extension) {
		super(grille, voisinage, extension);
	}

	/**
	 * calcule l'état suivant de l'automate en fonction de l'état précédent, la regle de l'automate, du voisinage et le type d'extension
	 */
	@Override
	public void nextIteration() {
		int width = grille.getWidth();
		int height = grille.getHeight();

		Cellule[][] precedenteGrille = grille.getCellules();
		Cellule[][] nouvelleGrille = new Cellule[height][width];
		for (int i = 0; i < height; i++)
			for (int j = 0; j < width; j++)
				nouvelleGrille[i][j] = new Cellule(precedenteGrille[i][j].getEtat());

		for (int i = 0; i < grille.getHeight(); i++)
			for (int j = 0; j < grille.getWidth(); j++) {
				Cellule[] voisins = voisinage.getVoisins(grille, i, j, extension);
				int voisinsVivants = 0;
				for (int k = 0; k < voisins.length; k++)
					if (voisins[k].getEtat() == 1)
						voisinsVivants++;

				if (precedenteGrille[i][j].getEtat() == 1) {
					if (voisinsVivants <= 1 || voisinsVivants >= 4)
						nouvelleGrille[i][j].setEtat(0);
				} else if (precedenteGrille[i][j].getEtat() == 0)
					if (voisinsVivants == 3)
						nouvelleGrille[i][j].setEtat(1);
			}
		grille.setCellules(nouvelleGrille);
	}

}
