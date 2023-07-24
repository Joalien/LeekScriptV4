package fr.kubys.leekscriptv4.options;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.*;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.Nullable;

@State(name = "LSSettings", storages = {@Storage("leekscript.xml")})
public class LSSettings implements PersistentStateComponent<LSSettings> {
    private String siteUrl = "https://leekwars.com";
    private String siteLogin;
    private String sitePassword;
    private boolean enableAuth;
    private String authLogin;
    private String authPassword;

    public static LSSettings getInstance() {
        return ApplicationManager.getApplication().getService(LSSettings.class);
    }

    @Nullable
    @Override
    public LSSettings getState() {
        return this;
    }

    @Override
    public void loadState(LSSettings state) {
        XmlSerializerUtil.copyBean(state, this);
    }

    public boolean isValid() {
        return StringUtils.isNotBlank(siteUrl) && StringUtils.isNotBlank(siteLogin) && StringUtils.isNotBlank(sitePassword);
    }

    public String getSiteUrl() {
        return siteUrl;
    }

    public String getAuthLogin() {
        return authLogin;
    }

    public String getAuthPassword() {
        return authPassword;
    }

    public String getSiteLogin() {
        return siteLogin;
    }

    public String getSitePassword() {
        return sitePassword;
    }

    public boolean isEnableAuth() {
        return enableAuth;
    }

    public void setSiteLogin(String siteLogin) {
        this.siteLogin = siteLogin;
    }

    public void setSitePassword(String sitePassword) {
        this.sitePassword = sitePassword;
    }

    public void setEnableAuth(boolean enableAuth) {
        this.enableAuth = enableAuth;
    }

    public void setAuthLogin(String authLogin) {
        this.authLogin = authLogin;
    }

    public void setAuthPassword(String authPassword) {
        this.authPassword = authPassword;
    }

    public void setSiteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
    }
}
