package org.schulcloud.mobile.util;

import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.schulcloud.mobile.data.model.CurrentUser;

import java.io.UnsupportedEncodingException;

/**
 * Created by niklaskiefer on 28.04.17.
 */

public class JWTUtil {
    public static String decodeToCurrentUser(String JWTEncoded) {
        try {
            String[] split = JWTEncoded.split("\\.");

            // get body of jwt
            JsonParser jsonParser = new JsonParser();
            String bodyJson = getJson(split[1]);
            JsonObject jsonObject = jsonParser.parse(bodyJson).getAsJsonObject();

            return jsonObject.get("userId").getAsString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String getJson(String strEncoded) throws UnsupportedEncodingException {
        byte[] decodedBytes = Base64.decode(strEncoded, Base64.URL_SAFE);
        return new String(decodedBytes, "UTF-8");
    }
}
