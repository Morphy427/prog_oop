package assembly;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class Demo {

    public static void main(String[] args) throws Exception {

        System.out.println("===== DEMONSTRATION DU FONCTIONNEMENT DE CONTIG ET READ =====\n");

        testChevauchementManuelSE();
        testChevauchementManuelAEA();
        testChevauchementManuelAER();

        // testSansErreur();

        // testAvecErreur(0.1f);

        // testEquivalence();
    }

    // ----------------------------------------------------------
    //  Test : assemblage sans erreur
    // ----------------------------------------------------------
    public static void testSansErreur() throws Exception {

        System.out.println(">>> Assemblage sans erreur de séquençage");
        System.out.println();

        System.out.println ( System.getProperty ( "user.dir" ) ) ;

		String filename = "/proj_3/src/assembly/my_reads2.txt" ;
		File monFichierTexte = new File ( System.getProperty ( "user.dir" ) + filename ) ;

		// Simple test to verify that the file exists.
		if ( monFichierTexte.exists() ) {
			System.out.println ( " The file " + filename + " is present in the given directory \n " ) ;
		} else {
			System.out.println ( " The file " + filename + " is NOT present in the given directory " ) ;
		}

        System.out.println("--------------------------------------------------\n");

		LinkedList <Read> list_reads = new LinkedList <Read>();

		BufferedReader br = new BufferedReader ( new FileReader ( monFichierTexte ));

		String line ;
		while (( line = br.readLine () ) != null ) {
			Read r1 = new Read ( line ) ;
			list_reads.add ( r1 ) ;
		}
		br.close () ;

        Contig cont = new Contig(list_reads.get(0));
		list_reads.remove(0);
		int pos = cont.nextRead(list_reads);

		while ((!list_reads.isEmpty()) && (pos != -1)){
			cont = cont.fusion(list_reads.get(pos));
			list_reads.remove(pos);
			System.out.println(String.format("Fusion with %s, still %d reads to assemble... work in process", pos, list_reads.size()));
			// System.out.println(list_reads.size());
			pos = cont.nextRead(list_reads);
			// System.out.println(pos);
		}

		System.out.print(String.format("\nContig obtained with %s reads", cont.getNb_fusions()));
		System.out.println(cont.fastaFormat());
        System.out.println("--------------------------------------------------\n");
    }

    // ----------------------------------------------------------
    //  Test : assemblage avec erreurs
    // ----------------------------------------------------------
    public static void testAvecErreur(float perror) throws Exception {

        System.out.println(">>> Assemblage avec erreurs de séquençage");
        System.out.println();

        System.out.println ( System.getProperty ( "user.dir" ) ) ;
       

		String filenameWerror = "/proj_3/src/assembly/my_reads_with_sequencing_errors.txt" ;
		File monFichierTexteWerror = new File ( System.getProperty ( "user.dir" ) + filenameWerror ) ;
		
		// Simple test to verify that the file exists.
		if ( monFichierTexteWerror.exists() ) {
			System.out.println ( " The file " + filenameWerror + " is present in the given directory \n " ) ;
		} else {
			System.out.println ( " The file " + filenameWerror + " is NOT present in the given directory " ) ;
		}

        System.out.println("--------------------------------------------------\n");

		LinkedList <Read> list_reads_errors = new LinkedList <Read>();

		BufferedReader br_error = new BufferedReader ( new FileReader ( monFichierTexteWerror ));

		String line_error ;
		while (( line_error = br_error.readLine () ) != null ) {
			Read r2 = new Read ( line_error ) ;
			list_reads_errors.add ( r2 ) ;
		}
		br_error.close () ;

		Contig contErr = new Contig(list_reads_errors.get(0));
		list_reads_errors.remove(0);
		int posErr = contErr.nextReadWithError(list_reads_errors, perror);

		// System.out.println(list_reads);

		while ((!list_reads_errors.isEmpty()) && (posErr != -1)){
			contErr = contErr.fusionWerror(list_reads_errors.get(posErr), perror);
			list_reads_errors.remove(posErr);
			System.out.println(String.format("Fusion with %s, still %d reads to assemble... work in process", posErr, list_reads_errors.size()));
			// System.out.println(list_reads_errors.size());
			posErr = contErr.nextReadWithError(list_reads_errors, perror);

			// System.out.println(pos);
		}

		System.out.println(String.format("Contig obtained with %s reads", contErr.getNb_fusions()));
		System.out.println(contErr.fastaFormat());
        System.out.println("--------------------------------------------------\n");
    }

    // ----------------------------------------------------------
    //  Test : chevauchement manuel et fusion
    // ----------------------------------------------------------
    public static void testChevauchementManuelSE() {

        System.out.println(">>> Vérification manuelle du chevauchement et de la fusion sans errreur");
        System.out.println();

        Read test1 = new Read("azertyuiop");
        Read test2 = new Read("yuiopqsdf");

        Contig ctest = new Contig(test1);

        System.out.println("Séquence 1 : " + test1.getSeq());
        System.out.println("Séquence 2 : " + test2.getSeq());
        System.out.println("Overlap chevauchement parfait : " + ctest.bestOverlap(test2)); // attendu : 5
        System.out.println("Fusion parfaite : " + ctest.fusion(test2).getSeq());
        System.out.println("--------------------------------------------------\n");
    }

    public static void testChevauchementManuelAEA() {

        System.out.println(">>> Vérification manuelle du chevauchement et de la fusion avec erreur accepté");
        System.out.println();

        Read test1 = new Read("azertyuiop");
        Read test2 = new Read("yoiopqsdf"); // contient une erreur
        
        Float perror = 0.3f;

        Contig ctest = new Contig(test1);

        System.out.println("Séquence 1 : " + test1.getSeq());
        System.out.println("Séquence 2 : " + test2.getSeq());
        System.out.println("Overlap chevauchement avec erreur tolérée : " + ctest.bestOverlapWithError(test2, perror));
        System.out.println("Fusion avec erreur tolérée : " + ctest.fusionWerror(test2, perror).getSeq());
        System.out.println("--------------------------------------------------\n");
    }

    public static void testChevauchementManuelAER() {

        System.out.println(">>> Vérification manuelle du chevauchement et de la fusion avec erreur refusée");
        System.out.println();
        
        Read test1 = new Read("azertyuiop");
        Read test2 = new Read("yuiapqsdf"); // contient une erreur
        
        Float perror = 0.1f;

        Contig ctest = new Contig(test1);

        System.out.println("Séquence 1 : " + test1.getSeq());
        System.out.println("Séquence 2 : " + test2.getSeq());
        System.out.println("Overlap chevauchement avec erreur tolérée : " + ctest.bestOverlapWithError(test2, perror));
        System.out.println("Fusion avec erreur tolérée : " + ctest.fusionWerror(test2, perror).getSeq());
        System.out.println("--------------------------------------------------\n");
    }


    // ----------------------------------------------------------
    //  Test : equivalence entre methodes sans erreurs et
    //         methode avec erreurs à perror = 0
    // ----------------------------------------------------------
    public static void testEquivalence() throws Exception {
        System.out.println(">>> Comparaison des résultats entre assemblage sans erreur et assemblage avec erreur à perror = 0");
        System.out.println();

        System.out.println ( System.getProperty ( "user.dir" ) ) ;

		String filename = "/proj_3/src/assembly/my_reads2.txt" ;
		File monFichierTexte = new File ( System.getProperty ( "user.dir" ) + filename ) ;
        File monFichierTexteWerror = new File ( System.getProperty ( "user.dir" ) + filename ) ;

		// Simple test to verify that the file exists.
		if ( monFichierTexte.exists() ) {
			System.out.println ( " The file " + filename + " is present in the given directory \n " ) ;
		} else {
			System.out.println ( " The file " + filename + " is NOT present in the given directory " ) ;
		}

        System.out.println("--------------------------------------------------\n");

		LinkedList <Read> list_reads = new LinkedList <Read>();

		BufferedReader br = new BufferedReader ( new FileReader ( monFichierTexte ));

		String line ;
		while (( line = br.readLine () ) != null ) {
			Read r1 = new Read ( line ) ;
			list_reads.add ( r1 ) ;
		}
		br.close () ;

        Contig cont = new Contig(list_reads.get(0));
		list_reads.remove(0);
		int pos = cont.nextRead(list_reads);

		while ((!list_reads.isEmpty()) && (pos != -1)){
			cont = cont.fusion(list_reads.get(pos));
			list_reads.remove(pos);
			// System.out.println(String.format("Fusion with %s, still %d reads to assemble... work in process", pos, list_reads.size()));
			// System.out.println(list_reads.size());
			pos = cont.nextRead(list_reads);
			// System.out.println(pos);
		}

		String resSE = cont.fastaFormat();



        // Avec erreur à perror = 0
        float perror = 0.0f;

		LinkedList <Read> list_reads_errors = new LinkedList <Read>();

		BufferedReader br_error = new BufferedReader ( new FileReader ( monFichierTexteWerror ));

		String line_error ;
		while (( line_error = br_error.readLine () ) != null ) {
			Read r2 = new Read ( line_error ) ;
			list_reads_errors.add ( r2 ) ;
		}
		br_error.close () ;

		Contig contErr = new Contig(list_reads_errors.get(0));
		list_reads_errors.remove(0);
		int posErr = contErr.nextReadWithError(list_reads_errors, perror);

		// System.out.println(list_reads);

		while ((!list_reads_errors.isEmpty()) && (posErr != -1)){
			contErr = contErr.fusionWerror(list_reads_errors.get(posErr), perror);
			list_reads_errors.remove(posErr);
			// System.out.println(String.format("Fusion with %s, still %d reads to assemble... work in process", posErr, list_reads_errors.size()));
			// System.out.println(list_reads_errors.size());
			posErr = contErr.nextReadWithError(list_reads_errors, perror);

			// System.out.println(pos);
		}
		
        String resAE = contErr.fastaFormat();

        System.out.println("Résultat sans erreur : \n" + resSE);
        System.out.println("--------------------------------------------------\n");
        System.out.println("Résultat avec erreur (perror = 0) : \n" + resAE);
        System.out.println("--------------------------------------------------\n");
        System.out.println("Equivalence des resultats : " + resSE.equals(resAE));
    }
}

