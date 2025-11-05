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
    public Automata(int n, double d, double p){
        // n : taille de la matrice, >= 10 | d : densité de population | p : proportion de vaccination
        if (n < 10) {
            throw new IllegalArgumentException("n doit être au moins 10");
        }

        if (0 >= d || d >= 1){
            throw new IllegalArgumentException("d doit être compris entre 0 et 1");
        }

        if (0 >= p || p >= 1){
            throw new IllegalArgumentException("p doit être compris entre 0 et 1");
        }

        this.dimension = n; 
        this.matrix = new int [n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = Math.random() < d ? 1 : 0; // Génère un nombre aléatoire entre 0 et 1, si inférieur à d, peuple la case
                if (matrix[i][j] == 1){ // si la case est peuplée
                    matrix[i][j] = Math.random() < p ? 2 : 1; // Chance d'être vacciné
                }
            }
        }
        
    }

    /**
     * Constructeur de l'Automata.
     * Initialise une matrice de taille 10 x 10 avec une population et une vaccination basée sur le taux de vaccination au Pakistan en 2023.
     * @param d La densité de population initiale. Un double entre 0.0 et 1.0. 
     * **C'est la probabilité qu'une case soit peuplée (valeur 0 ou 1).**
     * 86 % de vaccination
     */
    public Automata (double d){
        if (0 >= d || d >= 1){
            throw new IllegalArgumentException("d doit être compris entre 0 et 1");
        }
        this.dimension = 10;
        this.matrix = new int [10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                matrix[i][j] = Math.random() < d ? 1 : 0;
                if (matrix[i][j] == 1){
                    matrix[i][j] = Math.random() < 0.86 ? 2 : 1;
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

    // idem avec diagonales
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
     * @param m Risque d'être contaminé. Un double entre 0.0 et 1.0. 
     */
    private int nextState(int i, int j, double m){
        if (isSick(i, j)){
            return (Math.random() < m ? -1 : 3); 
        }
        if (isPeople(i, j) && hasNeighborSick(i, j)){
            return 5;
        }
        return this.matrix[i][j];
    }
    /**
     * Calcul l'état suivant sur les cases adjacentes, risque d'être contaminé fixé a 0.1
     * @param i coordonnée en ligne.
     * @param j coordonnée en colonne.
     */
    private int nextState(int i, int j){
        if (isSick(i, j)){
            return (Math.random() < 0.1 ? -1 : 3); 
        }
        if (isPeople(i, j) && hasNeighborSick(i, j)){
            return 5;
        }
        return this.matrix[i][j];
    }


       /**
     * Calcul l'état suivant sur les cases autours
     * @param i coordonnée en ligne.
     * @param j coordonnée en colonne.
     * @param m Risque d'être contaminé. Un double entre 0.0 et 1.0. 
     */
    private int nextState2(int i, int j, double m){
        if (isSick(i, j)){
            return (Math.random() < m ? -1 : 3);
        }
        if (isPeople(i, j) && hasNeighborSick2(i, j)){
            return 5;
        }
        return this.matrix[i][j];
    }

      /**
     * Calcul l'état suivant sur les cases autours, risque de contamination fixé a 0.1
     * @param i coordonnée en ligne.
     * @param j coordonnée en colonne.
     */
    private int nextState2(int i, int j){
        if (isSick(i, j)){
            return (Math.random() < 0.1 ? -1 : 3);
        }
        if (isPeople(i, j) && hasNeighborSick2(i, j)){
            return 5;
        }
        return this.matrix[i][j];
    }

    /**
    * Simule la propagation de l'épidémie. 
    * Propage l'épidémie avec une probabilité par défaut de 0,5
    */
    private void popagateDisease(){
        int size = this.dimension;
        int[][] newMatrix = new int [size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                newMatrix[i][j] = this.nextState(i, j, 0.5);
            }
        }
        this.matrix = newMatrix;
    }

    /**
    * Simule la propagation de l'épidémie sur les cases adjacentes. Propage l'épidémie avec une probabilité défini par l'utilisateur
    * @param m Pourcentage de risque de contamination
    */
    private void popagateDisease(double m){
        int size = this.dimension;
        int[][] newMatrix = new int [size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                newMatrix[i][j] = this.nextState(i, j, m);
            }
        }
        this.matrix = newMatrix;
    }

    /**
    * Simule la propagation de l'épidémie sur les cases autours. Propage l'épidémie avec une probabilité fixée a 0.5 
    */
    private void propagateDisease2(){
        int size = this.dimension;
        int[][] newMatrix = new int [size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                newMatrix[i][j] = this.nextState2(i, j, 0.5);
            }
        }
        this.matrix = newMatrix;
    }

    /**
    * Simule la propagation de l'épidémie sur les cases autours. Propage l'épidémie avec une probabilité défini par l'utilisateur
    * @param m Pourcentage de risque de contamination
    */
    private void propagateDisease2(double m){
        int size = this.dimension;
        int[][] newMatrix = new int [size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                newMatrix[i][j] = this.nextState2(i, j, m);
            }
        }
        this.matrix = newMatrix;
    }

    /**
     * Simule la propagation de la matrice
     * @param n Nombrre d'itération
     */
    public void propagateDisease(int n){
        for (int i = 0 ; i < n ; i++){
            this.popagateDisease();
            this.matrixDisplay();
            try {
                Thread.sleep(5000) ;
            } catch ( InterruptedException ex ) {
                Thread.currentThread().interrupt();
            }
        }
    }
    
    /**
     * Simule la propagation de la matrice avec un risque de contamination défini par l'utilisateur
     * Cases adjacentes
     * @param n Nombrre d'itération
     * @param m Pourcentage de risque d'être contaminé
     */
    public void propagateDisease(int n, double m){
        for (int i = 0 ; i < n ; i++){
            this.popagateDisease(m);
            this.matrixDisplay();
            try {
                Thread.sleep(5000) ;
            } catch ( InterruptedException ex ) {
                Thread.currentThread().interrupt();
            }
        }
    }
    /**
     * Simule la propagation de la matrice avec un risque de contamination fixé jusqu'à ce qu'il n'y ait plus de malade
     * Cases adjacentes
     */
    public void propagateDisease(){
        while(this.isSick()){
            this.popagateDisease();
            this.matrixDisplay();
            try {
                Thread.sleep(5) ;
            } catch ( InterruptedException ex ) {
                Thread.currentThread().interrupt();
            }
        }
    }
    /**
     * Simule la propagation de la matrice avec un risque de contamination défini par l'utilisateur jusqu'à ce qu'il n'y ait plus de malade
     * Cases adjacentes
     */
    public void propagateDisease(double m){
        while(this.isSick()){
            this.popagateDisease(m);
            this.matrixDisplay();
            try {
                Thread.sleep(5) ;
            } catch ( InterruptedException ex ) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * Simule la propagation de la matrice avec un risque de contamination fixé
     * Cases autours
     * @param n Nombre d'itération
     */
    public void propagateDisease2_2(int n){
        for (int i = 0 ; i < n ; i++){
            this.propagateDisease2();
            this.matrixDisplay();
            try {
                Thread.sleep(5000) ;
            } catch ( InterruptedException ex ) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * Simule la propagation de la matrice avec un risque de contamination fixé par l'utilisateur
     * Cases autours
     * @param n Nombre d'itération
     * @param m Pourcentage de risque d'être contaminé
     */
    public void propagateDisease2_2(int n, double m){
        for (int i = 0 ; i < n ; i++){
            this.propagateDisease2(m);
            this.matrixDisplay();
            try {
                Thread.sleep(5000) ;
            } catch ( InterruptedException ex ) {
                Thread.currentThread().interrupt();
            }
        }
    }

    
    /**
     * Simule la propagation de la matrice avec un risque de contamination fixé jusqu'à ce qu'il n'y ait plus de malade
     * Cases autours
     */
    public void propagateDisease2_2(){
        while(this.isSick()){
            this.propagateDisease2();
            this.matrixDisplay();
            try {
                Thread.sleep(5000) ;
            } catch ( InterruptedException ex ) {
                Thread.currentThread().interrupt();
            }
        }
        
    }

    /**
     * Simule la propagation de la matrice avec un risque de contamination défini par l'utilisateur jusqu'à ce qu'il n'y ait plus de malade
     * Cases autours
     * @param m Pourcentage de risque d'être contaminé
     */
    public void propagateDisease2_2(double m){
        while(this.isSick()){
            this.propagateDisease2(m);
            this.matrixDisplay();
            try {
                Thread.sleep(5000) ;
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
     * Vérifie si une personne est toujours présente sur la matrice
     */
    public boolean isAnnihilated(){ 
        boolean anihilated = true;
        for (int i = 0; i < this.dimension; i++) {
            for (int j = 0; j < this.dimension; j++) {
                if (this.matrix[i][j] == 1 || this.matrix[i][j] == 5){
                    anihilated = false;
                    break;
                }
            }
        }
        return anihilated;
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
        this.matrix[i][j] = 5;
    }

    // Contamine une case aléatoire
    public void contaminate(){
        Random randomNumber = new Random();
        int i = randomNumber.nextInt(this.dimension);
        int j = randomNumber.nextInt(this.dimension);
        this.matrix[i][j] = 5;
    }

    // Contamine une case aléatoire obligatoirement
    public void contaminateForrSure(){
        Random randomNumber = new Random();
        int i = randomNumber.nextInt(this.dimension);
        int j = randomNumber.nextInt(this.dimension);
        while (!isPeople(i, j)){
            i = randomNumber.nextInt(this.dimension);
            j = randomNumber.nextInt(this.dimension);
        }
        this.matrix[i][j] = 5;
    }

    public static void main(String[] args) {
        Automata test = new Automata(0.5);
        test.matrixDisplay();
        System.out.println(test.isAnnihilated());
    }
}
