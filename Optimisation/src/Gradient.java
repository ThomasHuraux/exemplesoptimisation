/*
 
 [Descente de gradient]

 */


public class Gradient {
	
	/*
	 
	 On cherche à minimiser E(x,y) avec une descente de gradient
	 
	 On a :
	 
	 xi+1 = xi – nu dE/dx(xi, yi)
	 yi+1 = yi – nu dE/dy(xi, yi)
	 
	 E(x,y) = (x-1)(x-2) + (y+3)(y+4)
	 dE/dx(x, y) = 2x - 3
	 dE/dy(x, y) = 2y + 7
	  
	 */


	public static void main(String[] args) {
		
		System.out.println("[DESCENTE DE GRADIENT]");
		
		int NB_ITER = 10000;
		double MIN_ERREUR = 0.01;
		
		double nu = 0.01;
		
		double x = 1;
		double y = 6;
		double E = 1;
		
		double erreurx = 1000;
		double erreury = 1000;
		double erreur = 1000;
		
		double dEdx = 1;
		double dEdy = 1;

		for(int i=0; i < NB_ITER && erreur > MIN_ERREUR; i++){
			
			E = (x-1)*(x-2) + (y+3)*(y+4);
			
			dEdx = 2*x-3;
			dEdy = 2*y+7;
			
			x -= nu*dEdx;
			y -= nu*dEdy;
			
			erreurx = (dEdx>0) ? dEdx : -dEdx;
			erreury = (dEdy>0) ? dEdy : -dEdy;
			erreur = (erreurx>erreury) ? erreurx : erreury;
			
			if(i%10 == 0){
				System.out.println("x="+x+" y="+y+" E="+E);
				System.out.println("\tdE/dx="+dEdx+" dE/dy="+dEdy);
			}
		}

	}

}
