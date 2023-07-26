package fr.kubys.leekscriptv4.actions;

import com.intellij.ide.fileTemplates.FileTemplate;
import com.intellij.ide.fileTemplates.FileTemplateManager;
import com.intellij.ide.fileTemplates.FileTemplateUtil;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiManager;
import fr.kubys.leekscriptv4.api.ApiException;
import fr.kubys.leekscriptv4.api.LSApiClient;
import fr.kubys.leekscriptv4.options.PluginNotConfiguredException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


public class DownloadAPITask implements Runnable {

    public static final String LEEKWARS_API_LKS = "leekwars-api.lks";
    private Project project;
    private PsiDirectory genDirectory;

    public DownloadAPITask(Project project) {
        this.project = project;
    }

    @Override
    public void run() {
        ApplicationManager.getApplication().invokeLater(() -> {
            LSApiClient.callAction(this::init);
            LSApiClient.callAction(this::downloadApi);
            Notifications.Bus.notify(new Notification("LeekScript", "Synchronization", "Successfully downloaded Leekwars API!", NotificationType.INFORMATION), project);
        });
    }

    private void init() throws FileNotFoundException {
        Module module = ModuleManager.getInstance(this.project).getModules()[0];
        VirtualFile genRoot = Arrays.stream(ModuleRootManager.getInstance(module).getSourceRoots())
                .filter(virtualFile -> virtualFile.getName().equals("gen"))
                .findAny()
                .orElseThrow(FileNotFoundException::new);
        genDirectory = PsiManager.getInstance(this.project).findDirectory(genRoot);
    }

    private void downloadApi() throws IOException, PluginNotConfiguredException, ApiException {
        System.out.println("Downloading " + LEEKWARS_API_LKS);

        FileTemplateManager templateManager = FileTemplateManager.getInstance();
        Map<String, Object> propsParam = new HashMap<>();
        propsParam.put("functions", LSApiClient.getInstance().getFunctions());
        propsParam.put("constants", LSApiClient.getInstance().getConstants());
        propsParam.put("chips", LSApiClient.getInstance().getChips());
        propsParam.put("weapons", LSApiClient.getInstance().getWeapons());

        ApplicationManager.getApplication().runWriteAction(() -> {
            try {
                Optional.ofNullable(genDirectory.findFile(LEEKWARS_API_LKS)).ifPresent(PsiElement::delete);
                FileTemplate internalTemplate = templateManager.getInternalTemplate(LEEKWARS_API_LKS);
                FileTemplateUtil.createFromTemplate(internalTemplate, LEEKWARS_API_LKS, propsParam, genDirectory, getClass().getClassLoader());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
