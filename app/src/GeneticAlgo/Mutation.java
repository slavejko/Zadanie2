package GeneticAlgo;

import java.util.Random;

public class Mutation {

Population oldpopulation;

    public static final void swap (Integer[] a, int i, int j) {
        Integer t = a[i];
        a[i] = a[j];
        a[j] = t;
    }


public void mutaSwap(Double percentage){
    long criterium =  Math.round(this.oldpopulation.getPopulation().size()*percentage);
    int chromsize=this.oldpopulation.getPopulation().get(0).getPermutation().length;
    for(int i = 0;i<criterium;i++){
        int rnd2 = new Random().nextInt(chromsize);
        int rnd = rnd2;
        while (rnd == rnd2){
            rnd = new Random().nextInt(chromsize);

        }
        swap(this.oldpopulation.getPopulation().get(i).getPermutation(),rnd,rnd2);
    }
    java.util.Collections.shuffle(this.oldpopulation.getPopulation());
    }



public void mutaPart(Double percentage){
        long criterium = Math.round(this.oldpopulation.getPopulation().size()*percentage);
        int length = this.oldpopulation.getPopulation().get(0).getPermutation().length/2;
        int size = this.oldpopulation.getPopulation().get(0).getPermutation().length;
        for(int i = 0; i < criterium;i++){
            for(int j = 0;j < length;j++){
                swap(this.oldpopulation.getPopulation().get(i).getPermutation(),j,size-1-j);
            }
        }
    java.util.Collections.shuffle(this.oldpopulation.getPopulation());
    }








    public Population getOldpopulation() {
        return oldpopulation;
    }

    public void setOldpopulation(Population oldpopulation) {
        this.oldpopulation = oldpopulation;
    }

}




