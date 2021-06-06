package net.brysonsteck.Resurrection.startup;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


public class CheckForUpdate {
    String version;
    String versionURL;

    public CheckForUpdate() {
        try {
            URL url = new URL("http://resurrect.brysonsteck.net");
            URLConnection request = url.openConnection();
            request.connect();
            JsonParser json = new JsonParser();
            JsonElement root = json.parse(new InputStreamReader((InputStream) request.getContent()));
            JsonObject rootobj = root.getAsJsonObject();
            version = rootobj.get("current-version").getAsString();
            versionURL = rootobj.get("release-url").getAsString();
        } catch (IOException e) {
            System.out.println("[Resurrection] An error has occurred while attempting to check for updates.");
            e.printStackTrace();
        }
    }

    public String getVersionURL() {
        return versionURL;
    }

    public String getVersion() {
        return version;
    }
}
