package org.taitasciore.android.wallapoptest;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.labo.kaji.fragmentanimations.SidesAnimation;
import com.wang.avi.AVLoadingIndicatorView;

import org.taitasciore.android.marvelmodel.Comic;
import org.taitasciore.android.marvelmodel.Image;
import org.taitasciore.android.presenter.DetailsPresenter;
import org.taitasciore.android.presenter.DetailsPresenterImpl;
import org.taitasciore.android.view.DetailsView;

import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by roberto on 21/03/17.
 */

public class DetailsFragment extends Fragment implements DetailsView {

    int mComicId;

    @BindView(R.id.img) SimpleDraweeView mImg;
    @BindView(R.id.tvTitle) TextView mTvTitle;
    @BindView(R.id.tvDescription) TextView mTvDescription;

    @BindView(R.id.loader) AVLoadingIndicatorView mLoader;
    @BindView(R.id.btnRetry) Button mBtnRetry;
    @OnClick(R.id.btnRetry) void onRetry() {
        hideRetryButton();
        mPresenter.getComicById(mComicId);
    }

    DetailsPresenter mPresenter;

    public static DetailsFragment newInstance(int comicId) {
        DetailsFragment f = new DetailsFragment();
        Bundle args = new Bundle();
        args.putInt("id", comicId);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        if (enter) return null;
        else return SidesAnimation.create(SidesAnimation.LEFT, enter, 500);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_details, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((AppBarLayout) getActivity().findViewById(R.id.appbar)).setExpanded(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Comic details");

        mComicId = getArguments().getInt("id");

        if (savedInstanceState == null && mPresenter.getSavedComic() == null) {
            mPresenter.getComicById(mComicId);
        } else if (mPresenter.getSavedComic() != null) {
            showComicInfo(mPresenter.getSavedComic());
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
        else mPresenter = new DetailsPresenterImpl(this);
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
    public void showRetryButton() {
        mBtnRetry.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRetryButton() {
        mBtnRetry.setVisibility(View.GONE);
    }

    @Override
    public void showComicInfo(Comic comic) {
        mTvTitle.setText(comic.getTitle());
        mTvDescription.setText(comic.getDescription());
        Image randomImg = getRandomImage(comic.getImages());
        String imgPath = randomImg.getPath() + "." + randomImg.getExtension();
        mImg.setImageURI(Uri.parse(imgPath));
    }

    @Override
    public void showError() {
        Toast.makeText(getActivity(), getString(R.string.error), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showNetworkError() {
        Toast.makeText(getActivity(), getString(R.string.network_error), Toast.LENGTH_SHORT).show();
    }

    private Image getRandomImage(List<Image> list) {
        Random r = new Random();
        return list.get(r.nextInt(list.size()));
    }
}
