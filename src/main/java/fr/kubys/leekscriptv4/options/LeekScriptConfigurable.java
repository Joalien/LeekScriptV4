package fr.kubys.leekscriptv4.options;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class LeekScriptConfigurable implements SearchableConfigurable, Configurable.NoScroll {

    private LSSettingsComponent lsSettingsComponent = new LSSettingsComponent();

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "Leekscript Account";
    }


    @Override
    public @Nullable JComponent getPreferredFocusedComponent() {
        return lsSettingsComponent.getPreferredFocusedComponent();
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        lsSettingsComponent = new LSSettingsComponent();
        return lsSettingsComponent.getPanel();
    }

    @Override
    public boolean isModified () {
        LSSettings settings = LSSettings.getInstance();

        return !StringUtil.equals(settings.getProxyDomainName(), lsSettingsComponent.proxyDomainName.getText())
                || !StringUtil.equals(settings.getSiteUrl(), lsSettingsComponent.siteUrl.getText())
                || !StringUtil.equals(settings.getProxyPort(), lsSettingsComponent.proxyPort.getText())
                || settings.isProxyEnabled() != lsSettingsComponent.enableProxy.isSelected()
                || !StringUtil.equals(settings.getSiteLogin(), lsSettingsComponent.siteLogin.getText())
                || !StringUtil.equals(settings.getSitePassword(), lsSettingsComponent.sitePassword.getText());
    }

    @Override
    public void apply () throws ConfigurationException {
        LSSettings settings = LSSettings.getInstance();

        settings.setSiteUrl(lsSettingsComponent.siteUrl.getText());
        settings.setEnableProxy(lsSettingsComponent.enableProxy.isSelected());
        settings.setProxyDomainName(lsSettingsComponent.proxyDomainName.getText());
        settings.setProxyPort(lsSettingsComponent.proxyPort.getText());
        settings.setSiteLogin(lsSettingsComponent.siteLogin.getText());
        settings.setSitePassword(lsSettingsComponent.sitePassword.getText());
    }

    @Override
    public void reset () {
        LSSettings settings = LSSettings.getInstance();

        lsSettingsComponent.siteUrl.setText(settings.getSiteUrl());
        lsSettingsComponent.enableProxy.setSelected(settings.isProxyEnabled());
        lsSettingsComponent.proxyDomainName.setText(settings.getProxyDomainName());
        lsSettingsComponent.proxyPort.setText(settings.getProxyPort());
        lsSettingsComponent.siteLogin.setText(settings.getSiteLogin());
        lsSettingsComponent.sitePassword.setText(settings.getSitePassword());
    }

    @Override
    public void disposeUIResources () {
        lsSettingsComponent = null;
    }

    @NotNull
    @Override
    public String getId() {
        return getDisplayName();
    }

    @Nullable
    @Override
    public Runnable enableSearch(String option) {
        return null;
    }

    @Nullable
    @Override
    public String getHelpTopic() {
        return null;
    }
}
