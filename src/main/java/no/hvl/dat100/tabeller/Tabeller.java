package no.hvl.dat100.tabeller;

public class Tabeller {

	// a)
	public static void skrivUt(int[] tabell) {
		//System.out.println("skrivUt(...)");
		int n = tabell.length;
		for(int i=0; i<n; i++) {
			if(i > 0) {
				System.out.print(",");
			}
			System.out.print(tabell[i]);
		}
		System.out.println("");
	}

	// b)
	public static String tilStreng(int[] tabell) {
		StringBuilder sBuilder = new StringBuilder();
		
		sBuilder.append("[");
		int n = tabell.length;
		for(int i=0; i<n; i++) {
			if(i > 0) {
				sBuilder.append(",");
			}			
			sBuilder.append(tabell[i]);
		}
		sBuilder.append("]");
		
		return sBuilder.toString();

		
		/*
		// kode dersom en ikke vil bruke StringBuilder. Jeg lot parantes
		// stå igjen rundt uttrykk sånn at en lett kan se at det var 
		// kall til StringBuilder objektet der.		
		String s = "";
		
		s += ("[");
		int n = tabell.length;
		for(int i=0; i<n; i++) {
			if(i > 0) {
				s += (",");
			}			
			s += (tabell[i]);
		}
		s += ("]");
		
		return s;
		*/
	}

	// c)
	public static int summer(int[] tabell) {
		// vi kunne sjekket om n var 0 og returnet tidlig, eller hvis n > 0: 
		// satt sum til tabell[0] og loopet fra 1 istedenfor 0 men for 
		// enkelhets skyld så kjører vi for-loop fra 0.
		int n = tabell.length;
		int sum = 0;
		for(int i=0; i<n; i++) {
			sum += tabell[i];
		}
		return sum;
	}

	// d)
	public static boolean finnesTall(int[] tabell, int tall) {	
		int n = tabell.length;
		for(int i=0; i<n; i++) {
			if(tabell[i] == tall) {
				return true;
			}
		}
		return false;
	}

	// e)
	public static int posisjonTall(int[] tabell, int tall) {
		int n = tabell.length;
		for(int i=0; i<n; i++) {
			if(tabell[i] == tall) {
				return i;
			}
		}
		return -1;
	}

	// f)
	public static int[] reverser(int[] tabell) {	
		int n = tabell.length;		
		
		// kopier tabell, vi kunne brukt tabell.clone();
		int[] result = new int[n];
		for(int i=0; i<n; i++) {
			result[i] = tabell[i];
		}
		
		// forslag Bjarte		
		int halfLength = n / 2;	// int-division
		for(int i=0; i<halfLength; i++) {
			// swap [i] og [(n - 1) - i]
			int tmp = result[i];
			result[i] = result[n - 1 - i];
			result[n - 1 - i] = tmp;
		}
		return result;
	}

	// g)
	public static boolean erSortert(int[] tabell) {
		// vi gjør en antagelse og tenker at stigende rekkefølge ikke betyr
		// større enn men større eller lik da [1, 2, 2, 3] også bør detekteres
		// som stigende. i en virkelig situasjon ville en sjekket hvilken som var
		// korrekt for å unngå trøbbel med andre caller som kunne hatt forventning
		// om at [1, 2, 2, 3] ikke var stigende. vi forstår at lærer kan ha ment strengt
		// stigende men velger likevel >= slik at metoden detekterer korrekt om et array
		// er sortert i (eller har) stigende rekkefølge. 
		// 
		// enhetstest vil ikke bry seg, vi sjekket med dataene
		
		int n = tabell.length;
		for(int i=1; i<n; i++) {
			if(!(tabell[i] >= tabell[i - 1])) {
				return false;
			}
		}
		return true;
	}

	// h)
	public static int[] settSammen(int[] tabell1, int[] tabell2) {
		int n1 = tabell1.length;
		int n2 = tabell2.length;
		
		int[] result = new int[n1 + n2];
		int index = 0;
		
		for(int i=0; i<n1; i++) {
			result[index++] = tabell1[i];
		}
		for(int i=0; i<n2; i++) {
			result[index++] = tabell2[i];
		}
		
		return result;
	}
}
