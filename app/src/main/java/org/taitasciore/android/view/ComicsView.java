package org.taitasciore.android.view;

import org.taitasciore.android.marvelmodel.Comic;

import java.util.List;

/**
 * Created by roberto on 21/03/17.
 */

public interface ComicsView {

    void showProgress();
    void hideProgress();
    void showRetryButton();
    void hideRetryButton();
    void showComics(List<Comic> comics);
    void showError();
    void showNetworkError();
}
