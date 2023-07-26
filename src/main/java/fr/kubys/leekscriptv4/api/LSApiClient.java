package fr.kubys.leekscriptv4.api;

import com.google.common.io.CharStreams;
import com.google.gson.GsonBuilder;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.util.net.HttpConfigurable;
import fr.kubys.leekscriptv4.actions.CompilationException;
import fr.kubys.leekscriptv4.api.dto.*;
import fr.kubys.leekscriptv4.model.ServerAction;
import fr.kubys.leekscriptv4.options.LSSettings;
import fr.kubys.leekscriptv4.options.PluginNotConfiguredException;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class LSApiClient {

    private static final LSApiClient INSTANCE = new LSApiClient();

    private String token;

    public static LSApiClient getInstance() {
        return INSTANCE;
    }

    /**
     * Wrapper for a call to a server action which handles exceptions.
     *
     * @param action the action to call
     */
    public static void callAction(ServerAction action) {
        try {
            action.doAction();
        } catch (IOException e) {
            Notifications.Bus.notify(new Notification("LeekScript", "Error", "Can't reach LeekWars server :(", NotificationType.ERROR));
            throw new RuntimeException(e);
        } catch (PluginNotConfiguredException e) {
            Notifications.Bus.notify(new Notification("LeekScript", "Can't connect to LeekWars server", "Please configure the LeekScript plugin", NotificationType.ERROR));
        } catch (ApiException e) {
            Notifications.Bus.notify(new Notification("LeekScript", "Can't connect to LeekWars server", e.getMessage(), NotificationType.ERROR));
        }
    }

//    public List<Weapon> getWeapons() throws IOException, PluginNotConfiguredException, ApiException {
//        WeaponsResponse resp = get("/api/weapon/get-all", WeaponsResponse.class);
//        Map<String, String> labels = get("https://raw.githubusercontent.com/leek-wars/leek-wars-client/master/src/lang/fr/weapon.json", Map.class);
//
//        List<Weapon> weapons = new ArrayList<Weapon>();
//
//        for (WeaponsResponse.Weap w : resp.getWeapons()) {
//            Weapon weapon = new Weapon();
//            weapon.id = "WEAPON_" + w.getName().toUpperCase();
//            weapon.name = labels.get(w.getName());
//            weapon.description = "";
//            weapon.level = "Niveau " + w.getLevel();
//            weapon.price = "" + w.getCost();
//            weapon.value = w.getId();
//            weapons.add(weapon);
//        }
//
//        return weapons;
//    }
//
//    public List<Chip> getChips() throws IOException, PluginNotConfiguredException, ApiException {
//        Map resp = get("/api/chip/get-all", Map.class);
//        Map<String, String> labels = get("https://raw.githubusercontent.com/leek-wars/leek-wars-client/master/src/lang/fr/chip.json", Map.class);
//
//        Map map = (Map) resp.get("chips");
//
//        List<Chip> chips = new ArrayList<Chip>();
//
//        for (Object o : map.values()) {
//            Map w = (Map) o;
//
//            Chip chip = new Chip();
//            chip.id = "CHIP_" + w.get("name").toString().toUpperCase();
//            chip.name = labels.get(w.get("name").toString());
//            chip.description = "";
//            chip.level = "Niveau " + (int) Double.parseDouble(w.get("level").toString());
//            chip.price = w.get("cost").toString();
//            chip.value = "" + (int) Double.parseDouble(w.get("id").toString());
//            chips.add(chip);
//        }
//
//        return chips;
//    }
//
//    public List<Function> getFunctions() throws IOException, PluginNotConfiguredException, ApiException {
//        FunctionsResponse resp = get("/api/function/get-all", FunctionsResponse.class);
//        Map<String, String> labels = get("https://raw.githubusercontent.com/leek-wars/leek-wars/master/src/lang/doc.fr.lang", Map.class);
//
//        List<Function> functions = new ArrayList<Function>();
//        String lastName = null;
//        int overloads = 0;
//
//        for (FunctionsResponse.Fun fun : resp.getFunctions()) {
//            Function function = new Function();
//            function.name = fun.getName();
//
//            String funName = fun.getName();
//
//            if (fun.getName().equals(lastName)) {
//                overloads++;
//                funName += "_" + (overloads + 1);
//            } else {
//                overloads = 0;
//            }
//            lastName = fun.getName();
//
//            function.description = labels.get("func_" + funName);
//
//            int argPos = 0;
//
//            for (String arg : fun.getArgumentNames()) {
//                argPos++;
//                if (!arg.matches("\\d+")) {
//                    function.getParameters().put(arg, labels.get("func_" + funName + "_arg_" + argPos));
//                }
//            }
//
//            functions.add(function);
//        }
//
//        return functions;
//    }
//
//    public List<Constant> getConstants() throws IOException, PluginNotConfiguredException, ApiException {
//        ConstantsResponse resp = get("/api/constant/get-all", ConstantsResponse.class);
//        Map<String, String> labels = get("https://raw.githubusercontent.com/leek-wars/leek-wars/master/src/lang/doc.fr.lang", Map.class);
//
//        List<Constant> constants = ContainerUtil.filter(resp.getConstants(), new Condition<Constant>() {
//            @Override
//            public boolean value(Constant constant) {
//                return !(constant.getName().startsWith("CHIP_") || constant.getName().startsWith("WEAPON_"));
//            }
//        });
//
//        for (Constant constant : constants) {
//            if (labels.containsKey("const_" + constant.getName())) {
//                constant.description = labels.get("const_" + constant.getName());
//            } else {
//                constant.description = constant.getName();
//            }
//        }
//
//        return constants;
//    }

    public AIsResponse listScripts() throws IOException, PluginNotConfiguredException, ApiException {
        return secureGet("/api/ai/get-farmer-ais", AIsResponse.class);
    }

    public AIResponse downloadScript(int id) throws IOException, PluginNotConfiguredException, ApiException {
        System.out.println("Downloading " + id);
        AIResponse resp = secureGet("/api/ai/get/" + id, AIResponse.class);
        return resp;
    }

    public void renameScript(int id, String name) throws IOException, PluginNotConfiguredException, ApiException {
        String params = String.format("ai_id=%d&new_name=%s", id, name);

        securePost("/api/ai/rename", params, Void[].class);
    }

    public void uploadScript(int id, String name, String content) throws CompilationException, IOException, PluginNotConfiguredException, ApiException {
        String params = String.format("ai_id=%d&code=%s", id, URLEncoder.encode(content, StandardCharsets.UTF_8));

        GenericResponse result = securePost("/api/ai/save", params, GenericResponse.class);

        renameScript(id, name);

        Object uploadResult = result.getResult().get("" + id);
        if (uploadResult instanceof List) {
            List<Object> list = (List<Object>) uploadResult;

            if (!list.isEmpty()) {
                throw new CompilationException();
            }
        }
    }

    public int createScript(String name, String content) throws IOException, PluginNotConfiguredException, CompilationException, ApiException {
        AIResponse resp = securePost("/api/ai/new-name", "folder_id=0&name="+name+"&version=2", AIResponse.class);

        int id = resp.getAi().getId();

        uploadScript(id, name, content);

        return id;
    }

    public void deleteScript(int scriptId) throws IOException, PluginNotConfiguredException, ApiException {
        secureDelete("/api/ai/delete", "ai_id=" + scriptId, Void[].class);
    }

    private <T> T securePost(String url, String params, Class<T> responseType) throws IOException, PluginNotConfiguredException, ApiException {
        HttpURLConnection connection = getConnection(url, params, true, true, "POST");

        if (connection.getResponseCode() == 200) {
            String json = CharStreams.toString(new InputStreamReader(connection.getInputStream()));

            System.out.println(json);

            return new GsonBuilder().create().fromJson(
                    json,
                    responseType
            );
        } else {
            System.out.println(CharStreams.toString(new InputStreamReader(connection.getErrorStream())));
            throw new ApiException("securePost", url, params, connection.getResponseMessage());
        }
    }

    private <T> T secureDelete(String url, String params, Class<T> responseType) throws IOException, PluginNotConfiguredException, ApiException {
        HttpURLConnection connection = getConnection(url, params, true, true, "DELETE");

        if (connection.getResponseCode() == 200) {
            return new GsonBuilder().create().fromJson(
                    new InputStreamReader(connection.getInputStream()),
                    responseType
            );
        } else {
            throw new ApiException("secureDelete", url, null, connection.getResponseMessage());
        }
    }

    private <T> T secureGet(String url, Class<T> responseType) throws IOException, PluginNotConfiguredException, ApiException {
        HttpURLConnection connection = getConnection(url, null, true, true, "GET");

        if (connection.getResponseCode() == 200) {
            return new GsonBuilder().create().fromJson(
                    new InputStreamReader(connection.getInputStream()),
                    responseType
            );
        } else {
            throw new ApiException("secureGet", url, null, connection.getResponseMessage());
        }
    }

    private <T> T get(String url, Class<T> responseType) throws IOException, PluginNotConfiguredException, ApiException {
        HttpURLConnection connection = getConnection(url, null, true, false, "GET");

        if (connection.getResponseCode() == 200) {
            return new GsonBuilder().create().fromJson(
                    new InputStreamReader(connection.getInputStream()),
                    responseType
            );
        } else {
            throw new ApiException("get", url, null, connection.getResponseMessage());
        }
    }

    private HttpURLConnection getConnection(String url, String postData, boolean followRedirects, boolean appendToken, String method) throws PluginNotConfiguredException, IOException {
        if (!LSSettings.getInstance().isValid()) {
            throw new PluginNotConfiguredException();
        }

        if (appendToken) {
            if (token == null) {
                connectToLeekWars();
            }
        }

        HttpURLConnection connection = HttpConfigurable.getInstance().openHttpConnection(buildUrl(url));
        connection.setDoInput(true);
        connection.setRequestMethod(method);
        connection.setInstanceFollowRedirects(followRedirects);
        if (appendToken) {
            connection.setRequestProperty("Authorization", "Bearer " + token);
        }
        connection.setRequestProperty("Accept", "application/json");

        if (postData != null) {
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

            try (DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, StandardCharsets.UTF_8))){
                writer.write(postData);
                out.flush();
            }
        }
        try {
            Thread.sleep(100); // Prevent 429 Too many requests
        } catch (InterruptedException ignored) {}
        connection.connect();

        return connection;
    }

    public void connectToLeekWars() throws IOException, PluginNotConfiguredException {
        URL url = new URL(buildUrl(LSSettings.getInstance().getSiteUrl()) + "/api/farmer/login-token");
        HttpURLConnection conn;
        if (LSSettings.getInstance().isProxyEnabled()) {
            Proxy webProxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(LSSettings.getInstance().getProxyDomainName(), Integer.parseInt(LSSettings.getInstance().getProxyPort())));
            conn = (HttpURLConnection) url.openConnection(webProxy);
        } else {
            conn = (HttpURLConnection) url.openConnection();
        }
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
            String data = Map.of("login", LSSettings.getInstance().getSiteLogin(),
                            "password", LSSettings.getInstance().getSitePassword())
                    .entrySet().stream()
                    .map(entry -> entry.getKey() + "=" + URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8))
                    .collect(Collectors.joining("&"));
            wr.write(data.getBytes());
        }
        Map map = new GsonBuilder().create().fromJson(new InputStreamReader(conn.getInputStream()), Map.class);

        if (map.containsKey("token")) {
            token = map.get("token").toString();
            System.out.println("token: " + token);
        } else {
            throw new PluginNotConfiguredException();
        }
    }

    private String buildUrl(String path) {
        if (path.startsWith("http")) {
            return path;
        }

        String host = LSSettings.getInstance().getSiteUrl();

        if (host.endsWith("/")) {
            host = host.substring(0, host.length() - 1);
        }

        return host + path;
    }
}
