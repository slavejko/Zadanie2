package GeneticAlgo;


import dictionary.NgramStats;
import helpers.TranspositionKey;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Selection {

    Population oldpopulation;
    Population newpopulation=new Population();
    ArrayList<Chromosome> sortedPopulation=new ArrayList<>();
    String encrypted_Text;
    ArrayList<HashMap<String, Double>> listOfNgramsDec;

    public Selection(String encrypted_Text, ArrayList<HashMap<String, Double>> listOfNgramsDec) {
        this.encrypted_Text = encrypted_Text;
        this.listOfNgramsDec = listOfNgramsDec;
    }

    public void setScoreToPop(){

        for(Chromosome c : this.oldpopulation.getPopulation()){
            Integer[] garbage =c.getPermutation();
            TranspositionKey transpositionKey = new TranspositionKey(garbage);
            //transpositionKey.setDecPerm(c.getPermutation());
            c.setScore(NgramStats.fitnessNgrams(transpositionKey,encrypted_Text,listOfNgramsDec,4));
        }
    }



    public void sortPopulation(){
        ArrayList<Chromosome> tmp= new ArrayList<>();


        for (Chromosome c : this.oldpopulation.getPopulation()
             ) {
            tmp.add(c);
        }
        while(tmp.size()>0) {
            Chromosome minC = new Chromosome();
            minC.setScore(9999.0);
            for (int i = 0;i<tmp.size();i++) {
                if (tmp.get(i).getScore() <= minC.getScore()) {

                    minC = tmp.get(i);
                }
            }
            this.sortedPopulation.add(minC);
            tmp.remove(minC);
        }
    }


    public void selBest(ArrayList<Integer> criterium){
        for(int i = 0; i < criterium.size();i++){
            for(int j = 0;j < criterium.get(i); j++){
                this.newpopulation.getPopulation().add(this.sortedPopulation.get(i));


            }

        }
                //java.util.Collections.shuffle(this.newpopulation.getPopulation());
    }

    public void selRand(int size){
        for(int i = 0; i< size;i++){
            int rnd = new Random().nextInt(this.oldpopulation.getPopulation().size());
            this.newpopulation.getPopulation().add(this.oldpopulation.getPopulation().get(rnd));
        }


    }








    public Population getOldpopulation() {
        return oldpopulation;
    }

    public void setOldpopulation(Population oldpopulation) {
        this.oldpopulation = oldpopulation;
    }

    public Population getNewpopulation() {
        return newpopulation;
    }

    public void setNewpopulation(Population newpopulation) {
        this.newpopulation = newpopulation;
    }

    public ArrayList<Chromosome> getSortedPopulation() {
        return sortedPopulation;
    }

    public void setSortedPopulation(ArrayList<Chromosome> sortedPopulation) {
        this.sortedPopulation = sortedPopulation;
    }
}
