package ipint.adom.tsp.tp5;

import java.util.ArrayList;
import java.util.List;

import ipint.adom.tsp.tp4.Critere;

public class Utils {
	
	/**
	 * Génère une solution aléatoire
	 * @return La solution générée
	 */
	private static List<Integer> genererSolution() {
		
		List<Integer> solution = new ArrayList<Integer>();
		
		// Ajout des numéros de villes uniques
		for(int i = 1; i <= 100; i++)
			solution.add(i);
		
		return solution;
	}
	
	/**
	 * Renvoie le cout global d'un critere selon deux valeurs lambda
	 * @param ville1 
	 * @param ville2 
	 * @param lambda1 Une valeur lambda1
	 * @param lambda2 Une valeur lambda2
	 * @param matrice La matrice utile aux calculs
	 * @return le cout global du critere
	 */
	private static double calculerCout(int ville1, int ville2, double lambda1, double lambda2, Critere[][] matrice) {
		
		Critere c;
		
		c = matrice[ville1-1][ville2-1];
		
		return lambda1 * c.getCout1() + lambda2 * c.getCout2();
	}
	
	/**
	 * Renvoie le cout d'un unique objectif d'un objet critere
	 * @param ville1
	 * @param ville2 
	 * @param cout Le type de cout a renvoyer
	 * @param matrice La matrice utile aux calculs
	 * @return
	 */
	private static int calculerCout(int ville1, int ville2, int cout, Critere[][] matrice) {
		
		Critere c;
	
		c = matrice[ville1-1][ville2-1];
		
		if(cout == 1) 
			return c.getCout1();
		else 
			return c.getCout2();
	}
	
	/**
	 * Evalue une solution et renvoie un tableau de couts d'une solution 
	 * @param solution La solution a évaluer
	 * @param lambda1
	 * @param lambda2
	 * @param matrice Une matrice utile aux calculs
	 * @return tableau de couts
	 */
	private static int[] evaluerSolution(List<Integer> solution, double lambda1, double lambda2, Critere[][] matrice) {
		
		// cout global d'une solution
		int coutGlobal = 0;
		// objecif1
		int coutTotalCout1 = 0;
		// objectif2
		int coutTotalCout2 = 0;
		
		// parcours de la solution
		for(int i = 0; i < solution.size(); i++) {
			
			if(i == solution.size() - 1) {
				
				coutGlobal += calculerCout(solution.get(solution.size()-1), solution.get(0), lambda1, lambda2, matrice);
				coutTotalCout1 += calculerCout(solution.get(solution.size()-1), solution.get(0), 1, matrice);
				coutTotalCout2 += calculerCout(solution.get(solution.size()-1), solution.get(0), 2, matrice);
				
			} else {
				
				coutGlobal += calculerCout(solution.get(i), solution.get(i+1), lambda1, lambda2, matrice);
				coutTotalCout1 += calculerCout(solution.get(i), solution.get(i+1), 1, matrice);
				coutTotalCout2 += calculerCout(solution.get(i), solution.get(i+1), 2, matrice);
			}
		}
		
		return new int[] {coutGlobal, coutTotalCout1, coutTotalCout2};
	}
	
	/**
	 * Meéthode de voisinage two-opt selon une approche scalaire
	 * @param lambda1
	 * @param lambda2
	 * @param matrice La matrice utile aux calculs
	 * @return
	 */
	private static Critere twoOptParApprocheScalaire(double lambda1, double lambda2, Critere[][] matrice){
		
		boolean boucle = true;
		
		List<Integer> solution = new ArrayList<Integer>();
		List<Integer> solutionTampon = new ArrayList<Integer>();
		
		solution.addAll(genererSolution());
		solutionTampon.addAll(solution);
		
		int[] res = evaluerSolution(solutionTampon, lambda1, lambda2, matrice);
		double meilleurCout = res[0];
		int meilleurCout1 = res[1];
		int meilleurCout2 = res[2];
		
		Critere c = null;
		
		while(boucle) {
			
			boucle = false;
			
			double meilleurCoutTampon = meilleurCout;
			int meilleurCout1Tampon = meilleurCout1;
			int meilleurCout2Tampon = meilleurCout2;
			c = new Critere(meilleurCout1Tampon, meilleurCout2Tampon);

			for(int i = 0; i < solution.size(); i++) {
				
				for(int k = 2; k < solution.size()-1; k++) {

					int j = i+k;
					
					if(j >= solution.size())
						j = j-solution.size();
					
					solutionTampon.clear();
					solutionTampon.addAll(solution);
					
					if(j == 0)
						solutionTampon = ipint.adom.tsp.tp2.Utils.permuterSolution(solutionTampon, i+1, solutionTampon.size()-1);
					else if(i+1 == solution.size())
						solutionTampon = ipint.adom.tsp.tp2.Utils.permuterSolution(solutionTampon, 0, j-1);
					else 
						solutionTampon = ipint.adom.tsp.tp2.Utils.permuterSolution(solutionTampon, i+1, j-1);
					
					res = evaluerSolution(solutionTampon, lambda1, lambda2, matrice);
					meilleurCoutTampon = res[0];
					meilleurCout1Tampon = res[1];
					meilleurCout2Tampon = res[2];
					
					if((int) meilleurCoutTampon < (int) meilleurCout && meilleurCoutTampon > 0) {
				
						solution.clear();
						solution.addAll(solutionTampon);
						meilleurCout = meilleurCoutTampon;
						meilleurCout1 = meilleurCout1Tampon;
						meilleurCout2 = meilleurCout2Tampon;
						c = new Critere(meilleurCout1, meilleurCout2);
						boucle = true;
					}
				}
			}
		}
		
		return c;
	}
	
	/**
	 * Filtrage offline sur les solutions retournées par la méthode two-opt
	 * @param criteres les criteres à filtrer
	 */
	private static void offLine(List<Critere> criteres) {
		
		List<Critere> dominants = new ArrayList<Critere>();
		
		boolean estDominant;
		for(int i = 0; i < criteres.size(); i++) {
			
			estDominant = true;
			for(int j = 0; j < criteres.size() && estDominant; j++) {
				estDominant = estDominant && ipint.adom.tsp.tp4.Utils.domine(criteres.get(i), criteres.get(j));
			}
			
			if(estDominant)
				dominants.add(criteres.get(i));
		}
		
		System.out.println("* Solutions dominées *");
		for(Critere c : criteres) {
			
			System.out.println(c.getCout1() + ", " + c.getCout2());
		}
		
		System.out.println("\n*  Solutions dominantes*");
		
		for(Critere c : dominants) {
			
			System.out.println(c.getCout1() + ", " + c.getCout2());
		}
	}
	
	/**
	 * Methode principale pour générer des solutions aléatoires selon une approche sscalaire
	 * @param matrice
	 */
	public static void approcheScalaire(Critere[][] matrice) {
		
		List<Critere> criteres = new ArrayList<Critere>();
		double lambda1 = 0, lambda2 = 0;
		
		int quotient = 100;
		for(int i = 0; i <= quotient; i++) {
			
			lambda1 = ((double) i/quotient);
			lambda2 = ((double) (quotient-i)/quotient);
			
			Critere c = twoOptParApprocheScalaire(lambda1, lambda2, matrice);
			
			// On évite d'ajouter des doublons à l'ensemble de solutions
			if(c != null && !criteres.contains(c))
				criteres.add(c);

		}
		
		// On filtre les solutions
		offLine(criteres);
	}
}
