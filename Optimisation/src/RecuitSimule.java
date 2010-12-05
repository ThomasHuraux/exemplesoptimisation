/*
 
 [Recuit Simule]

 Pour info, le recuit simule est la premiere metaheuristique qui a ete proposee.

 */

import java.util.Random;

public class RecuitSimule {

	/*
	 
	 On cherche à minimiser f(x) à l'aide du recuit simulé
	 
	 f(x) = 10*sin((0.3*x)*sin(1.3*x^2 + 0.00001*x^4 + 0.2*x^+ 80))
	 
	 [d'après http://www-lih.univ-lehavre.fr/~olivier/enseignement/Maitrise/TD/recuit/TDrecuit.html]
	 
	 */
	
	static final double BOLZMANN = Math.pow(1.380510,-23);
	
    static final int NB_MAX_ITER = 1000000;;
	static final double STEP = 0.0001;
	
    static double maxTemperature;
    static double minTemperature;

	
	public static double f(double x){
		return 10*Math.sin((0.3*x)*Math.sin(1.3*Math.pow(x,2) + 0.00001*Math.pow(x,4) + 0.2*x+ 80));
	}

	public static void main(String[] args) {
		 @SuppressWarnings("unused")
        Random r = new Random();
        
        double x = 1;
        double temperature = maxTemperature;
        
        int currentIteration = 0;
        @SuppressWarnings("unused")
		double eval = f(x);
        
        int totalIt = 1;
        while(temperature > minTemperature){
                for(int i = 0; i < NB_MAX_ITER; i++){
                        currentIteration++;
                        
                        /*
                         * [A TERMINER]
                         */
                        
                        
                        totalIt++;
                }
                temperature *= STEP;
        }
	}

}
