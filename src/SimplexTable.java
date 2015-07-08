import java.lang.reflect.Array;
import java.util.LinkedList;

/*
 * represent a simplex Table of an LP with equalities constraint only Ax=b x>=0
 */
public class SimplexTable {

	private Matrix A;
	private Vector b,c;
	private double z;
	
	private Matrix Q, AB,AN;
	private Vector p,r,
				   xB,  // vector of basic Variables;
				   xN,  // vector of non basic Variables;
				   B,
				   N,
				   cB,cN;
	private double z0;
					
	private int m,n;			   
	
	 
	
	public SimplexTable(Matrix A, Vector b, Vector c){
		
		if (A.rows()!= b.size())
			throw new RuntimeException("constraint and Matrix ileagel"); 
		removeDipendetConstraint(A,b);
		this.c=c;
		this.m= this.A.rows();
		this.n= this.A.cols();
		
		this.xN=new Vector(n-m);
		this.xB=new Vector(m);
	}
	
	/*
	 * if m.rows()> m.cols()  there is dependency between rows so this method use gaussien elimination
	 * to remove those
	 */
	private void removeDipendetConstraint(Matrix m,Vector v){
		
		if (m.rows()> m.cols()){
			
		
		Vector vec[] ={v};
		Matrix tmp= m.addColumns( new Matrix(vec) );
		
		tmp= tmp.rowReduce();
		
		LinkedList<Vector> nonZeroVec= new LinkedList<Vector>(); 
		
		for (int r=1; r<=tmp.rows() ; r++){
			
			Vector row= m.getRow(r);
			if (!row.isZero()){
				nonZeroVec.add(row);
			}
		}
		
		Vector matrixIndexes= Vector.runningNumbersVector(1, m.cols());
		
		A = tmp.getColumns(matrixIndexes);
		b = tmp.getColumn(m.cols()+1);
		
		if ( !validConstraint(m,b))
				throw new RuntimeException("constraint arent leagel");
		
		} else {
			A = m;
			b = v;
		}
		
	}
	
	private boolean validConstraint(Matrix m, Vector b){
		
		for (int r=1; r<=m.rows() ; r++){
			if (m.getRow(r).isZero() && !b.isZero())   // thers a constarint that is 0*x1 +0*x2+..+0*xn= a where a!=0
				return false;
		}
		
		return true;
	}
	
	
    public void update(Vector newBasis, Vector newBaseValues){
    	 
    	if (newBasis.size() != m)
    		throw new RuntimeException("basis != m");
    	
    	B = newBasis;
    	N = getNonbasic(B);
    	
    	xB= newBaseValues;
    	  
    	calc();
    }
    
    
    private Vector getNonbasic(Vector newBasis){
    	
    			
    	double res[] =new double[n-m];
    	
    	boolean exist[] = new boolean[n];
    	
    	for (int i=0; i < n ; i++){
    		exist[i]=false;
    	}
    	
    	for (int i=1; i <= m ; i++){
    		exist[(int) (newBasis.get(i)-1)]=true;
    	}
    	
    	int index =0;
    	for (int i=0; i < n ; i++){
    		if (!exist[i]) {
    			res[index]= i+1;
    			++index;
    		}
    		
    	}
		return new Vector(res);
    	
    }
	
    private void calc(){
    		
    	AB = A.getColumns(B);
    	AN = A.getColumns(N);
    	cB = c.getElemts(B);
    	cN = c.getElemts(N);
    	
    	Matrix ABinverse= AB.inverse();
    	
    	Q = ABinverse.mult(AN).mult(-1);   // AB^-1 * AN
    	
    	p(ABinverse.cols());
    	p(b);
    	p = ABinverse.mult(b);
    	z0 = cB.dot( ABinverse.mult(b));  // z0= cB* AB^-1 * b
    	
    	Matrix tmp = (ABinverse.mult(AN)).transpose();
    	
    	r= cN.sub(tmp.mult(cB));  // r=cN-  (cB^T * AB^-1 * AN)^T = cN-  (AB^-1*AN)^T * cB  
    	
    	z= z0+ r.dot(xN);
    	    	
    }
    
   
    
	private Pivot getPivot(){
		
		
		boolean found=false;
		int inIndex = -1;
		// search for a non negative coefficient of the non basic 
	    // variables at the target variable z.   z= z0 + r^T * xN
	
		double minNewValue  = Double.MAX_VALUE;
		int    outIndex=0;
		
		for (int i=1; i< m-n && !found; i++ ){
						 
			if (r.get(i)<=0)
				continue;
			
			found=true;
			inIndex = i;
			// search for the most greater change available
			// for xN_i
			double delta=0;
			
			for (int j= 1; j< m ; j++){
				// get the coefficient of Qji aka the  coefficient of xN_i at equation j 
				// in the table			
				double qi= Q.get(j,i);
				double pi= p.get(j);
				
				if ( qi<0 ){
					// calculate the max change available
					double val= (-pi/qi);
					delta = (val > 0) ? val : 0; 
				}
				
				if ( delta < minNewValue){
					minNewValue = delta;
					outIndex = j;
				}				 
			} 			
		}
		
		if (!found)
			return null;   // all non basic coefficients in z are non positive
				
		return new Pivot(inIndex, outIndex, minNewValue);
		 
	}
	
	
	private void getInitailBase( Vector base, Vector BaseValue){
		
		
		SimplexTable table;
		
		Matrix I= Matrix.getIdentity(m);
		Matrix newA=A.addColumns(I);
		Vector newC= new Vector(n+m);
		
		
		
		//set the Target object to maximize -(x_n+1 + x_n+2 +... x_n+m) 
		for (int i=n+1; i<= n+m ;i++){
			newC.set(i,-1);    
		}
		
		Vector newBase= Vector.runningNumbersVector(n+1, n+m);
		Vector newBaseValues= new Vector(b);
		
		table = new SimplexTable(newA, b, newC);
		Vector answer = table.solve( newBase, newBaseValues);
				
		if (  ! answer.getElemts( Vector.runningNumbersVector(n+1, n+m)).isZero() ){
			throw new RuntimeException(" no feasable base was found ");
		} 
		
		int counter =0;
				
		for (int i=1; i <n; i++){
			
			double element = answer.get(i);
			
			if (element > 0 ){
				
				counter++;
				
				if (counter> m)
					throw new RuntimeException(" theres an error , to many bases were found ");
				
				base.set(counter, i);
				BaseValue.set(counter, element); 
					
			}
		}
	}
	
	
	public Vector solve(Vector base, Vector BaseValues){
		  
		 update(base, BaseValues);
		 
		 Pivot pivot= getPivot();
		 
		 while ( pivot != null){
			 
			 changeBase(base, BaseValues, pivot);
			 update(base, BaseValues);
			 pivot= getPivot();
		 }
		return null;
		 	
	}
	
	private void changeBase(Vector base, Vector BaseValues, Pivot pivot){
		
		for (int i=0; i< m ; i++){
			if (base.get(i) == pivot.outIndex ){
				
				base.set(i, pivot.inIndex);
				BaseValues.set(i, pivot.newValue);
				return;
			}
			
		}
		
		//this code shloudnt be reached
		throw new RuntimeException("changeBase error - could not find the pivot ouTindex in the base");
		
	}
			

	
	
	
	
	public Vector solve(){
	 
		 Vector base =new Vector(m);
		 Vector baseValues =new Vector(m);
		 
		 getInitailBase(base, baseValues);
		 
		 return solve (base, baseValues);
	}
	
	
	
	
	
//	private void p(){ System.out.println();}
	private void p(Object o){ System.out.println(o);}
	
	
	
	
	
    public void printTable(){
	
		p("A=");
	  	p(A);
	  	
	  	p("b=");
	  	p(b);
	    
		p("c=");
	  	p(c);
	  	
	  	
	  	if (B != null ){
		  	p("Basis=");
		  	p(B);
		  	p("Basis values=");
		  	p(xB);
		  	
		  	p("AB=");
		  	p(AB);
		  	
		  	p("AN=");
		  	p(AN);
		  	
		  	p("Q=");
		  	p(Q);
		  	
		  	p("p=");
		  	p(p);
		  	
		  	p("z0=");
		  	p(z0);
		  	
		  	p("r=");
		  	p(r);
		  	
		  	p("z=");
		  	p(z);
	  	}
    }
}

 

class Pivot{

	public int outIndex;   // index of element that go out of the basis
	public int inIndex;	   // index of element that go in 	
	public double newValue;
	
	
	public Pivot(int inIndex, int outIndex, double newValue){
		this.outIndex= outIndex;
		this.inIndex = inIndex;
		this.newValue= newValue;
	}
}
