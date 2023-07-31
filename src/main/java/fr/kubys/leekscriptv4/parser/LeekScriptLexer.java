package fr.kubys.leekscriptv4.parser;

import com.intellij.lexer.FlexAdapter;

public class LeekScriptLexer extends FlexAdapter {
    public LeekScriptLexer() {
        super(new _LeekScriptLexer());
    }
}
