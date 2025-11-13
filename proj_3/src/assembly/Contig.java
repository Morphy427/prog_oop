package assembly;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class Contig implements Sequence{

	private int len ;
	private String contig ;
	private int nb_fusions ;
	

	public Contig() {
		contig = "azertyuiopqsdfghjklmwxcvbnazertyuiopdfghjklmqsdfghjklmllllkjhgfdsqsdfgaaaaaaaaaaacccccccccccccccccccccctttttttttttttttttttddddddddddddddddddhjklm"; 
		len = contig.length();
		nb_fusions = 0;
	}

	public Contig(String s) {
		contig = s; 
		len = contig.length();
		nb_fusions = 0;
	}

	public Contig(String s, int nb_fus) {
		contig = s; 
		len = contig.length();
		nb_fusions = nb_fus;
	}
	
	public Contig(Read r) {
		contig = r.getSeq(); 
		len = contig.length();
		nb_fusions = 0;
	}

	public static void main ( String[] args ) throws IOException {

		// without error

		System.out.println ( System.getProperty ( "user.dir" ) ) ;

		String filename = "/proj_3/src/assembly/my_reads.txt" ;
		File monFichierTexte = new File ( System.getProperty ( "user.dir" ) + filename ) ;

		// Simple test to verify that the file exists.
		if ( monFichierTexte.exists() ) {
			System.out.println ( " The file " + filename + " is present in the given directory \n " ) ;
		} else {
			System.out.println ( " The file " + filename + " is NOT present in the given directory " ) ;
		}

		LinkedList <Read> list_reads = new LinkedList <Read>();

		BufferedReader br = new BufferedReader ( new FileReader ( monFichierTexte ));

		String line ;
		while (( line = br.readLine () ) != null ) {
			Read r1 = new Read ( line ) ;
			list_reads.add ( r1 ) ;
		}
		br.close () ;

		// todo
		Contig cont = new Contig(list_reads.get(0));
		list_reads.remove(0);
		int pos = cont.nextRead(list_reads);

		// System.out.println(list_reads);

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

		


		// Contig cont2 = new Contig("CCACACCACACCCACACACCCACACACCACACCACACACCACACCACACCCACACACACACACAGCCCTAATCTAACCCTGGCCAACCTGTCTCTCAACTTACCCTCCATTACCCTGCCTGCCAACCTGTCTCTCAACTTACCCTCCATTACCCTGCCTCCACTCGTTACCCTGTCCCAT");
		// System.out.println(cont2.fastaFormat());


		// Contig cont3 = new Contig("azertyuiop");
		// Read r1 = new Read("yuiepqsd");
		// // System.out.println(Read.countError(cont3.getSeq(), r1.getSeq()));
		// System.out.println(cont3.bestOverlapWithError(r1, 0.5f));
		// Contig cont4 = cont3.fusionWerror(r1, 0.5f);
		// System.out.println(cont4);

		Read r1t = new Read("azertyuiop");
		Read r2t = new Read("yuiopqsdf");
		Contig c = new Contig(r1t);
		System.out.println(c.bestOverlapWithError(r2t, 0.0f)); // → 5
		System.out.println(c.fusionWerror(r2t, 0.0f));         // → azertyuiopqsdf


		// with error

		System.out.println ( System.getProperty ( "user.dir" ) ) ;

		String filenameWerror = "/proj_3/src/assembly/my_reads_with_sequencing_errors.txt" ;
		File monFichierTexteWerror = new File ( System.getProperty ( "user.dir" ) + filenameWerror ) ;

		float perror_test = 0.1f;
		// Simple test to verify that the file exists.
		if ( monFichierTexteWerror.exists() ) {
			System.out.println ( " The file " + filenameWerror + " is present in the given directory \n " ) ;
		} else {
			System.out.println ( " The file " + filenameWerror + " is NOT present in the given directory " ) ;
		}

		LinkedList <Read> list_reads_errors = new LinkedList <Read>();

		BufferedReader br_error = new BufferedReader ( new FileReader ( monFichierTexteWerror ));

		String line_error ;
		while (( line_error = br_error.readLine () ) != null ) {
			Read r2 = new Read ( line_error ) ;
			list_reads_errors.add ( r2 ) ;
		}
		br_error.close () ;

		//todo
		Contig contErr = new Contig(list_reads_errors.get(0));
		list_reads_errors.remove(0);
		int posErr = contErr.nextReadWithError(list_reads_errors, perror_test);

		// System.out.println(list_reads);

		while ((!list_reads_errors.isEmpty()) && (posErr != -1)){
			contErr = contErr.fusionWerror(list_reads_errors.get(posErr), perror_test);
			list_reads_errors.remove(posErr);
			System.out.println(String.format("Fusion with %s, still %d reads to assemble... work in process", posErr, list_reads_errors.size()));
			// System.out.println(list_reads_errors.size());
			posErr = contErr.nextReadWithError(list_reads_errors, perror_test);

			// System.out.println(pos);
		}

		System.out.println(String.format("Contig obtained with %s reads", contErr.getNb_fusions()));
		System.out.println(contErr.fastaFormat());

	}

	@Override public String toString(){
		return ("sequence : " + getSeq() + "\nlength : " + getLength() + "\nnumber of fusion : " + getNb_fusions() + "\n");
	}
	
	@Override public int getLength(){
		return this.len;
	}

	@Override public String getSeq(){
		return this.contig;
	}

	public int getNb_fusions() {
		return this.nb_fusions;
	}

	public void setLen(int len) {
		this.len = len;
	}

	public void setContig(String contig) {
		this.contig = contig;
	}

	public void setNb_fusions(int nb_fusions) {
		this.nb_fusions = nb_fusions;
	}
	
	public String fastaFormat(){
		String seq = this.getSeq();
		String seqFasta = "";
		for (int i = 0 ; i < this.getLength() ; i++){
			if (i%60 == 0){
				seqFasta += "\n";
			}
			seqFasta += seq.charAt(i);
		}
		return seqFasta;
	}

	public int bestOverlap(Read r){
		int maxCount = 0;
		String seqRead = r.getSeq();
		String seqContig = this.getSeq();

		for(int i = 0 ; i < this.getLength() ; i++){
			if (seqContig.charAt(i) == seqRead.charAt(0)){
				int count = 0;
				while (seqContig.charAt(i + count) == seqRead.charAt(count) && i+count < this.getLength()-1 && count < r.getLength()){
					count ++;
				}
				maxCount = Math.max(maxCount, count+1);
			}
		}
		return maxCount;
	}

	public int nextRead(LinkedList<Read> l){
		int pos = 0;
		int bestScore = 0;
		int bestScorePos = 0;
		for(Read read : l){
			if (bestOverlap(read) > bestScore){
				bestScore = bestOverlap(read);
				bestScorePos = pos;
			}
			pos++;
		}
		if (bestScore < 8){
			return -1;
		}else{
			return bestScorePos;
		}
	}

	public Contig fusion(Read r){
		int numToCut = this.bestOverlap(r);
		String seqToAdd = r.getSeq().substring(numToCut);
		String newSeq = this.getSeq().concat(seqToAdd);
		int nb_fus = this.getNb_fusions() + 1;
		return new Contig(newSeq, nb_fus);
	}

	public Contig fusionWerror(Read r, float perror){
		int numToCut = this.bestOverlapWithError(r, perror);
		if (numToCut == 0 ){
			return this;
		}
		String seqToAdd = r.getSeq().substring(numToCut);
		String newSeq = this.getSeq().concat(seqToAdd);
		int nb_fus = this.getNb_fusions() + 1;
		return new Contig(newSeq, nb_fus);
	}


	// public int bestOverlapWithError(Read r, float perror){
	// 	String seqRead = r.getSeq();
	// 	String seqContig = this.getSeq();

	// 	for(int i = r.getLength()-1 ; i >= 0 ; i--){
	// 		String subcontig = seqContig.substring(this.getLength()-i);
	// 		String subread = seqRead.substring(0, i);
	// 		if (r.nearlyEquals(subcontig, subread, perror)){
	// 			return i-1;
	// 		}
	// 	}
	// 	return -1;
	// }

	// public int bestOverlapWithError(Read r,float perror){
    //     String seqRead = r.getSeq();
    //     String seqContig = this.getSeq();
    //     int contigLen = seqContig.length(); 
    //     int readLen = seqRead.length();

    //     for(int i = 0 ; i < readLen ; i++){
	// 		String subcontig = seqContig.substring((contigLen - readLen) + i);
	// 		// System.out.println("subcontig : " + subcontig);
	// 		String subread = seqRead.substring(i);
	// 		// System.out.println("subread   : " + subread);
	// 		if (Read.nearlyEquals(subcontig, subread, perror)){
	// 			// System.out.println("nearly equals : " + subread.length() );
	// 			return subread.length()-1;
	// 		}
    //     }
    //     return 0;
    // }

	public int bestOverlapWithError(Read r, float perror) {
		String seqRead = r.getSeq();
		String seqContig = this.getSeq();
		int contigLen = seqContig.length();
		int readLen = seqRead.length();
		int maxOverlap = Math.min(contigLen, readLen);

		for (int i = maxOverlap; i > 0; i--) {
			String subContig = seqContig.substring(contigLen - i);
			String subRead = seqRead.substring(0, i);
			if (Read.nearlyEquals(subContig, subRead, perror)) {
				return i; // longueur du chevauchement
			}
		}
		return 0;
}



	public int nextReadWithError(LinkedList<Read> l, float perror){
		int pos = 0;
		int bestScore = 0;
		int bestScorePos = 0;
		for(Read read : l){
			// System.out.println(bestOverlapWithError(read, perror));
			if (bestOverlapWithError(read, perror) > bestScore){
				// System.out.println("overlap pos : " + pos);
				bestScore = bestOverlapWithError(read, perror);
				bestScorePos = pos;
			}
			pos++;
		}
		// System.out.println("best score = " + bestScore + "at position : " + bestScorePos);
		if (bestScore < 8){
			// System.out.println("best score pos : " + bestScorePos);
			// System.out.println("best score : " + bestScore);
			return -1;
		}else{
			// System.out.println("best score pos : " + bestScorePos);
			// System.out.println("best score : " + bestScore);
			return bestScorePos;
		}
	}

	public String greddyAlgo(LinkedList<Read> l){
        int index = nextRead(l);
        while (index != -1 && !l.isEmpty()){
            fusion(l.get(index));
            l.remove(index);
            System.out.println("Fusion with " + index +", still " + l.size() + " reads to assemble... WIP");
            index = nextRead(l);
        }
        return getSeq();
    }
	
}
