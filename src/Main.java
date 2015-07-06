
public class Main {

	public static void main(String[] args) {

		test();
	}

	
	static void p(Object o){
		System.out.println(o);
	}
	
	static void test(){
		

		double[] f1={1,2,3};
		double[] f2={4,5,6};
		
		
		Vector v1= new Vector(f1);
		Vector v2= new Vector(f2);
		Vector v3=null;
		
		p(v1);
		p(v2);
		
		v3= v1.add(v2);
		p("v1+v2= " +v3);
		
		v3= v2.add(v1);
		p("v2+v1= " + v3);
		
		v3= v1.add(v1);
		p("v1+v1= " + v3);
		
		
		v3= v2.sub(v1);
		p("v2-v1= " + v3);
		
		v3= v1.sub(v2);
		p("v1-v2= " + v3);
		
		v3= v1.mult(2);
		p("v1*2= " + v3);
		
		double f= v1.dot(v2);
		p("v1*v2= " + f);
		
		 f= v2.dot(v1);
		p("v2*v1= " + f);
		
		
		p("v1.get(1)=" + v1.get(1));
		p("v1.get(2)=" + v1.get(2));
		p("v1.set(2, 5.5)");
		v1.set(2, 5.5f);
		p("v1.get(2)=" + v1.get(2));
		p("v1.get(3)=" + v1.get(3));
		 
		
		p("");
		p("");
		
		double[][] f3={{1,2,3},{4,5,6},{7,8,9}};
		double[][] f4={{5,3,1},{0,8,2},{6,6,-1}};
		
		Matrix m1 = new Matrix(f3);
		Matrix m2 = new Matrix(f4);
		
		p(m1);
		p("");
		p(m2);
		p("");
		Matrix m3= m1.mult(m2);
		p(m3);
		p("");
		
		m3= m1.mult(3);
		p(m3);
		p("");
		
		
		v3=new Vector(v1);
		
		p( v1.equals(v1));
		p( v1.equals(v2));
		p( v1.equals(v3));
		p("");
		
		m3= new Matrix(m1);
		p( m1.equals(m1));
		p( m1.equals(m2));
		p( m1.equals(m3));
		
		v3= m1.mult(v1);
		p(m1);
		p(v1);
		p(v3);
		p("");
		p( Matrix.getIdentity(3));
		
		p("");
		p( m1.transpose().addColumns(3));
		
		p("");
		p( m1.transpose().addColumns( Matrix.getIdentity(3)));
		
		
		double  f5[] ={1,3};
		Vector ind= new Vector(f5);
		p("");
		p(v1);
		p(v1.getElemts(ind));
		
		
		double  f6[] ={1,0,1};
		Vector mask= new Vector(f6);
		p("");
		p(v1);
		p(v1.mask(mask));
		
		
		
		double[][] f7={{1,2,4},{3,6,12}};
		m3= new Matrix (f7);
		
		p("");
		p(m3); 
		p("");
		p(m3.rowReduce()); 
		p("");
		
		p("");
		p(m1); 
		p("");
		p(m2.inverse().mult(m2)); 

		
	}
}


