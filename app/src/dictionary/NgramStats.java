package dictionary;

import helpers.TranspositionCipher;
import helpers.TranspositionKey;

import java.io.*;
import java.util.*;


/*
*
* format : ngram - abs freq
* */
public class NgramStats {
    final static Integer[] WEIGHTS = new Integer[]{0,1,2,3};
    final static int NGRAMMAX = 4;

    private static String parseString(String line){
        char[] chars = line.toCharArray();
        StringBuilder returnString=new StringBuilder();
        for(Character c : chars){
            if(Character.isAlphabetic(c)){
                returnString.append(c);
            }

        }
        return returnString.toString();
    }

    private static int parseInt(String line){
        StringBuilder digit=new StringBuilder();
        int ret=0;
        char[] chars = line.toCharArray();
        for(Character c : chars){
            if(Character.isDigit(c)){
                digit.append(c);
            }
        }

        ret = Integer.parseInt(digit.toString());

        return ret;
    }

    /*
    * Function opens JFileChooser window and after choosing particular file, returns HashMap<String, Double>
    *     of nGrams and relative frequencies of nGrams
    *     -file have to be in format :  AAA 125
    *                                   BBB 555
    *
    * */
    public static HashMap computeNgramsFrequenciesFromFile(){  //TODO expeptions
        try {
            Double absCount = new Double(0);
            int frequency = 0;
            String line;
            String nGram;
            File file = Dictionary.pickFromFileChooser();
            return readNgramsFromFile(file.getPath());
        }
        catch (Exception ex){
            ex.printStackTrace();

        }
        return null;
    }

    /*
    * Inputs: String path of File
    * Returns HashMap<String, Double of nGrams and relative frequencies of nGrams
     *     -file have to be in format :  AAA 125
     *                                   BBB 555
    *                                    ...
    * */
    public static HashMap readNgramsFromFile(String path) {
        try {
            Double absCount = new Double(0);
            int frequency = 0;
            String line;
            String nGram;
            File file = new File(path);
            HashMap<String, Double> nGramsFrequencies = new HashMap<>();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

            while ((line = bufferedReader.readLine()) != null) {
                frequency = parseInt(line);
                nGram = parseString(line).toLowerCase();
                absCount += frequency;
                nGramsFrequencies.put(nGram, new Double(frequency));
            }
            //Double count=new Double(0);  for testing if sum is 100%
            for (String string : nGramsFrequencies.keySet()) {

                nGramsFrequencies.replace(string, nGramsFrequencies.get(string) / absCount);
                //count+=(nGramsFrequencies.get(string)); // for testing if sum is 100%
            }

            return nGramsFrequencies;



    }catch(Exception ex){
        ex.printStackTrace();
    }

        return null;
}

    /*
    * Inputs: String txt - by which is created HashMap<String, Double> of its nGram and frequencies
    *         int n      - size of nGrams
    *         boolean relativeFr - if is set, then is calculated relative f., instead of absolute f.
    * */
    public static HashMap readNgram(String txt, int n, boolean relativeFr){
        HashMap<String, Double> map = new HashMap<>();
        Double totalCounter = new Double(0);
        for(int i = 0; i <=txt.length()-n;i++){
            String substring = txt.substring(i,i+n).toLowerCase();
            totalCounter++;
            if(map.containsKey(substring)){
                Double newCount = map.get(substring) + 1;
                map.replace(substring, newCount);
            }
            else{
                map.put(substring, new Double(1));

            }
        }
        if(relativeFr){
            for(Map.Entry m : map.entrySet()){
                Double relativeFrequency = (Double)m.getValue()/totalCounter;
                map.put((String)m.getKey(),relativeFrequency);
            }
        }
        return map;
    }

    /*
    * Inputs  : realValues of nGrams from tested text, referenceValues of nGrams, int n - size of nGram
    * returns : sum of distances of nGrams
    * */
    public static Double nGramsDistance(Map<String, Double> realValues, Map<String, Double> referenceValues, int n ){
        Double difference;
        Double refVal;
        Double realVal;
        Double sum = new Double(0);
        for(String nGram : referenceValues.keySet()){
            refVal = Math.pow(referenceValues.get(nGram),n);
            if(realValues.containsKey(nGram)) {
                realVal = Math.pow(realValues.get(nGram),n);
            }
            else{
                realVal = new Double(0);
            }
            difference = Math.abs(refVal-realVal);
            sum+=difference;
        }

        return sum;
    }

    /*
    *
    * */
    public static Double allnGramsDistance(ArrayList<HashMap<String, Double>> realValues, ArrayList<HashMap<String, Double>> referenceValues, int NMAX){
        Double sum = new Double(0);
        if(NMAX>NGRAMMAX){
            throw new IllegalArgumentException("allnGramsDistance : NMAX out of range");
        }
        for(int i = 1; i< NMAX; i++){
            sum+= i *WEIGHTS[i]* nGramsDistance(realValues.get(i), referenceValues.get(i), i);
        }
        return sum;
    }

    /*
    *
    * */
    public static Double allnGramsDistance(String cipherText, Dictionary dictionary, int NMAX){

        return allnGramsDistance(cipherText, dictionary.getListOfNgrams(), NMAX);


    }

    /*
    *
    * */
    public static Double allnGramsDistance(String cipherText, ArrayList<HashMap<String, Double>> listOfReferenceNgrams, int NMAX){
        ArrayList<HashMap<String, Double>> listOfRealNgrams = new ArrayList();
        HashMap<String, Double> realLetterFrequencies = readNgram(cipherText, 1, true);
        HashMap<String, Double> realBigramFrequencies =readNgram(cipherText, 2, true);
        HashMap<String, Double> realTrigramFrequencies =readNgram(cipherText, 3, true);
        HashMap<String, Double> realQuadgramFrequencies =readNgram(cipherText, 4, true);
        listOfRealNgrams.add(realLetterFrequencies);
        listOfRealNgrams.add(realBigramFrequencies);
        listOfRealNgrams.add(realTrigramFrequencies);
        listOfRealNgrams.add(realQuadgramFrequencies);

        return allnGramsDistance(listOfRealNgrams, listOfReferenceNgrams, NMAX);


    }

    /*
    *
    * */
    public static Double fitnessNgrams(TranspositionKey transpositionKey, String ciphertext, ArrayList<HashMap<String, Double>> listOfReferenceNgrams, int NMAX){
        TranspositionCipher transpositionCipher = new TranspositionCipher();
        String permutatedText = transpositionCipher.decryptRows(ciphertext, transpositionKey);
        Double score = allnGramsDistance(permutatedText, listOfReferenceNgrams, NMAX);
        transpositionKey.setScore(score);
        return score;
    }

    /*
    *
    * */
    public static Double fitnessNgrams(TranspositionKey transpositionKey, String ciphertext, Dictionary dictionary, int NMAX){

        return fitnessNgrams(transpositionKey,ciphertext,dictionary.getListOfNgrams(),NMAX);
    }

    public static Double fitnessNgramsCols(TranspositionKey transpositionKey, String ciphertext, ArrayList<HashMap<String, Double>> listOfReferenceNgrams, int NMAX){
        TranspositionCipher transpositionCipher = new TranspositionCipher();
        String rowCipherText =transpositionCipher.readRows(ciphertext, transpositionKey.getBlockSize());
        return fitnessNgrams(transpositionKey,rowCipherText,listOfReferenceNgrams, NMAX);
    }

    public static ArrayList getAllNgrams(String cipherText){
        ArrayList<HashMap<String, Double>> listOfNgrams = new ArrayList<>();
        HashMap<String, Double> letters=null;
        HashMap<String, Double> bigrams= NgramStats.readNgram(cipherText,2, true);
        HashMap<String, Double> trigrams= NgramStats.readNgram(cipherText,3, true);
        HashMap<String, Double> quadgrams= NgramStats.readNgram(cipherText,4, true);

        listOfNgrams.add(letters);
        listOfNgrams.add(bigrams);
        listOfNgrams.add(trigrams);
        listOfNgrams.add(quadgrams);
        return listOfNgrams;
    }
    //TODO funckia ktora vytvori arraylist hashmap ngramov z textu

}
