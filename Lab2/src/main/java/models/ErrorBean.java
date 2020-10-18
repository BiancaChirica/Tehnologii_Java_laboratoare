package models;

import java.io.Serializable;

public class ErrorBean implements Serializable {
    private String errorText;

    public ErrorBean() {
    }

    public String getErrorText() {
        return errorText;
    }

    public void setErrorText(String errorText) {
        this.errorText = errorText;
    }
}
