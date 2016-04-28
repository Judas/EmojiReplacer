package com.judas.android.emojireplacer;

import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.judas.android.emojireplacer.model.EmojiLoadedEvent;
import com.judas.android.emojireplacer.model.EmojiModel;
import com.judas.android.emojireplacer.parsing.EmojiIndexed;
import com.judas.android.emojireplacer.parsing.EmojiSpan;

import java.util.List;

import rx.Subscription;

/**
 * Text Watcher replacing any found emoji with inline image spans.
 *
 * @see TextWatcher
 */
public class EmojiReplacer implements TextWatcher {

    private TextView textView;
    private Subscription subscription;

    private EmojiReplacer(final TextView tv) {
        textView = tv;
        textView.addTextChangedListener(this);

        if (!EmojiModel.instance(tv.getContext()).isLoaded()) {
            subscription = EmojiLoadedEvent.observe(this::onEmojiLoaded);
        }
    }

    /**
     * Start watching for emojis in this textview. Note that the emoji won't be reaplced until the emoji model is loaded (it may takes some time).
     *
     * @param tv The text view to watch.
     */
    public static void watch(final TextView tv) {
        new EmojiReplacer(tv);
    }

    @Override
    public void beforeTextChanged(final CharSequence s, final int start, final int count, final int after) {
    }

    @Override
    public void onTextChanged(final CharSequence s, final int start, final int before, final int count) {
    }

    @Override
    public void afterTextChanged(final Editable s) {
        replaceEmojis();
    }

    private void replaceEmojis() {
        // Record current cursor position
        final int start = Math.max(textView.getSelectionStart(), 0);
        final int end = Math.max(textView.getSelectionEnd(), 0);

        // Emoji not loaded yet
        if (!EmojiModel.instance(textView.getContext()).isLoaded()) {
            return;
        }

        // Text is empty
        final CharSequence text = textView.getText();
        if (text == null || text.toString().isEmpty() || text.toString().trim().isEmpty()) {
            return;
        }

        // No emojis found
        final List<EmojiIndexed> emojis = EmojiModel.instance(textView.getContext()).findEmojisIn(textView.getText().toString());
        if (emojis.isEmpty()) {
            return;
        }

        // Remove previous emoji spans
        final Editable editable = textView.getEditableText();
        final EmojiSpan[] spans = editable.getSpans(0, textView.getText().length(), EmojiSpan.class);
        for (final EmojiSpan span : spans) {
            editable.removeSpan(span);
        }
        final SpannableStringBuilder sb = new SpannableStringBuilder(editable);

        // Add new emoji spans
        for (final EmojiIndexed emojiIndexed : emojis) {
            final EmojiSpan span = emojiIndexed.emoji.getSpan(textView.getContext(), textView.getLineHeight());
            sb.setSpan(span, emojiIndexed.startIndex, emojiIndexed.endIndex, 0);
        }

        // Update text and reset cursor
        textView.removeTextChangedListener(this);
        textView.beginBatchEdit();
        textView.setText(sb);
        if (textView instanceof EditText) {
            ((EditText) textView).setSelection(start, end);
        }
        textView.endBatchEdit();
        textView.addTextChangedListener(this);

        textView.invalidate();
    }

    private void onEmojiLoaded(final EmojiLoadedEvent event) {
        subscription.unsubscribe();
        replaceEmojis();
    }
}
