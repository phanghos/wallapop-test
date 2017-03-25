package org.taitasciore.android.presenter;

/**
 * Created by roberto on 21/03/17.
 */

public interface Presenter<V> {

    void onViewAttached(V view);
    void onViewDetached();
    void onDestroyed();
}
