package dictionary;

import java.util.ArrayList;
import java.util.HashMap;

public class EnglishDictionary extends Dictionary {
    public final static HashMap<String,Double> letterFrequencies = null;
    public final static HashMap<String,Double> bigramFrequencies =NgramStats.readNgramsFromFile("../dictionaries/english_bigrams.txt");
    public final static HashMap<String,Double> trigramFrequencies=NgramStats.readNgramsFromFile("../dictionaries/english_trigrams.txt");;
    public final static HashMap<String,Double> quadgramFrequencies=NgramStats.readNgramsFromFile("../dictionaries/english_quadgrams.txt");;


    public EnglishDictionary() {
        listOfNgrams = new ArrayList<>();
        listOfNgrams.add(letterFrequencies);
        listOfNgrams.add(bigramFrequencies);
        listOfNgrams.add(trigramFrequencies);
        listOfNgrams.add(quadgramFrequencies);
    }
        //aplikuj na enc        //aplikuj na dec
        // 4 1 5 2 3       inv  // 2 4 5 1 3
        // J O Z E F            // O E F J Z
        // vysledok             //vysledok
        // O E F J Z            // J O Z E F
}       //
        //41523     //24513         //postlpcoch    //treba zostavit povodne zasifrovane riadky, hadame dlzku permutaci, trafili sme 5
        //JOZEF     //OEFJZ         //OAA          // staci urobit iba transpoziciu (riadky<->stlpce// )
        //MARTA     //ATAMR         //ETU           //OFMJZ
        //JAKUB     //AUBJK         //FAB           //TMAAR
                                    //JMJ           //UJBAK
                                    //ZRK           //a mame povodne zasifrovane riadky... cize skusame permutacie a zostavujeme riadky podla dlzky