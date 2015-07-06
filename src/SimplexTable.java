import java.util.LinkedList;

/*
 * represent a simplex Table of an LP with equalities constraint only Ax=b x>=0
 */
public class SimplexTable {

	private Matrix A;
	private Vector x,b,z;
	
	private Matrix Q,
				   AB,
				   AN;
	private Vector p,r,z0, 
				   xB,  // vector of basic Variables;
				   xN,  // vector of non basic Variables;
				   B,
				   N;
					
				   
				   
	
	public SimplexTable(Matrix A, Vector b, Vector z){
		
		removeDipendetConstraint(A,b, this.A, this.b);
		this.z=z;
		
	}
	
	/*
	 * if m.rows()> m.cols()  there is dependency between rows so this method use gaussien elimination
	 * to remove those
	 */
	private void removeDipendetConstraint(Matrix m,Vector v, Matrix destenationMatrix , Vector destenationVector){
		
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
		
		
		
		destenationMatrix = tmp.getColumns(matrixIndexes);
		destenationVector = tmp.getColumn(m.cols()+1);
		
		if ( !validConstarint(destenationMatrix,destenationVector))
				throw new RuntimeException("constraint arent leagel");
		
		} else {
			destenationMatrix = m;
			destenationVector = b;
		}
		
	}
	
	private boolean validConstarint(Matrix m, Vector b){
		
		for (int r=1; r<=m.rows() ; r++){
			if (m.getRow(r).isZero() && !b.isZero())   // thers a constarint that is 0*x1 +0*x2+..+0*xn= a where a!=0
				return false;
		}
		return true;
	}

	private void calcAB(){
			
	}
	
    private void CalcQ(){
    	 
    }
	
	
	
	
}
