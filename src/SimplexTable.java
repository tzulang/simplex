 
import java.util.LinkedList;

 

/*
 * represent a simplex Table of an LP with equalities constraint only Ax=b x>=0
 */
public class SimplexTable {
	
	public static enum MODE{ EQUALITY, INEQUALITY};
	
	private Matrix A;
	private Vector b,c;
	private double z;
	
	private Matrix Q, AB,AN;
	private Vector p,r,
				  
				   xN,  // vector of non basic Variables;
				   B,
				   N,
				   cB,cN;
	private double z0;
					
	private int m,n, originalNumOfVar;			   
	
	 
    public double getMaxValue(){
    	return z;
    }
	public Vector cutAnswerToOriginalVariables(Vector answer){

		 	Vector originalIndexes= Vector.runningNumbersVector(1, originalNumOfVar);
		 	return answer.getElemts(originalIndexes);
	}
	public SimplexTable(Matrix A, Vector b, Vector c, MODE mode){
		
		if (A.rows()!= b.size())
			throw new RuntimeException("constraint and Matrix ileagel"); 
		
		this.m= A.rows();
		originalNumOfVar= A.cols();
		if (mode == MODE.INEQUALITY){
			
			A=A.addColumns(Matrix.getIdentity(m));
			c=c.addElements(m);
		}
		removeDipendetConstraint(A,b);
		this.c=c;
	
		this.n= this.A.cols();
		
		this.xN=new Vector(n-m);
		 
		
		
	}
	
	/*
	 * if m.rows()> m.cols()  there is dependency between rows so this method use gaussien elimination
	 * to remove those
	 */
	private void removeDipendetConstraint(Matrix m,Vector v){
		
//		if (m.rows()> m.cols()){
			
		
		Vector vec[] ={v};
		Matrix tmp2= m.addColumns( new Matrix(vec).transpose() );
		
		Matrix tmp= tmp2.rowReduce();
		
		LinkedList<Vector> nonZeroVec= new LinkedList<Vector>(); 
		
		for (int r=1; r<=tmp.rows() ; r++){
			
			Vector row= tmp.getRow(r);
			if (!row.isZero()){
				nonZeroVec.add(row);
			}
		}
		Vector newRows[] =new Vector[nonZeroVec.size()];
		tmp = new Matrix(newRows);
		Vector matrixIndexes= Vector.runningNumbersVector(1, tmp.cols());
		
		A = tmp.getColumns(matrixIndexes);
		b = tmp.getColumn(m.cols()+1);
		
		if ( !validConstraint(m,b))
				throw new RuntimeException("constraint arent leagel");
		
//		} else {
//			A = m;
//			b = v;
//		}
		
	}
	
	private boolean validConstraint(Matrix m, Vector b){
		
		for (int r=1; r<=m.rows() ; r++){
			if (m.getRow(r).isZero() && !b.isZero())   // thers a constarint that is 0*x1 +0*x2+..+0*xn= a where a!=0
				return false;
		}
		
		return true;
	}
	
	
	public void update(Vector newBasis){
    	 
    	if (newBasis.size() != m)
    		throw new RuntimeException("basis != m");
    	
    	B = newBasis;
    	N = getNonbasic(B);
    	
    	 
    	
    	
    	calc();
//    	printTable();
    	
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
    	
    	B= B.sortElements();
    	N= N.sortElements();
    	
    	AB = A.getColumns(B);
    	AN = A.getColumns(N);
    	cB = c.getElemts(B);
    	cN = c.getElemts(N);
    	
    	Matrix ABinverse= AB.inverse();
    	
    	Q = ABinverse.mult(AN).mult(-1);   // AB^-1 * AN
    	
    	 
    	p = ABinverse.mult(b);
    	 
    	z0 = cB.dot( ABinverse.mult(b));  // z0= cB* AB^-1 * b
    	
    	Matrix tmp = (ABinverse.mult(AN)).transpose();
    	
    	r= cN.sub(tmp.mult(cB));  // r=cN-  (cB^T * AB^-1 * AN)^T = cN-  (AB^-1*AN)^T * cB  
    	
    	z= z0+ r.dot(xN);
    	    	
    }
    
   
    
	private Pivot getPivot(){
		
		Pivot pivot= null;
		boolean found=false;
		int inIndex = -1;
		// search for a non negative coefficient of the non basic 
	    // variables at the target variable z.   z= z0 + r^T * xN
	
		double minNewValue  = Double.MAX_VALUE;
		int    outIndex=0;
		
		for (int i=1; i<= n-m ; i++ ){
				
			if (r.get(i)<=0)
				continue;
			
			
			
			inIndex = (int) N.get(i);
			// search for the most greater change available
			// for xN_i
			double delta=-1;
			
			for (int j= 1; j<= m ; j++){
				// get the coefficient of Qji aka the  coefficient of xN_i at equation j 
				// in the table		
				 
				double qi= Q.get(j,i);
				double pi= p.get(j);
				
				if ( qi<0 ){
					// calculate the max change available
					delta= (-pi/qi);
					 
				}
				
				 
				
				if ( delta>= 0 && delta < minNewValue){
					minNewValue = delta;
					outIndex = (int) B.get(j);
				}				 
			}
			
			if (minNewValue!=Double.MAX_VALUE && (!found || pivot.newValue < minNewValue )){
				pivot= new Pivot(inIndex, outIndex, minNewValue);
				found=true;
			} 
			
		}
		
		 
		return  pivot;
		 
	}
	
	
	private void getInitailBase( Vector base){
		
		
		SimplexTable table;
		
		Matrix I= Matrix.getIdentity(m);
		Matrix newA=A.addColumns(I);
		Vector newC= new Vector(n+m);
		
		
		
		//set the Target object to maximize -(x_n+1 + x_n+2 +... x_n+m) 
		for (int i=n+1; i<= n+m ;i++){
			newC.set(i,-1);    
		}
		
		Vector newBase= Vector.runningNumbersVector(n+1, n+m);
		 
		
		table = new SimplexTable(newA, b, newC, MODE.EQUALITY);
		Vector answer = table.solve(newBase);
		answer = answer.round();
		if (  ! answer.getElemts( Vector.runningNumbersVector(n+1, n+m)).isZero() ){
			throw new RuntimeException(" no feasable base was found ");
		}
		
		
		for (int i= n+1 ; i <= n+m; i++){
			if (table.B.contains(i)  ){
				
				int indexB= table.B.indexOf(i);
				boolean found=false;
				for (int j= 1 ; j < table.N.size() && !found ; j++){
					
					int baseIn=(int) table.N.get(j);
					if (baseIn<= n){
						 found=true;
						 table.B.set(indexB, baseIn);
						 table.N.set(j, i);
					}
				}
			}
		}
		
		for (int i=1; i <= m; i++){
			if (table.B.get(i)> n)
				throw new RuntimeException(" no feasable base was found ");
		}
//		int counter =0;
		
		for (int i=1; i <=m; i++){
			
			base.set(i,table.B.get(i));	
		}
//			
//			double element = answer.get(i);		
//			if (element > 0 ){
//				
//				counter++;
//				
//				if (counter> m)
//					throw new RuntimeException(" theres an error , to many bases were found ");
//				
//				base.set(counter, i);
////				BaseValue.set(counter, element); 
//					
//			}
//		}
	}
	
	
	public Vector solve(Vector base){
		  
		 update(base);
		 
		 Pivot pivot= getPivot();
		 
		 while ( pivot != null){
			 
			 changeBase(base, pivot);
			 update(base);
			 pivot= getPivot();
		 }
		 
		Vector answer= new Vector(n);
	    for (int i=1; i<=m; i ++){
	    	answer.set ( (int) base.get(i), p.get(i) );
	    }
	    
	    
		return answer;
		 	
	}
	
	private void changeBase(Vector base,  Pivot pivot){
		
		for (int i=1; i <= m ; i++){
			if (base.get(i) == pivot.outIndex ){
				
				base.set(i, pivot.inIndex);
				 
				return;
			}
			
		}
		
		//this code shloudnt be reached
		throw new RuntimeException("changeBase error - could not find the pivot ouTindex in the base");
		
	}
			

	 
	
	
	
	public Vector solve(){
	 
		 Vector base =new Vector(m);
		 
		 
		 getInitailBase(base);
		 
		 return solve (base);
	}
	
	
	
	
	
//	private void p(){ System.out.println();}
	private void p(Object o){ System.out.println(o);}
	
	
	
	
	
    public void printTable(){
    	p("");
		p("A=");
	  	p(A);
	  	
	  	p("b=");
	  	p(b);
	    
		p("c=");
	  	p(c);
	  	
	  	
	  	if (B != null ){
		  	p("Base=");
		  	p(B);
		   
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
	
	public String toString(){
		return ("{ in: "+ inIndex + ", out: " +outIndex +", new Value: " + newValue +" }");
	}
}
