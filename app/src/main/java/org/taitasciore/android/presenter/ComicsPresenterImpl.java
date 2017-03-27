package org.taitasciore.android.presenter;

import android.app.Activity;
import android.support.v4.app.Fragment;

import org.taitasciore.android.marvelmodel.Comic;
import org.taitasciore.android.network.NetworkUtils;
import org.taitasciore.android.view.ComicsView;
import org.taitasciore.android.api.MarvelApiService;

import java.util.List;

/**
 * Created by roberto on 21/03/17.
 */

/**
 * This class acts as the presenter layer for the comics view
 */
public class ComicsPresenterImpl implements ComicsPresenter, MarvelApiService.OnComicsFinishListener {

    private ComicsView mView;
    private MarvelApiService mApi;
    private List<Comic> mComics;
    private boolean mIsRequestInProcess;

    public ComicsPresenterImpl(ComicsView view) {
        mView = view;
        mApi = new MarvelApiService();
    }

    /**
     * Sends an HTTP request to the API that returns a list of comics associated with a
     * Marvel character if there's no request in process and network connection is established.
     */
    @Override
    public void getComics() {
        if (!mIsRequestInProcess) {
            if (!NetworkUtils.isConnected(getContext())) {
                mView.showNetworkError();
                mView.showRetryButton();
                return;
            }
            mView.showProgress();
            mApi.getComics(this);
            mIsRequestInProcess = true;
        }
    }

    @Override
    public List<Comic> getSavedComics() {
        return mComics;
    }

    @Override
    public boolean isRequestInProcess() {
        return mIsRequestInProcess;
    }

    @Override
    public void onViewAttached(ComicsView view) {
        mView = view;
    }

    @Override
    public void onViewDetached() {
        mView = null;
    }

    @Override
    public void onDestroyed() {
        onViewDetached();
        mApi = null;
    }

    /**
     * Method invoked by the {@link MarvelApiService} instance via the
     * {@link org.taitasciore.android.api.MarvelApiService.OnComicsFinishListener} interface
     * if the request was successful. It will hide the loader animation and show some information
     * associated with the comic (image, title, price, description)
     * @param comics List of {@link Comic} instances
     */
    @Override
    public void onSuccess(List<Comic> comics) {
        mComics = comics;
        mIsRequestInProcess = false;
        mView.hideProgress();
        mView.showComics(comics);
    }

    /**
     * Method invoked by the {@link MarvelApiService} instance via the
     * {@link org.taitasciore.android.api.MarvelApiService.OnComicsFinishListener} interface
     * if the request failed. It will hide the loader and show a {@link android.widget.Toast}
     * message along with a retry button
     */
    @Override
    public void onError() {
        mIsRequestInProcess = false;
        mView.hideProgress();
        mView.showError();
        mView.showRetryButton();
    }

    /**
     * Helper method that returns the host activity
     * @return Host activity
     */
    private Activity getContext() {
        return ((Fragment) mView).getActivity();
    }
}
