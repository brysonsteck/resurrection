package net.brysonsteck.Resurrection.startup;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


public class CheckForUpdate {
    String version;
    String versionURL;

    public CheckForUpdate() {
        try {
            String json = urlReader();
            JsonElement root = new JsonParser().parse(json);
            JsonObject rootobj = root.getAsJsonObject();
            JsonElement softwareElement = rootobj.get("resurrection");
            JsonObject softwareObj = softwareElement.getAsJsonObject();
            version = softwareObj.get("current-release").toString();
//            version = rootobj.get("current-version").getAsString();
//            versionURL = rootobj.get("release-url").getAsString();
        } catch (IOException e) {
            System.out.println("[Resurrection] An error has occurred while attempting to check for updates.");
            e.printStackTrace();
        }
    }

    public String urlReader() throws IOException {
        URL website = new URL("https://brysonsteck.net/updates.json");
        URLConnection connection = website.openConnection();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        connection.getInputStream()));

        StringBuilder response = new StringBuilder();
        String inputLine;

        while ((inputLine = in.readLine()) != null)
            response.append(inputLine);

        in.close();

        return response.toString();
    }

    public String getVersionURL() {
        return versionURL;
    }

    public String getVersion() {
        return version;
    }
}
