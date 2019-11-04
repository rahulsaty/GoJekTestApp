package com.gojektestapp.net;

import com.gojektestapp.net.model.Repository;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface RestApi {

    @GET("repositories")
    public Single<List<Repository>> getRepositoryList();
}
