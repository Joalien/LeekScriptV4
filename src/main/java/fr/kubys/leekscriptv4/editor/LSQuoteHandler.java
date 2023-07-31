package fr.kubys.leekscriptv4.editor;

import com.intellij.codeInsight.editorActions.SimpleTokenSetQuoteHandler;
import fr.kubys.leekscriptv4.psi.LSTypes;

public class LSQuoteHandler extends SimpleTokenSetQuoteHandler {

    public LSQuoteHandler() {
        super(
                LSTypes.STRING
        );
    }
}
