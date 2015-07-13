import java.util.Scanner;


public class GeneralSimplexSolver {

	public GeneralSimplexSolver() {
		 
	}


	static final String defaultPath="./readme.txt";
	static final Scanner scan= new Scanner(System.in);
	static String fileName;
	static SimplexTable.MODE mode;
	static SimplexTable table;
	static  void getFileName(){
		
		fileName=null;
		
		p("please enter file Name or press ENTER for opening default file (readme.txt') : "); 
	    fileName= scan.nextLine();
		if (fileName==null || fileName.length()==0 || fileName=="\n")
			fileName=defaultPath;
		
		
	}
	
	private static void getMode(){
		 
		
		String in = null;
		char c;
		while ( true) {
			p("please define if the simplex table have equalities or inequalities  [E/I]:");		
			in= scan.nextLine();
			
			if (in.length()== 1){
				c= in.charAt(0);
				if (c =='E' || c =='e' || c=='I' || c=='i')
					break;
			}
				
		}
		if (c =='E' || c =='e' )
				mode = SimplexTable.MODE.EQUALITY; 
		if ( c=='I' || c=='i')
				mode =SimplexTable.MODE.INEQUALITY;
			
	}
	
	
	private  static void parse(String fileName) throws Exception{
		
		SimplexParser simplexParser= new SimplexParser();
		
		simplexParser.parseSimplexFile(fileName);
		
		Matrix A= simplexParser.getA();
		Vector b= simplexParser.getb();
		Vector c= simplexParser.getc();
		
	 
		table= new SimplexTable(A,b,c,mode);
	}
	public static void run(){
		
		getFileName(); 
		getMode();
		try {
			parse(fileName);
		} catch (Exception e) {

			p("there was an error opening file "+ fileName);
			p(e.getMessage());
		}
		 
		Vector ans=table.solve(); 
		p("Simplex Table at the End Of the process:");
		table.printTable();
		p();
		p("the answer is x= " + table.cutAnswerToOriginalVariables(ans) + "\n");
		
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
