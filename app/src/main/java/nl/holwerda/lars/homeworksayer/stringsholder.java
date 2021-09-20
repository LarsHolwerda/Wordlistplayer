package nl.holwerda.lars.homeworksayer;

import java.util.Locale;

public class stringsholder {

    private String localtext = "";
    private String translatedtext = "";
    private Locale translation;


    public String getLocaltext() {
        return localtext;
    }

    public void setLocaltext(String localtext) {
        this.localtext = localtext;
    }

    public String getTranslatedtext() {
        return translatedtext;
    }

    public void setTranslatedtext(String translatedtext) {
        this.translatedtext = translatedtext;
    }

    public Locale getTranslation() {
        return translation;
    }

    public void setTranslation(Locale translation) {
        this.translation = translation;
    }
}
