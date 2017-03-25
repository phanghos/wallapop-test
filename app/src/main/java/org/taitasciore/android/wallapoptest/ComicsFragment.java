package org.taitasciore.android.wallapoptest;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.wang.avi.AVLoadingIndicatorView;

import org.taitasciore.android.marvelmodel.Comic;
import org.taitasciore.android.presenter.ComicsPresenter;
import org.taitasciore.android.presenter.ComicsPresenterImpl;
import org.taitasciore.android.view.ComicsView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by roberto on 21/03/17.
 */

public class ComicsFragment extends Fragment implements ComicsView, ComicAdapter.OnItemClickListener {

    private static final String DETAILS_FRAGMENT = "details_fragment";

    @BindView(R.id.list) RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutMngr;
    ComicAdapter mAdapter;

    @BindView(R.id.loader) AVLoadingIndicatorView mLoader;
    @BindView(R.id.btnRetry) Button mBtnRetry;
    @OnClick(R.id.btnRetry) void onRetry() {
        hideRetryButton();
        mPresenter.getComics();
    }

    ComicsPresenter mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_comics, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Comics");
        setupRecyclerView();

        if (savedInstanceState == null && mPresenter.getSavedComics() == null) {
            mPresenter.getComics();
        } else if (mPresenter.getSavedComics() != null) {
            showComics(mPresenter.getSavedComics());
        } else if (mPresenter.isRequestInProcess()) {
            showProgress();
        } else {
            showRetryButton();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (mPresenter != null) mPresenter.onViewAttached(this);
        else mPresenter = new ComicsPresenterImpl(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroyed();
        mPresenter = null;
    }

    @Override
    public void showProgress() {
        mLoader.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mLoader.setVisibility(View.GONE);
    }

    @Override
    public void showError() {
        Toast.makeText(getActivity(), getString(R.string.error), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showNetworkError() {
        Toast.makeText(getActivity(), getString(R.string.network_error), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showRetryButton() {
        mBtnRetry.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRetryButton() {
        mBtnRetry.setVisibility(View.GONE);
    }

    @Override
    public void showComics(List<Comic> comics) {
        for (Comic comic : comics) mAdapter.add(comic);
    }

    @Override
    public void onItemClicked(int position) {
        Comic comic = mAdapter.get(position);
        DetailsFragment f = DetailsFragment.newInstance(comic.getId());
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, f, DETAILS_FRAGMENT).addToBackStack(null).commit();
    }

    private void setupRecyclerView() {
        mLayoutMngr = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(mLayoutMngr);
        mAdapter = new ComicAdapter();
        mAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
    }
}
