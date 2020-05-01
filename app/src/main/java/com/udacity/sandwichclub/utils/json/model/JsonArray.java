package com.udacity.sandwichclub.utils.json.model;

import java.util.List;

public class JsonArray extends JsonObject {
    private List<JsonObject> values;

    public JsonArray(List<JsonObject> values) {
        this.values = values;
    }

    public List<JsonObject> getValues() {
        return values;
    }
}
