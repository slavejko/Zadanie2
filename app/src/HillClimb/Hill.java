package HillClimb;

import dictionary.NgramStats;
import helpers.TranspositionKey;
import helpers.TranspositionCipher;

import java.util.*;
import java.util.List;

public class Hill {
    private TranspositionKey finalKey;
    private Random rnd = new Random(System.currentTimeMillis());
    private int count;
    private List<Integer[]> duplicates = new ArrayList<>();
    TranspositionCipher myTransCipher = new TranspositionCipher();

    private int permLength = 10;
    private int numRounds = 10;                                      // for for(;;){} ( local minimum )
    private int iterations = 100;
    private List<AllTexts> eachText = new ArrayList<>();

    public void hill_climb(String encryptedEditedOT_Eng, ArrayList<HashMap<String, Double>> listOfReferenceNgrams, int NMAX){

        Integer[] allTimeBest_perm = generateLengthPerm(permLength);
        Double allTimeBest_score = new Double(9999);

        AllTexts thisText = new AllTexts();
        thisText.setZT(encryptedEditedOT_Eng);
        String decryptedText = "";

        for(int keyLength = 5; keyLength < 15; keyLength++){

            permLength = keyLength;

            Integer[] global_best = generateLengthPerm(permLength);
            Double global_score = new Double(9999);
            String txt = "";

            for(int round = 0; round < numRounds; round++){              // for decreasing the chance of having local minimum

                int n = 0;
                count = 0;
                Integer[] key1 = generateLengthPerm(permLength);

                rndPerm(key1);
                TranspositionKey bestKey = new TranspositionKey(key1);
                Double score_best = NgramStats.fitnessNgrams(bestKey,encryptedEditedOT_Eng,listOfReferenceNgrams,NMAX);

                do{
                    Integer[] neigh = generateNeighbor(key1);

                    if(neigh != null){
                        TranspositionKey neighbor = new TranspositionKey(neigh);
                        Double score_neighbor = NgramStats.fitnessNgrams(neighbor,encryptedEditedOT_Eng,listOfReferenceNgrams,NMAX);

                        if((score_best - score_neighbor) > 0){
                            txt = "\033[34;0m";
                            txt += (score_best - score_neighbor);
                            txt += "\033[0m";
                            count = 0;
                        }else {
                            txt += (score_best - score_neighbor);
                            count++;
                        }

                        System.out.print(Arrays.toString(key1) + " " + Arrays.toString(neigh) + " ...... "
                                + score_best + ", " + score_neighbor + " --diff: " + txt + "\t n: " +  n);

                        if(Arrays.equals(key1,neigh)) System.out.print(" =>  EQUAL PERMS");
                        txt = "";

                        System.out.println();

                        if(score_neighbor.compareTo(score_best) > 0){
                            continue;

                        }else if(score_neighbor.compareTo(score_best) <= 0){      // choosing best from current and neighbor
                            score_best = score_neighbor;
                            bestKey = neighbor;
                            key1 = neigh;
                        }
                    }
                    if(count > (3*iterations)/5)  break;                          // if 60% of consecutive neighbors do not
                                                                                  // improve score, stop do{}while();
                }while((n += 1) != iterations);

                if(global_score > score_best){                                    //choosing global best
                    global_score = score_best;
                    global_best = key1;
                }

                System.out.println("\n\033[33;0mBest local permutation:\033[0m " + Arrays.toString(bestKey.getEncPerm())
                        + " \033[33;0mIts score:\033[0m : " + score_best);                   // print best local perm

                decryptedText = myTransCipher.decryptRows(encryptedEditedOT_Eng,bestKey);
                System.out.println("\nDesiforvane : " + decryptedText + "\n");
            }

            System.out.println("\033[32;0mBest overall permutation:\033[0m " + Arrays.toString(global_best)
                    + " \033[32;0mIts score:\033[0m : " + global_score + "\n\n");            // print out best overall perm

            if(allTimeBest_score > global_score){
                allTimeBest_score = global_score;
                allTimeBest_perm = global_best;
            }

            thisText.setScore(global_score);
            thisText.setPemLength(global_best.length);
            thisText.setPermutation(global_best);
            thisText.setDecryptZT(decryptedText);
            eachText.add(thisText);                                                         // added to list of all texts
        }

        // print out best key, possibly solution (depends on configuration)
        System.out.println("\n\n\n\033[31;0mBWINNER:\033[0m " + Arrays.toString(allTimeBest_perm) + " \033[31;0mWINNER score:\033[0m : " + allTimeBest_score + "\n\n");

        this.finalKey = new TranspositionKey(allTimeBest_perm);
    }

    public Integer[] generateLengthPerm(int len){
        Integer[] out = new Integer[len];

        for(int i = 0; i < len; i++){
            out[i] = i+1;
        }

        return out;
    }

    private int[] toIntArray(List<Integer> list){
        int[] ret = new int[list.size()];

        for(int i = 0;i < ret.length;i++){
            ret[i] = list.get(i);
        }

        return ret;
    }

    private void rndPerm(Integer[] input) {
        int size = input.length;

        for (int i = 0; i < (size - 1); i++) {
            int j = rnd.nextInt(size - i) + i;
            int tmp = input[i];

            input[i] = input[j];
            input[j] = tmp;
        }
    }

    private Integer[] generateNeighbor(Integer[] input){

        Integer[] out = new Integer[input.length];
        System.arraycopy( input, 0, out, 0, input.length );

        int j = rnd.nextInt(input.length) + 1;
        int i = rnd.nextInt(input.length) + 1;

        if(i == j){
            j = rnd.nextInt(input.length)+1;
        }

        if(i == j){
            i = rnd.nextInt(input.length)+1;
        }

        System.out.print("Switching i,j: " + i + ", " + j + " ____ Perms(current,neighbor): ");

        out[i-1] = input[j-1];
        out[j-1] = input[i-1];

        if(!duplicates.contains(out)){
            duplicates.add(out);

            return out;
        }

        System.out.println("  \\033[31;0mDuplicate found\\033[0m  ");
        return null;
    }

    public TranspositionKey getFinalKey() {
        return finalKey;
    }
}