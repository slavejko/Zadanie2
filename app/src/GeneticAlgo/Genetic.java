package GeneticAlgo;

import java.util.ArrayList;
import java.util.HashMap;

public class Genetic {

    Population myPopulation = new Population();
    String encrypted_Text;
    int keySize;
    ArrayList<HashMap<String, Double>> listOfNgramsDec;

    public Genetic(String encrypted_Text, ArrayList<HashMap<String, Double>> listOfNgramsDec,int keySize) {
        this.encrypted_Text = encrypted_Text;
        this.listOfNgramsDec = listOfNgramsDec;
        this.keySize = keySize;
    }

    public Integer[] genetic(){
        Selection finalSelection = new Selection(encrypted_Text,listOfNgramsDec);

        Integer[] result = new Integer[0];

        System.out.println("genetic");
        //Population myPopulation = new Population();

        ArrayList<Integer> mycriterium0= new ArrayList<>();
        mycriterium0.add(1);
        mycriterium0.add(1);

        //TODO selbest na mutaciu
        ArrayList<Integer> mycriterium= new ArrayList<>();
        mycriterium.add(3);
        mycriterium.add(2);
        mycriterium.add(1);




                                        //todo 2

        //TODO selbest2 na krizenie musi mat parny pocet      24
        ArrayList<Integer> mycriterium2= new ArrayList<>();
        mycriterium2.add(6);
        mycriterium2.add(5);
        mycriterium2.add(4);
        mycriterium2.add(3);
        mycriterium2.add(3);
        mycriterium2.add(3);



        //todo 16 /2


        myPopulation.generateRandomPopulation(20,keySize);
        for(int i = 0; i < 100;i++){





            Population newPop = new Population();   //TODO populacia na best
            Population newPop2 = new Population();  //TODO populacia na random
            Population newPop3 = new Population();  //TODO populacia na best

            Selection selbest = new Selection(encrypted_Text,listOfNgramsDec);
            Selection selbest2 = new Selection(encrypted_Text,listOfNgramsDec);
            Selection selbest0 = new Selection(encrypted_Text,listOfNgramsDec);
            CrossBreed crossBreed = new CrossBreed();
            Mutation mutation = new Mutation();




            selbest.setOldpopulation(myPopulation);
            selbest.setScoreToPop();

            selbest2.setOldpopulation(myPopulation);
            selbest2.setScoreToPop();

           selbest0.setOldpopulation(myPopulation);
           selbest0.setScoreToPop();

           selbest0.sortPopulation();
           selbest0.selBest(mycriterium0);

            selbest.sortPopulation();
            selbest.selBest(mycriterium);

            selbest2.sortPopulation();
            selbest2.selBest(mycriterium2);

            //selrand.selRand(11);                         //todo 7

            newPop2=selbest0.getNewpopulation();

            newPop=selbest.getNewpopulation();

            newPop3=selbest2.getNewpopulation();

            crossBreed.setOldpopulation(newPop3);
            crossBreed.cross(1);
            newPop3=crossBreed.getNewpopulation();

            mutation.setOldpopulation(newPop);
            mutation.mutaSwap(0.2);
            mutation.mutaPart(0.2);
            newPop=mutation.getOldpopulation();

/*
            Integer[] perm = new Integer[]{4,1,5,2,3,7,6,9,8,14,11,13,12,17,15,16,10};
            Chromosome c = new Chromosome();
            c.setPermutation(perm);
            ArrayList<Chromosome> p = new ArrayList<>();
            p.add(c);
            newPop.setPopulation(p);
*/


            myPopulation.setPopulation(newPop2.getPopulation());
            myPopulation.mergePopulation(newPop);
            myPopulation.mergePopulation(newPop3);




            this.removeDuplicates();

            if(i % 10 == 0){
                System.out.println("iteration "+ i + " best score " + this.myPopulation.getPopulation().get(0).getScore());

                for(int m = 0; m < keySize;m++){
                    System.out.print(this.myPopulation.getPopulation().get(0).getPermutation()[m]);
                }
                System.out.println();




            }

        }
        finalSelection.setOldpopulation(myPopulation);
        finalSelection.setScoreToPop();
        finalSelection.sortPopulation();
        myPopulation.setPopulation(finalSelection.getSortedPopulation());






        result = finalSelection.getSortedPopulation().get(0).getPermutation();




    return  result;
    }


    public void removeDuplicates(){

/*
        Integer[] perm = {1,2,3,4,5,6};
        Integer[] perm2 = {6,5,4,3,2,1};
        Chromosome c = new Chromosome();
        Chromosome c1 = new Chromosome();
        Chromosome c2 = new Chromosome();
        Chromosome c3 = new Chromosome();
        Chromosome c4 = new Chromosome();

        c.setPermutation(perm);
        c1.setPermutation(perm);
        c2.setPermutation(perm);
        c3.setPermutation(perm2);
        c4.setPermutation(perm2);

        ArrayList<Chromosome> arr = new ArrayList<>();

        arr.add(c);
        arr.add(c1);
        arr.add(c2);
        arr.add(c3);
        arr.add(c4);

        myPopulation.setPopulation(arr);
*/

        for(int i = 1; i < this.myPopulation.getPopulation().size();i++){
            for(int j = i-1; j > 0-1; j--){
                Boolean b = true;
                for(int k = 0;k<this.myPopulation.getPopulation().get(0).getPermutation().length;k++) {
                    if (this.myPopulation.getPopulation().get(i).getPermutation()[k] != this.myPopulation.getPopulation().get(j).getPermutation()[k]) {
                        b = false;
                    }
                }
                    if(b){
                        Chromosome d = new Chromosome();
                        d.genRanPerm(this.myPopulation.getPopulation().get(0).getPermutation().length);
                        this.myPopulation.getPopulation().get(i).setPermutation(d.getPermutation());

                    }



            }

        }


        //System.out.println();

    }

}
