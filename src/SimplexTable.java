import java.util.LinkedList;

/*
 * represent a simplex Table of an LP with equalities constraint only Ax=b x>=0
 */
public class SimplexTable {

	private Matrix A;
	private Vector x,b,c;
	double z;
	
	private Matrix Q, AB,AN;
	private Vector p,r,
				   xB,  // vector of basic Variables;
				   xN,  // vector of non basic Variables;
				   B,
				   N,
				   cB,cN;
	double z0;
					
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
		
		if ( !validConstarint(m,b))
				throw new RuntimeException("constraint arent leagel");
		
		} else {
			A = m;
			b = v;
		}
		
	}
	
	private boolean validConstarint(Matrix m, Vector b){
		
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
    	
    	Main.p(ABinverse.cols());
    	Main.p(b);
    	p = ABinverse.mult(b);
    	z0 = cB.dot( ABinverse.mult(b));  // z0= cB* AB^-1 * b
    	
    	Matrix tmp = (ABinverse.mult(AN)).transpose();
    	
    	r= tmp.mult(cB);  // r=  (cB^T * AB^-1 * AN)^T =  (AB^-1*AN)^T * cB  
    	
    	z= z0+ r.dot(xN);
    	
    	
//    	Main.p("AB");
//    	Main.p(AB);
//    	
//    	Main.p("AN");
//    	Main.p(AN);
//    	
//    	Main.p("Q");
//    	Main.p(Q);
//    	
//    	Main.p("p");
//    	Main.p(p);
//    	
//    	Main.p("z0");
//    	Main.p(z0);
//    	
//    	Main.p("r");
//    	Main.p(r);
    }
	
	
	
}
