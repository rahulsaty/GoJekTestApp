package com.gojektestapp.ui.viemodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gojektestapp.net.GitHubClient;
import com.gojektestapp.net.model.Repository;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

public class TrendingViewModel extends ViewModel {

    @Inject
    GitHubClient gitHubClient;

    MutableLiveData<List<Repository>> data = new MutableLiveData<>();

    public MutableLiveData<Boolean> isNetworkError = new MutableLiveData<>();
    public MutableLiveData<String> errorMessage = new MutableLiveData<>();

    @Inject
    public TrendingViewModel() {
    }

    public LiveData<List<Repository>> getData() {
        return data;
    }


    public void loadData() {
        Single<List<Repository>> repoRespone = gitHubClient.getRepositoryList();
        repoRespone.subscribe(new SingleObserver<List<Repository>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(List<Repository> repositories) {
                data.setValue(repositories);
            }

            @Override
            public void onError(Throwable e) {
                handleError(e);
            }
        });
    }

    private void handleError(Throwable e) {
        if (e instanceof IOException) {
            isNetworkError.setValue(true);
        } else {
            errorMessage.setValue(e.getMessage());
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();

    }

    public void sortByStar() {
        Collections.sort(getData().getValue(), (o1, o2) -> {
            return (int) (o2.getStars() - o1.getStars());
        });
    }

    public void sortByName() {
        Collections.sort(getData().getValue(), (o1, o2) -> {
            return o1.getName().compareToIgnoreCase(o2.getName());
        });

    }
}
