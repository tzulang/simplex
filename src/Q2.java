import java.util.Scanner;

 


public class Q2 {

	static final Scanner scan= new Scanner(System.in);
	static int n ;
	static int k ;
	static double pi;
	static SimplexTable table;
	public Q2() {
		
	}
	
	
	
	public static void run(){
		
		
//		
//		getParams();
//		
//		Matrix A =  createConstraint(n, k) ;
//		Vector b = create_b(k, 0.5);
//		Vector c = create_c(n);
//		
//		p("n="+ n +"\tk="+ k+ "\tpi="+ pi);
//		p("A=");
//		p(A);
//		p("b=");
//		p(b);
//		p("c=");
//		p(c);
//		 
//		table = new SimplexTable(A, b, c, SimplexTable.MODE.EQUALITY);
//		p("\npress ENTER to continue calculation");
//		scan.nextLine();
//		
//		calc();
		
		Matrix A = null;
		Vector b = null;
		Vector c = null;
		
		for (int n= 3; n<=11; n+=2 )
		{
			k=n-2;
			A =  createConstraint(n, k) ;
			b = create_b(k, 0.5);
			c = create_c(n);
			table = new SimplexTable(A, b, c, SimplexTable.MODE.EQUALITY);
		    table.solve(); 
			p("q_n( n="+n+")= " + (double) table.getMaxValue()); //(Math.round() / 100000);
		}
		
	}
	
	private static void calc(){
		
		Vector ans=table.solve(); 
		p("Simplex Table at the End Of the process:");
		table.printTable();
		p();
	
		p("\nthe answer is");
		p("Q = " + table.cutAnswerToOriginalVariables(ans) + "\n");
		p("p(x1=1,x2=1,... x_n=1) = " + table.getMaxValue());
	}
	private static void getParams(){
		
		
		boolean done = false;
		while (! done){
			p("please enter n:");
			
			String in= scan.nextLine();
			try{
			 
				n=Integer.parseInt(in);
				if (n>0)
					done = true;
					
			} catch (Exception e){
				done = false;
			}
		
		}
		
		done = false;
		while (! done){
			p("please enter k:");
			
			String in= scan.nextLine();
			try{
			 
				k=Integer.parseInt(in);
				if (k>0 && k <= n)
					done = true;
					
			} catch (Exception e){
				done = false;
			}

		}
		
		done = false;
		while (! done){
			p("please enter pi:");
			
			String in= scan.nextLine();
			try{
			 
				pi=Double.parseDouble(in);
				if (pi>0.0 && pi < 1.0)
					done = true;
					
			} catch (Exception e){
				done = false;
			}

		}
	
	}
	private static Vector create_c(int n){
		
		Vector res = new Vector(n+1);
		res.set(n+1,1);
		return res;
	}
	
	private static Vector create_b(int k, double pi)
	{
		double res[] =new double[k+1];
		for (int i=0; i<= k; i++){
			res [i]= Math.pow(pi, i);
		}
		return new Vector(res);
		
	}
	private static Matrix createConstraint(int n,int k){
		
		double res[][]= new double[k+1][n+1];
		
		for (int r= 0;  r <= k; r++){
			for (int c= 0;  c <= n; c++){
				
				 
				 res[r][c] = binom(n-r, c-r);
				
			}
		}
		
		
		return new Matrix (res);
		
	}
	
	 public static double binom(int n, final int k) {
		 	
		 	if (k<0)
		 		return 0;
		    final int min = (k < n - k ? k : n - k);
		    double bin = 1;
		    for (int i = 1; i <= min; i++) {
		      bin *= n;
		      bin /= i;
		      n--;
		    }
		    return bin;
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
