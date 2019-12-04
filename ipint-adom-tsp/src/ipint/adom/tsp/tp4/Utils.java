package ipint.adom.tsp.tp4;

import java.util.ArrayList;
import java.util.List;

public class Utils {

	/**
	 * Affiche une matrice 2D de Critere
	 * @param matrice La matrice à afficher
	 */
	public static void afficherMatrice(Critere[][] matrice) {
		
		for(int i = 0; i < matrice.length; i++) {
			
			for(int j = 0; j < matrice.length; j++) {
				
				Critere c = matrice[i][j];
				int cout1 = c.getCout1();
				int cout2 = c.getCout2();
				String s = "";
				
				if(cout1 > 999)
					s = "| ";
				else if(cout1 > 99)
					s = "|  ";
				else if(cout1 > 9)
					s = "|   ";
				else 
					s = "|    ";
				
				s += cout1;
				System.out.print(s);
				
				if(cout2 > 999)
					s = ", ";
				else if(cout2 > 99)
					s = ",  ";
				else if(cout2 > 9)
					s = ",   ";
				else 
					s = ",    ";
				
				s += cout2;
				System.out.print(s);	
			}
			
			System.out.println("|");
		}
	}
	
	/**
	 * Calcule les différents objectifs d'une solution
	 * @param solution La solution à évaluer 
	 * @param matrice La matrice utile aux calculs
	 * @return les objectifs totaux
	 */
	private static Critere evaluerSolution(List<Integer> solution, Critere[][] matrice) {
		// objectif 1
		int cout1 = 0; 
		// objectif 2
		int cout2 = 0;
		
		for(int i = 0; i < solution.size(); i++) {
			
			Critere c;
			
			// fin de la solution, calcul de la distance entre la ville d'arrivée et la ville de départ
			if(i == (solution.size() - 1)) {
				
				c = matrice[solution.get(0)-1][solution.get(i)-1];

				
			} else {
				
				c = matrice[solution.get(i)-1][solution.get(i+1)-1];
			}
			
			cout1 += c.getCout1(); cout2 += c.getCout2();
		}
		
		
		return new Critere(cout1, cout2);
	}
	
	/**
	 * Permet de savoir si un critere c2 domine un critere c1
	 * @param c1 Le premier critere à évaluer
	 * @param c2 Le second critere à évaluer
	 * @return un booleen
	 */
	public static boolean domine(Critere c1, Critere c2) {
		
		boolean b = true;
		
		// c1 domine c2
		if(c1.getCout1() > c2.getCout1() && c1.getCout2() > c2.getCout2())
			b = false;
			
		return b;
	}
	
	/**
	 * Filtrage offline
	 * @param nbIterations Nombre d'itérations à effectuer
	 * @param matrice La matrice utile aux calculs
	 */
	public static void offLine(int nbIterations, Critere[][] matrice) {
		
		// liste des resultats aléatoires
		List<Critere> criteres = new ArrayList<Critere>();
		// liste des resultats dominants
		List<Critere> dominants = new ArrayList<Critere>();
		
		// on génère des résultats aléatoires selon le nombre d'itérations
		for(int i = 1; i <= nbIterations; i++) {
			
			List<Integer> solution = ipint.adom.tsp.tp1.Utils.genererSolutionAleatoire();
			Critere c = evaluerSolution(solution, matrice);
			criteres.add(c);
		}
		
		boolean estDominant;
		// parcours des solutions générées
		for(int i = 0; i < criteres.size(); i++) {
			
			estDominant = true;
			// comparaison d'une solution courante aux autres solutions
			for(int j = 0; j < criteres.size() && estDominant; j++) {
				estDominant = estDominant && domine(criteres.get(i), criteres.get(j));
			}
			
			// la solution courante est considérée comme dominante
			if(estDominant)
				dominants.add(criteres.get(i));
		}
		
		// Affichage des points aléatoires
		System.out.println("* Solutions dominées *");
		for(Critere c : criteres) {
			
			System.out.println(c.getCout1() + ", " + c.getCout2());
		}
		
		System.out.println("\n* Solutions dominantes *");
		
		// Affichage des résultats dominants
		for(Critere c : dominants) {
			
			System.out.println(c.getCout1() + ", " + c.getCout2());
		}
	}
	
	/**
	 * Filtrage onLine
	 * @param nbIterations Nombre d'itérations à effectuer
	 * @param matrice La matrice 2D utile aux calculs
	 */
	public static void onLine(int nbIterations, Critere[][] matrice) {
		
		// liste des points aléatoires
		List<Critere> criteres = new ArrayList<Critere>();
		// liste des solutions dominantes
		List<Critere> dominants = new ArrayList<Critere>();
		
		// generation des solutions aléatoires
		for(int i = 1; i <= nbIterations; i++) {
			
			List<Integer> solution = ipint.adom.tsp.tp1.Utils.genererSolutionAleatoire();
			Critere c = evaluerSolution(solution, matrice);
			criteres.add(c);
		}
		
		dominants.add(criteres.get(0));
		
		boolean arret = false;
		boolean superieur = true;
		
		// on introduit les solutions générées, une par une
		for(int i = 1; i < criteres.size(); i++) {
			
			arret = true;
			
			// on compare la solution à la liste de solutions dominantes
			for(int j = 0; j < dominants.size() && arret; j++) {
				
				superieur = true;
				arret = true;
				
				if(domine(criteres.get(i), dominants.get(j))) {
		
					if(!domine(dominants.get(j), criteres.get(i))) {
						// La solution dominante s'avère être une solution dominée, on la supprime
						dominants.remove(j);
					}
					
					superieur = superieur && true;
				} else {
					
					superieur = false;
					arret = false;
				}
			}
			
			if(superieur) {
				
				dominants.add(criteres.get(i));
				superieur = false;
			}
		}
		
		// Affichage des points aléatoires
		System.out.println("* Solutions dominées *");
		for(Critere c : criteres) {
			
			System.out.println(c.getCout1() + ", " + c.getCout2());
		}
		
		System.out.println("\n* Solutions dominantes *");
		// Affichage des solutions dominantes
		for(Critere c : dominants) {
			
			System.out.println(c.getCout1() + ", " + c.getCout2());
		}
	}
}
