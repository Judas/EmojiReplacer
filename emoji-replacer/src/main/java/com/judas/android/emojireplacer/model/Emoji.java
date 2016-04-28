package com.judas.android.emojireplacer.model;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.style.DynamicDrawableSpan;

import com.judas.android.emojireplacer.parsing.EmojiSpan;

/**
 * The emoji base object.
 */
public class Emoji {

    /**
     * The emoji unicode character integers as a "_" separated string.
     */
    public String unicode;

    /**
     * Creates an emoji object.
     *
     * @param codepoint The emoji unicode character integers as a "_" separated string.
     */
    public Emoji(final String codepoint) {
        unicode = codepoint;
    }

    /**
     * Creates an {@link EmojiSpan} for this emoji.
     *
     * @param c          A context to retrieve the image resource.
     * @param lineHeight The line height of the text view displaying the emoji for resizing.
     * @return An {@link EmojiSpan} corresponding to this emoji.
     * @see EmojiSpan
     */
    public EmojiSpan getSpan(final Context c, final int lineHeight) {
        final int resId = getDrawableResId(c);
        final Drawable emojiIcon = c.getResources().getDrawable(resId);
        emojiIcon.setBounds(0, 0, lineHeight, lineHeight);
        return new EmojiSpan(emojiIcon, DynamicDrawableSpan.ALIGN_BASELINE);
    }

    /**
     * Returns the unicode string for this emoji.
     *
     * @return An unicode string.
     */
    public String getString() {
        final StringBuilder sb = new StringBuilder();
        for (final String s : unicode.split("_")) {
            sb.append(new String(Character.toChars(Integer.parseInt(s, 16))));
        }
        return sb.toString();
    }

    private int getDrawableResId(final Context c) {
        return c.getResources().getIdentifier("emoji_" + unicode, "drawable", c.getPackageName());
    }
}
