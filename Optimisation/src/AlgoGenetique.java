/*
 
 [Algorithme genetique]
 	Un exemple simple 
 
 Probleme :
 	Retrouver une chaine de caracteres
 
 Individu :
 	Une chaine de caracteres
 
 Chromosomes : 
 	Les caracteres

 Evaluation :
 	Somme des distances dans la table ASCII entre les caracteres de l'individu et la cible
 
 2 operateurs : 
 	- Reproduction (cross site)
 	- Mutation (on change un caractere par un autre au hasard)

 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

class Individu{
	char[] chromosomes;
	int fitness;
	
	public Individu(int taille){
		chromosomes = new char[taille];
		fitness = 0;
	}
}

public class AlgoGenetique {

	static final int TAILLE_POPULATION = 800;
	static final int MAX_ITER = 50000;
	
	static final double tauxSelection	= 0.25;
	static final double tauxMutation	= 0.2;
	
	static Random random = new Random();
	static char [] cible;

	static ArrayList<Individu> population = new ArrayList<Individu>();
	static ArrayList<Individu> proch_generation = new ArrayList<Individu>();
	
	public static char randomGene(){
		return (char)(65+random.nextInt(58));
	}
	
	public static void init(String s){
 
		cible = s.toCharArray();
		
		for(int i = 0; i < TAILLE_POPULATION; i++){
			Individu individu = new Individu(cible.length);
			for(int j = 0; j < cible.length; j++)
				individu.chromosomes[j] = randomGene();
			population.add(individu);
		}
		
	}
	
	public static void evaluation(){
		for(Individu i : population){
			i.fitness = 0;
			for(int j = 0; j < cible.length; j++)
				i.fitness += Math.abs(i.chromosomes[j] - cible[j]);
		}
	}
	
	public static void classement(){
		Comparator<Individu> comp = new Comparator<Individu>(){
			public int compare(Individu arg0, Individu arg1) {
				if(arg0.fitness > arg1.fitness)
					return 1;
				else
					return 0;
			}
		};
		Collections.sort(population,comp);
	}
	
	public static void mutation(Individu individu){
		int i = random.nextInt(cible.length);
		individu.chromosomes[i] = randomGene();
	}
	
	public static Individu reproduction(){
		Individu i = new Individu(cible.length);
		int site = random.nextInt(cible.length);
		
		String s1 = new String(population.get(random.nextInt(population.size())).chromosomes).substring(0,site);
		String s2 = new String(population.get(random.nextInt(population.size())).chromosomes).substring(site);

		i.chromosomes = s1.concat(s2).toCharArray();
		return i;
	}
	
	public static void evolution(){

		int nbMeilleurs  = (int) (population.size() * tauxSelection);
		
		proch_generation = new ArrayList<Individu>(population.subList(0, nbMeilleurs));
		
		for(int i = nbMeilleurs; i<population.size(); i++){
			proch_generation.add(reproduction());
			if(random.nextFloat() < tauxMutation)
				mutation(proch_generation.get(i));
		}
		
		population = proch_generation;
		
	}
	
	public static void main(String[] args){
	
		init("Les_algorithmes_genetiques_sont_inspires_du_mecanisme_biologique_de_la_selection_naturelle");
		
		for (int generation = 0; generation < MAX_ITER; generation++){
			
			evaluation();
			
			System.out.println(new String(population.get(0).chromosomes)+" [fitness="+population.get(0).fitness+"]");
			
			if(population.get(0).fitness == 0){
				System.out.println("\t- Trouve en "+generation+" generations d'une population de "+TAILLE_POPULATION+" individus -");
				break;
			}
			
			classement();
			evolution();
			
		}
		
	}

}
