package com.udacity.sandwichclub.utils.json.model;

public class JsonString extends JsonObject implements JsonPrimitive {
    private String value;

    public JsonString(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
