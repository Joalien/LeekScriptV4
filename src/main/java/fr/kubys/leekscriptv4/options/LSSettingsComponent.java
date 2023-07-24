package fr.kubys.leekscriptv4.options;

import com.google.gson.GsonBuilder;
import com.intellij.ui.TextFieldWithStoredHistory;
import com.intellij.util.ui.FormBuilder;

import javax.swing.*;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Collectors;

public class LSSettingsComponent {

    private final JPanel rootComponent;
    final TextFieldWithStoredHistory siteUrl = new TextFieldWithStoredHistory("LeekScript.SiteUrl");
    final JTextField siteLogin = new JTextField();
    final JTextField sitePassword = new JPasswordField();
    JCheckBox enableProxy = new JCheckBox("Enable Proxy");
    JTextField proxyDomainName = new JTextField();
    JTextField proxyPort = new JTextField();
    private final JButton checkConnexion = new JButton("Check Connexion");
    private final JLabel checkConnexionResult = new JLabel("");

    public LSSettingsComponent() {
        siteUrl.setEnabled(false);

        enableProxy.addActionListener(event -> {
            proxyDomainName.setEnabled(enableProxy.isSelected());
            proxyPort.setEnabled(enableProxy.isSelected());
        });
        proxyDomainName.setEnabled(enableProxy.isSelected());
        proxyPort.setEnabled(enableProxy.isSelected());
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
                try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
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

        JPanel proxyComponent = FormBuilder.createFormBuilder()
                .addComponent(enableProxy, 1)
                .addLabeledComponent(new JLabel("Proxy domain name"), proxyDomainName, 1, false)
                .addLabeledComponent(new JLabel("Proxy port"), proxyPort, 1, false)
                .getPanel();
        rootComponent = FormBuilder.createFormBuilder()
                .addLabeledComponent(new JLabel("LeekWars URL"), siteUrl, 1, false)
                .addLabeledComponent(new JLabel("LeekWars login"), siteLogin, 1, false)
                .addLabeledComponent(new JLabel("LeekWars password"), sitePassword, 1, false)
                .addComponent(proxyComponent, 2)
                .addComponent(checkConnexion, 1)
                .addComponent(checkConnexionResult, 1)
                .addComponentFillVertically(new JPanel(), 0)
                .getPanel();
    }

    public JPanel getPanel() {
        return rootComponent;
    }

    public JComponent getPreferredFocusedComponent() {
        return siteLogin;
    }
}
