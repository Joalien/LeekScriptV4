package fr.kubys.leekscriptv4.actions;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import fr.kubys.leekscriptv4.api.ApiException;
import fr.kubys.leekscriptv4.api.LSApiClient;
import fr.kubys.leekscriptv4.api.dto.AIResponse;
import fr.kubys.leekscriptv4.options.PluginNotConfiguredException;

import java.io.IOException;
import java.util.Map;


public class UpdateAPITask implements Runnable {

    private Project project;

    public UpdateAPITask(Project project) {
        this.project = project;
    }

    @Override
    public void run() {
        LSApiClient.callAction(this::writeApiFile);
    }

    private void writeApiFile() {
//        ApplicationManager.getApplication().invokeLater(() ->
//                ApplicationManager.getApplication().runWriteAction(() -> {
//                    try {
//                        Map<Integer, String> integerStringMap = LSApiClient.getInstance().listScripts();
//                        integerStringMap.forEach((key, value) -> {
//                            try {
//                                AIResponse ai = LSApiClient.getInstance().downloadScript(key);
//
//                            } catch (IOException e) {
//                                throw new RuntimeException(e);
//                            } catch (PluginNotConfiguredException e) {
//                                throw new RuntimeException(e);
//                            } catch (ApiException e) {
//                                throw new RuntimeException(e);
//                            }
//                        });
//
//                    } catch (Exception e) {
//                        Notifications.Bus.notify(new Notification("LeekScript", "LeekWars API", "Can't write API", NotificationType.ERROR));
//                        throw new RuntimeException(e);
//                    }
//                }
//        ));
    }

//    private PsiDirectory findOrCreateGenSourceRoot() throws IOException {
//        Module module = ModuleManager.getInstance(project).getModules()[0];
//        VirtualFile genRoot = LeekWarsApi.getGenRoot(project);
//
//        if (genRoot == null) {
//            ModifiableRootModel model = ModuleRootManager.getInstance(module).getModifiableModel();
//
//            ContentEntry contentEntry = model.getContentEntries()[0];
//
//            if (contentEntry.getFile() != null) {
//                VirtualFile genDirectory = contentEntry.getFile().createChildDirectory(this, "gen");
//                JavaSourceRootProperties properties = JpsJavaExtensionService.getInstance().createSourceRootProperties("", true);
//                genRoot = contentEntry.addSourceFolder(genDirectory, JavaSourceRootType.SOURCE, properties).getFile();
//                model.commit();
//                module.getProject().save();
//            }
//        }
//
//        if (genRoot == null) {
//            throw new IOException("Could not generate gen source root");
//        }
//
//        return PsiManager.getInstance(project).findDirectory(genRoot);
//    }
}
