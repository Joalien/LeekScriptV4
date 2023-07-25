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
    private boolean enableProxy;
    private String proxyDomainName;
    private String proxyPort;

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

    public String getSiteLogin() {
        return siteLogin;
    }

    public String getSitePassword() {
        return sitePassword;
    }

    public boolean isProxyEnabled() {
        return enableProxy;
    }

    public void setSiteLogin(String siteLogin) {
        this.siteLogin = siteLogin;
    }

    public void setSitePassword(String sitePassword) {
        this.sitePassword = sitePassword;
    }

    public void setEnableProxy(boolean enableAuth) {
        this.enableProxy = enableAuth;
    }

    public void setSiteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
    }

    public String getProxyDomainName() {
        return proxyDomainName;
    }

    public void setProxyDomainName(String proxyDomainName) {
        this.proxyDomainName = proxyDomainName;
    }

    public String getProxyPort() {
        return proxyPort;
    }

    public void setProxyPort(String proxyPort) {
        this.proxyPort = proxyPort;
    }
}
