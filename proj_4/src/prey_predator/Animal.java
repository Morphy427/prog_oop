package prey_predator;

import java.util.Random;

public class Animal {

    private double averageReproductionRate = 0.3 ;
    private double stdDevReproductionRate = 0.1 ;

    private double averageLifeExpectancy = 10 ;
    private double stdDevLifeExpectancy = 4 ;

    private int age ;
    private double reproductionRate ;
    private double lifeExpectancy ;

    public Animal () {
    
        Random r = new Random () ;
        
        do {reproductionRate = r.nextGaussian() * stdDevReproductionRate + averageReproductionRate ;
        } while (reproductionRate < 0 || reproductionRate > 1);

        do {lifeExpectancy = r.nextGaussian() * stdDevLifeExpectancy + averageLifeExpectancy ; ;
        } while (lifeExpectancy <= 0);
        
        age = ( int ) ( r.nextDouble() * this.lifeExpectancy ) ;

    }

    public Animal (double averageReproductionRate, double stdDevReproductionRate, double averageLifeExpectancy, double stdDevLifeExpectancy ) {
        
        // ajouter controle sur valeures des 4 args

        Random r = new Random ();
        
        do {reproductionRate = r.nextGaussian() * stdDevReproductionRate + averageReproductionRate;
        } while (reproductionRate < 0 || reproductionRate > 1);

        do {lifeExpectancy = r.nextGaussian() * stdDevLifeExpectancy + averageLifeExpectancy;
        } while (lifeExpectancy <= 0);
        
        age = (int) (r.nextDouble() * this.lifeExpectancy) ;

    }

    /* Main */
    public static void main(String[] args) {
        Animal trotro = new Animal () ;
        System.out.println ( trotro ) ;

        Animal picsou = new Animal (0.95 ,0.2 , 10 ,2) ;
        System.out.println ( picsou ) ;
    }

    /* setteur */
    public void setLifeExp(int newAge){
        this.lifeExpectancy = newAge;
    }

    /* accesseurs */
    public double getReproRate () {
        return reproductionRate ;
    }

    public double getLifeExp() {
        return lifeExpectancy ;
    }

    public int getAge () {
        return age ;
    }

    /* m t h o d e s pour tous les animaux */
    public boolean isAbleToReproduce () {
        return Math.random () < this.reproductionRate ;
    }

    public boolean isAlive(){
        return (age <= lifeExpectancy);
    }

    public void incrementAge(){
        age += 1;
    }
    
    /* surcharge de la m t h o d e toString */
    public String toString () {
    
        String s = " **** Animal ****\n " ;
        s += " Age : " + this.age + " pour une esperance de vie de " + this.lifeExpectancy + " \n " ;
        s += " Taux de reproduction de : " + this.reproductionRate + " \n " ;
        return s ;
    }

}
