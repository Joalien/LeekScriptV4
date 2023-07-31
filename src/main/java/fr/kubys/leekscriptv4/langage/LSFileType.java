package fr.kubys.leekscriptv4.langage;

import com.intellij.openapi.fileTypes.LanguageFileType;
import com.intellij.openapi.util.IconLoader;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class LSFileType extends LanguageFileType {
    public static final LSFileType INSTANCE = new LSFileType();

    private LSFileType() {
        super(LeekScriptLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public String getName() {
        return "Leekscript";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "Script for LeekWars";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return "lks";
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return IconLoader.getIcon("/icons/leek.png", LSFileType.class);
    }
}
