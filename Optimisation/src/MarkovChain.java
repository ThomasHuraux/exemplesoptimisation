/*
 
 [Chaine de Markov]
 
Exemple tire de wikipedia (http://fr.wikipedia.org/wiki/Chaine_de_Markov)

Doudou, le hamster paresseux, ne connait que trois endroits dans sa cage : les copeaux ou il dort,
la mangeoire ou il mange et la roue ou il fait de l'exercice.
Ses journees sont assez semblables les unes aux autres, et son activite se represente aisement par
une chaine de Markov. Toutes les minutes, il peut soit changer d'activite, soit continuer
celle qu'il etait en train de faire. L'appellation processus sans memoire n'est pas du tout exageree
pour parler de Doudou.

	-	Quand il dort, il a 9 chances sur 10 de ne pas se reveiller la minute suivante.
	-	Quand il se reveille, il y a 1 chance sur 2 qu'il aille manger et 1 chance sur 2
		qu'il parte faire de l'exercice.
	-	Le repas ne dure qu'une minute, apres il fait autre chose.
	-	Apres avoir mange, il y a 3 chances sur 10 qu'il parte courir dans sa roue, mais
		surtout 7 chances sur 10 qu'il retourne dormir.
	-	Courir est fatigant ; il y a 8 chances sur 10 qu'il retourne dormir au bout d'une
	 	minute. Sinon il continue en oubliant qu'il est deja un peu fatigue

But : predire ou sera Doudou au temps t

 */

public class MarkovChain {
	
	static double [][] transitions = {{0.9, 0.05, 0.05}, {0.7, 0, 0.3}, {0.8, 0, 0.2}};
	
	static double [] predire(double [] initial, int t){
		
		return multiplie(puissance(transitions,t),initial);

	}
	
	public static void main(String[] args) {
		
		double [] initial = {1,0,0};
		int temps = 5;
		
		System.out.println("t=0\t\t1.0\t\t0.0\t\t0.0");
		
		double [] etats;
		
		for(int t=0;t<temps;t++){
			etats = predire(initial,t);
			System.out.print("t="+(t+1));
			for(int l = 0; l<etats.length; l++)
				System.out.print("\t\t"+Math.abs(Math.round(etats[l] * 100000.0) / 100000.0));
			System.out.println();
		}

	}
	
	static double [][] puissance(double [][] m, int n){
		
		double [][] temp = m;
		
		for(int i = 0; i<n; i++)
			temp = multiplie(temp,m);		
		
		return temp;
	}
	
	static double [][] multiplie(double [][] a, double [][] b){
		
		double [][] temp = new double[a.length][a.length];
		double [] col = new double[a.length];
		for(int i=0; i<a.length; i++){
			for(int j=0; j<a.length; j++){
				col[j] = b[j][i];
			}
			for(int k=0; k<a.length; k++){
				double [] li = a[k];
				double d = 0;
				for(int l=0; l<a.length; l++)
					d += li[l]*col[l];
				temp[k][i] = d;
			}
			
		}
		return temp;
	}
	
	static double [] multiplie(double [][] a, double [] v){
		double [] temp = new double[v.length];
		for(int l = 0; l<a.length; l++)
			for(int c = 0; c<a.length; c++)
				temp[l] += v[c] * a[c][l];
		return temp;
	}
}
	