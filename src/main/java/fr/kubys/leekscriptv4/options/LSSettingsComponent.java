package fr.kubys.leekscriptv4.options;

import com.intellij.ui.TextFieldWithStoredHistory;
import com.intellij.util.ui.FormBuilder;
import fr.kubys.leekscriptv4.api.LSApiClient;

import javax.swing.*;

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
            checkConnexionResult.setText("Connecting...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            try {
                LSApiClient.getInstance().connectToLeekWars();
                checkConnexionResult.setText("Connexion successful!");
            } catch (Exception e) {
                checkConnexionResult.setText("Connexion error: " + e);
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
