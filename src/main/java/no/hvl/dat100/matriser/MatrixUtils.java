package no.hvl.dat100.matriser;

public class MatrixUtils {
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
		if(mat == null || mat.length == 0 || mat[0].length == 0) {
			throw new IllegalArgumentException("Invalid matrix");
		}
		
		validateNonNullMatrix(mat);
		validateNonZeroRowMatrix(mat);	
		validateNonNullMatrixRow(mat[0]);
		
		return mat.length * mat[0].length;
	}
	
	// return true only if all rows of matrix have same length
	// matrix must have non-zero number of rows and columns
	public static boolean isRectangularMatrix(int[][] mat) {
		return isRectangularMatrix(mat, true);
	}
	public static boolean isRectangularMatrix(int[][] mat, boolean checkEveryRow) {		
		validateNonNullMatrix(mat);
		validateNonZeroRowMatrix(mat);
		validateNonNullMatrixRow(mat[0]);
		
		/*
		if(n == 0) {
			throw new IllegalArgumentException(ERR_MATRIX_MUST_HAVE_AT_LEAST_ONE_ROW);
		}
		
		if(mat[0] == null || mat[0].length == 0) {
			throw new IllegalArgumentException("Matrix must have at least one column");
		}
		*/
				
		if(checkEveryRow) {
			int n = mat.length;			
			int m = mat[0].length;		
			for(int i=0; i<n; i++) {
				if(mat[i].length != m) {
					return false;
				}
			}
		}else {
			if(mat[0].length == 0) {
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
		if((a == null) ^ (b == null)) {
			throw new IllegalArgumentException("Matrix must be non-null");
		}
		if(a == null) {
			throw new IllegalArgumentException("Matrix must be non-null");
		}
		
		int aNumRows = a.length;
		int bNumRows = b.length;
		
		// check that both matrices have non-zero row count
		if(aNumRows == 0 || bNumRows == 0) {
			throw new IllegalArgumentException(ERR_MATRIX_MUST_HAVE_AT_LEAST_ONE_ROW);
		}
		
		// get matrix dimensions, n = rows, m = cols
		int aNumCols = a[0].length;			

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
			if((a[i] == null) ^ (b[i] == null)){
				return false;
			}
			if(a[i] == null){
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
	


}
