/*
 
 [Resolution du TSP]
  Méthodes :
   -recherche tabou
   -algo fourmis (ACO)
   
 */


import java.util.ArrayList;

class Chemin{
	public Ville v1 ;
	public Ville v2 ;
	public int distance;
	
	
	public Chemin(Ville v1, Ville v2, int d){
		this.v1 = v1 ;
	    this.v2 = v2 ;
	    distance = d;
	}
}

class Chemins{
	public ArrayList<Chemin> chemins;
	
	public Chemins(){
		chemins = new ArrayList<Chemin>();
	}
	
	public void createChemin(Ville v1, Ville v2, int d){
		chemins.add(new Chemin(v1,v2,d));
	}
	
	public int getDistance(Ville v1, Ville v2){
		int d = -1;
		for(int i = 0; i<chemins.size(); i++){
			if(chemins.get(i).v1.equals(v1) && chemins.get(i).v2.equals(v2)
				|| chemins.get(i).v2.equals(v1) && chemins.get(i).v1.equals(v2)
			)
			d = chemins.get(i).distance;
		}
		return d;
	}
}

class Ville{
   public String nom ;
   
   public Ville(String n){
      nom = n ;
   }
}

class Solution{
	
	public ArrayList<Ville> liste;
	
	public Solution(ArrayList<Ville> villes){
		
		liste = new ArrayList<Ville>();
		
		for(int i = 0; i<villes.size(); i++){
			Ville v = villes.get((int)(Math.random() * villes.size()));
			while(liste.contains(v))
				v = villes.get((int)(Math.random() * villes.size()));
			
			liste.add(v);
		}
	}
	
	public int evaluer(Chemins chemins)
	{
		int v = 0;
		for(int i = 0; i<liste.size()-1; i++)
			v += chemins.getDistance(liste.get(i), liste.get(i+1));
		return v;
	}
	
	public void affiche(){
		for(int i = 0 ; i < liste.size(); i++)
			System.out.print(liste.get(i).nom +" - ");
		System.out.println();
	}
}

//########################## METHODE TABOU ##############################

class Tabou{
	
	public Tabou(){
		
		ArrayList<Ville> villes = new ArrayList();
		
		Ville v0 = new Ville("Ville0");
		villes.add(v0);
		Ville v1 = new Ville("Ville1");
		villes.add(v1);
		Ville v2 = new Ville("Ville2");
		villes.add(v2);
		Ville v3 = new Ville("Ville3");
		villes.add(v3);
		Ville v4 = new Ville("Ville4");
		villes.add(v4);
		Ville v5 = new Ville("Ville5");
		villes.add(v5);
		Ville v6 = new Ville("Ville6");
		villes.add(v6);
		
		Chemins chemins = new Chemins();
		chemins.createChemin(v0, v1, 6);
		chemins.createChemin(v0, v6, 8);
		chemins.createChemin(v1, v6, 5);
		chemins.createChemin(v0, v2, 7);
		chemins.createChemin(v2, v3, 2);
		chemins.createChemin(v2, v4, 4);
		chemins.createChemin(v5, v6, 4);
		chemins.createChemin(v3, v4, 3);
		chemins.createChemin(v3, v5, 2);
		chemins.createChemin(v4, v5, 3);
	}
	
}

//########################## ACO ################################

// http://khayyam.developpez.com/articles/algo/voyageur-de-commerce/colonies-de-fourmis/


//################################################################
public class TSP{
	
	

	public static void main(String[] args) {
		
	}
	
}
