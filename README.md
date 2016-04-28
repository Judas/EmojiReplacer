# EmojiReplacer

Android text watcher replacing emojis with inline image spans inside a TextView.
This library allows you to have a consistent emoji management on any device and any Android version.
All emojis of [Unicode 8.0][unicode] are supported and will be replaced with images from the latest [Google Noto Font][noto].

![Sample](https://raw.github.com/Judas/EmojiReplacer/master/doc/sample.png)

# Get it

[![Bintray](https://api.bintray.com/packages/judas/maven/emoji-replacer/images/download.svg) ](https://bintray.com/judas/maven/emoji-replacer/_latestVersion)
[![Circle CI](https://circleci.com/gh/Judas/EmojiReplacer.svg?style=shield&circle-token=9878cb7f923d0ccd06dc3ba6bc2738815827dc70)](https://circleci.com/gh/Judas/EmojiReplacer)

```
compile 'com.judas.android:emoji-replacer:1.0'
```

# Use it

Add a TextWatcher to any TextView/EditText in one simple line:

```java
final TextView myTextView = (TextView) findViewById(R.id.mytextview);
EmojiReplacer.watch(myTextView);
```

## Thanks

This library is built upon the great work of the [emoji-java][emojijava] project.
See NOTICE file for an exhaustive list of used libraries.

## License

    Copyright 2016 Jules Tréhorel

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

 [unicode]: http://unicode.org/emoji/charts/full-emoji-list.html
 [noto]: https://www.google.com/get/noto/#emoji-qaae-color
 [emojijava]: https://github.com/vdurmont/emoji-java
