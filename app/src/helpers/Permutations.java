package helpers;

import java.util.*;

public class Permutations {

    private static Random rnd = new Random(System.currentTimeMillis());

    /*
    * Input: permutation of objects input[]
    * Return: nothing, rndPerm just creates random permutation from input[]
    *
    * */
    public static void rndPerm(Object input[]) {
        int size = input.length;
        for (int i = 0; i < (size - 1); i++) {
            int j = rnd.nextInt(size - i) + i;
            // swap
            Object tmp = input[i];
            input[i] = input[j];
            input[j] = tmp;
        }
    }

    /*
    * Input: permutation of objects input[]
    * Return: ArrayList of all permutations created from input[]
    * */
    public static List allPerm(Object input[]) {
        List<Object[]> retVal = new ArrayList(factorial(input.length));
        allPerm(0, input, retVal);
        return retVal;
    }

    /*
    * help function for allPerm(Object input[])
    * */
    private static void allPerm(int fixed, Object input[], List output) {
        Object in[] = input.clone();
        if (fixed == input.length) {
            output.add(input);
        } else {
            for (int i = fixed; i < input.length; i++) {
                // swap
                Object tmp = in[i];
                in[i] = in[fixed];
                in[fixed] = tmp;
                // recursion
                allPerm(fixed + 1, in, output);
            }
        }
    }

    /*
    * Input: M = {a, .... z}, Character
    * */
    public static Character[] inverse(Character[] perm) {
        Character[] inv = new Character[perm.length];
        for (int i = 0; i < perm.length; i++) {
            inv[perm[i] - 'a'] = (char) (i + 'a');
        }
        return inv;
    }

    /*
    * Input: M = {1, .... m}, Integer
    * */
    public static Integer[] inverse(Integer[] perm) {
        Integer[] inv = new Integer[perm.length];
        for (int i = 0; i < perm.length; i++) {
            inv[perm[i]-1] = i+1;
        }
        return inv;
    }
    /*
    * Input: Array of objects which can be sorted
    * */
    public static Integer[] inverse(Object[] perm){
        List sorted = Arrays.asList(perm.clone()); // kopia do zoznamu
        Collections.sort(sorted); // zoradime

        Integer[] tmp = new Integer[perm.length];
        for(int i=0; i < perm.length; i++){
            tmp[i] = sorted.indexOf(perm[i])+1;
            // poradie prvku zo vstupu pri zakladnom usporiadani vstupnej mn.
        }
        return inverse(tmp);
    }

    /*
    * Input: String word, by which is created permutation. Word is tranformed into lowercase
    * Example: input : AUTO
    *          output: 1432
    * */
    public static Integer[] wordPerm(String word){
        String lowerCaseWord = word.toLowerCase();
        Integer[] perm =new Integer[lowerCaseWord.length()];
        int index = 1;
        for(int i = 'a'; i <= 'z'; i++){
            for(int j = 0; j < lowerCaseWord.length();j++){
                if(lowerCaseWord.charAt(j)==(char)i){
                    perm[j] = index++;
                }
            }
        }
        return perm;
    }


    /*
    *
    * print functions
    * */
    public static void printPerm(Object[] perm){
        System.out.print("[");
        for(Object o : perm){
            System.out.print(o.toString()+",");
        }
        System.out.println("]");
    }



    /*
    *
    * math functions
    * */
    public static int factorial(int n) {
        int retVal = 1;
        for (int i = 2; i <= n; i++) {
            retVal *= i;
        }
        return retVal;
    }

}