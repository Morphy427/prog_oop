package prey_predator;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import java.util.*;

public class PreyPredator {

    List<Prey> proies = new LinkedList<Prey>() ; /* on utilise une LinkedLList pour faire une liste mais on veut etre sur qu'elle ne soit pas utilisé comme une queue */
    List<Predator> predateurs = new LinkedList<Predator>() ; /* peu pertinent de faire un set pour les predateurs, il faut refaire une liste */
    int proiesMax = 3000 ;
    int year = 0 ;

    public PreyPredator(){
        Random random = new Random();

        int npreys = random.nextInt(2450) + 50;
        int npred = random.nextInt(700) + 50;

        for (int i = 0 ; i < npreys ; i++){
            Prey prey = new Prey();
            proies.add(prey);
        }

        for (int i = 0 ; i < npred ; i++){
            Predator predator = new Predator();
            predateurs.add(predator);
        }
    }
    public PreyPredator (int npreys , int npred ) {
        /* completer */
        for (int i = 0 ; i < npreys ; i++){
            Prey prey = new Prey();
            proies.add(prey);
        }

        for (int i = 0 ; i < npred ; i++){
            Predator predator = new Predator();
            predateurs.add(predator);
        }
    }
    public PreyPredator (int npreys , int npred,double avr, double sdr, double avl, double sdl, double ave, double sde, double ada, double sda) {
        /* completer */
        for (int i = 0 ; i < npreys ; i++){
            Prey prey = new Prey(avr, sdr, avl, sdl , ave, sde);
            proies.add(prey);
        }

        for (int i = 0 ; i < npred ; i++){
            Predator predator = new Predator(avr, sdr, avl, sdl ,ada, sda);
            predateurs.add(predator);
        }
    }


    public void main ( String[] args ) {

        // config 
        double avr = 0.3; double sdr = avr*0.1;  // Average / sd reproduction rate  0.3 / 0.1
        double avl = 10; double sdl = avl*0.3; // Average / sd life Expancy 10 / 4
        double ave = 0.7; double sde = ave*0.15; // Average / sd escape rate 0.7 / 0.1
        double ada = 0.9; double sda = ada*0.15; //  Average / sd attack rate 0.9 / 0.1

        List<Integer> values = new ArrayList<>(Arrays.asList(250, 500, 750, 1000, 1250, 1500, 1750, 2000));
        // for (int npreds : values ) {
            
            PreyPredator pp = new PreyPredator (500 ,1500, avr, sdr, avl, sdl, ave, sde, ada, sda) ;
            List<Integer> history_prey = new LinkedList<Integer>();
            List<Integer> history_pred = new LinkedList<Integer>();
            List<Integer> time = new LinkedList<Integer>();

            for ( int y = 0; y < 100; y++) {
                System.out.println ( pp ) ;
                pp.oneYear () ;
                time.add(y);
                history_prey.add(pp.proies.size()); 
                history_pred.add(pp.predateurs.size()); 

            }
            System.out.println(time);System.out.println(history_pred);System.out.println(history_prey);
        }
    // }


    public void oneYear() {
        /* birth of animals */
        double babyPreds = 0 ;
        for ( Predator p : predateurs )
            if ( p.isAbleToReproduce() )
                babyPreds += 0.5 ;
        for ( int i =0; i < babyPreds ; i++) {
            Predator b = new Predator() ;
            predateurs.add (b) ;
        }

        /* completer */
        double babyPreys = 0 ;
        for ( Prey p : proies )
            if ( p.isAbleToReproduce() )
                babyPreys += 0.5 ;
        for ( int i =0; i < babyPreys ; i++) {
            if (proies.size() == proiesMax) { // Peut être mettre un proies.size() >= proiesMax pour éviter un éventuel saut qui ne devrait normalement pas arriver avec une incrémentation de 0.5 ?
                break;
            }
            Prey b = new Prey() ;
            proies.add (b) ;
        }

        /* lunch time */
        Random r = new Random() ;
        for ( Predator p : predateurs ) {
            if ( p.canAttack() && proies.size() > 1) {
                /* completer */
                // tirer une proie
                int indexPrey = r.nextInt(proies.size()-1);
                Prey attackedPrey = proies.get(indexPrey);

                //verifier si elle s'echappe
                if (attackedPrey.isAbleToEscape()){
                    // si oui penalité predateur
                    p.starvation();
                } else {
                    // si non mort de la proie
                    proies.remove(indexPrey);
                } 
            }

        }
        /* age increment and death of animals */
        /* completer */
        for (int i = 0 ; i < proies.size() ; i++){
            if (!proies.get(i).isAlive()){
                proies.remove(i);
            }
        }
        for (int i = 0 ; i < predateurs.size() ; i++){
            if (!predateurs.get(i).isAlive()){
                predateurs.remove(i);
            }
        }
        year += 1 ;
    }

    public String toString(){
        // **** An 4 **** Proies : 521 & Predateurs : 46
        return (String.format("**** An %d **** Proies : %d & Predateurs : %d", this.year, this.proies.size(), this.predateurs.size()));
    } 


    
}

    
