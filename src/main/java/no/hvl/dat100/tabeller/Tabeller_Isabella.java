package no.hvl.dat100.tabeller;

public class Tabeller_Isabella {

	// a)
	public static void skrivUt(int[] tabell) {
		System.out.print("[");
		
		for(int i = 0; i < tabell.length; i++) {
			System.out.print(tabell[i]);
			
			if(i < tabell.length - 1) {
				System.out.print(", ");
			}
		}
		
		System.out.println("]");
	}

	// b)
	public static String tilStreng(int[] tabell) {
		String result = "[";
		
		for(int i = 0; i < tabell.length; i++) {
			result += tabell[i];
			
			if(i < tabell.length - 1) {
				result += ",";
			}
		}
		
		result += "]";
		return result;
	}

	// c)
	public static int summer(int[] tabell) {
		int sum = 0;
		for(int i = 0; i < tabell.length; i++) {
			sum += tabell[i];
		}
		return sum;
	}

	// d)
	public static boolean finnesTall(int[] tabell, int tall) {
		for(int i = 0; i < tabell.length; i++) {
			if (tall == tabell[i]) {
				return true;
			}
		}
		return false;
	}

	// e)
	public static int posisjonTall(int[] tabell, int tall) {
		for(int i = 0; i < tabell.length; i++) {
			if(tall == tabell[i]) {
				return i;
			}
		}
		return -1;
	}

	// f)
	public static int[] reverser(int[] tabell) {
		int[] reverse = new int[tabell.length];
		for(int i = 0; i < tabell.length; i++) {
			reverse[(reverse.length - 1) - i] = tabell[i];
		}
		return reverse;
	}

	// g)
	public static boolean erSortert(int[] tabell) {
		for(int i = 1; i < tabell.length; i++) {
			if (tabell[i] < tabell[i-1]) {
				return false;
			}
		}
		return true;
	}

	// h)
	public static int[] settSammen(int[] tabell1, int[] tabell2) {
		int[] tabell = new int[tabell1.length + tabell2.length];
		for(int i = 0; i < tabell1.length; i++) {
			tabell[i] = tabell1[i];
		}
		for(int i = 0; i < tabell2.length; i++) {
			tabell[tabell1.length + i] = tabell2[i];
		}
		return tabell;
	}
}
