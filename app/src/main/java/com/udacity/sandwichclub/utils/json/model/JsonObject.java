package com.udacity.sandwichclub.utils.json.model;

import java.util.Map;

public class JsonObject {

    private Map<String, JsonObject> keyValues;

    public JsonObject() {}

    public JsonObject(Map<String, JsonObject> keyValues) {
        this.keyValues = keyValues;
    }

    public JsonObject getValue(String key) {
        return keyValues.get(key);
    }

}
