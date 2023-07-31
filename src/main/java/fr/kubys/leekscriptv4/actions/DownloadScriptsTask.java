package fr.kubys.leekscriptv4.actions;

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
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;
import com.intellij.psi.PsiManager;
import fr.kubys.leekscriptv4.api.ApiException;
import fr.kubys.leekscriptv4.api.LSApiClient;
import fr.kubys.leekscriptv4.api.dto.AIDto;
import fr.kubys.leekscriptv4.api.dto.AIResponse;
import fr.kubys.leekscriptv4.api.dto.AIsResponse;
import fr.kubys.leekscriptv4.api.dto.FolderDto;
import fr.kubys.leekscriptv4.langage.LSFileType;
import fr.kubys.leekscriptv4.options.PluginNotConfiguredException;
import org.apache.commons.lang.StringEscapeUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class DownloadScriptsTask implements Runnable {
    private static final Pattern AI_IDS_REGEX = Pattern.compile("__AI_IDS = \\[([^\\]]+)\\];");
    private static final Pattern AI_NAMES_REGEX = Pattern.compile("__AI_NAMES = \\[([^\\]]+)\\];");
    private final Map<Integer, PsiDirectory> folderPsiDirectory = new HashMap<>();
    private Map<Integer, FolderDto> folderTree = new HashMap<>();

    private Map<String, String> files = new LinkedHashMap<>();
    private Project project;

    public DownloadScriptsTask(Project project) {
        this.project = project;
    }

    private void init() throws FileNotFoundException {
        Module module = ModuleManager.getInstance(this.project).getModules()[0];
        VirtualFile sourceRoots = Arrays.stream(ModuleRootManager.getInstance(module).getSourceRoots())
                .filter(virtualFile -> virtualFile.getName().equals("src"))
                .findAny()
                .orElseThrow(FileNotFoundException::new);
        PsiDirectory sourceDirectory = PsiManager.getInstance(this.project).findDirectory(sourceRoots);
        folderPsiDirectory.put(0, sourceDirectory);
    }

    @Override
    public void run() {
        ApplicationManager.getApplication().invokeLater(() -> {
            LSApiClient.callAction(this::init);
            LSApiClient.callAction(this::downloadFiles);
            Notifications.Bus.notify(new Notification("LeekScript", "Synchronization", "Synchronization successful!", NotificationType.INFORMATION), project);
        });
    }

    private void downloadFiles() throws IOException, PluginNotConfiguredException, ApiException {
        System.out.println("Downloading files...");

        AIsResponse aIsResponse = LSApiClient.getInstance().listScripts();
        folderTree = aIsResponse.getFolders().stream().collect(Collectors.toMap(FolderDto::getId, f -> f));

        aIsResponse.getFolders().forEach(this::createFolder);

        for (AIDto ai : aIsResponse.getAis()) {
            // TODO run async
            downloadScript(folderPsiDirectory.get(ai.getParent()), ai.getId(), ai.getName());
        }
    }

    private void createFolder(FolderDto folder) {
        PsiDirectory parent = folderPsiDirectory.get(folder.getParent());
        if (parent == null) {
            createFolder(folderTree.get(folder.getParent()));
            parent = folderPsiDirectory.get(folder.getParent());
        }
        createFolder(parent, folder);
    }


    private void createFolder(PsiDirectory parentDirectory, FolderDto folder) {
        if (parentDirectory.findSubdirectory(folder.buildName()) == null) {
            ApplicationManager.getApplication().runWriteAction(() -> {
                folderPsiDirectory.put(folder.getId(), parentDirectory.createSubdirectory(folder.buildName()));
            });
        } else {
            folderPsiDirectory.put(folder.getId(), parentDirectory.findSubdirectory(folder.buildName()));
        }
    }

    private void downloadScript(PsiDirectory srcDirectory, Integer id, String name) throws IOException, PluginNotConfiguredException, ApiException {
        AIResponse leekScript = LSApiClient.getInstance().downloadScript(id);

        String fileName = String.format("%s__%s.lks", name, id);

        String text = leekScript.getAi().getCode();
        PsiFile file = PsiFileFactory.getInstance(project).createFileFromText(fileName, LSFileType.INSTANCE, text);

        PsiFile existingFile = srcDirectory.findFile(fileName);

        if (existingFile == null) {
            srcDirectory.add(file);
        } else {
            assert existingFile.getViewProvider().getDocument() != null;
            if (!Objects.equals(existingFile.getViewProvider().getDocument().getText(), file.getText())) {
                int result = JOptionPane.showConfirmDialog(
                        null,
                        String.format("Do you want to override %s?", name),
                        "Changes have been detected!",
                        JOptionPane.YES_NO_OPTION
                );

                if (result == JOptionPane.YES_OPTION) {
                    ApplicationManager.getApplication().runWriteAction(() -> existingFile.getViewProvider().getDocument().setText(file.getText()));
                }
            }
        }
    }

    public void parseScriptTags(Document editor) {
        Elements scripts = editor.select("head script");

        for (Element script : scripts) {
            Matcher matcher = AI_IDS_REGEX.matcher(script.data());

            if (matcher.matches()) {
                parseIdentifiers(matcher);
            }

            matcher = AI_NAMES_REGEX.matcher(script.data());

            if (matcher.matches()) {
                parseNames(matcher);
            }
        }
    }

    private void parseNames(Matcher matcher) {
        String[] names = matcher.group(1).split(",");
        int i = 0;

        for (Map.Entry<String, String> entry : files.entrySet()) {
            entry.setValue(StringEscapeUtils.unescapeHtml(names[i].substring(1, names[i].length() - 1)));
            i++;
        }
    }

    private void parseIdentifiers(Matcher matcher) {
        for (String id : matcher.group(1).split(",")) {
            files.put(id, null);
        }
    }

    Map<String, String> getFiles() {
        return files;
    }
}
