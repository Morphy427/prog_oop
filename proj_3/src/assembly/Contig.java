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

	/**
	 * Default constructor
	 */
	public Contig() {
		contig = "azertyuiopqsdfghjklmwxcvbnazertyuiopdfghjklmqsdfghjklmllllkjhgfdsqsdfgaaaaaaaaaaacccccccccccccccccccccctttttttttttttttttttddddddddddddddddddhjklm"; 
		len = contig.length();
		nb_fusions = 0;
	}
	/**
	 * Constructor
	 * @param s String representing the contig sequence
	 */
	public Contig(String s) {
		contig = s; 
		len = contig.length();
		nb_fusions = 0;
	}
	/**
	 * Constructor
	 * @param s String representing the contig sequence
	 * @param nb_fus int representing the number of fusions done to obtain this contig
	 */
	public Contig(String s, int nb_fus) {
		contig = s; 
		len = contig.length();
		nb_fusions = nb_fus;
	}
	/**
	 * Constructor
	 * @param r Read used to create the contig
	 */
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
	/**
	 * Formats the contig sequence in FASTA format (60 characters per line)
	 * @return String representing the contig in FASTA format
	 */
	public String fastaFormat(){
		String seq = this.getSeq(); 
		String seqFasta = "";
		// for each character in the sequence, add a line break every 60 characters
		for (int i = 0 ; i < this.getLength() ; i++){ 
			if (i%60 == 0){
				seqFasta += "\n";
			}
			seqFasta += seq.charAt(i);
		}
		return seqFasta;
	}

	/**
	 * Finds the best overlap between the contig and a read
	 * @param r Read to compare with the contig
	 * @return int representing the length of the best overlap
	 */
	public int bestOverlap(Read r){
		int maxCount = 0;
		String seqRead = r.getSeq();
		String seqContig = this.getSeq();
		// iterate through the contig sequence to find the best overlap with the current read
		for(int i = 0 ; i < this.getLength() ; i++){
			if (seqContig.charAt(i) == seqRead.charAt(0)){  // if the first character of the read matches the current character of the contig
				int count = 0;
				// count the number of matching characters
				while (seqContig.charAt(i + count) == seqRead.charAt(count) && i+count < this.getLength()-1 && count < r.getLength()){
					count ++;
				}
				maxCount = Math.max(maxCount, count+1); // update the maximum overlap length found
			}
		}
		return maxCount;
	}
	/**
	 * Finds the next read in a list that has the best overlap with the contig
	 * @param l LinkedList of reads to search through
	 * @return int representing the position of the read with the best overlap, or -1 if no suitable read is found
	 */
	public int nextRead(LinkedList<Read> l){
		int pos = 0;
		int bestScore = 0;
		int bestScorePos = 0;
		// iterate through the list of reads to find the one with the best overlap
		for(Read read : l){
			if (bestOverlap(read) > bestScore){
				bestScore = bestOverlap(read);
				bestScorePos = pos;
			}
			pos++;
		}
		if (bestScore < 8){ // if the best overlap is less than 8, return -1
			return -1;
		}else{
			return bestScorePos;
		}
	}

	/**
	 * Fuses the contig with a read based on the best overlap
	 * @param r Read to fuse with the contig
	 * @return new Contig resulting from the fusion
	 */
	public Contig fusion(Read r){
		int numToCut = this.bestOverlap(r); // get the length of the best overlap
		String seqToAdd = r.getSeq().substring(numToCut); // get the part of the read that is not overlapping
		String newSeq = this.getSeq().concat(seqToAdd); // concatenate the contig sequence with the non-overlapping part of the read
		int nb_fus = this.getNb_fusions() + 1;
		return new Contig(newSeq, nb_fus);
	}

	/**
	 * Fuses the contig with a read based on the best overlap, allowing for sequencing errors
	 * @param r Read to fuse with the contig
	 * @param perror float representing the allowed error rate
	 * @return new Contig resulting from the fusion
	 */
	public Contig fusionWerror(Read r, float perror){
		int numToCut = this.bestOverlapWithError(r, perror); // get the length of the best overlap
		if (numToCut == 0 ){ // no overlap found
			return this;
		}
		String seqToAdd = r.getSeq().substring(numToCut); // get the part of the read that is not overlapping
		String newSeq = this.getSeq().concat(seqToAdd); // concatenate the contig sequence with the non-overlapping part of the read
		int nb_fus = this.getNb_fusions() + 1;
		return new Contig(newSeq, nb_fus);
	}

	/**
	 * Finds the best overlap between the contig and a read, allowing for sequencing errors
	 * @param r Read to compare with the contig
	 * @param perror float representing the allowed error rate
	 * @return int representing the length of the best overlap
	 */

	public int bestOverlapWithError(Read r, float perror) {
		String seqRead = r.getSeq();
		String seqContig = this.getSeq();
		int contigLen = seqContig.length();
		int readLen = seqRead.length();
		int maxOverlap = Math.min(contigLen, readLen);

		// Iterate from the maximum possible overlap length down to 1
		for (int i = maxOverlap; i > 0; i--) {
			String subContig = seqContig.substring(contigLen - i); // Suffix of the contig
			String subRead = seqRead.substring(0, i); // Prefix of the read

			// Check if the suffix of the contig nearly equals the prefix of the read within the allowed error rate
			if (Read.nearlyEquals(subContig, subRead, perror)) {
				return i; // Return the length of the best overlap found
			}
		}
		return 0; // No suitable overlap found
}


	/**
	 * Finds the next read in a list that has the best overlap with the contig, allowing for sequencing errors
	 * @param l LinkedList of reads to search through
	 * @param perror float representing the allowed error rate
	 * @return int representing the position of the read with the best overlap, or -1 if no suitable read is found
	 */
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

}
