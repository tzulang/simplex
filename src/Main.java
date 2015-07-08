
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
		
		p(A);
		p(b);
		p(c);
		SimplexTable table= new SimplexTable(A,b,c);
		
		double base[]={1,2};
		Vector vb= new Vector(base);
		table.update(vb);
		
		
		
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


