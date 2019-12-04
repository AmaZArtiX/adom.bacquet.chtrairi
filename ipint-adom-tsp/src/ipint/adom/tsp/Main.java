package ipint.adom.tsp;

import java.util.List;

import ipint.adom.tsp.tp2.Option;

public class Main {
	
	// Instance a lire pour le TP1
	private final static String FICHIERTP1 = "/Users/simonbacquet/Documents/Cours/Master/m2-miage/ADOM/Projets/Instances/randomA100.txt";
	// Instances a lire pour les TPs 4 et 5
	private final static String FICHIER1TP4 = "/Users/simonbacquet/Documents/Cours/Master/m2-miage/ADOM/Projets/Instances/randomA100.txt";
	private final static String FICHIER2TP4 = "/Users/simonbacquet/Documents/Cours/Master/m2-miage/ADOM/Projets/Instances/randomB100.txt";
	
	public static void main(String[] args) {
		
		/***** TP1 *****/
		System.out.println("\n*** TP1 ***\n");
	
		// Lecture du fichier d'instance
		int[][] matrice = ipint.adom.tsp.tp1.Parseur.lireFichier(FICHIERTP1);
		
		// Affichage de la matrice 
		System.out.println("Affichage de la matrice après lecture.\n");
		ipint.adom.tsp.tp1.Utils.afficherMatrice(matrice);
		
		//On génère et évalue 100 solutions aléatoires
		System.out.println("\nAffichage de 100 couts de solutions aléatoires.\n");
		for(int i = 1; i <= 100; i++) {
			
			List<Integer> solutionAleatoire = ipint.adom.tsp.tp1.Utils.genererSolutionAleatoire();
			System.out.println("Cout Solution Aleatoire : " + ipint.adom.tsp.tp1.Utils.evaluerSolution(solutionAleatoire, matrice));
		}
	
		// On applique l'algorithme du plus proche voisin sur chaque ville
		System.out.println("\nAffichage de 100 couts pour chaque ville de départ selon l'algorithme du plus proche voisin.\n");
		for(int i = 1; i <= 100; i++) {
			
			List<Integer> solutionPlusProcheVoisin = ipint.adom.tsp.tp1.Utils.algorithmePlusProcheVoisin(i, matrice);
			System.out.println("Cout Solution pour la ville " + i + " : " + ipint.adom.tsp.tp1.Utils.evaluerSolution(solutionPlusProcheVoisin, matrice));
		}
		
		/***** TP2 *****/
		System.out.println("\n*** TP2 ***\n");
		
		// Appel de l'algorithme hill climbing selon une configuration particulière
		System.out.println("Algorithme hill-climbing\n");
		ipint.adom.tsp.tp2.Utils.algorithmeHillClimbing(Option.HEURISTIQUE, Option.TWOOPT, Option.PREMIERVOISIN, matrice);
				

		/***** TP4 *****/
		System.out.println("\n*** TP4 ***\n");
		
		// Lecture des fichiers d'instance
		ipint.adom.tsp.tp4.Critere[][] matrice2 = ipint.adom.tsp.tp4.Parseur.lireFichiers(FICHIER1TP4, FICHIER2TP4);
		
		// Affichage de la matrice
		System.out.println("Affichage de la matrice après lecture.\n");
		ipint.adom.tsp.tp4.Utils.afficherMatrice(matrice2);
		
		// Filtrage offLine
		System.out.println("\nFiltrage offLine\n");
		ipint.adom.tsp.tp4.Utils.offLine(500, matrice2);
		
		System.out.print("\n\n\n");
		
		// Filtrage onLine
		System.out.println("Filtrage onLine\n");
		ipint.adom.tsp.tp4.Utils.onLine(500, matrice2);	
		
		/***** TP5 *****/
		System.out.println("\n*** TP5 ***\n");
		
		System.out.println("Approche scalaire\n");
		ipint.adom.tsp.tp5.Utils.approcheScalaire(matrice2);	
	}
}
