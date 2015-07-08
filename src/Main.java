
public class Main {

	public static void main(String[] args) {

		test();
		
	}

	
	
	static void test(){
		
		Parser parser= new Parser();
		parser.readFile("./LP.txt");
		
		Matrix A= parser.getA();
		Vector b= parser.getb();
		Vector c= parser.getc();
		
	 
		SimplexTable table= new SimplexTable(A,b,c);
		
		double nb[] ={3,4,5};
		double nbv[] ={1,3,2};
		
		Vector newBasis=  new Vector(nb);
		Vector newBaseValues=  new Vector(nbv);
		
//		table.update(newBasis, newBaseValues);
//		table.printTable();
		
		Vector ans=table.solve(); 
		p("Simplex Table at the End Of the process:");
		table.printTable();
		p();
		p("the answer is x= " + ans + "\n");

		 
		
		
		
	}
	
	
	
	static void p(Object o)
	{
		System.out.println(o);
	}
	
	static void p()
	{
		System.out.println();
	}
}


