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
	
	
	
	
	
	
	
	public  Matrix(double[][] elemnts){
					
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
	
	
	
	
	
	
	
	public double get( int row, int col){
		
		if (row <= 0 || row > rowVec.length ){
				throw new RuntimeException("element index out of boundaries");
			}
		
		return rowVec[row-1].get(col);
	}
	
	
	
	
	
	
	
	public void set( int row, int col, double value){
		
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
		
		double res[][]= new double[rows()][cols()];
		
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
    
    
    
    
    
    
    
	public Matrix mult( double scalar){
		
		 
		int rows= rows();
		
		Vector rowVectors[]= new Vector[rows];
		
		for (int r=0; r< rows; r++){
		 			
			rowVectors[r]= rowVec[r].mult(scalar);
			 
			
		}
		return new Matrix(rowVectors);	
		
	}
	
	
	
	
	
	
	public Matrix transpose (){
		
	 
		
		Vector res[]= new Vector[cols()];
		
		 
		for (int i=0; i< cols(); i++){
			
			res[i]= getColumn(i+1);
			
		}
		return new Matrix (res);	
		
	}
	
	
	
	
	
	
	
	
	
	
	public Vector mult( Vector v){
		
		 
		int rows= rows();
		
		double res[]= new double[rows];
		
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
		
		double elemnets[]= new double[rows()];
		for (int i=0; i< rows ; i++){
			
			elemnets[i]= rowVec[i].get(col);
			
		}
		return new Vector(elemnets);
	}
	
	
	
	
	public Vector getRow(int row){
		
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
  
	 /*
	 * receives a vector of element of ones and zeros that is used to mask the Matrix rows and remove the 0 masked columns 
	 * i.e for matrix : {11,12,13}
	 * 					{14,15,16} 
	 * and  the indexes vector {1,3} , 
	 * the function will return {{11,13},
	 * 							 {14,16}}
	 * throws exception if the size of 'indexes' is greater than this.rows() or if an index of 'indexes' is greater than this.cols() or have a fraction different than zero
	 */
	public Matrix getColumns(Vector indexes){
    	
   	 
    	if  (indexes.size() > cols() )
			throw new RuntimeException("get Columns index have different elements size");
    	
    	int rows= rows();
    	Vector res[]= new Vector[rows];
    	
		 
		
		for (int r=0 ; r < rows; r++){		
			res[r] = rowVec[r].getElemts(indexes);
		}
		
		return new Matrix(res);
    	    	
    }
	
	
	
	
	public Matrix getRows(Vector indexes){
    	
	   	 
    	if  (indexes.size() > rows() )
			throw new RuntimeException("get Columns index have different elements size");
    	
    	Matrix res =transpose().getColumns(indexes);
		return res.transpose();
    	    	
    }
	
		
	 /*
     * receives a vector of element of ones and zeros that is used to mask the Matrix rows  
     * i.e for matrix : {11,12}
     * 					{13,14} 
     * and  the mask vector {1,0} , 
     * the function will return {11,0}
     * 							{13,0}
     * throws exception if the size of 'mask' is different than this.rows() or if the  elements different than zeros and ones
     */
	public Matrix mask(Vector mask){
    	
   	 
    	if  (mask.size() != rows() )
			throw new RuntimeException("mask index have different elements size");
    	
    	double res[][]= new double[rows()][cols()];
    	
    	int rows= rows();
		int cols= cols();
		
		for (int c=0; c < cols; c++){	
			if  ( mask.get(c) != 0  &&  mask.get(c) !=1)
				throw new RuntimeException("element mask is not 0 or 1");
		}
		
		for (int r=1 ; r <= rows; r++){		
			for (int c=1; c <= cols; c++){
								  
					res[r-1][c-1]= get(r,c) * mask.get(c) ;
			}
		}
	 
		return new Matrix(res);
    	    	
    }
	
	
    public Matrix inverse() 
    
    {
    	
     
    	if ( rows() != cols() ){
    		throw new RuntimeException("inverse of non square Matrix");	
    	}
    	int n=rows();
    	
    	Matrix res =this.addColumns( Matrix.getIdentity(n) ); // add the identity to the right of the matrix
    	
    	res= res.rowReduce();
    	
    	Vector leftIndexes= Vector.runningNumbersVector(1, n); 
    	Vector rightIndexes= Vector.runningNumbersVector(n+1, 2*n); 
    	
    	Matrix originalRowReduced = res.getColumns(leftIndexes);
    	
    	if (originalRowReduced.haveRowOfZeros())   // the original matrix have a rows of zeros in its canonical form. thus it is singular
    		throw new RuntimeException("Matrix is singular");	
    	
    	Matrix inverse = res.getColumns(rightIndexes);
    	
		return inverse;
    		
    }
    
   
    
 
    private boolean haveRowOfZeros(){
    	
    	for (int i=0; i < rows(); i++ ){
    		if (rowVec[i].isZero())
    			return true;
    	}
    	
    	return false;
    }
    
    
    
    
    
	 // Method to carry out the partial-pivoting Gaussian
	
	 public Matrix rowReduce() { 		 
	 
	        int rows = rows();
	        int cols = cols();
	        
	        Matrix res= new Matrix(this);
	        int c= 1;
	        int r= 1;
	        
	    	while( r <= rows && c<=cols ){
	    		 
	    			
		    		int max = r;
		    		// find pivot row
		            for (int i = r + 1; i <= rows; i++) {
		                
		            	if (Math.abs(res.get(i,c)) > Math.abs(res.get(max,c)) ) {
		                    max = i;
		                }
		            }
		            //swap
		            Vector temp = res.rowVec[r-1]; 
		            res.rowVec[r-1] = res.rowVec[max-1]; 
		            res.rowVec[max-1] = temp;
		            
		           
		            
		            // check if leading element is non zero
		            if (Math.abs( res.get(r,c)) > Const.EPSILON ) {
		                 
			            // normlize
			            res.rowVec[r-1]= res.rowVec[r-1].mult( 1/res.get(r, c)  ) ;
			            
			            for (int i =1; i <= rows; i++) {
			            	if (i != r){
			            		double alpha= res.get(i, c)/ res.get(r, c) ;
			            		res.rowVec[i-1]= res.rowVec[i-1].sub(  res.rowVec[r-1].mult(alpha) ) ;  //  R[i]= R[i]- alpha * R[r]
			            	} 
			            }
			            
		            } else {
		            	 res.set(r,c,0);
		            }
            r++;        
		    c++;        
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
	
	
	
	
}
