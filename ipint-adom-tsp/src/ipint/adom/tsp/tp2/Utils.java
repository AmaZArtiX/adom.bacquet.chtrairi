package ipint.adom.tsp.tp2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Utils {

	/**
	 * Améliore une solution selon la méthode de voisinage swap
	 * @param mouvement Strategie de mouvement
	 * @param matrice Matrice 2D de coûts utile aux calculs
	 * @param solution La solution a améliorer
	 * @param cout Le coût à améliorer
	 * @return Une solution améliorée
	 */
	private static List<Integer> swap(Option mouvement, int[][] matrice, List<Integer> solution, int cout) {
		
		// future solution améliorée
		List<Integer> nouvelleSolution = new ArrayList<Integer>(); 
		// solution tampon pour effectuer les calculs
		List<Integer> solutionTampon = new ArrayList<Integer>(); 
		// condition  d'arret de la boucle
		boolean boucle = true;
		// condition d'arret du parcours
		boolean arret = false;
		// le futur cout amélioré
		int meilleurCout = cout;
		
		// la nouvelle solution prend les valeurs de la solution a améliorer
		nouvelleSolution.addAll(solution);
		solutionTampon.addAll(solution);
		
		// boucle d'amélioration
		while(boucle) {
			
			boucle = false;
			arret = false;
			int coutTampon = 0;
			
			// parcours d'amélioration
			for(int i = 0; i < nouvelleSolution.size() && !arret; i++) {
				
				for(int j = i+1; j < nouvelleSolution.size() && !arret; j++) {
					
					// on effectue les calculs sur une solution tampon
					solutionTampon.clear();
					solutionTampon.addAll(nouvelleSolution);
					// echange des villes aux indices i, j
					Collections.swap(solutionTampon, i, j);
					// calcul du du cout 
					coutTampon = ipint.adom.tsp.tp1.Utils.evaluerSolution(solutionTampon, matrice);
					
					// il s'agit d'un cout ameliorant
					if(coutTampon < meilleurCout) {
						
						// sauvegarde de la nouvelle solution et du cout 
						nouvelleSolution.clear();
						nouvelleSolution.addAll(solutionTampon);
						meilleurCout = coutTampon;
						boucle = true;
					
						// on a trouvé un voisin améliorant, on quitte le parcours d'amélioration
						if(mouvement == Option.PREMIERVOISIN)
							arret = true;	
					} 
				}
			}
		}
		
		return nouvelleSolution;
	}
	
	/**
	 * Améliore une solution selon la méthode de voisinage two-opt
	 * @param mouvement Strategie de mouvement
	 * @param matrice Matrice 2D de coûts utile aux calculs
	 * @param solution La solution a améliorer
	 * @param cout Le coût à améliorer
	 * @return Une solution améliorée
	 */
	private static List<Integer> twoOpt(Option mouvement, int[][] matrice, List<Integer> solution, int cout) {
		
		// future solution amelioree
		List<Integer> nouvelleSolution = new ArrayList<Integer>();
		// solution utile aux traitements 
		List<Integer> solutionTampon = new ArrayList<Integer>();
		nouvelleSolution.addAll(solution);
		// condition d'arret de la boucle d'amelioration
		boolean boucle = true;
		// condition d'arret du parcours d'amelioration
		boolean arret = false;
		// futur cout amélioré
		int meilleurCout = cout;
		
		// boucle d'amelioration
		while(boucle) {
			
			boucle = false;
			arret = false;
			int coutTampon = 0;
			
			// parcous d'amélioration
			for(int i = 0; i < nouvelleSolution.size() && !arret; i++) {
				
				for(int k = 2; k < nouvelleSolution.size()-1 && !arret; k++) {
					
					int j = i+k;
					
					if(j >= nouvelleSolution.size())
						j = j-nouvelleSolution.size();
					
					// on effectue les calculs sur une solution tampon
					solutionTampon.clear();
					solutionTampon.addAll(nouvelleSolution);
					
					// On déconnecte et reconnecte un segment à la solution selon les indices i et j
					if(j == 0)
						solutionTampon = permuterSolution(solutionTampon, i+1, solutionTampon.size()-1);
					else if(i+1 == nouvelleSolution.size())
						solutionTampon = permuterSolution(solutionTampon, 0, j-1);
					else 
						solutionTampon = permuterSolution(solutionTampon, i+1, j-1);
					// calcul du cout
					coutTampon = ipint.adom.tsp.tp1.Utils.evaluerSolution(solutionTampon, matrice);
					
					// il s'agit d'un cout améliorant
					if(coutTampon < meilleurCout && coutTampon > 0) {

						// sauvegarde de la nouvelle solution et du cout
						nouvelleSolution.clear();
						nouvelleSolution.addAll(solutionTampon);
						meilleurCout = coutTampon;
						boucle = true;
						
						// on a trouve un premier voisin ameliorant, on arrete l'amélioration
						if(mouvement == Option.PREMIERVOISIN)
							arret = true;
					}
				}
			}
		}
		
		return nouvelleSolution;
	}
	
	/**
	 * Extrait un segement d'une solution afin de le permuter et de le reconnecter à la solution
	 * @param solution Solution à permuter
	 * @param x Borne de découpe
	 * @param y Borne de découpe
	 * @return Une solution permutée
	 */
	public static List<Integer> permuterSolution(List<Integer> solution, int x, int y) {
		
		// nouvelle solution
		List<Integer> nouvelleSolution = new ArrayList<Integer>();
		// segments à permuter
		List<Integer> permutations = new ArrayList<Integer>();
		
		
		if(x < y) {
			
			nouvelleSolution.addAll(solution.subList(0, x));
			permutations = solution.subList(x, y+1);
			Collections.reverse(permutations);
			nouvelleSolution.addAll(permutations);
			nouvelleSolution.addAll(solution.subList(y+1, solution.size()));
			
		} else {
			
			int compteur = 0;
			
			for(int i = x; i < solution.size(); i++) {
				permutations.add(solution.get(i));
				compteur++;
			}
			
			for(int i = 0; i <= y; i++)
				permutations.add(solution.get(i));
			
			Collections.reverse(permutations);
			
			for(int i = compteur; i < permutations.size(); i++)
				nouvelleSolution.add(permutations.get(i));
			
			for(int i = y+1; i < x; i++)
				nouvelleSolution.add(solution.get(i));
			
			for(int i = 0; i < compteur; i++) 
				nouvelleSolution.add(permutations.get(i));	
		}
		
		return nouvelleSolution;
	}
	
	/***** ALGORITHME PRINCIPAL *****/
	
	public static void algorithmeHillClimbing(Option initialisation, Option voisinage, Option mouvement, int[][] matrice) {
		
		// solution initiale à améliorer 
		List<Integer> solutionDepart = new ArrayList<Integer>();
		
		// choix de la méthode d'initialisation
		switch(initialisation) {
			
			case ALEATOIRE:
				solutionDepart = ipint.adom.tsp.tp1.Utils.genererSolutionAleatoire();
				break;
			case HEURISTIQUE:
				// choix aléatoire de la ville de départ
				int villeAleatoire = 1 + (int)(Math.random() * ((100 - 1) + 1));
				solutionDepart = ipint.adom.tsp.tp1.Utils.algorithmePlusProcheVoisin(villeAleatoire, matrice);
				break;
			default:
				System.out.println("Le choix d'initialisation est incorrect.");
				break;
		}
		
		// Affichage de la solution de départ
		System.out.print("Solution de départ : ");
		for(int i = 0; i < solutionDepart.size(); i++) {
			
			System.out.print(solutionDepart.get(i) + " ");
		}
		// calcul du cout de la solution de départ
		int coutSolutionDepart = ipint.adom.tsp.tp1.Utils.evaluerSolution(solutionDepart, matrice);
		System.out.println("\nEvaluation de la solution de départ : " + coutSolutionDepart);
		// solution améliorée
		List<Integer> nouvelleSolution = new ArrayList<Integer>();
		// choix de la méthode de voisinage
		switch(voisinage) {
		
			case SWAP:
				nouvelleSolution = swap(mouvement, matrice, solutionDepart, coutSolutionDepart);
				break;
			case TWOOPT:
				nouvelleSolution = twoOpt(mouvement, matrice, solutionDepart, coutSolutionDepart);
				break;
			default:
				System.out.println("Le choix de voisinage est incorrect.");
				break;
		}
		// Affichage de la nouvelle solution
		System.out.print("\nNouvelle Solution : ");
		for(int i = 0; i < nouvelleSolution.size(); i++) {
			
			System.out.print(nouvelleSolution.get(i) + " ");
		}
		// calcul du cout de la nouvelle solution
		int coutNouvelleSolution = ipint.adom.tsp.tp1.Utils.evaluerSolution(nouvelleSolution, matrice);
		System.out.println("\nEvaluation de la solution de la nouvelle solution : " + coutNouvelleSolution);
	}
}
