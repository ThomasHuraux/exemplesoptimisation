/*
 
 [ACO - Ant Colony Optimisation]
 
 Resoution du TSP
 (D'apres http://khayyam.developpez.com/articles/algo/voyageur-de-commerce/colonies-de-fourmis/)

 */

import java.util.ArrayList;
import java.util.Random;

/*
 * ############################################
 */

class Ant{
	
	static final int SEARCHING_PATH = 1;
	static final int RETURNING = 2;
	static final int NOTHING = 3;
	static final int DEAD = 4;
	
	static Random random = new Random();
	
	static Path bestSolution = new Path(Long.MAX_VALUE,new ArrayList<Ville>());
	 
    ArrayList<Ville> visitedCities;
    ArrayList<Ville> citiesStillToVisit;
	
	int state;
    double tmpVisitedLength;
    double currentArcSize;
    double currentArcPos;
    int currentDestination;
    int currentOrigin;
    
    Environment env;
    
    @SuppressWarnings("unchecked")
	Ant(Environment e){

    	visitedCities = new ArrayList<Ville>();
    	citiesStillToVisit = new ArrayList<Ville>();
    	
    	env = e;
    	
        tmpVisitedLength = 0;
        currentArcPos = -1;
        currentDestination = 0;
        currentOrigin = 0;
        state = NOTHING;
        
        citiesStillToVisit = (ArrayList<Ville>) env.villes.clone();
    }
    
    void update(){
    	/*
    	System.out.print("["+this+"] \t");
    	for(Ville i : visitedCities)
    		System.out.print(i.id+"   ");
    	System.out.println();
    	*/
        switch(state){
            case SEARCHING_PATH:
                tmpVisitedLength ++;
            case RETURNING:
                currentArcPos++;               
                if (currentArcPos >= currentArcSize)
                    findNextSearchDestination();
                break;                                       
            case NOTHING:
                findNextSearchDestination();
                break;                
        }
    
    }
    
    void findNextSearchDestination(){
    	switch(state){
    		case NOTHING:{
    			
    			visitedCities.add(env.villes.get(0));
    			citiesStillToVisit.remove(env.villes.get(0));		
    			
    			int dest = getNearCity(0);
    			
    			if(dest != -1){
	    			state = SEARCHING_PATH;
	    			currentOrigin = 0;
	    			currentDestination = dest;
	    			currentArcPos = 0;
	    			currentArcSize = env.arcs[0][currentDestination].distance;
    			}
    			break;
    		}
    		case SEARCHING_PATH:{
    			// on a atteint currentDestination
    			visitedCities.add(env.villes.get(currentDestination));  
    			citiesStillToVisit.remove(env.villes.get(currentDestination));

                if (citiesStillToVisit.size() == 0){
                    // plus rien à visiter, le chemin est complet
                    // on revient vers le nid
    				//tmpVisitedLength += env.arcs[currentDestination][0].distance;

                    state = RETURNING;
                    currentOrigin = visitedCities.get(visitedCities.size()-1).id;
                    currentDestination = visitedCities.get(visitedCities.size()-2).id;    
                    currentArcSize = env.arcs[currentOrigin][currentDestination].distance;
                    currentArcPos = currentArcSize;       
                    return;                                
                }

    			int dest = getNearCity(currentDestination);
    			if(dest != -1){
	    			currentOrigin = currentDestination;
	    			currentDestination = dest;			
	    			currentArcSize = env.arcs[currentOrigin][currentDestination].distance;
	    			currentArcPos = 0;
    			}
    			break;
    		}
    		case RETURNING:{
    			env.setPheromones(currentOrigin,currentDestination,tmpVisitedLength);

    			if (currentDestination == 0){

    				if(bestSolution.value>tmpVisitedLength){
    					bestSolution = new Path(tmpVisitedLength,visitedCities);
    					System.out.print("["+Ant.bestSolution.value+"] \t");
    			    	for(Ville i : Ant.bestSolution.cities)
    			    		System.out.print(i.id+"   ");
    			    	System.out.println();
    				}
    				
    				state = DEAD;
                }else{
               
                // trouver la ville précédemment visitée et la passer en destination
                // mettre des phéromones sur l'arc parcouru
                currentOrigin = currentDestination;
                currentDestination = visitedCities.get(visitedCities.indexOf(env.villes.get(currentDestination))-1).id;          
                currentArcSize = env.arcs[currentOrigin][currentDestination].distance;                                          
    			currentArcPos = currentArcSize;
                }
    			break;
    		}
    	}	
    }
    
    int getNearCity(int from){
    	
        double max = -1;
        int maxId = 0;
    
        for (int i = 0; i < citiesStillToVisit.size(); i++){
        	double w = random.nextFloat()*env.arcs[from][citiesStillToVisit.get(i).id].pheromone;
        	//System.out.print(env.arcs[from][citiesStillToVisit.get(i).id].pheromone+":"+w+"\t");
        	if( w > max){
        		max = w;
        		maxId = citiesStillToVisit.get(i).id;
        	}
        }
        //System.out.println();
    	return maxId;
    }
 
}

/*
 * ############################################
 */

class Ville{
	String nom;
	int id;
	
	Ville(int i){
		id = i;
	}
}

/*
 * ############################################
 */

class Arc{
	
	int distance;
	double pheromone;
	
	public Arc(int distance, double ph) {
		this.distance = distance;
		this.pheromone = ph;
	}
	
}

/*
 * ############################################
 */

class Environment{
	
	Random random = new Random();
	
	Arc[][] arcs;
	ArrayList<Ville> villes;
    int nbCities;
    double borneMax, borneMin;
    double evaporation;
    int optimalLength;
    
    Environment(int nbVilles, double borne1, double borne2, double coeff){
    	
    	nbCities = nbVilles;
    	borneMax = borne1;
    	borneMin = borne2;
    	evaporation = coeff;
    	
    	villes = new ArrayList<Ville>();
    	arcs = new Arc[nbCities][nbCities];
    	
    	for(int i=0; i<nbCities; i++){
    		villes.add(new Ville(i));
            arcs[i][i] = new Arc(0,borneMin);
    		for (int j = i+1; j<nbCities; j++)
                arcs[i][j] = arcs[j][i] = new Arc(random.nextInt(100),borneMin);
    	}
    	
        // solution optimale
        for (int i=0; i<nbCities; i++)
            arcs[i][(i+1)%nbCities].distance = arcs[(i+1)%nbCities][i].distance = 1;

    	optimalLength = nbCities;
    }
    
    void setPheromones(int i, int j, double wayLength){
        double ph = 100*optimalLength / (double)(wayLength + 1 - optimalLength);
        //System.out.println(ph);
        arcs[i][j].pheromone += ph;

    	if( arcs[i][j].pheromone < borneMin)
    		arcs[i][j].pheromone = borneMin;
    	
    	if (arcs[i][j].pheromone > borneMax)
    		arcs[i][j].pheromone = borneMax;

    	arcs[j][i].pheromone = arcs[i][j].pheromone;
    }

    void evaporate(){
        for (int i=0; i<nbCities; i++)
            for (int j=0; j<i; j++){
            	arcs[i][j].pheromone *= evaporation;
                if ((int)(arcs[i][j].pheromone) < borneMin)
                	arcs[i][j].pheromone = borneMin;
                //System.out.println(arcs[i][j].pheromone);
                arcs[j][i].pheromone = arcs[i][j].pheromone;                
            }
    }
}

/*
 * ############################################
 */

class Path{
	
	double value;
    ArrayList<Ville> cities;
    
	public Path(double value, ArrayList<Ville> cities) {
		this.value = value;
		this.cities = cities;
	}
    
}

/*
 * ############################################
 */

public class ACO {

	static ArrayList<Ant> ants = new ArrayList<Ant>();
	static Environment env;
	
	static int curIteration;
	
	static void init(int nbAnt, Environment e){
		
		env = e;
		
		for(int i=0; i<nbAnt; i++)
			ants.add(new Ant(env));

	}
	
	static void run(int n){        
	    for (curIteration=0; curIteration<n; curIteration++){
	        // process each ant
	        for(int i=0; i<ants.size(); i++){
	        	if(ants.get(i).state != Ant.DEAD)
	        		ants.get(i).update();
	        	else{
	        		//System.out.println("Init a new ant");
	        		ants.set(i,new Ant(env));
	        	}
	        }
	       
	        // evaporate the pheromones
			if (curIteration % 20 == 0)
				env.evaporate();  
			
				
		    if(Ant.bestSolution.value <= env.nbCities){
		    	System.out.println(" - Solution optimale trouvee -");
				return;
			}
	    	
	    }
	    
	    System.out.println(" - Solution optimale non trouvee -");
	}
	
	public static void main(String[] args) {
	
		Environment env = new Environment(20, 500.0, 1.0, .0001);
		init(1000,env);
		run(1000000);

	}

}
