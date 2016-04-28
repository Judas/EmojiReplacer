package com.judas.android.emojireplacer.parsing;

import com.judas.android.emojireplacer.model.Emoji;

import java.util.HashMap;
import java.util.Map;

/**
 * Tree node for rapid emoji sequence lookup.
 */
public class EmojiNode {

    /**
     * Represents a match result for emoji sequence.
     */
    public enum Match {
        EXACT, POSSIBLE, IMPOSSIBLE
    }

    private Map<Character, EmojiNode> children;
    public Emoji emoji;

    public EmojiNode() {
        children = new HashMap<>();
    }

    public boolean hasChild(final char child) {
        return children.containsKey(child);
    }

    public void addChild(final char child) {
        children.put(child, new EmojiNode());
    }

    public EmojiNode getChild(final char child) {
        return children.get(child);
    }
}
