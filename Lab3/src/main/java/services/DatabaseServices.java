package services;

import models.WordBean;
import models.WordBeanList;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class DatabaseServices {
    public static final String DICTIONARY_BASE_PATH = "C:\\Users\\bianc\\Desktop\\Tehnologii_Java_laboratoare\\Lab3\\src\\main\\java\\dictionaries\\";

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
            bw.write(new SimpleDateFormat("dd/MM/yyyy HH:mm").format(wordBean.getDate()));
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public String getDefinitionByWord(String word, String language) {
        String fileName = DICTIONARY_BASE_PATH + language + ".txt";
        try {
            File myObj = new File(fileName);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String wordRead = myReader.nextLine().trim();
                if (myReader.hasNext()) {
                    String definition = myReader.nextLine().trim();
                    if (wordRead.toLowerCase().equals(word.trim().toLowerCase()))
                        return definition;
                }
                myReader.nextLine();
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return "No definition found";
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
                    if (myReader.hasNext()) {
                        String dateString = myReader.nextLine().trim();
                        Date date =new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(dateString);
                        WordBean wordBean = new WordBean(language, word, definition, date);
                        resultList.addWord(wordBean);
                    }
                }
            }
            myReader.close();
        } catch (FileNotFoundException | ParseException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return resultList;
    }
}
