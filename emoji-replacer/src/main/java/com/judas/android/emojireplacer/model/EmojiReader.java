package com.judas.android.emojireplacer.model;

import android.content.Context;

import com.judas.android.emojireplacer.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Parser that read emojis from the json file.
 */
public class EmojiReader {

    /**
     * Parses and load emojis form the json file.
     *
     * @param c A context.
     * @return A list of emoji objects.
     * @throws IOException
     * @throws JSONException
     */
    public static List<Emoji> read(final Context c) throws IOException, JSONException {
        final JSONObject root = new JSONObject(readEmojiFile(c));
        final JSONArray emojiArray = root.getJSONArray("emojis");

        final List<Emoji> emojis = new ArrayList<>();
        for (int i = 0; i < emojiArray.length(); i++) {
            emojis.add(new Emoji(emojiArray.getJSONObject(i).getString("unicode")));
        }
        return emojis;
    }

    private static String readEmojiFile(final Context c) throws IOException {
        final InputStream is = c.getResources().openRawResource(R.raw.emojis);
        final BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF8"));

        final StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }
}
