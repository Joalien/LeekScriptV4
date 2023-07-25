package fr.kubys.leekscriptv4.actions;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import fr.kubys.leekscriptv4.api.ApiException;
import fr.kubys.leekscriptv4.api.LSApiClient;
import fr.kubys.leekscriptv4.api.dto.AIResponse;
import fr.kubys.leekscriptv4.options.PluginNotConfiguredException;
import fr.kubys.leekscriptv4.psi.PsiUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DownloadScriptsTask implements Runnable {
    private static final Pattern AI_IDS_REGEX = Pattern.compile("__AI_IDS = \\[([^\\]]+)\\];");
    private static final Pattern AI_NAMES_REGEX = Pattern.compile("__AI_NAMES = \\[([^\\]]+)\\];");

    private Map<String, String> files = new LinkedHashMap<>();
    private Project project;

    public DownloadScriptsTask(Project project) {
        this.project = project;
    }

    @Override
    public void run() {
        ApplicationManager.getApplication().invokeLater(() -> ApplicationManager.getApplication().runWriteAction(() ->
                LSApiClient.callAction(this::downloadFiles)
        ));
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

    private void downloadFiles() throws IOException, PluginNotConfiguredException, ApiException {
        System.out.println("Downloading files...");
        Module module = ModuleManager.getInstance(project).getModules()[0];
        VirtualFile sourceRoots = ModuleRootManager.getInstance(module).getSourceRoots()[0];
        PsiDirectory srcDirectory = PsiManager.getInstance(project).findDirectory(sourceRoots);

        for (Map.Entry<Integer, String> entry : LSApiClient.getInstance().listScripts().entrySet()) {
            // TODO run async
            downloadScript(srcDirectory, entry);
        }
    }

    private void downloadScript(PsiDirectory srcDirectory, Map.Entry<Integer, String> entry) throws IOException, PluginNotConfiguredException, ApiException {
        AIResponse leekScript = LSApiClient.getInstance().downloadScript(entry.getKey());

        String fileName = String.format("%s__%s.lks", entry.getValue(), entry.getKey());

        PsiFile file = PsiUtils.createDummyFile(project, fileName, leekScript.getAi().getCode());

        PsiFile existingFile = srcDirectory.findFile(fileName);

        if (existingFile == null) {
            srcDirectory.add(file);
        } else {
            assert existingFile.getViewProvider().getDocument() != null;
            existingFile.getViewProvider().getDocument().setText(file.getText());
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
