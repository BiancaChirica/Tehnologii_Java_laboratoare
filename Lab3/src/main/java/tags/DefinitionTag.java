package tags;

import services.DatabaseServices;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.DynamicAttributes;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class DefinitionTag extends SimpleTagSupport {

    private String word;
    private String language;
    private final DatabaseServices databaseServices = new DatabaseServices();


    public void setWord(String word) {
        this.word = word;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public void doTag() throws JspException, IOException {
        JspWriter out = getJspContext().getOut();
        System.out.println(word);
        if (word != null && language != null) {
            out.println(databaseServices.getDefinitionByWord(word, language));
        } else {
            out.print("This shouldn't happen!");
        }
    }


}
