package org.taitasciore.android.presenter;

import org.taitasciore.android.marvelmodel.Comic;
import org.taitasciore.android.view.DetailsView;

/**
 * Created by roberto on 24/03/17.
 */

public interface DetailsPresenter extends Presenter<DetailsView> {

    void getComicById(int id);
    Comic getSavedComic();
    boolean isRequestInProcess();
}
