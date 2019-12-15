package dictionary;

import java.util.ArrayList;
import java.util.HashMap;

public class SlovakDictionary extends Dictionary {
    public final static HashMap<String,Double> letterFrequencies = null;
    public final static HashMap<String,Double> bigramFrequencies =NgramStats.readNgramsFromFile("../dictionaries/english_bigrams.txt");
    public final static HashMap<String,Double> trigramFrequencies=NgramStats.readNgramsFromFile("../dictionaries/english_trigrams.txt");;
    public final static HashMap<String,Double> quadgramFrequencies=NgramStats.readNgramsFromFile("../dictionaries/english_quadgrams.txt");;


    public SlovakDictionary() {
        listOfNgrams = new ArrayList<>();
        listOfNgrams.add(letterFrequencies);
        listOfNgrams.add(bigramFrequencies);
        listOfNgrams.add(trigramFrequencies);
        listOfNgrams.add(quadgramFrequencies);
    }
}
