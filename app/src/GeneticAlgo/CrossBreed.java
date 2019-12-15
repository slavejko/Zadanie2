package GeneticAlgo;

import java.util.ArrayList;
import java.util.Random;

public class CrossBreed {

    Population oldpopulation;
    Population newpopulation = new Population();

    // TODO velkost populacie bude parne cislo pre istotu vzdy
    // TODO lebo krizenie vracia polovicny pocet jedincov


    public static final void swap (Integer[] a, int i, int j) {
        Integer t = a[i];
        a[i] = a[j];
        a[j] = t;
    }



    public void cross(int percentage){
        ArrayList<Chromosome> tmp = new ArrayList<>();
        for(Chromosome c : this.oldpopulation.getPopulation()){
            tmp.add(c);
        }

        for(int i = 0; i < this.oldpopulation.getPopulation().size()/2 ; i ++){

            int rnd1 = new Random().nextInt(tmp.size());
            Chromosome c = new Chromosome();
            c.setPermutation(tmp.get(rnd1).getPermutation());
            tmp.remove(rnd1);

            int rnd2 = new Random().nextInt(tmp.size());
            Chromosome d = new Chromosome();
            d.setPermutation(tmp.get(rnd2).getPermutation());
            tmp.remove(rnd2);

            //TODO mame c a d chromosomy, spravime krizenie ich genov na zaklade intu percentage


            ArrayList<Integer> positions=new ArrayList<>();
            for(int j = 0; j < c.getPermutation().length;j++){
                positions.add(j+1);   //TODO 1 2 3 4 5
            }

            for(int j = 0; j < percentage;j++){
                int rnd = new Random().nextInt(positions.size());
                int toswap = c.getPermutation()[rnd]; //TODO  rnd = 2  ... 4  5  [1]  3  2
                for(int k = 0; k < d.getPermutation().length;k++){
                    if(d.getPermutation()[k]==toswap){
                        swap(d.getPermutation(),k,rnd);
                        break;
                    }
                }
                positions.remove(rnd);

            }
        this.newpopulation.getPopulation().add(d);
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
}
