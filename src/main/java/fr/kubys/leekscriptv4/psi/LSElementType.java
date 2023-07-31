package fr.kubys.leekscriptv4.psi;

import com.intellij.psi.tree.IElementType;
import fr.kubys.leekscriptv4.language.LeekScriptLanguage;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class LSElementType extends IElementType {
    public LSElementType(@NotNull @NonNls String debugName) {
        super(debugName, LeekScriptLanguage.INSTANCE);
    }
}