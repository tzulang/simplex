import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
 



public class CouplingParser {
	
	private static final String DELIMITER=" ";
	private static final char COMMENTER='#';
 
	
	
	 
	private Vector p,q;
	
	public CouplingParser(){ 
		q= null; 
		p= null;
	}
	
	public void parseCouplingFile(String fileName) throws Exception  {
		int m,n;
		FileReader fileReader;
		BufferedReader bufferedReader = null;
		 
			
			fileReader = new FileReader(fileName);
			bufferedReader = new BufferedReader(fileReader);
			 
			
			LinkedList<Double> numList=getNumberBuffer(fileReader, bufferedReader);
			
			 
			n =numList.get(0).intValue();
			
			int index=1;
			
			 
			double vectorQ[] =new double[n];
			double vectorP[] =new double[n];
			
		 
			
			for (int r=0; r < n; r++){
				vectorP[r]= numList.get(index); 
				++index;
			}
			
			for (int r=0; r< n; r++){
				vectorQ[r]= numList.get(index); 
				++index;
			}
			
		 
			q= new Vector(vectorQ);
			p= new Vector(vectorP);
				
			bufferedReader.close();
			fileReader.close();
	  
		
	}
	
	public Vector get_q(){
		return q;
	}
	public Vector get_p(){
		return p;
	}
 
	private LinkedList<Double> getNumberBuffer(FileReader fileReader,BufferedReader bufferedReader) throws IOException{
		
		String line;
		LinkedList<Double> numlist= new LinkedList<Double>();
		while((line = bufferedReader.readLine()) != null) {
            
            
            String[] parts = line.split(DELIMITER);
             
            if (parts ==null  || parts.length==0 || parts[0].charAt(0)== COMMENTER)
            	continue;
 
            for (int i= 0; i < parts.length; i++){
            	
            	try
            	{
            	if (parts[i].length()!=0)
            		numlist.add( Double.parseDouble(parts[i]));
            	} catch (Exception e){
            		
            	}
            }
        }
		
		return numlist;
		   
	}
}
