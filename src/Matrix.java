import java.util.Arrays;




/**
 * represent a matrix with mXn elements 
 * elements rows and columns runs from from 1 to the given size  
 */
public class Matrix {

	
	private Vector[] rowVec; 
	
	
	
	public  Matrix(int rows, int cols){
		
		if ( rows ==0 || cols==0)
			throw new RuntimeException("zero size matrix");
		rowVec =new Vector[rows];
		for (int i =0 ; i< rows ; i++){
			rowVec[i] = new Vector(cols);
		}
	}
	
	
	
	
	
	
	
	public  Matrix(float[][] elemnts){
					
		if ( elemnts ==null || elemnts.length <=0 || elemnts[0].length==0)	
				throw new RuntimeException("zero size matrix");
		
		int rows = elemnts.length;
		 
				
		rowVec =new Vector[rows];
		for (int i =0 ; i< rows ; i++){
			rowVec[i] = new Vector(elemnts[i]);
		}
	 }
	
	
	
	
	
	
	
	
	
	public  Matrix(Matrix m){
					
		
		int rows = m.rows();
		 		
		rowVec= new Vector[rows];
		for (int i =0 ; i< rows ; i++){
			rowVec[i] = new Vector(m.rowVec[i]);
		}
	 }
	
	
	
	
	
	
	
	
	public  Matrix( Vector rowVectors[]){
					
		rowVec= rowVectors;
	}
	
	
	
	
	
	
	
	public float get( int row, int col){
		
		if (row <= 0 || row > rowVec.length ){
				throw new RuntimeException("element index out of boundaries");
			}
		
		return rowVec[row-1].get(col);
	}
	
	
	
	
	
	
	
	public void set( int row, int col, float value){
		
		if (row <= 0 || row > rowVec.length ){
				throw new RuntimeException("element index out of boundaries");
			}
		
		 rowVec[row-1].set(col, value);
	}
	
 
	
	
	
	
	
	
	
	private void checkSize(Matrix m, String op){
		
		if (m.rows() != rows() || m.cols() != cols() )
			throw new RuntimeException(op  + "opperation between different size Matrices");
	}
	
	
	
	
	
	
	
	public Matrix add (Matrix m){
		
		checkSize(m, "add");
		
		Vector res[]= new Vector[rows()];
		
		int rows= rows();
		for (int i=0; i< rows-1; i++){
			
			res[i]= rowVec[i].add(rowVec[i]);
			
		}
		return m;
		
		
		
	}
	
	
	
	
	
	
	
	public Matrix sub(Matrix m){
		
		checkSize(m, "sub");
		
		Vector res[]= new Vector[rows()];
		
		int rows= rows();
		for (int i=0; i< rows-1; i++){
			
			res[i]= rowVec[i].sub(rowVec[i]);
			
		}
		return m;	
		
	}
	
	
	
	
	
	
	

	public Matrix mult(Matrix m){
		
		checkSize(m, "sub");
		
		float res[][]= new float[rows()][cols()];
		
		int rows= rows();
		int cols= cols();
		
		
		for (int r=0; r< rows; r++){
			for (int c=0; c< cols; c++){
				
				res[r][c]=  rowVec[r].dot( m.getColumn(c+1));
			}
		
			
		}
		return new Matrix(res);	
		
	}
	
	
	
	
	
	
	
    public static Matrix getIdentity(int size){
    		
    	
    	Matrix m = new Matrix(size,size);
    	
    	for (int  i=0; i<size ; i++){
    		m.rowVec[i].set(i+1,1);
    	}
		return m;
    }
    
    
    
    
    
    
    
	public Matrix mult( float scalar){
		
		 
		int rows= rows();
		
		Vector rowVectors[]= new Vector[rows];
		
		for (int r=0; r< rows; r++){
		 			
			rowVectors[r]= rowVec[r].mult(scalar);
			 
			
		}
		return new Matrix(rowVectors);	
		
	}
	
	
	
	
	
	
	public Matrix transpose (){
		
	 
		
		Vector res[]= new Vector[rows()];
		
		int rows= rows();
		for (int i=0; i< rows; i++){
			
			res[i]= getColumn(i+1);
			
		}
		return new Matrix (res);	
		
	}
	
	
	
	
	
	
	
	
	
	
	public Vector mult( Vector v){
		
		 
		int rows= rows();
		
		float res[]= new float[rows];
		
		for (int r=0; r< rows; r++){
		 			
			 res[r]= rowVec[r].dot(v);
			 
			
		}
		return new Vector(res);	
		
	}
	
	
	
	
	
	
	
	
	
	public Vector getColumn(int col){
		
		if (col <= 0 || col > cols() ){
			throw new RuntimeException("column index out of boundaries");
		}
		
		 
		int rows= rows();
		
		float elemnets[]= new float[rows()];
		for (int i=0; i< rows ; i++){
			
			elemnets[i]= rowVec[i].get(col);
			
		}
		return new Vector(elemnets);
	}
	
	
	
	
	public Vector getRows(int row){
		
		if (row <= 0 || row > rowVec.length ){
			throw new RuntimeException("column index out of boundaries");
		}
		
		int rowIndex=row-1;
	 	
		return new Vector( rowVec[rowIndex]);
	}
	
	
	
	
	
	public int rows(){
		return rowVec.length;
	}
	
	
	
	public int cols(){
		return rowVec[0].size();
	}
	
	
	
	
	
	
	
	
	public  String toString(){
		
		StringBuilder s= new StringBuilder("{");
		int rows= rows();
		
		for (int i=0; i< rows-1; i++){
			
			s.append(rowVec[i].toString());
			s.append("\n ");
			
		}
		s.append(rowVec[rows-1].toString()); 
		s.append(" }");
		
		return s.toString();
	 }


	/*
	 *  adds numOfcolumns columns of zero at the end of the matrix
	 */
	public Matrix addColumns(int numOfcolumns){
		
		Matrix m= new Matrix(rows(),cols()+ numOfcolumns);
		
		int rows= rows();
		int cols= cols();
		for (int r=1 ; r <= rows; r++){
			for (int c=1; c <= cols; c++){
				m.set(r,c, get(r,c));
			}
		}
		return m;
		
	}
	
	public Matrix addColumns(Matrix m){
		
		if (m.rows() != rows())
			throw new RuntimeException(" add columns opperation between different row size Matrices");
		
		
		Matrix res= new Matrix(rows(),cols()+ m.cols());
		
		int rows= rows();
		int cols= cols();
		for (int r=1 ; r <= rows; r++){
			for (int c=1; c <= cols; c++){
				res.set(r,c, get(r,c));
			}
		}
		
		
		int cols2= m.cols();
		for (int r=1 ; r <= rows; r++){
			for (int c=1; c <= cols2; c++){
				res.set(r,c+cols(), m.get(r,c));
			}
		}
		return res;
		
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
		if (!Arrays.equals(rowVec, other.rowVec))
			return false;
		return true;
	}
	
	
	
	
}
