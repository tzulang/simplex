import java.util.Arrays;


/**
 * represent a vector with n elements 
 * elements index runs from from 1 to the 
 */
public class Vector {

	
	
	float elemetns[];
	
	
	
	
	
	
	public Vector (int size){
		
		if (size <=0 )
			throw new RuntimeException("zero size vector");
		elemetns= new float [size];
		
		for (int i=0 ; i<size; i++){
			elemetns[i]=0;
		}
		
	}
	
	public Vector (float elemnets[]){
		if ( elemnets ==null || elemnets.length <=0 )
			throw new RuntimeException("zero size vector");
		elemetns= elemnets;
		
	 
	}
	//deep copy	
	public Vector (Vector v){
		
		int size=v.elemetns.length;
		elemetns= new float [size];
		
		for (int i=0 ; i<size; i++){
			elemetns[i]=v.elemetns[i];
		}
		
	}
	
	public boolean sizeChek(Vector v){
		
		return v.elemetns.length == elemetns.length;
	}
	
	
    private  void sameSize(Vector v, String op){
		
		if ( v.elemetns.length != elemetns.length){
			throw new RuntimeException(op + " opperation between different size vectors");
		}
		
	}
    
    
    public Vector add(Vector v){
    	
    	sameSize(v, "add");
    	int size=elemetns.length;
		float res[]= new float [size];
		
		for (int i=0 ; i<size; i++){
			res[i]= elemetns[i]+v.elemetns[i];
		}
		
		return new Vector(res);
    }
    
    public Vector sub(Vector v){
    	
    	sameSize(v, "add");
    	int size=elemetns.length;
		float res[]= new float [size];
		
		for (int i=0 ; i<size; i++){
			res[i]= elemetns[i]-v.elemetns[i];
		}
		
		return new Vector(res);
    }
    
    public Vector mult(float scalar){
    	
    	 
    	int size=elemetns.length;
		float res[]= new float [size];
		
		for (int i=0 ; i<size; i++){
			res[i]= elemetns[i] *scalar;
		}
		
		return new Vector(res);
    }
    
    public float dot(Vector v){
    	
    	sameSize(v, "dot product");
    	int size=v.elemetns.length;
		float res=0;
		
		for (int i=0 ; i<size; i++){
			res+= elemetns[i]*v.elemetns[i];
		}
		
		return res;
    }
    
    public String toString(){
    	
    	StringBuilder s= new StringBuilder("(");
    	int size= elemetns.length;
    	
    	for (int i=0 ; i<size-1; i++){
			s.append(elemetns[i]);
			s.append(", ");
		}
    	s.append(elemetns[size-1]);
    	s.append(")");
    	return s.toString();
    			
    }
    
    
    
    public int size(){
    	
    	return elemetns.length;
    	
    }
    
    
    public float get( int element){
    	
    	
    	if (element <= 0 || element > elemetns.length ){
			throw new RuntimeException("element index out of boundaries");
		}
    	return elemetns[element-1];
    	
    }
    
    
    public void set( int element, float value){
    	
    	
    	if (element <= 0 || element > elemetns.length ){
			throw new RuntimeException("element index out of boundaries");
		}
    	elemetns[element-1]= value;
    	
    }
    
    
     public void setAll( int element, float value){
    	
    	
    	if (element <= 0 || element > elemetns.length ){
			throw new RuntimeException("element index out of boundaries");
		}
    	
    	int size= elemetns.length;
    	
    	for (int i=0 ; i<size-1; i++){
        	elemetns[element-1]= value;
		}

    	
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
    
	
}
