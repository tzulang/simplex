import java.text.DecimalFormat;
import java.util.Arrays;


/**
 * represent a vector with n elements 
 * elements index runs from from 1 to the 
 */
public class Vector {

	
	
	private double elemetns[];
	
	
	
	
	
	
	public Vector (int size){
		
		 
		elemetns= new double [size];
		
		for (int i=0 ; i<size; i++){
			elemetns[i]=0;
		}
		
	}
	
	public Vector (double elemnets[]){
		if ( elemnets ==null)
			throw new RuntimeException("null vector init");
		elemetns= elemnets;
		
	 
	}
	//deep copy	
	public Vector (Vector v){
		
		int size=v.elemetns.length;
		elemetns= new double [size];
		
		for (int i=0 ; i<size; i++){
			elemetns[i]=v.elemetns[i];
		}
		
	}
	
 
	
    public int size(){
		
		return elemetns.length;
		
	}

	private  void sameSize(Vector v, String op){
		
		if ( v.elemetns.length != elemetns.length){
			throw new RuntimeException(op + " opperation between different size vectors");
		}
		
	}

	public Vector add(Vector v){
    	
    	sameSize(v, "add");
    	int size=elemetns.length;
		double res[]= new double [size];
		
		for (int i=0 ; i<size; i++){
			res[i]= elemetns[i]+v.elemetns[i];
		}
		
		return new Vector(res);
    }
    
    public Vector sub(Vector v){
    	
    	sameSize(v, "add");
    	int size=elemetns.length;
		double res[]= new double [size];
		
		for (int i=0 ; i<size; i++){
			res[i]= elemetns[i]-v.elemetns[i];
		}
		
		return new Vector(res);
    }
    
    public Vector mult(double scalar){
    	
    	 
    	int size=elemetns.length;
		double res[]= new double [size];
		
		for (int i=0 ; i<size; i++){
			res[i]= elemetns[i] *scalar;
		}
		
		return new Vector(res);
    }
    
    public double dot(Vector v){
    	
    	sameSize(v, "dot product");
    	int size=v.elemetns.length;
		double res=0;
		
		for (int i=0 ; i<size; i++){
			res+= elemetns[i]*v.elemetns[i];
		}
		
		return res;
    }
    
    public double get( int element){
    	
    	
    	if (element <= 0 || element > elemetns.length ){
			throw new RuntimeException("element index out of boundaries");
		}
    	return elemetns[element-1];
    	
    }
    
    
    public void set( int element, double value){
    	
    	
    	if (element <= 0 || element > elemetns.length ){
			throw new RuntimeException("element index out of boundaries");
		}
    	elemetns[element-1]= value;
    	
    }
    
    
     public void setAll(double value){
    	
    	
     	for (int i=0 ; i<size(); i++){
        	elemetns[i]= value;
		}

    	
    }

    /*
     * receives a vector of element indexes, and return a vector with the elements of the original vector which correspond to the given indexes
     * i.e for vector {11,12,13,14} and  the index vector {1,3} , the function will return {11,13}
     * throws exception if the size of 'indexes' is greater than this.size() or if an index of 'indexes' is greater than this.size() or have a fraction different than zero
     */
    public Vector getElemts(Vector indexes){
    	
    	 
    	if  (indexes.size() > size() )
			throw new RuntimeException("get element index have to many elements");
    	
    	double res[]= new double[indexes.size()];
    	
    	for (int i=0; i < indexes.size(); i++ ){
    		
    		if  (indexes.elemetns[i] > size() || indexes.elemetns[i]<= 0 )
    			throw new RuntimeException("element index out of boundaries in get elements");
    		if  ( (double) Math.floor(indexes.elemetns[i])  != indexes.elemetns[i] )
    			throw new RuntimeException("element index is not an integer value");
    		res[i]= elemetns[(int) indexes.elemetns[i] -1] ;
    		
    	}
    	
		return new Vector(res);
    	
    	
    	
    }
    
    /*
     * receives a vector of element of ones and zeros that is used to mask the original vector 
     * i.e for vector {11,12,13,14} and  the mask vector {1,0,1,0} , the function will return {11,0,13,0}
     * throws exception if the size of 'mask' is different than this.size() or if the  elements different than zeros and ones
     */
    public Vector mask(Vector mask){
    	
    	 
    	if  (mask.size() != size() )
			throw new RuntimeException("mask index have different elements size");
    	
    	double res[]= new double[size()];
    	
    	for (int i=0; i < size(); i++ ){
    		
    		 
    		if  ( mask.elemetns[i] != 0  && mask.elemetns[i] !=1)
    			throw new RuntimeException("element mask is not 0 or 1");
    		
    		res[i]= elemetns[i] * mask.elemetns[i] ;
    	}
    	
		return new Vector(res);
    	
    	
    	
    }
    
    
    public boolean isZero(){
		
		for (int i=0; i < size(); i++ ){
			if (elemetns[i]!=0)
				return false;
		}
		return true;
		
	}

	@Override
	public boolean equals(Object obj) {
		
		if (this == obj)
			return true;
		
		if (obj == null)
			return false;
		
		if (getClass() != obj.getClass())
			return false;
		
		Vector other = (Vector) obj;
		if (!Arrays.equals(elemetns, other.elemetns))
			return false;
		
		return true;
	}

	
	
	public String toString(){
		
		StringBuilder s= new StringBuilder("(");
		int size= elemetns.length;
		
	 
		double val;
		for (int i=0 ; i<size-1; i++){
			val = ( Math.abs(elemetns[i])<= Const.EPSILON) ? 0f : elemetns[i];
					
			s.append(val);
			s.append(", ");
		}
		
		val = ( Math.abs(elemetns[size-1])<= Const.EPSILON) ? 0f : elemetns[size-1];
		s.append(val);
		s.append(")");
		return s.toString();
				
	}
    
	public static Vector runningNumbersVector(int start , int end){
		
		if (start > end)
			return null;
		
		Vector res =new Vector(end-start+1);
		
		int val=start;
		for (int i=0; i<=end-start; i++){
			res.elemetns[i]= val;
			val++;
		}
		
		return res;
	}
	
}
