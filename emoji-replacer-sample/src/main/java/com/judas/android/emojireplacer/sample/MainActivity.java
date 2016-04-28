package com.judas.android.emojireplacer.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.judas.android.emojireplacer.EmojiReplacer;

public class MainActivity extends AppCompatActivity {

    private final static String[] EMOJI_TEST = new String[]{
            "\u263A", "\uD83D\uDE07", "\uD83D\uDC22", "\uD83C\uDF85\uD83C\uDFFF",
            "\uD83C\uDDEB\uD83C\uDDF7", "\uDBB9\uDCE7", "\uD83C\uDDFF\uD83C\uDDFC", "\uD83D\uDD95"
    };

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < EMOJI_TEST.length; i++) {
            sb.append(i + 1);
            sb.append(". [");
            sb.append(EMOJI_TEST[i]);
            sb.append("]\n");
        }

        final TextView nowatcher = (TextView) findViewById(R.id.nowatcher);
        nowatcher.setText("NO REPLACER :\n\n" + sb.toString());

        final TextView watcher = (TextView) findViewById(R.id.watcher);
        EmojiReplacer.watch(watcher);
        watcher.setText("REPLACER :\n\n" + sb.toString());
    }
}
