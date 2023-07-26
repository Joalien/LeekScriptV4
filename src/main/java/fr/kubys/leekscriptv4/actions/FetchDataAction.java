package fr.kubys.leekscriptv4.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.vcs.changes.RunnableBackgroundableWrapper;

public class FetchDataAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        ProgressManager.getInstance().run(new RunnableBackgroundableWrapper(e.getProject(), "Downloading scripts from LeekWars server...", new DownloadScriptsTask(e.getProject())));
        ProgressManager.getInstance().run(new RunnableBackgroundableWrapper(e.getProject(), "Fetching data from LeekWars server...", new DownloadAPITask(e.getProject())));
    }
}
