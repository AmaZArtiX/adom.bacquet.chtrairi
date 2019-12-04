package ipint.adom.tsp.tp1;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Parseur {
	
	/**
	 * Initialise une matrice 2D d'entiers à 0
	 * @param matrice Matrice 2D à initialiser
	 */
	private static void initMatrice(int[][] matrice) {
		
		// parcours des lignes
		for(int i = 0; i < matrice.length; i++) {
			
			// parcours de colonnes 
			for(int j = 0; j < matrice.length; j++) {
				
				// Ajout de la valeur 
				matrice[i][j] = 0;
			}
		}
	}
	
	/**
	 * Lit une instance et renvoie la matrice de coûts correspondante
	 * @param fichier Instance à lire
	 * @return Une matrice 2D de coûts
	 */
	public static int[][] lireFichier(String fichier) {
		
		int[][] matrice = new int[100][100];
		
		// Initialisation de la matrice
		initMatrice(matrice);
		
		try {
			// Ouverture du fichier
			InputStream flux = new FileInputStream(fichier); 
			InputStreamReader lecture = new InputStreamReader(flux);
			BufferedReader buff = new BufferedReader(lecture);
			
			String ligne;
			
			// On passe les 7 premières lignes de texte
			for(int i = 1; i <= 7; i++) {
				
				ligne = buff.readLine();
			}
			
			// Remplissage de la matrice
			// parcours ligne
			for(int i = 0; i < matrice.length; i++){
				
				// parcours colonnes
				for(int j = i; j < matrice.length; j++) {
					
					// lecture d'une ligne du fichier
					ligne = buff.readLine();
					
					// on traite uniquement les nombres
					if(Character.isDigit(ligne.charAt(0))) {
						// On cast la valeur lue en int
						int valeur = Integer.parseInt(ligne);
						// On ne s'intéresse pas aux distances entre deux memes villes
						if(i != j) {
							// Ajout des valeurs dans la matrice
							matrice[i][j] = valeur;
							matrice[j][i] = valeur;
						}
					}
				}
			}
			
			// fermeture du fichier
			buff.close(); 
			
		} catch (Exception e) {
			
			System.out.println("Une erreur est survenue lors de la lecture du fichier.");
		}
		
		return matrice;
	}
}
