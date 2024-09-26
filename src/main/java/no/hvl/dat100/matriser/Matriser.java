package no.hvl.dat100.matriser;

/*
 * vi har definert to utility klasser MatrixUtils og VectorUtils for enkelhetsskyld
 * vi har brukt StringBuilder istedenfor plain String i streng-metoder fordi det
 * kan fungere bedre med minneallokering. 
 */

class MatrixUtils{
	// hide constructor
	private MatrixUtils() {}

	
	// define errors
	static final String ERR_MATRIX_MUST_HAVE_AT_LEAST_ONE_ROW = "Matrix must have at least one row";
	
	// fin måte å skrive ut matriser på som kan brukes til å debugge matrisefunksjoner
	// en 2x2 matrise med numericStringWidth 6 blir seende slik ut
	// {      1,     2 }
	// {      3,     4 }
	public static String toPrettyString(int[][] mat, int numericStringWidth) {
		// vi bruker StringBuilder. Den har append og allokerer
		// minne automatisk. sBuilder.append (uttrykk) er litt som s += (uttrykk)
		// men med et bedre interface og med færre kopier vanligvis
		//
		// vi bruker String.format med format "%" + numericStringWidth + "d"
		// slik at ved å endre numericStringWidth kan vi tvinge verdien til
		// å bli formatert med minst numericStringWidth bokstaver. Disse vil
		// i denne koden bli satt til " " automatisk, istedenfor til 0, da det
		// er lettere å lese mellomrom enn 0 for mennesker.
		
		validateNonNullMatrix(mat);
		
		// verdi-formatet vårt blir minst numericStringWidth langt
		String fmt = "%" + numericStringWidth + "d";

		StringBuilder sBuilder = new StringBuilder();		
		for(int[] row : mat) {
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
	
	public static void validateNonNullMatrix(Object ref) {
		if(ref == null) {
			throw new IllegalArgumentException("Matrix must be non-null");
		}
	}	
	
	public static void validateNonNullMatrixRow(int[] row) {
		if(row == null) {
			throw new IllegalArgumentException("Matrix row must be non-null");
		}
	}
	
	// compute area of matrix (rows * columns)
	public static int getMatrixArea(int[][] mat) {
		validateNonNullMatrix(mat);
		validateNonZeroRowMatrix(mat);	
		validateNonNullMatrixRow(mat[0]);
		
		return mat.length * mat[0].length;
	}
	
	// return true only if all rows of matrix have same length
	// matrix must have non-zero number of rows and columns
	public static boolean isRectangularMatrix(int[][] mat) {
		if(mat == null) { throw new IllegalArgumentException("Matrix must be non-null"); }
		
		int n = mat.length;
		if(n == 0) {
			throw new IllegalArgumentException(ERR_MATRIX_MUST_HAVE_AT_LEAST_ONE_ROW);
		}
		
		if(mat[0] == null || mat[0].length == 0) {
			throw new IllegalArgumentException("Matrix must have at least one column");
		}
		
		int m = mat[0].length;		
		for(int i=0; i<n; i++) {
			if(mat[i].length != m) {
				return false;
			}
		}
		
		return true;
	}
	
	// return true only if matrix has non-zero area and is quadratic
	public static boolean isQuadraticMatrix(int[][] mat) {
		return (isRectangularMatrix(mat) && (mat[0].length != mat.length));
	}	

	// validate 
	public static void validateNonZeroRowMatrix(int[][] mat) {
		if(mat == null) { throw new IllegalArgumentException("Matrix must be non-null"); }
		
		int n = mat.length;
		if(n == 0) {
			throw new IllegalArgumentException(ERR_MATRIX_MUST_HAVE_AT_LEAST_ONE_ROW);
		}		
	}
	
	public static void validateRectangularMatrix(int[][] mat) {
		if(!isRectangularMatrix(mat)) {
			throw new IllegalArgumentException("Matrix must be rectangular and have non-zero area");
		}
	}
	
	public static void validateQuadraticMatrix(int[][] mat) {
		if(!isQuadraticMatrix(mat)) {
			throw new IllegalArgumentException("Matrix must be quadratic and have non-zero area");
		}
	}		
	
	// return number of rows in a matrix
	public static int getNumberOfRows(int[][] mat) {
		if(mat == null) { throw new IllegalArgumentException("Matrix must be non-null"); }
		
		return mat.length;
	}
	
	// return number of columns on first row of matrix
	// user must know or make sure all columns are of same size
	public static int getNumberOfColumns(int[][] mat) {
		//validateNonZeroRowMatrix(mat);
		
		return (getNumberOfRows(mat) > 0) ? mat[0].length : 0;
	}

	
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
			throw new IllegalArgumentException(ERR_MATRIX_MUST_HAVE_AT_LEAST_ONE_ROW);
		}
		
		if(mat[0] == null) {
			throw new IllegalArgumentException("Matrix row can not be null");
		}
			
		return new int[n][mat[0].length];		
	}
	
	
	// multiply two matrices, straight forward -> return (a x c)
	// - matrices must be rectangular or quadratic
	// - matrices must have non-zero dimensions 
	// - a must have same number of columns that b has rows
	public static int[][] multiply(int[][] a, int[][] b) {
		if(a == null || b == null) {
			throw new IllegalArgumentException("Matrix must be non-null");
		}
		
		int aNumRows = a.length;
		int bNumRows = b.length;
		
		// check that both matrices have non-zero row count
		if(aNumRows == 0 || bNumRows == 0) {
			throw new IllegalArgumentException(ERR_MATRIX_MUST_HAVE_AT_LEAST_ONE_ROW);
		}
		
		// get matrix dimensions, n = rows, m = cols
		int aNumCols = (aNumRows > 0) ? a[0].length : 0;			

		// check that number of cols in a matches number of rows in b
		if(bNumRows != aNumCols) {
			throw new IllegalArgumentException("Second matrix row count must equal first matrix column count");
		}
		
		// declare dot product accumulator
		int sumDotProduct;		
		
		// make result matrix equal in structure to a
		int[][] result = MatrixUtils.cloneMatrixStructure(a);
		

		// for each row
		for(int r=0; r < aNumRows; r++) {		
			// for each col, out[r][c] = (row a) dotProduct (column c) 
			for(int c=0; c < aNumCols; c++) {
				sumDotProduct = 0;
				
				// compute dotproduct += (a[r][q] * b[q][c])
				// similar to dotproduct = v1 * v2 
				// where 
				// 		v1 = (row vector from a)
				// 		v2 = (column vector from b)
				for(int q=0; q < aNumCols; q++) {
					sumDotProduct += a[r][q] * b[q][c];
				}

				// result[r][c] = (rad r) dotProduct (kolonne c)
				result[r][c] = sumDotProduct;
			}
		}
		
		return result;
	}	

	
	// check if two 2d int matrices are identical
	public static boolean equals(int[][] a, int[][] b) {	
		return java.util.Arrays.deepEquals(a, b);

		/*
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
		*/		
	}

	
	/*
	// check that matrix a and b has same structure
	public static boolean equalMatrixStructure(int[][] a, int[][] b) {
		if(a == null || b == null){
			throw new IllegalArgumentException("Matrix must be non-null");
		}
		
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
