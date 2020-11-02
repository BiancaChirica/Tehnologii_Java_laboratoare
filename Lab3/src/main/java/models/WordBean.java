package models;

import java.io.Serializable;
import java.util.Date;

public class WordBean implements Serializable {
    private String language;
    private String word;
    private String definition;
    private Date date;

    public WordBean() {
    }

    public WordBean(String language, String word, String definition) {
        this.language = language;
        this.word = word;
        this.definition = definition;
        this.date = new java.util.Date();
    }

    public WordBean(String language, String word, String definition, Date date) {
        this.language = language;
        this.word = word;
        this.definition = definition;
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }
}
