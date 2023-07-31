package fr.kubys.leekscriptv4.psi;

import com.intellij.psi.tree.IElementType;
import fr.kubys.leekscriptv4.langage.LeekScriptLanguage;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class LSTokenType extends IElementType {

    public LSTokenType(@NotNull @NonNls String debugName) {
        super(debugName, LeekScriptLanguage.INSTANCE);
    }
}