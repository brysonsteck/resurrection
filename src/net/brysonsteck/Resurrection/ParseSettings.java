package net.brysonsteck.Resurrection;

import java.util.Hashtable;

public class ParseSettings {
    Hashtable<String, String> settings = new Hashtable<>();

    public ParseSettings() {

    }

    public String getSetting(String setting) {
        return settings.get(setting);
    }
}
