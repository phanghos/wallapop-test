package org.taitasciore.android.presenter;

import org.taitasciore.android.marvelmodel.Comic;
import org.taitasciore.android.view.ComicsView;

import java.util.List;

/**
 * Created by roberto on 24/03/17.
 */

public interface ComicsPresenter extends Presenter<ComicsView> {

    void getComics();
    List<Comic> getSavedComics();
    boolean isRequestInProcess();
}
