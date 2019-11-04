package com.gojektestapp.net;

import com.gojektestapp.net.model.Repository;
import com.gojektestapp.rx.RxSchedulerProvider;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class GitHubClient {

    @Inject
    RestApi api;
    @Inject
    RxSchedulerProvider appSchedulerProvider;

    @Inject
    public GitHubClient() {
    }

    public Single<List<Repository>> getRepositoryList() {
        return api.getRepositoryList().subscribeOn(appSchedulerProvider.io())
                .observeOn(appSchedulerProvider.ui());
    }

}
