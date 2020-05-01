package com.udacity.sandwichclub.utils.json.model;

public class JsonInt extends JsonObject implements JsonPrimitive {
    private int value;

    public JsonInt(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
