import GeneticAlgo.Genetic;
import HillClimb.Hill;
import dictionary.*;
import helpers.Permutations;
import helpers.TranspositionCipher;
import helpers.TranspositionKey;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


public class Main {

    public static void main(String[] args) {

        NgramStats ngramStats = new NgramStats();
        EnglishDictionary englishDictionary = new EnglishDictionary();


        try {
            //TODO Slovniky hc nGramy testy
            //System.out.println(slovakDictionary.loadFromFile(true));
            //slovakDictionary.toStandardAlphabet();
            //slovakDictionary.toLowerCase();
            //slovakDictionary.printDirectory();
            //slovakDictionary.writeToFile();
            //System.out.println(slovakDictionary.getSize());

            //ngramStats.computeNgramsFrequenciesFromFile();
            //String ciphertext = "abcdabcdabcd";
            //HashMap<String, Double> mapFromCipherText = NgramStats.readNgram(ciphertext,2,true);
            //System.out.println(mapFromCipherText.toString());

            //TODO sifrovanie hc desifrovanie po riadkoch resp stlpcoch testy
            String openText = "SkuskaSifrovaniaDesifrovaniaTextu";
            TranspositionKey transpositionKey = new TranspositionKey(new Integer[]{4,1,5,2,3});
            TranspositionCipher transpositionCipher = new TranspositionCipher();

            String cipherTextRows = transpositionCipher.encryptRows(openText,transpositionKey);
            String cipherTextCols =transpositionCipher.encyptCols(openText,transpositionKey);
            System.out.println("cipher rows: "+cipherTextRows);
            System.out.println("cipher cols: "+cipherTextCols);

            String decryptedTextRows = transpositionCipher.decryptRows(cipherTextRows, transpositionKey);
            String decryptedTextCols = transpositionCipher.decryptCols(cipherTextCols , transpositionKey);
            System.out.println("decipher rows: "+decryptedTextRows);
            System.out.println("decipher cols: "+decryptedTextCols);

            //TODO testy permutacia zo slova
            Permutations.printPerm(Permutations.wordPerm("aazzzbaacdefghijklmnopzyx"));


            //TODO sifrovanie desifrovanie testy

            //permutacia
            Integer[] rndPermutation = new Integer[]{4,1,5,2,3,6,7};
            Permutations.rndPerm(rndPermutation);

            //kluc
            TranspositionKey testKey = new TranspositionKey(rndPermutation);
            String OT_Eng = "The earliest forms of secret writing required little more than writing implements since most people could not read. More literacy, or literate opponents, required actual cryptography. The main classical cipher types are transposition ciphers, which rearrange the order of letters in hc message (e.g., 'hello world' becomes 'ehlol owrdl' in hc trivially simple rearrangement scheme), and substitution ciphers, which systematically replace letters or groups of letters with other letters or groups of letters (e.g., 'fly at once' becomes 'gmz bu podf' by replacing each letter with the one following it in the Latin alphabet). Simple versions of either have never offered much confidentiality from enterprising opponents. An early substitution cipher was the Caesar cipher, in which each letter in the plaintext was replaced by hc letter some fixed number of positions further down the alphabet. Suetonius reports that Julius Caesar used it with hc shift of three to communicate with his generals. Atbash is an example of an early Hebrew cipher. The earliest known use of cryptography is some carved ciphertext on stone in Egypt (ca 1900 BCE), but this may have been done for the amusement of literate observers rather than as hc way of concealing information.The Greeks of Classical times are said to have known of ciphers (e.g., the scytale transposition cipher claimed to have been used by the Spartan military).[16] Steganography (i.e., hiding even the existence of hc message so as to keep it confidential) was also first developed in ancient times. An early example, from Herodotus, was hc message tattooed on hc slave's shaved head and concealed under the regrown hair.[10] More modern examples of steganography include the use of invisible ink, microdots, and digital watermarks to conceal information.";
            //upraveny text
            String editedOT_Eng = Dictionary.normalizeText(OT_Eng);
            System.out.println("Edited text: "+editedOT_Eng);

            //zasifrovany text
            String encryptedEditedOT_Eng = transpositionCipher.encryptRows(editedOT_Eng, testKey);
            System.out.println("Cipher Text: "+encryptedEditedOT_Eng);

            //desifrovany text
            String decryptedEditedOT_Eng = transpositionCipher.decryptRows(encryptedEditedOT_Eng, testKey);
            System.out.println("Decipher Text: "+decryptedEditedOT_Eng);

            //TODO vytvorenie statistik ngramov nezasifrovaneho textu hc hc ngramov zasifrovaneho textu - idealny pripad
            ArrayList<HashMap<String, Double>> listOfNgramsDec = NgramStats.getAllNgrams(decryptedEditedOT_Eng);
            ArrayList<HashMap<String, Double>> listOfNgramsEnc= NgramStats.getAllNgrams(encryptedEditedOT_Eng);

            //TODO funkcia fitness spolu s metodou bruteforcom preto musi najst hodnotu 0 -> uplna zhoda

            // scoreBefore1 hc scoreBefore2 sa musia rovnat, ide len o test funkcii
            Double scoreBefore1 = NgramStats.allnGramsDistance(encryptedEditedOT_Eng, listOfNgramsDec, 4 );
            Double scoreBefore2 = NgramStats.allnGramsDistance(listOfNgramsEnc,listOfNgramsDec, 4);
            System.out.println("Score encText vs ideal reference nGram Values ="+scoreBefore1);
            System.out.println("Score encText vs ideal reference nGram Values ="+scoreBefore2);


            //score klucu, ktorym bol zasifrovany text, pocitany z idealnych hodnot -> musi byt 0
            Double scoreOfMatchingKey = NgramStats.fitnessNgrams(testKey,encryptedEditedOT_Eng, listOfNgramsDec,4);
            System.out.println("test Ngrams decText vs decNgrams Stats ="+scoreOfMatchingKey);

            //TODO brute force testy

            //brute force na hladanie minima -> v tomto pripade 0, pretoze hladame ngramy voci povodnym hodnotam
            //prehladava mnozinu klucov dlky 7
            TranspositionKey resultIdeal = BruteForceAttack.tryAllPermN(encryptedEditedOT_Eng, listOfNgramsDec, testKey.getBlockSize()); //funguje
            System.out.println("Score of found Transpotiton Key encText vs reference values of open text nGram values ="+resultIdeal.getScore());
            System.out.println("Enc Permutation"+Arrays.toString(resultIdeal.getEncPerm()));
            System.out.println("Dec text: "+transpositionCipher.decryptRows(encryptedEditedOT_Eng,resultIdeal));

            //brute force voci hodnotam zo slovnika
            //TranspositionKey resultDictionary = BruteForceAttack.tryAllPermN(encryptedEditedOT_Eng, englishDictionary.getListOfNgrams(), testKey.getBlockSize()); //funguje
            //System.out.println("Score of found Transpotiton Key encText vs dictionary nGram values ="+resultDictionary.getScore());
            //System.out.println("Enc Permutation"+Arrays.toString(resultDictionary.getEncPerm()));
            //System.out.println("Dec text: "+transpositionCipher.decryptRows(encryptedEditedOT_Eng,resultDictionary));

            //TranspositionKey bestKey = BruteForceAttack.tryAllPerm(encryptedEditedOT_Eng,englishDictionary.getListOfNgrams(),6);
            //System.out.println(bestKey.getEncPerm());
            //System.out.println(transpositionCipher.decryptRows(encryptedEditedOT_Eng,bestKey));


            System.out.println("\n\033[34;0mHC\033[0m");

            Hill hc = new Hill();
            hc.hill_climb(encryptedEditedOT_Eng,englishDictionary.getListOfNgrams(),4);

            // v pripade, ze chceme zasifrovat vlastny text vlastnym klucom a potom nan spustit HC, treba odkomentovat nizzsie riadky
            //Integer[] myPerm = new Integer[]{1,2,3,4,5,6,7,8,9,10};
            //TranspositionCipher myKey = new TranspositionCipher(myPerm);
            //String myText = transpositionCipher.encryptRows(openText,transpositionKey);
            //hc.hill_climb(myText,englishDictionary.getListOfNgrams(),4);

            System.out.println(encryptedEditedOT_Eng);
            System.out.println(transpositionCipher.decryptRows(encryptedEditedOT_Eng,hc.getFinalKey()));


            System.out.println("\n\n\033[31;0mGA\033[0m\n");
            Genetic ga = new Genetic(encryptedEditedOT_Eng,englishDictionary.getListOfNgrams(),rndPermutation.length);
            Integer[] genetic = ga.genetic();

            System.out.println(encryptedEditedOT_Eng);
            System.out.println(transpositionCipher.decryptRows(encryptedEditedOT_Eng,new TranspositionKey(genetic)));
        }

        catch(Exception e){
            e.printStackTrace();
        }
    }
}