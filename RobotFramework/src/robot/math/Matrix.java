package robot.math;

import java.util.Arrays;

/**
 * Represents a matrix 
 * 
 * @author Mitchell
 *
 */
public class Matrix {
	private final double[][] array;
	/**
	 * Create a new Matrix
	 * The matrix will be initialized to all 0s
	 * @param rows the number of rows (the height)
	 * @param cols the number of columns (the width)
	 */
	public Matrix(int rows, int cols){
		if(rows<=0 || cols<=0)
			throw new IllegalArgumentException("The dimension of the maxtrix must be positive integers");
		array = new double[rows][cols];
	}
	
	/**
	 * @return the number of rows the matrix has (the height)
	 */
	public int getNumRows(){
		return array.length;
	}
	
	/**
	 * @return the number of columns the matrix has (the width)
	 */
	public int getNumCols(){
		return array[0].length;
	}

	/**
	 * @param row
	 * @param col
	 * @return a single value from the matrix
	 */
	public double get(int row, int col){
		return array[row][col];
	}
	/**
	 * Set the value at a position in the matrix
	 * @param row
	 * @param col
	 * @param value 
	 */
	public void set(int row, int col, double value){
		array[row][col] = value;
	}
	
	/**
	 * Add one matrix to this one
	 * @param matrix
	 * @return this matrix
	 */
	public Matrix add(Matrix matrix){
		if(matrix.getNumRows()!=getNumRows() || matrix.getNumCols()!=getNumCols())
			throw new IllegalArgumentException("To add two matricies they must have the same size");
		for(int r = 0; r<getNumRows(); ++r)
			for(int c = 0; c<getNumCols(); ++c)
				array[r][c] += matrix.array[r][c];
		return this;
	}
	
	/**
	 * Negate all values in this matrix
	 * @return this matrix
	 */
	public Matrix negate(){
		for(int r = 0; r<getNumRows(); ++r)
			for(int c = 0; c<getNumCols(); ++c)
				array[r][c] = -array[r][c];
		return this;
	}
	
	
	
	
	
	
	

	@Override
	public int hashCode() {
		return Arrays.deepHashCode(array);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Matrix other = (Matrix) obj;
		if (!Arrays.deepEquals(array, other.array))
			return false;
		return true;
	}
	
	@Override
	public String toString(){
		return Arrays.deepToString(array);
	}
	
	
}
