package com.judas.android.emojireplacer.model;

import android.content.Context;

import com.judas.android.emojireplacer.parsing.EmojiIndexed;
import com.judas.android.emojireplacer.parsing.EmojiNode;
import com.judas.android.emojireplacer.parsing.EmojiTree;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * The full emoji model singleton.
 */
public class EmojiModel {
    private static EmojiModel instance;

    private EmojiTree tree;
    private boolean loaded;

    private EmojiModel(final Context c) {
        load(c).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        t -> tree = t,
                        Throwable::printStackTrace,
                        () -> {
                            loaded = true;
                            EmojiLoadedEvent.send();
                        }
                );
    }

    /**
     * Gets the instance of the emoji model. Note that the model may not be loaded yet.
     *
     * @param c A context.
     * @return The EmojiModel instance.
     */
    public static EmojiModel instance(final Context c) {
        if (instance == null)
            instance = new EmojiModel(c);
        return instance;
    }

    /**
     * Checks if the emoji model is loaded.
     *
     * @return {@code true} if model is ready to use.
     */
    public boolean isLoaded() {
        return loaded;
    }

    private Observable<EmojiTree> load(final Context c) {
        return Observable.create(subscriber -> {
            try {
                subscriber.onNext(new EmojiTree(EmojiReader.read(c)));
                subscriber.onCompleted();
            } catch (final IOException | JSONException e) {
                subscriber.onError(e);
            }
        });
    }

    /**
     * Search for emojis in the given text.
     *
     * @param input A text string.
     * @return A list of found emoji with their indexes.
     */
    public List<EmojiIndexed> findEmojisIn(final String input) {
        final char[] inputCharArray = input.toCharArray();
        final List<EmojiIndexed> candidates = new ArrayList<>();

        for (int i = 0; i < inputCharArray.length; i++) {
            final int emojiEnd = getEmojiEndPos(inputCharArray, i);

            if (emojiEnd != -1) {
                final char[] characters = Arrays.copyOfRange(inputCharArray, i, emojiEnd);
                candidates.add(new EmojiIndexed(tree.getEmoji(characters), i, emojiEnd));
                i = emojiEnd - 1;
            }
        }

        return candidates;
    }

    private int getEmojiEndPos(final char[] text, final int startPos) {
        int best = -1;

        for (int j = startPos + 1; j <= text.length; j++) {
            final EmojiNode.Match status = tree.isEmoji(Arrays.copyOfRange(text, startPos, j));

            if (status == EmojiNode.Match.EXACT) {
                best = j;
            } else if (status == EmojiNode.Match.IMPOSSIBLE) {
                return best;
            }
        }

        return best;
    }
}
