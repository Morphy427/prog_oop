package prey_predator;

import java.util.Random;

public class Predator extends Animal {

    private double averageAttack = 0.9 ;
    private double stdDevAttack = 0.1 ;
    private double attackRate ;


    /*
    1. Taux d’attaque moyen : 0,9
    2. écart-type du taux d’attaque : 0,1
    3. taux de reproduction moyen : 0,4
    4. écart-type du taux de reproduction : 0,1
    5. espérance de vie moyenne : 12
    6. écart-type de l’espérance de vie : 3
     */
    public Predator() {
        super (0.4, 0.1, 12.0, 3.0) ;
        Random r = new Random () ;
        do {attackRate = r.nextGaussian () * stdDevAttack + averageAttack;
        } while (attackRate < 0 || attackRate > 1);
    }

    public Predator (double avr , double sdr , double avl , double sdl , double ava , double sda) {
        /* c o m p l t e r ici */
        super (avr ,sdr ,avl ,sdl);
        Random r = new Random () ;
        do {attackRate = r.nextGaussian () * ava + sda;
        } while (attackRate < 0 || attackRate > 1);
    }

    public static void main(String[] args) {
        Animal nergigante = new Predator () ;
        System.out.println ( nergigante ) ;

        Animal tom = new Predator (0.95 ,0.2 , 10 ,2, 0.8, 0.1 ) ;
        System.out.println ( tom ) ;
    }

    /* accesseur */
    public double getAttackRate(){
        return attackRate;
    }

    /* Methodes */
    public boolean canAttack(){
        return Math.random () < this.attackRate ;
    }
    
    public void starvation(){
    super.setLifeExp((int)getLifeExp() - 2); 
    }

    public String toString() {
        /* c o m p l t e r ici */
        String s = super.toString();
        s += " Taux d'attaque' : " + this.attackRate + " \n " ;
        return s;
    }

    

}
