package com.udacity.sandwichclub.utils;

import android.net.ParseException;
import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.json.JsonParser;
import com.udacity.sandwichclub.utils.json.JsonTokenizer;
import com.udacity.sandwichclub.utils.json.model.JsonArray;
import com.udacity.sandwichclub.utils.json.model.JsonObject;
import com.udacity.sandwichclub.utils.json.model.JsonString;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JsonUtils {

    private static final String TAG = JsonUtils.class.toString();

    public static Sandwich parseSandwichJson(String json) {
        Log.d(TAG, "parseSandwichJson: " + json);
        JsonObject jsonObj = new JsonParser().parse(json);

//        public Sandwich(String mainName, List<String> alsoKnownAs, String placeOfOrigin, String description, String image, List<String> ingredients) {
        JsonObject nameObj = jsonObj.getValue("name");
        JsonString mainNameObj = (JsonString) nameObj.getValue("mainName");
        String name = mainNameObj.getValue();

        JsonArray aliasesJsonArr = (JsonArray) nameObj.getValue("alsoKnownAs");
        List<String> aliases = new ArrayList<>();
        for (JsonObject obj: aliasesJsonArr.getValues()) {
            JsonString s = (JsonString) obj;
            aliases.add(s.getValue());
        }

        JsonString placeObj = (JsonString) jsonObj.getValue("placeOfOrigin");
        String place = placeObj.getValue();

        JsonString descriptionObj = (JsonString) jsonObj.getValue("description");
        String description = descriptionObj.getValue();

        JsonString imgObj = (JsonString) jsonObj.getValue("image");
        String img = imgObj.getValue();

        JsonArray ingrsJsonArr = (JsonArray) jsonObj.getValue("ingredients");
        List<String> ingredients = new ArrayList<>();
        for (JsonObject obj: ingrsJsonArr.getValues()) {
            JsonString s = (JsonString) obj;
            ingredients.add(s.getValue());
        }

        return new Sandwich(name, aliases, place, description, img, ingredients);
    }
}
