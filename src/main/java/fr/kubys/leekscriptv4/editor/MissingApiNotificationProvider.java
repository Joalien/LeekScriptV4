//package fr.kubys.leekscriptv4.editor;
//
//import com.intellij.openapi.fileEditor.FileEditor;
//import com.intellij.openapi.project.Project;
//import com.intellij.openapi.util.Key;
//import com.intellij.openapi.vfs.VirtualFile;
//import com.intellij.psi.PsiFile;
//import com.intellij.psi.PsiManager;
//import com.intellij.ui.EditorNotificationPanel;
//import com.intellij.ui.EditorNotifications;
//import fr.kubys.leekscriptv4.actions.UpdateAPITask;
//import fr.kubys.leekscriptv4.language.LeekScriptLanguage;
//import fr.kubys.leekscriptv4.model.ModelManager;
//import org.jetbrains.annotations.NotNull;
//import org.jetbrains.annotations.Nullable;
//
//public class MissingApiNotificationProvider extends EditorNotifications.Provider<EditorNotificationPanel> {
//
//    private static final Key<EditorNotificationPanel> KEY = Key.create("setup.leekwars.api");
//
//    private final Project myProject;
//
//    public MissingApiNotificationProvider(Project project) {
//        myProject = project;
//    }
//
//    @NotNull
//    @Override
//    public Key<EditorNotificationPanel> getKey() {
//        return KEY;
//    }
//
//    @Nullable
//    @Override
//    public EditorNotificationPanel createNotificationPanel(@NotNull VirtualFile file, @NotNull FileEditor fileEditor) {
//        final PsiFile psiFile = PsiManager.getInstance(myProject).findFile(file);
//        if (psiFile == null) {
//            return null;
//        }
//
//        if (psiFile.getLanguage() != LeekScriptLanguage.INSTANCE) {
//            return null;
//        }
//
//        try {
//            LeekWarsApi.getApiPsiFile(myProject);
//        } catch (ApiNotFoundException e) {
//            return createPanel(myProject);
//        }
//
//        return null;
//    }
//
//
//    @NotNull
//    private static EditorNotificationPanel createPanel(final @NotNull Project project) {
//        final EditorNotificationPanel panel = new EditorNotificationPanel();
//
//        panel.setText("LeekWars API could not be found");
//        panel.createActionLabel("Update LeekWars API", new UpdateAPITask(project, ModelManager.getInstance(project)));
//
//        return panel;
//    }
//}
