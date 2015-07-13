import java.util.Scanner;

 


public class Q3 {

	static final Scanner scan= new Scanner(System.in);
	static int n;
	static Vector p,q;
	static final String defaultFileName="coupling.txt"; 
	
	static SimplexTable table;
	public Q3() {
		
	}
	
	
	
	public static void run(){
	   
	  
	 parseFile();
	 Coupling coupling=new Coupling(p, q);
		 
	 Matrix A =coupling.getConstraint();
	 Vector b= coupling.get_b();
	 Vector c= coupling.get_c();
	 
	 
	 table= new SimplexTable (A,b,c, SimplexTable.MODE.EQUALITY);
	 Vector r[]={b};
	 
//	 Matrix k = (new Matrix(r)).transpose();
//	 Matrix g= A.addColumns(k);
// 	 p(A.rowReduce());
//  	 p(b);
// 	 Vector base= Vector.runningNumbersVector(1, 4);
	 Vector ans= table.solve();
//	 p(coupling.answerToMatrix(ans));
		
	}
	
	private static void parseFile(){
		String fileName;
		CouplingParser parser= new CouplingParser();
//		p("please enter the file name (or press ENTER for default coupling.txt)):");
//		fileName= scan.nextLine();
//			if (fileName==null || fileName.length()==0 || fileName=="\n")
				fileName= defaultFileName;
		
		try{
			parser.parseCouplingFile(fileName);
			
			q= parser.get_q();
			p= parser.get_p();
			
		} catch (Exception e) {
			
			p("there was an error opening file "+ fileName);
			p(e.getMessage());
		}
		
	}
	
	private static void p(Object o)
	{
		System.out.println(o);
	}
	
	private  static void p()
	{
		System.out.println();
	}
	

}

 
 


class Coupling{
	
	int n;
	Vector q,p;
	
	public Coupling(Vector q, Vector p) {
		 
		if (q.size()!= p.size()){
			throw new  RuntimeException("coupling of different size vectors");
		}
		
		if (!checkDistribution(q)){
			throw new  RuntimeException("Q distribution sum of probabilies is not 1: "+ q);
		}
		
		if (!checkDistribution(p)){
			throw new  RuntimeException("P distribution sum of probabilies is is not 1" + p);
		}
		
		this.n = q.size();
		this.q = q;
		this.p = p;
		 
		
		
		
	}
	private boolean checkDistribution(Vector v){
		
		double sum=0;
		
		for (int i=1; i<= v.size() ; i++ ){
			sum+= v.get(i);
		}
		return Math.abs(1-sum)<= Const.EPSILON;	
	}
	
	public static int toIndex(int row,int col, int  n){
		
		int index= (row-1) * n + (col);
		if (index<=0 || index>n*n)
			throw new RuntimeException("index of coupling out of bundaries");
		return index;
		
	}
	
//	public static Coordinate toCoord(int index ,int n ){
//		
//		if (index<=0 || index>n*n)
//			throw new RuntimeException("index of coupling out of bundaries");
//		
//		 return new Coordinate( ( (index-1) / n) +1, (index-1) % (n) +1  );
//	}

	
	public Vector get_c(){
		
		Vector m = new Vector(n*n);
		for (int r=1; r<=n; r++){
			for (int c=1; c <= n; c++){
				if (r!=c)
					m.set( toIndex(r, c, n) , -1);
			}
			
		}
		return m;
	}
	
	public Matrix getConstraint(){
		
		int n2=n*n;
		Matrix A = new Matrix( 2*n , n2);
		
	 
		for (int p=1; p<=n; p++){ 
				for (int i=1 ; i <= n; i++){
					
					A.set(p, (p-1)*n + i,1);
					A.set(p+n, (i-1)*n +p ,1);
				}
		}	
	 
		return A;
	}  
	
	public Vector get_b(){
		
		Vector b= new Vector(2*n);
		
		for (int i=1; i<= n; i++){
			b.set(i, p.get(i));
			b.set(i+n, q.get(i));
		}
		
		return b;
	}
	
	public Matrix answerToMatrix(Vector ans){
		
		if (ans.size()!=n*n){
			throw new RuntimeException("answerToMatrix answer not in proper size");
		}
		Matrix res= new Matrix(n,n);
		int n2=n*n;
		for (int r=1; r<=n; r++){
			for (int c=1; c <= n; c++){
				
				double val=ans.get(toIndex(r, c, 3));
				res.set(r, c, val);
			}
		}
		return res;
		
	}
	
}
