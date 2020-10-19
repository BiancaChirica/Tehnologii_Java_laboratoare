import models.WordBean;
import models.WordBeanList;

import java.io.*;
import java.util.Scanner;

public class DatabaseServices {
    public static final String DICTIONARY_BASE_PATH = "C:\\Users\\bianc\\Desktop\\Tehnologii_Java_laboratoare\\Lab2\\src\\main\\java\\dictionaries\\";

    public DatabaseServices() {
    }

    /**
     * Checks if the word already exist in the data structure.
     *
     * @param wordBean : the word to be verified
     * @return : true if it already exists, false otherwise
     */
    public boolean isDuplicateWord(WordBean wordBean) {
        String fileName = DICTIONARY_BASE_PATH + wordBean.getLanguage() + ".txt";
        try {
            File myObj = new File(fileName);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String word = myReader.nextLine();
                if (word.equals(wordBean.getWord()))
                    return true;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Add new word to the data structure
     *
     * @param wordBean : the word to be added
     */
    public void addNewWord(WordBean wordBean) {
        String fileName = DICTIONARY_BASE_PATH + wordBean.getLanguage() + ".txt";
        try {
            FileWriter fw = new FileWriter(fileName, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(wordBean.getWord());
            bw.newLine();
            bw.write(wordBean.getDefinition());
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * Returns all words from the data structure for a language
     *
     * @param language : the language that the words belong to
     * @return : list of words
     */
    public WordBeanList getAllWordsForALanguage(String language) {
        String fileName = DICTIONARY_BASE_PATH + language + ".txt";
        WordBeanList resultList = new WordBeanList();
        resultList.setLanguage(language);
        try {
            File myObj = new File(fileName);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String word = myReader.nextLine().trim();
                if (myReader.hasNext()) {
                    String definition = myReader.nextLine().trim();
                    WordBean wordBean = new WordBean(language, word, definition);
                    resultList.addWord(wordBean);
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return resultList;
    }
}
