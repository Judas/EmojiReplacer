package com.judas.android.emojireplacer.parsing;

import com.judas.android.emojireplacer.model.Emoji;

import java.util.List;

/**
 * A simple tree object for rapid emoji sequence lookup.
 */
public class EmojiTree {

    private EmojiNode root;

    /**
     * Creates a tree and stores the given emojis into it.
     *
     * @param emojis A list of emojis to fill in the tree.
     */
    public EmojiTree(final List<Emoji> emojis) {
        root = new EmojiNode();

        for (final Emoji emoji : emojis) {
            EmojiNode tree = root;
            for (final char c : emoji.getString().toCharArray()) {
                if (!tree.hasChild(c)) {
                    tree.addChild(c);
                }
                tree = tree.getChild(c);
            }
            tree.emoji = emoji;
        }
    }

    /**
     * Checks if the given sequence exists in the tree.
     *
     * @param sequence An emoji char array.
     * @return Match.EXACT if there is an emoji matching exactly this sequence,
     * Match.POSSIBLE if this sequence could be an emoji (with some other added chars),
     * Match.IMPOSSIBLE if the sequence could not be found into the tree.
     */
    public EmojiNode.Match isEmoji(final char[] sequence) {
        if (sequence == null) {
            return EmojiNode.Match.POSSIBLE;
        }

        EmojiNode tree = root;
        for (final char c : sequence) {
            if (!tree.hasChild(c)) {
                return EmojiNode.Match.IMPOSSIBLE;
            }
            tree = tree.getChild(c);
        }

        return tree.emoji != null ? EmojiNode.Match.EXACT : EmojiNode.Match.POSSIBLE;
    }

    /**
     * Gets the emoji corresponding to the given sequence of chars.
     *
     * @param chars A char array representing an emoji.
     * @return The corresponding Emoji or {@code null} if sequence could not be found into the tree.
     */
    public Emoji getEmoji(final char[] chars) {
        EmojiNode tree = root;
        for (final char c : chars) {
            if (!tree.hasChild(c)) {
                return null;
            }
            tree = tree.getChild(c);
        }
        return tree.emoji;
    }
}
