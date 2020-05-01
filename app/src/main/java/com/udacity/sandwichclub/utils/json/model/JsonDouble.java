package com.udacity.sandwichclub.utils.json.model;

public class JsonDouble extends JsonObject implements JsonPrimitive {
    private double value;

    public JsonDouble(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }
}
