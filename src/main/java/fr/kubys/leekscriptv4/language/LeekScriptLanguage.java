package fr.kubys.leekscriptv4.language;

import com.intellij.lang.Language;

public class LeekScriptLanguage extends Language {

    public static final LeekScriptLanguage INSTANCE = new LeekScriptLanguage();

    private LeekScriptLanguage() {
        super("LeekScriptV4");
    }
}
