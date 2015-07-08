
public class Main {

	public static void main(String[] args) {

		double v[]= {11,12,13,14,15};
		double in[]= {3,1,5};
		
		Vector vec= new Vector(v);
		Vector ind= new Vector(in);
		
		p(vec.getElemts(ind));
		
		
	}

	
	
	static void test(){
		
		Parser parser= new Parser();
		parser.readFile("./LP.txt");
		
		Matrix A= parser.getA();
		Vector b= parser.getb();
		Vector c= parser.getc();
		
	 
		SimplexTable table= new SimplexTable(A,b,c);
		
		double base[]={1,2};
		double baseVal[]={0,0};
		
		table.update( new Vector(base), new Vector(baseVal));
		table.printTable();
		
		
		
		
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


