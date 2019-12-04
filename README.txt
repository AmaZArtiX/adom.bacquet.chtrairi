ADOM - TPs

BACQUET Simon - simon.bacquet.etu@univ-lille.fr
CHTAIRI Yacine - yacine.chtairi.etu@univ-lille.fr

Arborescence du projet ipint-adom-tsp :
  src
   |--- ipint.adom.tsp
      |--- Main.java (Classe principale de l'application contenant la méthode main())
   |--- ipint.adom.tsp.tp1 (code source lié au TP1)
      |--- Parseur.java (Classe permettant la lecture d'une instance monoobjective et la construction d'une marice de coûts associée)
      |--- Utils.java (Classe contenant les méthodes de calculs et d'optimisation)
   |--- ipint.adom.tsp.tp2 (code source lié au TP2)
      |--- Option.java (Classe définissant les configurations pour l'algorithme hill-climing)
      |--- Utils.java (Classe contenant les méthodes de calculs et d'optimisation)
   |--- ipint.adom.tsp.tp4 (code source lié au TP4)
      |--- Critere.java (Classe représentant un critere multiobjectif)
      |--- Parseur.java (Classe permettant la lecture d'une instance multiobjective et la construction d'une marice de coûts associée)
      |--- Utils.java (Classe contenant les méthodes de calculs et d'optimisation)
   |--- ipint.adom.tsp.tp5 (code source lié au TP5)
      |--- Utils.java (Classe contenant les méthodes de calculs et d'optimisation)

Description de la méthode principale main()

  Les fichiers d'instances lus sont représentés par les constantes :

    FICHIERTP1 (utile à la matrice monoobjective)
    FICHIER1TP4 (utile à la matrice multiobjective)
    FICHIER2TP4 (utile à la matrice multiobjective)

    ATTENTION : Les valeurs de ces constantes sont à modifier pour lire un fichier sur une nouvelle machine

  Cas d'utilisation :

    - On initialise une matrice monoobjective selon une instance particulière
    - On affiche la matrice
    - On génère 100 solutions aléatoires pour afficher leur cout
    - On génère 100 solutions selon une heuristique constructive pour afficher leur cout

    - On génère une solution améliorante selon la méthode hill-climbing

    - On initialise une matrice multiobjective selon deux instances particulière
    - On affiche la matrice
    - On effectue un filtrage offLine sur 500 solutions aléatoires
    - On effectue un filtrage onLine sur 500 solutions aléatoires

    - On génère un ensemble de solutions depuis une approche sclaire

Utilisation des méthodes

  Les classes de type Utils et Parseur sont les seules classes à appler dans la méthode principale.
  Ces classes possédent des méthodes dites 'static', il n'est donc pas nécessaire de créer des objets de ces types pour appeler les méthodes.

  TP1

    Parseur.java

      Pour lire une instance monoobjective et la parser en une matrice 2D de criteres monoobjectif il faut appeler la méthode :

        public static int[][] lireFichier(String fichier)

          fichier -> instance monoobjective à lire

    Utils.java

      Pour afficher une matrice 2D de criteres monoobjectif il faut appeler la méthode :

        public static void afficherMatrice(int[][] matrice)

          matrice -> la matrice 2D monoobjective à afficher

      Pour générer une solution aléatoire,  il faut appeler la méthode :

        public static List<Integer> genererSolutionAleatoire()

      Pour évaluer une solution, il faut appler la méthode :

        public static int evaluerSolution(List<Integer> solution, int[][] matrice)

          solution -> la solution à évaluer
          matrice -> la matrice 2D monoobjective utile aux calculs

      Pour générer une solution selon une heuristique constructive, il faut appeler la méthode

        public static List<Integer> algorithmePlusProcheVoisin(int ville, int[][] matrice)

        ville -> la ville de départ
        matrice -> la matrice 2D monoobjective utile aux calculs

  TP2

    Utils.java

      Pour générer une solution selon une méthode de type hyll-climbing, il faut appeler la méthode :

      public static void algorithmeHillClimbing(Option initialisation, Option voisinage, Option mouvement, int[][] matrice)

        initialisation -> Option de la stratégie d'initialisation
        voisinage -> Option de la stratégie de voisinage
        mouvement -> Option de la stratégie de mouvement
        matrice -> la matrice 2D monoobjective utile aux calculs

  TP4

    Parseur.java

    Pour lire une instance multiobjective et la parser en une matrice 2D de criteres multiobjectif il faut appeler la méthode :

      public static Critere[][] lireFichiers(String fichier1, String fichier2

        fichier1 -> instance monoobjective à lire
        fichier2 -> instance monoobjective à lire

    Utils.java

      Pour afficher une matrice 2D de criteres multiobjectif il faut appeler la méthode :

        public static void afficherMatrice(Critere[][] matrice)

          matrice -> la matrice 2D multiobjective à afficher

      Pour effectuer un filtrage dit hors-ligne sur un ensemble de solutions, il faut appler la méthode :

        public static void offLine(int nbIterations, Critere[][] matrice)

          nbIterations -> le nombre de solutions à générer
          matrice -> la matrice 2D multiobjective utile aux calculs

      Pour effectuer un filtrage dit en-ligne sur un ensemble de solutions, il faut appler la méthode :

        public static void onLine(int nbIterations, Critere[][] matrice)

          nbIterations -> le nombre de solutions à générer
          matrice -> la matrice 2D multiobjective utile aux calculs

  TP5

    Utils.java

      Pour générer en ensemble de solutions selon une approche scalaire, il faut appeler la méthode :

        public static void approcheScalaire(Critere[][] matrice)

          matrice -> la matrice 2D multiobjective utile aux calculs

Analyse des résultats (tableurs et graphiques Excel)

 Les résultats obtenus pour les différents algorithmes d'optimisation sont contenus dans des fichiers de type .xlsx
 Le fichier bacquet.chtairi.resultats.tp1.xlsx contient les résultats du TP1
 Le fichier bacquet.chtairi.resultats.tp2.xlsx contient les résultats du TP2
 Le fichier bacquet.chtairi.resultats.tp4.xlsx contient les résultats du TP4
 Le fichier bacquet.chtairi.resultats.tp5.xlsx contient les résultats du TP5
