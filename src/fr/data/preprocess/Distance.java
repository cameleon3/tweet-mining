package fr.data.preprocess;

public class Distance {

	public static void main(String[] args) {
		System.out.println(" premier");
		
		System.out.println(" x1 : "+vectorLenght(new Vector(1, 2), new Vector(1, 3)));
		System.out.println(" x1 : "+vectorLenght(new Vector(1, 2), new Vector(5, 7)));
		System.out.println("\n\n next");
		
		System.out.println(" x2 : "+vectorLenght(new Vector(2, 4), new Vector(1, 3)));
		System.out.println(" x2 : "+vectorLenght(new Vector(2, 4), new Vector(5, 7)));
		System.out.println("\n\n next");
		
		System.out.println(" x4 : "+vectorLenght(new Vector(2, 8), new Vector(1, 3)));
		System.out.println(" x4 : "+vectorLenght(new Vector(2, 8), new Vector(5, 7)));
		System.out.println("\n\n next");
		
		System.out.println(" x5 : "+vectorLenght(new Vector(2, 3), new Vector(1, 3)));
		System.out.println(" x5 : "+vectorLenght(new Vector(2, 3), new Vector(5, 7)));
		System.out.println("\n\n next");
		
		
		System.out.println(" x7 : "+vectorLenght(new Vector(3, 3), new Vector(1, 3)));
		System.out.println(" x7 : "+vectorLenght(new Vector(3, 3), new Vector(5, 7)));
		System.out.println("\n\n next");
		
		System.out.println(" x8 : "+vectorLenght(new Vector(4, 6), new Vector(1, 3)));
		System.out.println(" x8 : "+vectorLenght(new Vector(4, 6), new Vector(5, 7)));
		System.out.println("\n\n next");
		
		System.out.println(" x9 : "+vectorLenght(new Vector(2, 1), new Vector(1, 3)));
		System.out.println(" x1 : "+vectorLenght(new Vector(2, 1), new Vector(5, 7)));
		System.out.println("\n\n next");
		
		System.out.println(" x10 : "+vectorLenght(new Vector(2, 2), new Vector(1, 3)));
		System.out.println(" x1 : "+vectorLenght(new Vector(2, 2), new Vector(5, 7)));
		System.out.println("\n\n next");
		
		System.out.println(" x11 : "+vectorLenght(new Vector(4, 7), new Vector(1, 3)));
		System.out.println(" x1 : "+vectorLenght(new Vector(4, 7), new Vector(5, 7)));
		System.out.println("\n\n next");
		
		System.out.println(" x12 : "+vectorLenght(new Vector(4, 8), new Vector(1, 3)));
		System.out.println(" x1 : "+vectorLenght(new Vector(4, 8), new Vector(5, 7)));
		System.out.println("\n\n next");
		
		System.out.println(" x13 : "+vectorLenght(new Vector(5, 6), new Vector(1, 3)));
		System.out.println(" x1 : "+vectorLenght(new Vector(5, 6), new Vector(5, 7)));
		System.out.println("\n\n next");
		
		
		System.out.println(" x14 : "+vectorLenght(new Vector(1, 4), new Vector(1, 3)));
		System.out.println(" x1 : "+vectorLenght(new Vector(1, 4), new Vector(5, 7)));
		System.out.println("\n\n next");
		
		System.out.println(" x15 : "+vectorLenght(new Vector(6, 6), new Vector(1, 3)));
		System.out.println(" x1 : "+vectorLenght(new Vector(6, 6), new Vector(5, 7)));
		System.out.println("\n\n next");
		
		System.out.println(" x16 : "+vectorLenght(new Vector(6, 5), new Vector(1, 3)));
		System.out.println(" x1 : "+vectorLenght(new Vector(6, 5), new Vector(5, 7)));
		System.out.println("\n\n next");
		
		System.out.println(" x17 : "+vectorLenght(new Vector(5, 3), new Vector(1, 3)));
		System.out.println(" x1 : "+vectorLenght(new Vector(5, 3), new Vector(5, 7)));
		System.out.println("\n\n next");
		
		System.out.println(" x18 : "+vectorLenght(new Vector(6, 9), new Vector(1, 3)));
		System.out.println(" x1 : "+vectorLenght(new Vector(6, 9), new Vector(5, 7)));
		System.out.println("\n\n next");
		
		System.out.println(" x19 : "+vectorLenght(new Vector(5, 8), new Vector(1, 3)));
		System.out.println(" x1 : "+vectorLenght(new Vector(5, 8), new Vector(5, 7)));
		
		System.out.println("\n\n next");
		
		System.out.println(" x1 : "+vectorLenght(new Vector(7, 7), new Vector(1, 3)));
		System.out.println(" x1 : "+vectorLenght(new Vector(7, 7), new Vector(5, 7)));
		
	
	
		
		


	}

	
	
	public static double vectorLenght(Vector a,Vector b){
		
		return Math.sqrt(Math.pow(a.getX()-b.getX(),2)+ Math.pow(a.getY()-b.getY(),2) );
	}
}
