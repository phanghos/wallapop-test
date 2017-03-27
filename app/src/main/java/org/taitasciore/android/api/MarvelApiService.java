package org.taitasciore.android.api;

import android.util.Log;

import org.taitasciore.android.marvelmodel.ApiResponse;
import org.taitasciore.android.marvelmodel.Comic;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by roberto on 21/03/17.
 */

public class MarvelApiService {

    public static String API_URL = "http://gateway.marvel.com/v1/public/";

    private MarvelApi mApi;

    public interface OnComicsFinishListener {

        void onSuccess(List<Comic> comics);
        void onError();
    }

    public interface OnSingleComicFinishListener {

        void onSuccess(Comic comic);
        void onError();
    }

    public MarvelApiService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mApi = retrofit.create(MarvelApi.class);
    }

    public void getComics(final OnComicsFinishListener listener) {
        long ts = getTimestamp();
        Call<ApiResponse> call = mApi.getComics(1009220, ts, ApiUtils.generateHash(ts));
        Log.i("Request URL", call.request().url().encodedPath());
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    Log.i("debug", "successful");
                    ApiResponse apiResponse = response.body();
                    listener.onSuccess(apiResponse.getData().getResults());
                } else {
                    Log.i("not successful", response.message() + " (" + response.code() + ")");
                    listener.onError();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.i("debug", "failure");
                listener.onError();
            }
        });
    }

    public void getComicById(int comicId, final OnSingleComicFinishListener listener) {
        long ts = getTimestamp();
        Call<ApiResponse> call = mApi.getComicById(comicId, ts, ApiUtils.generateHash(ts));
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    Log.i("debug", "successful");
                    ApiResponse apiResponse = response.body();
                    listener.onSuccess(apiResponse.getData().getResults().get(0));
                } else {
                    Log.i("not successful", response.message() + " (" + response.code() + ")");
                    listener.onError();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.i("debug", "failure");
                listener.onError();
            }
        });
    }

    private long getTimestamp() {
        return new Date().getTime();
    }
}
