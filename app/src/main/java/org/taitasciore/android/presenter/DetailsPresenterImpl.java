package org.taitasciore.android.presenter;

import android.app.Activity;
import android.support.v4.app.Fragment;

import org.taitasciore.android.marvelmodel.Comic;
import org.taitasciore.android.network.NetworkUtils;
import org.taitasciore.android.view.DetailsView;
import org.taitasciore.android.api.MarvelApiImpl;

/**
 * Created by roberto on 21/03/17.
 */

public class DetailsPresenterImpl implements DetailsPresenter, MarvelApiImpl.OnSingleComicFinishListener {

    private DetailsView mView;
    private MarvelApiImpl mApiService;
    private Comic mComic;
    private boolean mIsRequestInProcess;

    public DetailsPresenterImpl(DetailsView view) {
        mView = view;
        mApiService = new MarvelApiImpl();
    }

    @Override
    public void getComicById(int comicId) {
        if (!mIsRequestInProcess) {
            if (!NetworkUtils.isConnected(getContext())) {
                mView.showNetworkError();
                mView.showRetryButton();
                return;
            }
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

    @Override
    public void onSuccess(Comic comic) {
        mComic = comic;
        mIsRequestInProcess = false;
        mView.hideProgress();
        mView.showComicInfo(comic);
    }

    @Override
    public void onError() {
        mIsRequestInProcess = false;
        mView.hideProgress();
        mView.showError();
        mView.showRetryButton();
    }

    private Activity getContext() {
        return ((Fragment) mView).getActivity();
    }
}
