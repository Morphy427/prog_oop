package assembly;

public class Read implements Sequence{

	private int len ;
	private String seq ;
	
	public static void main(String[] args) {
		Read r1 = new Read("azertyuiop");
		Read r2 = new Read("ertyuiopuui");
		Read r3 = new Read("azeryyuiop");
		System.out.println(r1);
		System.out.println(nearlyEquals(r1.getSeq(), r2.getSeq(), 0.0f));
		System.out.println(nearlyEquals(r1.getSeq(), r3.getSeq(), 0.0f));
	//	System.out.println(r1.bestOverlap(r2));
	}
	
	public Read() {
		seq = "azertyuiopqsdfghjklmwxcvbnazertyuiopdfghjklmqsdfghjklmllllkjhgfdsqsdfgaaaaaaaaaaacccccccccccccccccccccctttttttttttttttttttddddddddddddddddddhjklm"; 
		len = seq.length();
	}

	public Read(String s) {
		seq = s; 
		len = seq.length();
	}

	@Override public String toString(){
		return ("sequence : " + getSeq() + "\nlength : " + getLength());
	}
	
	@Override public int getLength(){
		return len;
	}

	@Override public String getSeq(){
		return seq;
	}

	public void setLen(int len) {
		this.len = len;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}
	
	// public boolean nearlyEquals(String s1, String s2){

	// 	return (s1.compareTo(s2) <= 1);
	// }

	// public boolean nearlyEquals(String s1, String s2, float perror){
	// 	// System.out.println(s1.compareTo(s2)/s1.length());
	// 	return (s1.compareTo(s2)/s1.length() <= perror);
	// }

	public static int countError(String s1, String s2){
		int minLen = Math.min(s1.length(),s2.length());
        int error = 0;
        for (int i = 0; i < minLen; i++) {
            if (s1.charAt(i) != s2.charAt(i)){error ++;}
        }
        return error;
    }

	public static boolean nearlyEquals(String s1, String s2){
        int minLen = Math.min(s1.length(),s2.length());
        int error = 0;
        for (int i = 0; i < minLen; i++) {
            if (s1.charAt(i) != s2.charAt(i)){error ++;}
        }
        return (error <= 1);
    }

    public static boolean nearlyEquals(String s1, String s2, float perror){
		float minLen = Math.min(s1.length(), s2.length());
        int error = 0;
        for (int i = 0; i < minLen; i++) {
            if (s1.charAt(i) != s2.charAt(i)){error ++;}
			// System.out.println(error);
        }
        return (((float)error/minLen) <= perror);
    }
	
}
