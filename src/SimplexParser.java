import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
 



public class SimplexParser {
	
	private static final String DELIMITER=" ";
	private static final char COMMENTER='#';
 
	
	
	Matrix A;
	Vector b,c;
	
	public SimplexParser(){ 
		A= null; 
		b= null;
	}
	
	public void parseSimplexFile(String fileName) throws Exception  {
		int m,n;
		FileReader fileReader;
		BufferedReader bufferedReader = null;
		 
			
			fileReader = new FileReader(fileName);
			bufferedReader = new BufferedReader(fileReader);
			 
			
			LinkedList<Double> numList=getNumberBuffer(fileReader, bufferedReader);
			
			m =numList.get(0).intValue();
			n =numList.get(1).intValue();
			
			int index=2;
			
			double matrix[][]= new double[m][n];
			double vectorB[] =new double[m];
			double vectorC[] =new double[n];
			
			for (int r=0; r< m; r++){
				for (int c=0; c< n; c++){
					matrix[r][c]= numList.get(index);
					++index;
				}
			}
				
			
			for (int r=0; r < m; r++){
				vectorB[r]= numList.get(index); 
				++index;
			}
			
			for (int r=0; r< n; r++){
				vectorC[r]= numList.get(index); 
				++index;
			}
			
			A= new Matrix(matrix);
			b= new Vector(vectorB);
			c= new Vector(vectorC);
				
			bufferedReader.close();
			fileReader.close();
	  
		
	}
	
	public Matrix getA(){
		return A;
	}
	public Vector getb(){
		return b;
	}
	public Vector getc(){
		return c;
	}
	
	private LinkedList<Double> getNumberBuffer(FileReader fileReader,BufferedReader bufferedReader) throws IOException{
		
		String line;
		LinkedList<Double> numlist= new LinkedList<Double>();
		while((line = bufferedReader.readLine()) != null) {
            
            
            String[] parts = line.split(DELIMITER);
             
            if (parts ==null  || parts.length==0 || parts[0].length()==0 || parts[0].charAt(0)== COMMENTER)
            	continue;
 
            for (int i= 0; i < parts.length; i++){
            	
            	try
            	{
            	numlist.add( Double.parseDouble(parts[i]));
            	} catch (Exception e){
            		
            	}
            }
        }
		
		return numlist;
		   
	}
}
