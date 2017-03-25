package org.taitasciore.android.view;

import org.taitasciore.android.marvelmodel.Comic;

/**
 * Created by roberto on 21/03/17.
 */

public interface DetailsView {

    void showProgress();
    void hideProgress();
    void showRetryButton();
    void hideRetryButton();
    void showComicInfo(Comic comic);
    void showError();
    void showNetworkError();
}
