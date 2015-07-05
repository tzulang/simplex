
public class Main {

	public static void main(String[] args) {

		
		float[] f1={1,2,3};
		float[] f2={4,5,6};
		
		
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
		
		float f= v1.dot(v2);
		p("v1*v2= " + f);
		
		 f= v2.dot(v1);
		p("v2*v1= " + f);
		
		
		p("v1.get(1)=" + v1.get(1));
		p("v1.get(2)=" + v1.get(2));
		p("v1.set(2, 5.5)");
		v1.set(2, 5.5f);
		p("v1.get(2)=" + v1.get(2));
		p("v1.get(3)=" + v1.get(3));
		p("v1.get(-1)=" + v1.get(-1));
	}

	
	static void p(Object o){
		System.out.println(o);
	}
}


