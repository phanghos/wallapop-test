package org.taitasciore.android.presenter;

/**
 * Created by roberto on 21/03/17.
 */

/**
 * Interface that every presenter class must implement
 * @param <V> Type of presenter
 */
public interface Presenter<V> {

    void onViewAttached(V view);
    void onViewDetached();
    void onDestroyed();
}
