package ipint.adom.tsp.tp1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Utils {

	/**
	 * Affiche une matrice 2D d'entiers
	 * @param matrice La matrice à afficher
	 */
	public static void afficherMatrice(int[][] matrice) {
		
		int valeur = 0;
		
		// parcours ligne
		for(int i = 0; i < matrice.length; i++) {
			// parcours colonnes
			for(int j = 0; j < matrice.length; j++) {
				// recupération de la valeur a afficher
				valeur = matrice[i][j];
				
				// calcul de l'espace que prendra la valeur en console afin de construire la colonne
				if(valeur > 999) {
					
					System.out.print("| ");
					
				} else if (valeur > 99) {
					
					System.out.print("|  ");
					
				} else if (valeur > 9){
					 
					System.out.print("|   ");
					
				} else {
					
					System.out.print("|    ");
				}
				
				System.out.print(valeur);
			}
			
			System.out.println("|");
		}
	}
	
	/**
	 * Renvoie l'évaluation d'une solution
	 * @param solution La solution à évaluer
	 * @param matrice La matrice de coûts servant aux calculs
	 * @return Le coût de la solution
	 */
	public static int evaluerSolution(List<Integer> solution, int[][] matrice) {
		
		// cout total de la solution
		int cout = 0;
		
		// parcours de la solution
		for(int i = 0; i < solution.size(); i++) {
			
			// fin de la solution 
			if(i == (solution.size() - 1)) {
				// on calcule la distance entre la ville d'arrivee et la ville de depart 
				cout += matrice[solution.get(0)-1][solution.get(i)-1];
				
			} else {
				// calcul de la distance entre deux villes de la solution
				cout += matrice[solution.get(i)-1][solution.get(i+1)-1];
			}
		}

		return cout;
	}
	
	/**
	 * Genere une solution aléatoire
	 * @return Une solution aléatoire
	 */
	public static List<Integer> genererSolutionAleatoire() {
		
		List<Integer> liste = new ArrayList<Integer>();
		
		// On ajoute les villes (uniques de 1 a 100) à la liste
		for(int i = 1; i <= 100; i++) {
			
			liste.add(i);
		}
		
		// on melange la liste
		Collections.shuffle(liste);
		
		return liste;
	}
	
	/**
	 * Renvoie l'heuristique constructive du plus proche voisin pour le TSP
	 * @param ville La ville de départ
	 * @param matrice La matrice de coûts utile aux calculs
	 * @return Une solution 
	 */
	public static List<Integer> algorithmePlusProcheVoisin(int ville, int[][] matrice) {
		
		// Le chemin à emprunter
		List<Integer> chemin = new ArrayList<Integer>();
		// Les villes à visiter
		List<Integer> villes = new ArrayList<Integer>();
		int derniereVilleVisitee = 0;
		
		// On ajoute toutes les villes à visiter dans une liste
		for(int i = 1; i <= 100; i++) {
			// sauf la ville de départ
			if(ville != i)
				villes.add(i);
		}
		
		// ajout de la ville de depart a la solution
		chemin.add(ville);
		
		// Tant que toutes les villes n'ont pas été visitées
		while(!villes.isEmpty()) {
			
			// distance minimale courante pour commencer les calculs
			int minDistance = matrice[ville-1][villes.get(0)-1];
			// dernire ville visitée tampon
			derniereVilleVisitee = villes.get(0);
			
			// On regarde la distance la plus courte selon toutes les villes à visiter
			for(int i = 1; i < villes.size(); i++) {
				
				// une distance inférieur à la distance courante est trouvée
				if(matrice[ville-1][villes.get(i)-1] < minDistance) {
					// remplacement de la distance minimale courante
					minDistance = matrice[ville-1][villes.get(i)-1];
					// remplacement de la derniere ville visitee courante
					derniereVilleVisitee = villes.get(i);
				}
			}
			// suppression de la derniere ville visitee de la liste des villes a visiter
			villes.remove(new Integer(derniereVilleVisitee));
			// ville dernierement visitée
			ville = derniereVilleVisitee;
			// ajout de la ville a la solution
			chemin.add(ville);
		}
		
		return chemin;
	}
}
