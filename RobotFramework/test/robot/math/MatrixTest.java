package robot.math;

import static org.junit.Assert.*;

import org.junit.*;
import org.junit.runner.*;
import org.junit.runners.*;

@RunWith(JUnit4.class)
public class MatrixTest {
	@Test
	public void testConstructor(){
		Matrix matrix;
		try{
			matrix = new Matrix(0, 10);
			fail();
		} catch(IllegalArgumentException e){
			//success
		}
		try{
			matrix = new Matrix(24, 0);
			fail();
		} catch(IllegalArgumentException e){
			//success
		}
		try{
			matrix = new Matrix(0, 0);
			fail();
		} catch(IllegalArgumentException e){
			//success
		}
		try{
			matrix = new Matrix(-10, -6);
			fail();
		} catch(IllegalArgumentException e){
			//success
		}
		matrix = new Matrix(5, 4);
		assertEquals(5, matrix.getNumRows());
		assertEquals(4, matrix.getNumCols());
		for(int r = 0; r<matrix.getNumRows(); ++r)
			for(int c = 0; c<matrix.getNumCols(); ++c)
				assertEquals(0, matrix.get(r, c), 0);
		
		matrix = new Matrix(5, 5);
		assertEquals(5, matrix.getNumRows());
		assertEquals(5, matrix.getNumCols());
		for(int r = 0; r<matrix.getNumRows(); ++r)
			for(int c = 0; c<matrix.getNumCols(); ++c)
				assertEquals(0, matrix.get(r, c), 0);

		matrix = new Matrix(1, 44);
		assertEquals(1, matrix.getNumRows());
		assertEquals(44, matrix.getNumCols());
		for(int r = 0; r<matrix.getNumRows(); ++r)
			for(int c = 0; c<matrix.getNumCols(); ++c)
				assertEquals(0, matrix.get(r, c), 0);
	}

	@Test
	public void testSetGet(){
		Matrix matrix = new Matrix(5, 4);
		for(int r = 0; r<matrix.getNumRows(); ++r)
			for(int c = 0; c<matrix.getNumCols(); ++c)
				matrix.set(r, c, (r+c)*r+c);
		for(int r = 0; r<matrix.getNumRows(); ++r)
			for(int c = 0; c<matrix.getNumCols(); ++c)
				assertEquals((r+c)*r+c, matrix.get(r, c), 0);
	}

	@Test
	public void testEquals(){
		Matrix matrix1 = new Matrix(5, 4);
		for(int r = 0; r<matrix1.getNumRows(); ++r)
			for(int c = 0; c<matrix1.getNumCols(); ++c)
				matrix1.set(r, c, (r+c)*r+c);

		assertEquals(true, matrix1.equals(matrix1));
		
		assertEquals(false, matrix1.equals(null));
		assertEquals(false, matrix1.equals(new Object()));
		assertEquals(false, matrix1.equals(new Matrix(5, 3)));
		assertEquals(false, matrix1.equals(new Matrix(4, 4)));
		assertEquals(false, matrix1.equals(new Matrix(5, 4)));

		Matrix matrix2 = new Matrix(5, 4);
		for(int r = 0; r<matrix2.getNumRows(); ++r)
			for(int c = 0; c<matrix2.getNumCols(); ++c)
				matrix2.set(r, c, r+c);
		assertEquals(false, matrix1.equals(matrix2));

		matrix2 = new Matrix(5, 4);
		for(int r = 0; r<matrix2.getNumRows(); ++r)
			for(int c = 0; c<matrix2.getNumCols(); ++c)
				matrix2.set(r, c, (r+c)*r+c);
		assertEquals(true, matrix1.equals(matrix2));
		assertEquals(true, matrix1.hashCode() == matrix2.hashCode());
	}

	@Test
	public void testNegate(){
		Matrix matrix = new Matrix(5, 4);
		for(int r = 0; r<matrix.getNumRows(); ++r)
			for(int c = 0; c<matrix.getNumCols(); ++c)
				matrix.set(r, c, (r+c)*r+c);
		matrix.negate();
		for(int r = 0; r<matrix.getNumRows(); ++r)
			for(int c = 0; c<matrix.getNumCols(); ++c)
				assertEquals(-((r+c)*r+c), matrix.get(r, c), 0);
	}
	
	@Test
	public void testAdd(){
		Matrix matrix1 = new Matrix(5, 4);
		for(int r = 0; r<matrix1.getNumRows(); ++r)
			for(int c = 0; c<matrix1.getNumCols(); ++c)
				matrix1.set(r, c, (r+c)*r+c);

		Matrix matrix2 = new Matrix(5, 4);
		for(int r = 0; r<matrix2.getNumRows(); ++r)
			for(int c = 0; c<matrix2.getNumCols(); ++c)
				matrix2.set(r, c, r+c);

		try{
			matrix1.add(new Matrix(5, 3));
			fail();
		} catch(IllegalArgumentException e){
			//success
		}
		try{
			matrix1.add(new Matrix(4, 4));
			fail();
		} catch(IllegalArgumentException e){
			//success
		}
		
		matrix1.add(matrix2);
		
		for(int r = 0; r<matrix1.getNumRows(); ++r)
			for(int c = 0; c<matrix1.getNumCols(); ++c)
				assertEquals((r+c)*r+c + r+c, matrix1.get(r, c), 0);
	}
	
	@Test
	public void testToString(){
		Matrix matrix = new Matrix(5, 4);
		for(int r = 0; r<matrix.getNumRows(); ++r)
			for(int c = 0; c<matrix.getNumCols(); ++c)
				matrix.set(r, c, (r+c)*r+c);
		assertEquals("[[0.0, 1.0, 2.0, 3.0], [1.0, 3.0, 5.0, 7.0], [4.0, 7.0, 10.0, 13.0], [9.0, 13.0, 17.0, 21.0], [16.0, 21.0, 26.0, 31.0]]",
				matrix.toString());
	}
}
