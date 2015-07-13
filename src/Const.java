
public class Const {
	
	 
	public static final double EPSILON = 1e-10;
	
	
	public static  double round(double x){
		return  Math.round(x  /EPSILON ) * EPSILON;
	}
}
