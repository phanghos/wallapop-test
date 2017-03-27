package org.taitasciore.android.api;

import org.taitasciore.android.marvelmodel.ApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by roberto on 21/03/17.
 */

/**
 * 
 */
public interface MarvelApi {

    String PUBLIC_KEY = "6a7ed890b4b941a925202a5630d5b162";
    String PRIVATE_KEY = "0f1d0fdf46a0bf32f962b0b9997233c0395cdf8e";

    @GET("characters/{characterId}/comics?apikey=" + PUBLIC_KEY)
    Call<ApiResponse> getComics(@Path("characterId") int characterId,
                                @Query("ts") long ts, @Query("hash") String hash);

    @GET("comics/{comicId}?apikey=" + PUBLIC_KEY)
    Call<ApiResponse> getComicById(@Path("comicId") int comicId,
                                   @Query("ts") long ts, @Query("hash") String hash);
}
