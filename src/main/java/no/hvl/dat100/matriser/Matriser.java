package no.hvl.dat100.matriser;

/*
 * vi har definert to utility klasser MatrixUtils og VectorUtils for enkelhetsskyld
 * vi har brukt StringBuilder istedenfor plain String i streng-metoder fordi det
 * kan fungere bedre med minneallokering. 
 */

class MatrixUtils2{
	// clone a 2d int matrix
	public static int[][] cloneMatrix(int[][] mat) {
		if(mat == null) { throw new IllegalArgumentException("Matrix must be non-null"); }
		
		int[][] result = new int[mat.length][0];
		int n = mat.length;
		for(int i=0; i<n; i++) {
			result[i] = mat[i].clone();
		}
		return result;	
	}
	
	// make a 2d int matrix	of same dimension as input
	// allows different sized rows
	public static int[][] cloneMatrixStructure(int[][] mat) {	
		if(mat == null) { throw new IllegalArgumentException("Matrix must be non-null"); }
		
		int n = mat.length;
		if(n == 0) {
			throw new IllegalArgumentException("Matrix row count can not be zero");
		}
		
		if(mat[0] == null) {
			throw new IllegalArgumentException("Matrix row can not be null");
		}
			
		return new int[n][mat[0].length];		
	}
}

/*
class VectorUtils{
	public static String vectorToString(int a[]) {
		return "[" + java.util.Arrays.toString(a) + "]";
	}
	
	// ta dotproduct mellom to vektorer
	public static int dotProduct(int[] a, int[] b) {
		// krav at vektorer må ha lik lengde
		if(a.length != b.length) {
			throw new IllegalArgumentException("vectors must have equal length");
		}
		
		int sum = 0;
		int n = a.length;
		for(int i=0; i<n; i++) {
			sum += (a[i] * b[i]);
		}

		return sum;
	}	
};
*/

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
		int[][] result = MatrixUtils2.cloneMatrix(matrise);
		
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
		// retirm java.util.Arrays.deepEquals(a, b);
		
		// get outer array lengths
		int n1 = a.length;
		int n2 = b.length;
		
		// return false if lengths differ
		if(n1 != n2) {
			return false;
		}
		
		for(int i=0; i<n1; i++) {
			if(((a[i] == null) ^ (b[i] == null)) || ((a[i] == null) ^ (b[i] == null))) {
				return false;
			}
			
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
		int[][] result = MatrixUtils2.cloneMatrixStructure(matrise);
		
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
		int[][] result = MatrixUtils2.cloneMatrixStructure(a);
		
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
