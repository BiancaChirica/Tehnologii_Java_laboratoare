package models;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class Languages implements Serializable {
    private final List<String> languages = Arrays.asList("en", "ro", "fr" );
    private String defaultLanguage = languages.get(0);

    public Languages() {
    }

    public List<String> getLanguages() {
        return this.languages;
    }

    public void setDefaultLanguage(String defaultLanguage) {
        this.defaultLanguage = defaultLanguage;
    }

    public String getDefaultLanguage() {
        return defaultLanguage;
    }
}
