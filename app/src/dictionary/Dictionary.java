package dictionary;

import javax.swing.*;
import java.io.*;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class Dictionary {
    private LinkedList<String> words = null;                     //TODO static final frequencies
    private boolean isLowerCase = false;
    private boolean isLoaded = false;
    private boolean isStandardAlphabet = false;
    ArrayList<HashMap<String, Double>> listOfNgrams = null;


    /**
     * constructors
     */
    public Dictionary() {
        this.isLoaded = false;
        this.isLowerCase = false;
        this.isStandardAlphabet = false;
    }

    public Dictionary(LinkedList<String> words) {
        this.words = words;
        this.isLowerCase = false;
        this.isStandardAlphabet = false;
        if (words.size() > 0) {
            this.isLoaded = true;
        } else {
            this.isLoaded = false;
        }
    }

    /**
     * functions
     **/
    //TODO isLoaded,isStandardAlphabet,isLowerCase setter
    public static File pickFromFileChooser() throws Exception {
        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new File("../dictionaries"));
        fc.setDialogTitle("Select the file to open... ");
        fc.setMultiSelectionEnabled(false);
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        File f = null;
        int returnVal = fc.showOpenDialog(null);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            f = fc.getSelectedFile();

        }
        return f;

    }//TODO exceptions

    public boolean loadFromFile(boolean windows1250) {
        try {
            File selectedFile = pickFromFileChooser();
            BufferedReader bufferedReader;
            if (windows1250) {
                bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(selectedFile), "Windows-1250"));
            } else {
                bufferedReader = new BufferedReader(new FileReader(selectedFile));
            }

            String line;
            this.words = new LinkedList<>();

            while ((line = bufferedReader.readLine()) != null) {
                words.add(line);
            }


            if (words.size() > 0) {
                this.isLoaded = true;
            } else {
                this.isLoaded = false;
            }
            this.isLowerCase = false;
            this.isStandardAlphabet = false;
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {

        }

    } //TODO exceptions

    private boolean writeToFile(File fileToSave) {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(fileToSave));
            bw.write(wordsToStringLines());
            bw.flush();
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();

        } finally {
            try {
                bw.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }//TODO exceptions

    public boolean writeToFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("./dictionaries"));
        fileChooser.setDialogTitle("Save dictionary");

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            if (writeToFile(fileToSave)) {
                return true;
            }
        }
        return false;
    }
        public void toLowerCase () {
            if (words != null) {
                LinkedList<String> newWords = new LinkedList<>();
                for (String s : words) {
                    newWords.add(s.toLowerCase());
                }
                words = newWords;
                isLowerCase = true;
            }
        }

        public void toStandardAlphabet () {
            if (words != null) {
                LinkedList<String> newWords = new LinkedList<>();
                for (String word : words) {
                    newWords.add(wordToStandardAlphabet(word));
                }
                words = newWords;
                isStandardAlphabet = true;
            }
        }

        private String wordToStandardAlphabet (String word){ //TODO
            String[] pd = {"ď", "ľ", "š", "č", "ť", "ž", "ý", "á", "í", "é", "ú",
                    "ä", "ň", "ĺ", "ŕ", "ô", "ó", "Ď", "Ľ", "Š", "Č", "Ť", "Ž",
                    "Ý", "Á", "Í", "É", "Ú", "Ň", "Ó", "Ŕ", "Ĺ"};

            String[] p = {"d", "l", "s", "c", "t", "z", "y", "a", "i", "e", "u",
                    "a", "n", "l", "r", "o", "o", "D", "L", "S", "C", "T", "Z",
                    "Y", "A", "I", "E", "U", "N", "O", "R", "L"};

            String newWord = word;
            for (int i = 0; i < pd.length; i++) {
                newWord = newWord.replaceAll(pd[i], p[i]);
            }
            return newWord;
        }

        public static String normalizeText (String text){
            String retText = text.toLowerCase();
            retText = Normalizer.normalize(retText, Normalizer.Form.NFD);
            retText = retText.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
            retText = retText.replaceAll("[^a-z]", "");
            return retText;
        }




        /*
         * print functions
         **/
        public void printDirectory () {
            if (isLoaded) {
                System.out.println(words.toString());
            } else {
                System.out.println("There is no loaded words in directory. Try loadFromFile function");
            }
        }



        /*
         * getters and setters
         **/
        public LinkedList<String> getWords () {
            return words;
        }

        public void setWords (LinkedList < String > words) {
            if (words.size() > 0) {
                this.isLoaded = true;
                this.words = words;
            }
        }

        public boolean isLowerCase () {
            return isLowerCase;
        }

        public boolean isLoaded () {
            return isLoaded;
        }

        public boolean isStandardAlphabet () {
            return isStandardAlphabet;
        }

        public int getSize () {
            return words.size();
        }

        public ArrayList<HashMap<String, Double>> getListOfNgrams () {
            return listOfNgrams;
        }

        /*
         * helpers
         * */
        private String wordsToStringLines () {
            StringBuilder stringBuilder = new StringBuilder();
            for (String word : words) {
                stringBuilder.append(word + '\n');
            }
            return stringBuilder.toString();
        }

    }

