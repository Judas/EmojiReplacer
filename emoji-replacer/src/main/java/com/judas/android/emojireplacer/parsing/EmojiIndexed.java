package com.judas.android.emojireplacer.parsing;

import com.judas.android.emojireplacer.model.Emoji;

/**
 * Simple data object to store an emoji parsed into a text.
 */
public class EmojiIndexed {
    /**
     * The corresponding emoji.
     */
    public final Emoji emoji;

    /**
     * The emoji start index.
     */
    public final int startIndex;

    /**
     * The emoji end index.
     */
    public final int endIndex;

    /**
     * Creates an index to store a found emoji.
     *
     * @param e     The emoji.
     * @param start The emoji start index.
     * @param end   The emoji end index.
     */
    public EmojiIndexed(final Emoji e, final int start, final int end) {
        emoji = e;
        startIndex = start;
        endIndex = end;
    }
}
