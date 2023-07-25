package fr.kubys.leekscriptv4.model;


import fr.kubys.leekscriptv4.api.ApiException;
import fr.kubys.leekscriptv4.options.PluginNotConfiguredException;

import java.io.IOException;

@FunctionalInterface
public interface ServerAction {

    void doAction() throws PluginNotConfiguredException, IOException, ApiException;
}
