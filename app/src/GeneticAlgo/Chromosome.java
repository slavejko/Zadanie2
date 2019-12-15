package GeneticAlgo;

import java.util.ArrayList;

public class Chromosome {
    Integer[] permutation;
    Double score;


    public void genRanPerm(int length){
        ArrayList<Integer> genome = new ArrayList<>();
        for(int j = 1; j < length+1; j++){
            genome.add(j);
            java.util.Collections.shuffle(genome);

        }
        Integer[] gen = new Integer[genome.size()];
        for(int j = 0; j < genome.size();j++){
            gen[j]=genome.get(j);

        }
        this.setPermutation(gen);

    }


    public Integer[] getPermutation() {
        return permutation;
    }




    public void setPermutation(Integer[] permutation) {
        this.permutation = permutation;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }
}
