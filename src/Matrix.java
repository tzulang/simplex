


/**
 * represent a matrix with mXn elements 
 * elements rows and columns runs from from 1 to the given size  
 */
public class Matrix {

	
	Vector[] rowVec; 
	
	
	
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
		int cols = elemnts[0].length;
				
		rowVec =new Vector[rows];
		for (int i =0 ; i< rows ; i++){
			rowVec[i] = new Vector(cols);
		}
	 }
	
	
	
	public float get( int row, int col){
		
	if (row <= 0 || row > rowVec.length ){
			throw new RuntimeException("element index out of boundaries");
		}
	
	return rowVec[row].get(col);
	}
	
	
	public void set( int row, int col, float value){
		
	if (row <= 0 || row > rowVec.length ){
			throw new RuntimeException("element index out of boundaries");
		}
	
	 rowVec[row].set(col, value);
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
	
	
	
	
}
