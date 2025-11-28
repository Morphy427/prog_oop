package prey_predator;

import java.util.Random ;

public class Prey extends Animal {

    private double averageEscapeRate = 0.7 ;
    private double stdDevEscapeRate = 0.1 ;
    private double escapeRate ;

    public Prey() {
        super (0.9, 0.1, 10.0, 1.0) ;
        Random r = new Random () ;
        do {escapeRate = r.nextGaussian () * stdDevEscapeRate + averageEscapeRate;
        } while (escapeRate < 0 || escapeRate > 1);
        
    }

    public Prey (double avr , double sdr , double avl , double sdl , double ave , double sde) {
        /* c o m p l t e r ici */
        super (avr ,sdr ,avl ,sdl) ;
        Random r = new Random () ;
        do {escapeRate = r.nextGaussian () * ave + sde;
        } while (escapeRate < 0 || escapeRate > 1);
        
    }

    public static void main(String[] args) {
        Animal aptonoth = new Prey () ;
        System.out.println ( aptonoth ) ;

        Animal jerry = new Prey (0.95 ,0.2 , 10 ,2, 0.8, 0.1 ) ;
        System.out.println ( jerry ) ;
    }

    /* accesseur */
    public double getEscapeRate(){
        return escapeRate;
    }

    /* Methodes */
    public boolean isAbleToEscape(){
        return Math.random () < this.escapeRate ;
    }

    public String toString() {
        /* c o m p l t e r ici */
        String s = super.toString();
        s += " Taux d'echappement' : " + this.escapeRate + " \n " ;
        return s;
    }

}
