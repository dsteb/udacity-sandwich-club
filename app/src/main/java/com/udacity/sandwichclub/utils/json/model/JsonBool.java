package com.udacity.sandwichclub.utils.json.model;

public class JsonBool extends JsonObject implements JsonPrimitive {
    private boolean value = false;

    public JsonBool(boolean value) {
        this.value = value;
    }

    public boolean isValue() {
        return value;
    }
}
