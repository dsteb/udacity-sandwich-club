package com.udacity.sandwichclub.utils.json;


import java.util.ArrayList;
import java.util.List;

/**
 * Given JSON string returns List<String> of tokens.
 * Example tokens:
 * {, }, ", :, abc, 123, 123.12, [, ], null, true, false
 */
public class JsonTokenizer {

    public List<String> getTokens(String jsonString) {
        List<String> tokens = new ArrayList<>();
        boolean parsingPrimitive = false;
        boolean parsingString = false;
        // string is always surrounded by quotes that we keep as separate tokens
        String string = "";
        // primitive is true,false,null,123,456.67
        String primitive = "";
        for (int i = 0; i < jsonString.length(); ++i) {
            char c = jsonString.charAt(i);
            String s = String.valueOf(c);
            if (!parsingString && (c == '{' || c == '[')) {
                tokens.add(s);
            } else if (!parsingString && c == '"') {
                parsingString = true;
                string = "";
                tokens.add(s);
            } else if (parsingString && c == '"') {
                char prev = jsonString.charAt(i - 1);
                if (prev == '\\') {
                    // replace backslash with quotes
                    string = string.substring(0, string.length() - 1) + '"';
                } else {
                    parsingString = false;
                    tokens.add(string);
                    tokens.add(s);
                }
            } else if (parsingString) {
                string += c;
            } else if (!parsingPrimitive && (c == 'n' || c == 't' || c == 'f' ||
                    Character.isDigit(c)) ) {
                parsingPrimitive = true;
                primitive = s;
            } else if (parsingPrimitive && (c == '}' || c == ']' || c == ',')) {
                tokens.add(primitive.trim());
                tokens.add(s);
                parsingPrimitive = false;
            } else if (c == '}' || c == ']' || c == ',' || c == ':') {
                tokens.add(s);
            } else if (c == ' ') {
                continue;
            } else {
                String msg = String.format("Unexpected char '%s' on position %d: %s", c, i,
                        jsonString);
                throw new RuntimeException(msg);
            }
        }
        if (parsingString) {
            throw new RuntimeException("Unterminated string: " + jsonString);
        }
        if (parsingPrimitive) {
            tokens.add(primitive);
        }
        return tokens;
    }

}
