package ipint.adom.tsp.tp4;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Parseur {

	/**
	 * Initialise les valeurs d'une matrice 2D d'objets Critere avec des couts nuls
	 * @param matrice
	 */
	private static void initMatrice(Critere[][] matrice) {
		// parcours lignes
		for(int i = 0; i < matrice.length; i++) {
			// parcours lignes
			for(int j = 0; j < matrice.length; j++) {
				// ajout de la valeur 
				matrice[i][j] = new Critere(0, 0);
			}
		}
	}
	
	/**
	 * Lis deux instances et construit une matrice de critere
	 * @param fichier1 La premiere instance à lire
	 * @param fichier2 La seconde instance à lire
	 * @return Une matrice 2D d'objets Critere
	 */
	public static Critere[][] lireFichiers(String fichier1, String fichier2) {
		
		Critere[][] matrice = new Critere[100][100];
		
		// initialisation de la matrice
		initMatrice(matrice);
		
		try {
			// lecture des fichiers
			InputStream fluxFichier1 = new FileInputStream(fichier1); 
			InputStreamReader lectureFichier1 = new InputStreamReader(fluxFichier1);
			BufferedReader bufferFichier1 = new BufferedReader(lectureFichier1);
			String ligne1;
			
			InputStream fluxFichier2 = new FileInputStream(fichier2); 
			InputStreamReader lectureFichier2 = new InputStreamReader(fluxFichier2);
			BufferedReader bufferFichier2 = new BufferedReader(lectureFichier2);
			String ligne2;
			
			// On passe les 7 premières lignes de texte
			for(int i = 1; i <= 7; i++) {
				
				ligne1 = bufferFichier1.readLine();
				ligne2 = bufferFichier2.readLine();
			}
			
			// Remplissage de la matrice
			for(int i = 0; i < matrice.length; i++){
				
				for(int j = i; j < matrice.length; j++) {
					
					ligne1 = bufferFichier1.readLine();
					ligne2 = bufferFichier2.readLine();
					// on s'intéresse aux nombres
					if(Character.isDigit(ligne1.charAt(0)) && Character.isDigit(ligne2.charAt(0))) {
						
						if(i != j) {
							
							matrice[i][j] = new Critere(Integer.parseInt(ligne1), Integer.parseInt(ligne2));
							matrice[j][i] = new Critere(Integer.parseInt(ligne1), Integer.parseInt(ligne2));
						}
					}
				}
			}
			// fermeture des fichiers
			bufferFichier1.close(); 
			bufferFichier2.close(); 
			
		} catch (Exception e) {
			
			System.out.println("Une erreur est survenue lors de la lecture du fichier.");
		}
		
		return matrice;
	}
	
	
}
