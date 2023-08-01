package fr.kubys.leekscriptv4.psi;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import fr.kubys.leekscriptv4.language.LSFileType;
import fr.kubys.leekscriptv4.language.LeekScriptLanguage;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.regex.Pattern;

public class LSFile extends PsiFileBase {
    public static final Pattern LKS_FILE_PATTERN = Pattern.compile("(.*)__(\\d+).lks");

    public LSFile(@NotNull FileViewProvider viewProvider) {
        super(viewProvider, LeekScriptLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public FileType getFileType() {
        return LSFileType.INSTANCE;
    }

    @Override
    public String toString() {
        return "LeekScript " + getName();
    }

    @Override
    public Icon getIcon(int flags) {
        return super.getIcon(flags);
    }
}