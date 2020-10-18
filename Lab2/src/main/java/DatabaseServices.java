import models.WordBean;
import models.WordBeanList;

import java.io.*;
import java.util.Scanner;

import static java.rmi.server.LogStream.log;

public class DatabaseServices {
    public static final String DICTIONARY_BASE_PATH = "C:\\Users\\bianc\\Desktop\\Tehnologii_Java_laboratoare\\Lab2\\src\\main\\java\\dictionaries\\";

    public DatabaseServices() {
    }

    // checks if the word already exist in tha database
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
            System.out.println("An error occurred." );
            e.printStackTrace();
        }
        return false;
    }

    // add new word
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
            System.out.println("An error occurred." );
            e.printStackTrace();
        }
    }

    // returns all words for a language
    public WordBeanList getAllWordsForALanguage(String language) {
        String fileName = DICTIONARY_BASE_PATH + language + ".txt";
        WordBeanList resultList = new WordBeanList();
        resultList.setLanguage(language);
        try {
            File myObj = new File(fileName);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String word = myReader.nextLine().trim();
                log(word);
                if (myReader.hasNext()) {
                    String definition = myReader.nextLine().trim();
                    WordBean wordBean = new WordBean(language, word, definition);
                    resultList.addWord(wordBean);
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred." );
            e.printStackTrace();
        }

        return resultList;
    }
}
