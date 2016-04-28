package com.judas.android.emojireplacer.parsing;

import android.graphics.drawable.Drawable;
import android.text.style.ImageSpan;

/**
 * Custom ImageSpan to show Emoji. The purpose of this class is just to distinguish emoji image spans
 * from other image spans that may already be added into text.
 */
public class EmojiSpan extends ImageSpan {
    public EmojiSpan(final Drawable d, final int verticalAlignment) {
        super(d, verticalAlignment);
    }
}
