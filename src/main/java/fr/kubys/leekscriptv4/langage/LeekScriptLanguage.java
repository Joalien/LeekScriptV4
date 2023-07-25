package fr.kubys.leekscriptv4.langage;

import com.intellij.lang.Language;

public class LeekScriptLanguage extends Language {

    public static final LeekScriptLanguage INSTANCE = new LeekScriptLanguage();

    private LeekScriptLanguage() {
        super("LeekScriptV4");
    }
}
