package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WordBeanList implements Serializable {
    private List<WordBean> wordList = new ArrayList<>();
    private String language;

    public WordBeanList() {
    }

    public List<WordBean> getWordList() {
        return wordList;
    }

    public void addWord(WordBean wb) {
        wordList.add(wb);
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
