package com.judas.android.emojireplacer.model;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Event triggered when emoji model has finished loading.
 */
public class EmojiLoadedEvent {
    private static Subject<Object, Object> bus;

    /**
     * Sends an event to all observers notifying the end of the emoji model load.
     */
    public static void send() {
        if (bus != null) {
            bus.onNext(new EmojiLoadedEvent());
        }
    }

    /**
     * Observe emoji model load event.
     *
     * @param onNext The action to trigger when emoji model is loaded.
     * @return A subscription to the event (for later unsubscribing).
     */
    public static Subscription observe(final Action1<EmojiLoadedEvent> onNext) {
        bus = new SerializedSubject<>(PublishSubject.create());
        return bus.filter(event -> event.getClass().equals(EmojiLoadedEvent.class))
                .map(obj -> (EmojiLoadedEvent) obj)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, Throwable::printStackTrace);
    }
}
