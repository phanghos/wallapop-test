package org.taitasciore.android.presenter;

import android.app.Activity;
import android.support.v4.app.Fragment;

import org.taitasciore.android.marvelmodel.Comic;
import org.taitasciore.android.network.NetworkUtils;
import org.taitasciore.android.view.ComicsView;
import org.taitasciore.android.api.MarvelApiImpl;

import java.util.List;

/**
 * Created by roberto on 21/03/17.
 */

public class ComicsPresenterImpl implements ComicsPresenter, MarvelApiImpl.OnComicsFinishListener {

    private ComicsView mView;
    private MarvelApiImpl mApi;
    private List<Comic> mComics;
    private boolean mIsRequestInProcess;

    public ComicsPresenterImpl(ComicsView view) {
        mView = view;
        mApi = new MarvelApiImpl();
    }

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

    @Override
    public void onSuccess(List<Comic> comics) {
        mComics = comics;
        mIsRequestInProcess = false;
        mView.hideProgress();
        mView.showComics(comics);
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
