
public class Test {
	
	public static void main(String[] args) {
		long debut = System.currentTimeMillis();
		System.out.println("Average score : "+Kmeans.getScore("input.txt"));
		long duree = System.currentTimeMillis()-debut;
		System.out.println("Computing time : "+duree+" ms");
	}

}













