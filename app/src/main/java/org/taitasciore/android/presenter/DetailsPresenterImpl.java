package org.taitasciore.android.presenter;

import android.app.Activity;
import android.support.v4.app.Fragment;

import org.taitasciore.android.marvelmodel.Comic;
import org.taitasciore.android.network.NetworkUtils;
import org.taitasciore.android.view.DetailsView;
import org.taitasciore.android.api.MarvelApiService;

/**
 * Created by roberto on 21/03/17.
 */

/**
 * This class acts as the presenter layer for the details view
 */
public class DetailsPresenterImpl implements DetailsPresenter, MarvelApiService.OnSingleComicFinishListener {

    private DetailsView mView;
    private MarvelApiService mApiService;
    private Comic mComic;
    private boolean mIsRequestInProcess;

    public DetailsPresenterImpl(DetailsView view) {
        mView = view;
        mApiService = new MarvelApiService();
    }

    /**
     * Sends an HTTP request to the API that returns all the information available about
     * a particular Marvel character if there's no request in process
     * and network connection is established
     * @param comicId ID of the comic to fetch from the API
     */
    @Override
    public void getComicById(int comicId) {
        if (!mIsRequestInProcess) {
            /**
             * If there's no network connection, a toast along with a
             * retry button will be shown
             */
            if (!NetworkUtils.isConnected(getContext())) {
                mView.showNetworkError();
                mView.showRetryButton();
                return;
            }
            /**
             * Otherwise, a loader animation will be shown and the HTTP request
             * will be started
             */
            mView.showProgress();
            mApiService.getComicById(comicId, this);
            mIsRequestInProcess = true;
        }
    }

    @Override
    public Comic getSavedComic() {
        return mComic;
    }

    @Override
    public boolean isRequestInProcess() {
        return mIsRequestInProcess;
    }

    @Override
    public void onViewAttached(DetailsView view) {
        mView = view;
    }

    @Override
    public void onViewDetached() {
        mView = null;
    }

    @Override
    public void onDestroyed() {
        onViewDetached();
        mApiService = null;
    }

    /**
     * Method invoked by the {@link MarvelApiService} instance via the
     * {@link org.taitasciore.android.api.MarvelApiService.OnSingleComicFinishListener} interface
     * if the request was successful. It will hide the loader animation and show some information
     * associated with the comic (image, title, price, description)
     * @param comic {@link Comic} instance
     */
    @Override
    public void onSuccess(Comic comic) {
        mComic = comic;
        mIsRequestInProcess = false;
        mView.hideProgress();
        mView.showComicInfo(comic);
    }

    /**
     * Method invoked by the {@link MarvelApiService} instance via the
     * {@link org.taitasciore.android.api.MarvelApiService.OnSingleComicFinishListener} interface
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
