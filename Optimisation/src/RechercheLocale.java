/*
 
 [Recherche locale]

 */

public class RechercheLocale {

	/*
	 
	 On cherche à minimiser -sin(2x)/(x+1) avec une simple recherche locale
	 
	 (On utlise ici le "meilleur voisin" mais on peut egalement prendre un voisin au hasard) 
	  
	 */
	
	static final int NB_MAX_ITER = 1000000;	
	static final double STEP = 0.0001;
	
	static double currentX = 0;
	static double min = Double.MAX_VALUE;
	static int i = 0;
	
	public static double f(double x){
		return -Math.sin(2*x)/(x+1);
	}
	
	public static double getNext(){
		double temp = Math.min(f(currentX+STEP),f(currentX-STEP));
		if(f(currentX+STEP)>f(currentX-STEP))
			currentX -= STEP;
		else
			currentX += STEP;
		return temp;
	}
	
	public static void main(String[] args) {	
		
		while(getNext()<min && i<NB_MAX_ITER)
			i++;
			
		System.out.println("Minimum obtenu : f("+currentX+")="+f(currentX));
	}
	
}
