package fr.kubys.leekscriptv4.options;

import com.google.gson.GsonBuilder;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.ui.TextFieldWithStoredHistory;
import org.jdesktop.swingx.VerticalLayout;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Collectors;

public class LeekScriptConfigurable implements SearchableConfigurable, Configurable.NoScroll {

    private JPanel rootComponent;
    private TextFieldWithStoredHistory siteUrl;
    private JTextField siteLogin;
    private JTextField sitePassword;
    private JCheckBox enableProxy;
    private JTextField proxyDomainName;
    private JTextField proxyPort;

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

    @Nls
    @Override
    public String getDisplayName() {
        return "LeekScript";
    }

    @Nullable
    @Override
    public String getHelpTopic() {
        return null;
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        if (rootComponent == null) {
            rootComponent = new JPanel();
            rootComponent.setLayout(new VerticalLayout());

            rootComponent.add(new JLabel("LeekWars URL"));
            siteUrl = new TextFieldWithStoredHistory("LeekScript.SiteUrl");
            siteUrl.setEnabled(false);
            rootComponent.add(siteUrl);

            rootComponent.add(new JLabel("LeekWars login"));
            siteLogin = new JTextField();
            rootComponent.add(siteLogin);

            rootComponent.add(new JLabel("LeekWars password"));
            sitePassword = new JPasswordField();
            rootComponent.add(sitePassword);

            enableProxy = new JCheckBox("Enable Proxy");
            rootComponent.add(enableProxy);
            enableProxy.addActionListener(event -> {
                proxyDomainName.setEnabled(enableProxy.isSelected());
                proxyPort.setEnabled(enableProxy.isSelected());
            });

            rootComponent.add(new JLabel("Proxy domain name"));
            proxyDomainName = new JTextField();
//            proxyDomainName.setEnabled(enableProxy.isSelected());
            rootComponent.add(proxyDomainName);

            rootComponent.add(new JLabel("Proxy port"));
            proxyPort = new JTextField();
//            proxyPort.setEnabled(enableProxy.isSelected());
            rootComponent.add(proxyPort);

            JButton checkConnexion = new JButton("Check Connexion");
            rootComponent.add(checkConnexion);
            JLabel checkConnexionResult = new JLabel("");
            rootComponent.add(checkConnexionResult);
            checkConnexion.addActionListener(event -> {
                try {
                    URL url = new URL(siteUrl.getText() + "/api/farmer/login-token");
                    HttpURLConnection conn;
                    if (enableProxy.isSelected()) {
                        Proxy webProxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyDomainName.getText(), Integer.parseInt(proxyPort.getText())));
                        conn = (HttpURLConnection) url.openConnection(webProxy);
                    } else {
                        conn = (HttpURLConnection) url.openConnection();
                    }
                    conn.setDoOutput(true);
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    try(DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
                        String data = Map.of("login", siteLogin.getText(),
                                        "password", sitePassword.getText())
                                .entrySet().stream()
                                .map(entry -> entry.getKey() + "=" + URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8))
                                .collect(Collectors.joining("&"));
                        wr.write(data.getBytes());
                    }
                    Map map = new GsonBuilder().create().fromJson(new InputStreamReader(conn.getInputStream()), Map.class);
                    String token = map.get("token").toString();
                    checkConnexionResult.setText("Connexion successful!");
                } catch (Exception e) {
                    checkConnexionResult.setText("Connexion error: " + e.getMessage());
                }
            });
        }

        return rootComponent;
    }

    @Override
    public boolean isModified() {
        LSSettings settings = LSSettings.getInstance();

        return !StringUtil.equals(settings.getAuthLogin(), proxyDomainName.getText())
                || !StringUtil.equals(settings.getSiteUrl(), siteUrl.getText())
                || !StringUtil.equals(settings.getAuthPassword(), proxyPort.getText())
                || settings.isEnableAuth() != enableProxy.isSelected()
                || !StringUtil.equals(settings.getSiteLogin(), siteLogin.getText())
                || !StringUtil.equals(settings.getSitePassword(), sitePassword.getText());
    }

    @Override
    public void apply() throws ConfigurationException {
        LSSettings settings = LSSettings.getInstance();

        settings.setSiteUrl(siteUrl.getText());
        settings.setEnableAuth(enableProxy.isSelected());
        settings.setAuthLogin(proxyDomainName.getText());
        settings.setAuthPassword(proxyPort.getText());
        settings.setSiteLogin(siteLogin.getText());
        settings.setSitePassword(sitePassword.getText());
    }

    @Override
    public void reset() {
        LSSettings settings = LSSettings.getInstance();

        siteUrl.setText(settings.getSiteUrl());
        enableProxy.setSelected(settings.isEnableAuth());
        proxyDomainName.setText(settings.getAuthLogin());
        proxyPort.setText(settings.getAuthPassword());
        siteLogin.setText(settings.getSiteLogin());
        sitePassword.setText(settings.getSitePassword());
    }

    @Override
    public void disposeUIResources() {

    }
}
