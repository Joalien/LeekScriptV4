package fr.kubys.leekscriptv4.project;

import com.intellij.ide.util.projectWizard.ModuleBuilder;
import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.ide.util.projectWizard.WizardContext;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.roots.ContentEntry;
import com.intellij.openapi.roots.ModifiableRootModel;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.Optional;

public class LSModuleBuilder extends ModuleBuilder {
    @Override
    public void setupRootModel(@NotNull ModifiableRootModel modifiableRootModel) throws ConfigurationException {
        ContentEntry contentEntry = doAddContentEntry(modifiableRootModel);

        try {
            VirtualFile srcDirectory = Optional.ofNullable(contentEntry)
                    .map(ContentEntry::getFile)
                    .orElseThrow()
                    .createChildDirectory(this, "src");
            contentEntry.addSourceFolder(srcDirectory, false);

            VirtualFile genDirectory = Optional.ofNullable(contentEntry)
                    .map(ContentEntry::getFile)
                    .orElseThrow()
                    .createChildDirectory(this, "gen");
            contentEntry.addSourceFolder(genDirectory, false);
        } catch (IOException e) {
            throw new ConfigurationException(e.getMessage(), "Unable to Create Folder");
        }

    }

    @Override
    public ModuleType getModuleType() {
        return LSModuleType.getInstance();
    }

    @Nullable
    @Override
    public ModuleWizardStep getCustomOptionsStep(WizardContext context, Disposable parentDisposable) {
        return new LSModuleWizardStep();
    }
}
