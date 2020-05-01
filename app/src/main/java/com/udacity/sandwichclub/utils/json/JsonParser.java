package com.udacity.sandwichclub.utils.json;

import android.util.Log;

import com.udacity.sandwichclub.utils.json.model.JsonArray;
import com.udacity.sandwichclub.utils.json.model.JsonBool;
import com.udacity.sandwichclub.utils.json.model.JsonDouble;
import com.udacity.sandwichclub.utils.json.model.JsonInt;
import com.udacity.sandwichclub.utils.json.model.JsonNull;
import com.udacity.sandwichclub.utils.json.model.JsonObject;
import com.udacity.sandwichclub.utils.json.model.JsonString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonParser {

    private static final String TAG = JsonParser.class.getSimpleName();

    private int cursor = 0;
    private List<String> tokens;


    public JsonObject parse(String jsonString) {
        tokens = new JsonTokenizer().getTokens(jsonString);
        cursor = 0;
        JsonObject result = parseJsonObject();
        if (cursor < tokens.size()) {
            throw new RuntimeException("Unexpected end of json at token position " + cursor);
        }
        return result;
    }

    private JsonObject parseJsonObject() {
        String current = tokens.get(cursor);
        ++cursor;
        if (current.equals("{")) {
            Map<String, JsonObject> keyValues = parseKeyValues();
            return new JsonObject(keyValues);
        } else if (current.equals("[")) {
            List<JsonObject> jsonObjects = parseArray();
            return new JsonArray(jsonObjects);
        } else if (current.equals("\"")) {
            String s = parseString();
            return new JsonString(s);
        } else {
            return parseJsonPrimitive();
        }
    }

    private JsonObject parseJsonPrimitive() {
        String current = tokens.get(cursor);
        ++cursor;
        if (current.equals("\"")) {
            String s = parseString();
            return new JsonString(s);
        } else if (current.equals("null")) {
            return new JsonNull();
        } else if (current.equals("true")) {
            return new JsonBool(true);
        } else if (current.equals("false")) {
            return new JsonBool(false);
        } else if (isDouble(current)) {
            double value = Double.parseDouble(current);
            return new JsonDouble(value);
        } else if (isInt(current)) {
            int value = Integer.parseInt(current);
            return new JsonInt(value);
        } else {
            throw new RuntimeException("Unexpected token '" + current + "' at position " +
                    (cursor - 1) + " ");
        }
    }

    private boolean isInt(String current) {
        try {
            Integer.parseInt(current);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isDouble(String current) {
        try {
            Double.parseDouble(current);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private String parseString() {
        String s = tokens.get(cursor);
        ++cursor;
        String close = tokens.get(cursor);
        ++cursor;
        if (!close.equals("\"")) {
            throw new RuntimeException("Expected String termination at cursor position " +
                    (cursor - 1));
        }
        return s;
    }

    private List<JsonObject> parseArray() {
        List<JsonObject> array = new ArrayList<>();
        return parseArray(array);
    }

    private List<JsonObject> parseArray(List<JsonObject> array) {
        String current = tokens.get(cursor);
        if (current.equals("]")) {
            ++cursor;
            return array;
        } else {
            JsonObject obj = parseJsonObject();
            array.add(obj);
            String lookup = tokens.get(cursor);
            ++cursor;
            if (lookup.equals(",")) {
                return parseArray(array);
            } else if (lookup.equals("]")) {
                return array;
            } else {
                throw new RuntimeException("Unknown token " + lookup + " at position " + cursor);
            }
        }
    }

    private Map<String, JsonObject> parseKeyValues() {
        Map<String, JsonObject> keyValues = new HashMap<>();
        return parseKeyValues(keyValues);
    }

    private Map<String, JsonObject> parseKeyValues(Map<String, JsonObject> keyValues) {
        String current = tokens.get(cursor);
        ++cursor;
        if (current.equals("}")) {
            return keyValues;
        } else if (current.equals("\"")) {
            String key = parseString();
            String colon = tokens.get(cursor);
            ++cursor;
            if (colon.equals(":")) {
                JsonObject value = parseJsonObject();
                keyValues.put(key, value);
            }
            String lookup = tokens.get(cursor);
            if (lookup.equals(",")) {
                ++cursor;
                return parseKeyValues(keyValues);
            } else if (lookup.equals("}")) {
                ++cursor;
                return keyValues;
            } else {
                throw new RuntimeException("Unknown token " + lookup + " at position " + cursor);
            }
        } else {
            throw new RuntimeException("Unknown token " + current + " at position " + cursor);
        }
    }
}
