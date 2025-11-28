import java.util.Random;

public class Automata {
    // simule la propagation non realiste d'une epidemie
    // individus sains non vaccinés = 1
    // individus sains vaccinés = 2
    // individus guéris = 3 / y a t il un intéret a differencier les guéris des vaccinés ?
    // individus souffrant de la polio = 5
    // personnes décédées = -1
    // cellule vide = 0

    // Définition des attributs de la classe
    private int[][] matrix; 
    private int dimension;
    private double mortality;
    private double contagion;

    public int getDim(){
        return this.dimension;
    }
    
    // Constructeurs
    /**
     * Constructeur de l'Automata.
     * Initialise une matrice de taille n x n avec une population et une vaccination aléatoires.
     * * @param n La taille de la matrice (carrée). Doit être supérieur ou égal à 10.
     * @param d La densité de population initiale. Un double entre 0.0 et 1.0. 
     * **C'est la probabilité qu'une case soit peuplée (valeur 0 ou 1).**
     * @param p La proportion de vaccination. Un double entre 0.0 et 1.0. 
     * Si une case est peuplée (valeur 2 dans p % des cas).
     */
    public Automata(int n, double d, double p, double m, double c){
        // n : taille de la matrice, >= 10 | d : densité de population | p : proportion de vaccination
        if (n < 10) {
            throw new IllegalArgumentException("n doit être au moins 10");
        }

        if (0 > d || d > 1){
            throw new IllegalArgumentException("d doit être compris entre 0 et 1");
        }

        if (0 > p || p > 1){
            throw new IllegalArgumentException("p doit être compris entre 0 et 1");
        }

        if (0 > m || m > 1){
            throw new IllegalArgumentException("m doit être compris entre 0 et 1");
        }

        if (0 > c || c > 1){
            throw new IllegalArgumentException("c doit être compris entre 0 et 1");
        }

        this.dimension = n; 
        this.matrix = new int [n][n];
        this.mortality = m;
        this.contagion = c;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.matrix[i][j] = Math.random() < d ? 1 : 0; // Génère un nombre aléatoire entre 0 et 1, si inférieur à d, peuple la case
                if (this.matrix[i][j] == 1){ // si la case est peuplée
                    this.matrix[i][j] = Math.random() < p ? 2 : 1; // Chance d'être vacciné
                }
            }
        }
        
    }

    /**
     * Constructeur de l'Automata.
     * Initialise une matrice de taille 10 x 10 avec un taux de vaccination basée sur le taux de vaccination au Pakistan en 2023.
     * @param d La densité de population initiale. Un double entre 0.0 et 1.0. 
     * **C'est la probabilité qu'une case soit peuplée (valeur 0 ou 1).**
     * 86 % de vaccination
     */
    public Automata (double d){
        if (0 >= d || d >= 1){
            throw new IllegalArgumentException("d doit être compris entre 0 et 1");
        }

        int n = 10;
        double p = 0.86;
        double m = 0.1;
        double c = 1;

        this.dimension = n;
        this.matrix = new int [n][n];
        this.mortality = m;
        this.contagion = c;

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                this.matrix[i][j] = Math.random() < d ? 1 : 0;
                if (this.matrix[i][j] == 1){
                    this.matrix[i][j] = Math.random() < p ? 2 : 1;
                }
            }
        }
    }

    /**
     * Constructeur par defaut de l'Automata.
     * Initialise une matrice de taille 10 x 10 avec une population et une vaccination basée sur le taux de vaccination au Pakistan en 2023.
     * 0.274 habitants/m^2
     * 86 % de vaccination
     */
    public Automata (){
        int n = 10;
        double d = 0.274;
        double p = 0.86;
        double m = 0.1;
        double c = 1;
        // this(n, d, p);
        
        this.dimension = n;
        this.matrix = new int [n][n];
        this.mortality = m;
        this.contagion = c;

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                this.matrix[i][j] = Math.random() < d ? 1 : 0;
                if (this.matrix[i][j] == 1){
                    this.matrix[i][j] = Math.random() < p ? 2 : 1;
                }
            }
        }
    }

    // Dit si une case contient une personne saine non vaccinée
    private boolean isPeople(int i, int j){
        return this.matrix[i][j] == 1;
    }

    // Dit si une case contient une personne malade
    private boolean isSick(int i, int j){
        return this.matrix[i][j] == 5;
    }

    // Check voisinnage cardinaux malades
    private boolean hasNeighborSick(int i, int j){
        if(i-1 >= 0){
            if(this.matrix[i-1][j] == 5){
                return true;
            }
        }
        if(i+1 < this.dimension){
            if(this.matrix[i+1][j] == 5){
                return true;
            }
        }
        if(j-1 >= 0){
            if(this.matrix[i][j-1] == 5){
                return true;
            }
        }
        if(j+1 < this.dimension){
            if(this.matrix[i][j+1] == 5){
                return true;
            }
        }
        return false;
    }

    // Check voisinnage malades avec diagonales
    private boolean hasNeighborSick2(int i, int j){
        if(i-1 >= 0){
            if(this.matrix[i-1][j] == 5){
                return true;
            }
            if(j-1 >= 0){
                if(this.matrix[i-1][j-1] == 5){
                    return true;
                }
            }
            if(j+1 < this.dimension){
                if(this.matrix[i-1][j+1] == 5){
                    return true;
                }
            }
        }
        if(i+1 < this.dimension){
            if(this.matrix[i+1][j] == 5){
                return true;
            }
            if(j-1 >= 0){
                if(this.matrix[i+1][j-1] == 5){
                    return true;
                }
            }
            if(j+1 < this.dimension){
                if(this.matrix[i+1][j+1] == 5){
                    return true;
                }
            }
        }
        if(j-1 >= 0){
            if(this.matrix[i][j-1] == 5){
                return true;
            }
        }
        if(j+1 < this.dimension){
            if(this.matrix[i][j+1] == 5){
                return true;
            }
        }
        return false;
    }

    /**
     * Calcul l'état suivant sur les cases adjacentes
     * @param i coordonnée en ligne.
     * @param j coordonnée en colonne.
     * @param m Risque de décès. Un double entre 0.0 et 1.0. 
     */
    private int nextState(int i, int j){
        if (isSick(i, j)){
            return (Math.random() < this.mortality ? -1 : 3); 
        }
        if (isPeople(i, j) && hasNeighborSick(i, j)){
            return (Math.random() < this.contagion ? 5 : 1);
        }
        return this.matrix[i][j];
    }

    /**
     * Calcul l'état suivant sur les cases autours
     * @param i coordonnée en ligne.
     * @param j coordonnée en colonne.
     * @param m Risque de décès. Un double entre 0.0 et 1.0. 
     */
    private int nextState2(int i, int j){
        if (isSick(i, j)){
            return (Math.random() < this.mortality ? -1 : 3);
        }
        if (isPeople(i, j) && hasNeighborSick2(i, j)){
            return (Math.random() < this.contagion ? 5 : 1);
        }
        return this.matrix[i][j];
    }

    /**
     * Simule la propagation de l'épidémie. 
     * Propage l'épidémie avec une probabilité par défaut de 0,5
     */
    private void spreadDisease(){
        if (this.isSick()){
            int size = this.dimension;
        int[][] newMatrix = new int [size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                newMatrix[i][j] = this.nextState(i, j);
            }
        }
        this.matrix = newMatrix;
        }
    }

    /**
     * Simule la propagation de l'épidémie sur les cases autours. Propage l'épidémie avec une probabilité fixée a 0.5 
     */
    private void spreadDisease2(){
        if (this.isSick()){
            int size = this.dimension;
            int[][] newMatrix = new int [size][size];
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    newMatrix[i][j] = this.nextState2(i, j);
                }
            }
            this.matrix = newMatrix;
        }
    }

    /**
     * Simule la propagation de la matrice sur n itération
     * @param n Nombre d'itération
     */
    public void propagateDisease(int n){
        for (int i = 0 ; i < n ; i++){
            this.spreadDisease();
            this.matrixDisplay();
            try {
                Thread.sleep(50) ;
            } catch ( InterruptedException ex ) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * Simule la propagation de la matrice avec un risque de décès fixé jusqu'à ce qu'il n'y ait plus de malade
     * Cases adjacentes
     */
    public void propagateDiseaseUntilEnd(){
        while(this.isSick()){
            this.spreadDisease();
            this.matrixDisplay();
            try {
                Thread.sleep(50) ;
            } catch ( InterruptedException ex ) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * Simule la propagation de la matrice
     * Cases autours
     * @param n Nombre d'itération
     */
    public void propagateDisease2(int n){
        for (int i = 0 ; i < n ; i++){
            this.spreadDisease2();
            this.matrixDisplay();
            try {
                Thread.sleep(50) ;
            } catch ( InterruptedException ex ) {
                Thread.currentThread().interrupt();
            }
        }
    }
    
    /**
     * Simule la propagation de la matrice jusqu'à ce qu'il n'y ait plus de malade
     * Cases autours
     */
    public void propagateDiseaseUntilEnd2(){
        while(this.isSick()){
            this.spreadDisease2();
            this.matrixDisplay();
            try {
                Thread.sleep(50) ;
            } catch ( InterruptedException ex ) {
                Thread.currentThread().interrupt();
            }
        }
        
    }

    /**
    * Affichage de la matrice
    * . : vide | P : personne non vaccinée | V : personne vaccinée | G: personne guérie | O : Malade | _ : Décès
    */
    public void matrixDisplay(){
        // . : vide | P : personne non vaccinée | V : personne vaccinée | G: personne guérie | O : Malade | _ : delamorkitu
        String toString = "";
        for (int i = 0; i < this.dimension; i++) {
            toString += "[";
            for (int j = 0; j < this.dimension; j++) {
                int current = this.matrix[i][j];
                switch(current){
                    case 0 :
                        toString += " .";
                        break;
                    case 1 :
                        toString += " P";
                        break;
                    case 2 :
                        toString += " V";
                        break;
                    case 3 :
                        toString += " G";
                        break;
                    case 5 :
                        toString += " O";
                        break;
                    case -1 :
                        toString += " _";
                        break;
                }
            }
            toString += " ]\n";
        }
        System.out.println(toString); 
    }
   
    
    /**
     * Vérifie si une personne est malade sur la matrice
     */
    public boolean isSick(){
        boolean sick = false;
        for (int i = 0; i < this.dimension; i++) {
            for (int j = 0; j < this.dimension; j++) {
                if (this.matrix[i][j] == 5){
                    sick = true;
                    break;
                }
            }
        }
        return sick;
    }

    // Contamine une case précise
    public void contaminate(int i, int j){
        if (this.isPeople(i,j)) {
            this.matrix[i][j] = 5;
        }
    }

    // Contamine une case aléatoire obligatoirement
    public void contaminateForSure(){
        Random randomNumber = new Random();
        int i = randomNumber.nextInt(this.dimension);
        int j = randomNumber.nextInt(this.dimension);
        while (!isPeople(i, j)){
            i = randomNumber.nextInt(this.dimension);
            j = randomNumber.nextInt(this.dimension);
        }
        this.matrix[i][j] = 5;
    }

    // rajouter un display qui prend en compte le nombre d'itération plutôt que des prints dans le terminal
}
