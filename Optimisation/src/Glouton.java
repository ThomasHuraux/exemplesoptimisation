/*
 
 [Problème du sac à dos]
 Utilisation d'un algorithme glouton
  
 */


import java.util.ArrayList;

class Boite {

	public int poids;
	boolean isIn = false;
	
	public Boite(int poids) {
		this.poids = poids;
	}
}

public class Glouton {

	public static void main(String[] args) {
	
		int NB_OBJETS = 50;
		int MAX_POIDS_SAC = 200;
		
		int MAX_POIDS = 200;
		int MIN_POIDS = 10;
		
		ArrayList<Boite> objets = new ArrayList<Boite>();

		// Generation aleatoire des objets
		for(int i=0; i<NB_OBJETS; i++){		
			Boite b = new Boite(
					(int)(Math.random() * (MAX_POIDS-MIN_POIDS)) + MIN_POIDS
			);			
			objets.add(b);
		}
		
		// On considere que les objets sont tries par ordre décroissant d'efficacite
		
		int poids_conso = 0;
		int valeur_total = 0;

		for(int i = 0; i<NB_OBJETS; i++){
			if(objets.get(i).poids + poids_conso <= MAX_POIDS_SAC){
				objets.get(i).isIn = true;
				poids_conso += objets.get(i).poids;
				valeur_total += NB_OBJETS-i;
			}else 
				objets.get(i).isIn = false;
		}
		
		// Affichage :
		for(int i = 0; i<NB_OBJETS; i++){
			System.out.print("O"+(i+1)+" w="+objets.get(i).poids);
			if(objets.get(i).isIn)
				System.out.println(" [Dans le sac]");
			else
				System.out.println();
		}
		
		System.out.println("Le sac contient "+poids_conso+" (max="+MAX_POIDS_SAC+") pour une valeur totale de "+valeur_total);
	}

}
