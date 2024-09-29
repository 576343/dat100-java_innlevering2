package no.hvl.dat100.matriser;

public class Matriser_Isabella {

	// a)
	public static void skrivUt(int[][] matrise) {
		for(int[] tabell : matrise) {
			System.out.print("{");
			for(int verdi : tabell) {
				System.out.print(" " + verdi + " ");
			}
			System.out.println("}");
		}
		System.out.println();
	}

	// b)
	public static String tilStreng(int[][] matrise) {
		String resultat = "";
		for(int[] tabell : matrise) {
			for(int verdi : tabell) {
				resultat += verdi + " ";
			}
			resultat += "\n";
		}
		return resultat;
	}

	// c)
	public static int[][] skaler(int tall, int[][] matrise) {
		int[][] skalert = new int[matrise.length][matrise[0].length];
		for(int i = 0; i < matrise.length; i++) {
			for(int j = 0; j < matrise[0].length; j++) {
				skalert[i][j] = matrise[i][j] * tall;
			}
		}
		return skalert;
	}

	// d)
	public static boolean erLik(int[][] a, int[][] b) {
		if(a.length != b.length) {
			return false;
		}
		
		if(a[0].length != b[0].length) {
			return false;
		}
		
		for(int i = 0; i < a.length; i++) {
			for(int j = 0; j < a[0].length; j++) {
				if(a[i][j] != b[i][j]) {
					return false;
				}
			}
		}
		return true;
	}
	
	// e)
	public static int[][] speile(int[][] matrise) {

		// TODO

		throw new UnsupportedOperationException("Metoden speile ikke implementert");
	
	}

	// f)
	public static int[][] multipliser(int[][] a, int[][] b) {

		// TODO
		throw new UnsupportedOperationException("Metoden multipliser ikke implementert");
	
	}
}
