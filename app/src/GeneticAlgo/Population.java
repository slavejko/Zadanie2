package GeneticAlgo;

import java.util.ArrayList;

public class Population {

    ArrayList<Chromosome> population = new ArrayList<>();

    public void generateRandomPopulation(int size, int keysize){
        this.population = new ArrayList<>();
        for(int i = 1; i < size+1; i++){
            Chromosome c = new Chromosome();
            ArrayList<Integer> genome=new ArrayList<>();
            for(int j = 1; j < keysize+1; j++){
                genome.add(j);
                java.util.Collections.shuffle(genome);

            }
            Integer[] gen = new Integer[genome.size()];
            for(int j = 0; j < genome.size();j++){
                gen[j]=genome.get(j);

            }
            c.setPermutation(gen);
            this.population.add(c);
        }
    }


    public void mergePopulation(Population toMerge){
        for (Chromosome c: toMerge.getPopulation()
             ) {
            this.population.add(c);
        }

    }


    public ArrayList<Chromosome> getPopulation() {
        return population;
    }

    public void setPopulation(ArrayList<Chromosome> pupulation) {
        this.population = pupulation;
    }
}
