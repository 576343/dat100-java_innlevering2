package no.hvl.dat100.matriser;

/*
 * vi har definert to utility klasser MatrixUtils og VectorUtils for enkelhetsskyld
 * vi har brukt StringBuilder istedenfor plain String i streng-metoder fordi det
 * kan fungere bedre med minneallokering. 
 */

class MatrixUtils{
	// fin måte å skrive ut matriser på som kan brukes til å debugge matrisefunksjoner
	// en 2x2 matrise med numericStringWidth 6 blir seende slik ut
	// {      1,     2 }
	// {      3,     4 }
	public static String toPrettyString(int[][] matrise, int numericStringWidth) {
		// vi bruker StringBuilder. Den har append og allokerer
		// minne automatisk. sBuilder.append (uttrykk) er litt som s += (uttrykk)
		// men med et bedre interface og med færre kopier vanligvis
		//
		// vi bruker String.format med format "%" + numericStringWidth + "d"
		// slik at ved å endre numericStringWidth kan vi tvinge verdien til
		// å bli formatert med minst numericStringWidth bokstaver. Disse vil
		// i denne koden bli satt til " " automatisk, istedenfor til 0, da det
		// er lettere å lese mellomrom enn 0 for mennesker.
		
		// verdi-formatet vårt blir minst numericStringWidth langt
		String fmt = "%" + numericStringWidth + "d";

		StringBuilder sBuilder = new StringBuilder();		
		for(int[] row : matrise) {
			sBuilder.append("{ ");
			int n = row.length;
			for(int i = 0; i < n; i++) {
				if(i > 0) {
					sBuilder.append(',');
				}
				sBuilder.append(String.format(fmt, row[i]));
			}
			sBuilder.append(" }");
			sBuilder.append('\n');
		}
		return sBuilder.toString();
	}	
	
	// clone a 2d int matrix
	public static int[][] cloneMatrix(int[][] matrise) {
		int[][] result = new int[matrise.length][0];
		int n = matrise.length;
		for(int i=0; i<n; i++) {
			result[i] = matrise[i].clone();
		}
		return result;	
	}
	
	// make a 2d int matrix	of same dimension as input
	public static int[][] cloneMatrixStructure(int[][] matrise) {
		int[][] result = new int[matrise.length][0];
		int n = matrise.length;
		for(int i=0; i<n; i++) {
			result[i] = new int[matrise[i].length];
		}
		return result;	
	}
	
	// multiply two matrices, straight forward -> return (a x c)
	// - matrices must be rectangular or quadratic
	// - matrices must have non-zero dimensions 
	// - a must have same number of columns that b has rows

	public static int[][] multiply(int[][] a, int[][] b) {	
		// should check dimensions ... 
		// (3x3) X (3x4) is NOT ok
		// (4x3) x (3x3) IS ok
		
		// lag tom kopi av matrise
		int[][] result = MatrixUtils.cloneMatrixStructure(a);
		
		// get matrix dimensions, n = rows, m = cols
		int numRows = a.length;		
		int numCols = a[0].length;	
		
		int sumDotProduct;

		// for each row
		for(int r=0; r < numRows; r++) {		
			// for each col, out[r][c] = (row a) dotProduct (column c) 
			for(int c=0; c < numCols; c++) {				
				sumDotProduct = 0;
				
				// compute dotproduct += (a[r][q] * b[q][c])
				// similar to dotproduct = v1 * v2 
				// where 
				// 		v1 = (row vector from a)
				// 		v2 = (column vector from b)
				for(int q=0; q < numCols; q++) {
					sumDotProduct += a[r][q] * b[q][c];
				}

				// result[r][c] = (rad r) dotProduct (kolonne c)
				result[r][c] = sumDotProduct;
			}
		}
		
		return result;
	}	
	/*
	// check if two 2d int matrices are identical
	public static boolean equals(int[][] a, int[][] b) {		
		// get outer array lengths
		int n1 = a.length;
		int n2 = b.length;
		
		// return false if lengths differ
		if(n1 != n2) {
			return false;
		}
		
		for(int i=0; i<n1; i++) {
			// get inner array lengths
			int m1 = a[i].length;
			int m2 = b[i].length;
			
			// return false if lengths differ
			if(m1 != m2) {
				return false;
			}
			
			
			// if(!Arrays.equals(a[i], b[i])){
			// 		return false;
			// }
			for(int j=0; j<m1; j++) {
				if(a[i][j] != b[i][j]) {
					return false;
				}
			}
		}
		
		return true;
	}
	*/	
	
	/*
	// make a 2d int matrix	of same dimension as input
	public static boolean equalMatrixStructure(int[][] a, int[][] b) {
		int n1 = a.length;
		int n2 = b.length;
		
		// return false if lengths differ
		if(n1 != n2) { 
			return false; 
		}
		
		for(int i=0; i<n1; i++) {
			int m1 = a[i].length;
			int m2 = b[i].length;
			
			// return false if lengths differ			
			if(m1 != m2) {
				return false;
			}
		}
		return true;		
	}	
	*/
	
	public static int getNumberOfColumns(int[][] a) {
		return (a.length > 0) ? a[0].length : 0;
	}
	public static int getNumberOfRows(int[][] a) {
		return a.length;
	}
};

class VectorUtils{
	public static String vectorToString(int a[]) {
		return "[" + java.util.Arrays.toString(a) + "]";
	}
	
	// ta dotproduct mellom to vektorer
	public static int dotProduct(int[] a, int[] b) {
		// krav at vektorer må ha lik lengde
		if(a.length != b.length) {
			throw new RuntimeException("vectors must have equal length");
		}
		
		int sum = 0;
		int n = a.length;
		for(int i=0; i<n; i++) {
			sum += (a[i] * b[i]);
		}

		return sum;
	}	
};

public class Matriser {	
	// a)
	public static void skrivUt(int[][] matrise) {
		// bruk tilStreng metoden
		String s = tilStreng(matrise);
		System.out.println(s);
	}

	// b)
	public static String tilStreng(int[][] matrise) {
		// alternative til StringBuilder er String s = ""; s += (uttrykk); 
		StringBuilder sBuilder = new StringBuilder();
		for(int[] row : matrise) {
			for(int value : row) {
				sBuilder.append(value);
				sBuilder.append(' ');
			}
			sBuilder.append('\n');
		}
		return sBuilder.toString();
	}


			
		
	// c)
	public static int[][] skaler(int tall, int[][] matrise) {
		// allocate a new matrix for result variable
		int[][] result = MatrixUtils.cloneMatrix(matrise);
		
		int n = matrise.length;
		for(int i=0; i<n; i++) {
			int m = matrise[i].length;
			for(int j=0; j<m; j++) {
				result[i][j] *= tall;
			}
		}
		
		return result;
	}

	// d)
	public static boolean erLik(int[][] a, int[][] b) {
		// return MatrixUtils.equals(a, b);
		
		// get outer array lengths
		int n1 = a.length;
		int n2 = b.length;
		
		// return false if lengths differ
		if(n1 != n2) {
			return false;
		}
		
		for(int i=0; i<n1; i++) {
			// get inner array lengths
			int m1 = a[i].length;
			int m2 = b[i].length;
			
			// return false if lengths differ
			if(m1 != m2) {
				return false;
			}
			
			for(int j=0; j<m1; j++) {
				if(a[i][j] != b[i][j]) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	// e) 
	// oppgave: mirror matrix along diagonal from topleft to bottomrighjt
	public static int[][] speile(int[][] matrise) {
		int[][] result = MatrixUtils.cloneMatrixStructure(matrise);
		
		// fungerer bare for kvadratisk matrise så vi kan gjenbruke
		// n i begge loops, en ikke kvadratisk matrise har ingen klart
		// definert diagonal ved en-bort-en-ned tankegang
		int n = matrise.length;
		for(int i=0; i<n; i++) {
			for(int j=0; j<n; j++) {
				result[i][j] = matrise[j][i];
			}
		}
		return result;
	}

	// f)
	// should check dimensions ... 
	// (3x3) X (3x4) is NOT ok
	// (4x3) x (3x3) IS ok	
	// multiply two matrices, straight forward -> return (a x c)
	// - matrices must be rectangular or quadratic
	// - matrices must have non-zero dimensions 
	// - a must have same number of columns that b has rows	
	public static int[][] multipliser(int[][] a, int[][] b) {	
		// lag tom kopi av matrise
		int[][] result = MatrixUtils.cloneMatrixStructure(a);
		
		// get matrix dimensions, n = rows, m = cols
		int numRows = a.length;		
		int numCols = a[0].length;	
		
		int sumDotProduct;

		// for each row
		for(int r=0; r < numRows; r++) {		
			// for each col, out[r][c] = (row a) dotProduct (column c) 
			for(int c=0; c < numCols; c++) {				
				// reset accumulator and compute dot product
				sumDotProduct = 0;
				for(int q=0; q < numCols; q++) {
					sumDotProduct += a[r][q] * b[q][c];
				}

				// result[r][c] = (row r) dotProduct (col c)
				result[r][c] = sumDotProduct;
			}
		}
		
		return result;
	}
}
