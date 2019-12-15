package dictionary;

import helpers.Permutations;
import helpers.TranspositionKey;

import java.util.ArrayList;
import java.util.HashMap;



public class BruteForceAttack {
    /*
    * Inputs : ciphertext - ciphertext
    *          n - len of permutations
    *          Hashmap of reference Values of nGrams
    *          //directory - directory with reference values of nGrams frequencies
    * Description:
    *           -function start with generating all possible permutations of length n, which are applied to cipherText
    *           -for each permutation is created TranspositionKey which use generated permutation as encryption key
    *           -to each TranspositionKey is calculated and set score, as result of comparision of language nGram frequencies and real nGram frequencies
    *           -TranspositionKey with best score is returned (best : converging to zero)
    */
    public static TranspositionKey tryAllPermN( String cipherText, ArrayList<HashMap<String, Double>> listOfReferenceNgrams, int n){
        Integer[] initialPerm = new Integer[n];
        for(int i =0; i < n; i++){
            initialPerm[i] = i+1;
        }
        ArrayList<Integer[]> allPermutations =(ArrayList)Permutations.allPerm(initialPerm);
        TranspositionKey currentKey=null;
        TranspositionKey bestKey=null;
        Double worstScore = new Double(99999);
        Double score = null;

        /*
         * TODO list of keys and its score
         */


        for(int i = 0; i < allPermutations.size(); i++){
            currentKey = new TranspositionKey(allPermutations.get(i));
            score = NgramStats.fitnessNgrams(currentKey,cipherText,listOfReferenceNgrams, 4);
            if(score<worstScore){
                bestKey = currentKey;
                worstScore = score;
            }
        }


        return bestKey;
    }

    /*
    * function tries all perm up to len MAXLEN ... 7 is optimal
     */
    public static TranspositionKey tryAllPerm(String cipheText, ArrayList<HashMap<String, Double>> listOfReferenceNgrams, int MAXLEN){
        ArrayList<TranspositionKey> bestKeys = new ArrayList<>();
        for(int i = 3; i <= MAXLEN; i++){
            TranspositionKey currentKey = tryAllPermN(cipheText, listOfReferenceNgrams, i);
            bestKeys.add(currentKey);
        }
        Double bestScore = bestKeys.get(0).getScore();
        TranspositionKey bestKey = bestKeys.get(0);
        for(int i = 1; i < MAXLEN; i++){
            Double currentScore = bestKeys.get(i).getScore();
            if(currentScore<bestScore){
                bestKey = bestKeys.get(i);
                bestScore=currentScore;
            }
        }
        return bestKey;
    }
}
