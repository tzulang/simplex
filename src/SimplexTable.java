
public class SimplexTable {

	private Matrix A;
	private Vector x,b,z;
	
	private Matrix Q,
				   AB,
				   AN;
	private Vector p,r,z0, 
				   xB,  // vector of basic Variables;
				   xN;  // vector of Basic Variables;
				   
					
				   
				   
	
	public SimplexTable(Matrix A, Vector b, Vector z){
		
		this.A=A;
		this.b=b;
		this.z=z;
		
	}
	
	
    private void CalcQ(){
    	 
    }
	
	
	
	
}
