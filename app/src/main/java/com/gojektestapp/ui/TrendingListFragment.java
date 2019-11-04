package com.gojektestapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;
import com.gojektestapp.R;
import com.gojektestapp.di.ViewModelFactory;
import com.gojektestapp.ui.adapter.TrendingListAdapter;
import com.gojektestapp.ui.viemodel.TrendingViewModel;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class TrendingListFragment extends DaggerFragment {
    @Inject
    ViewModelFactory factory;
    @Inject
    TrendingListAdapter adapter;
    TrendingViewModel repoViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Do something that differs the Activity's menu here
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_star:

                if (repoViewModel != null) {
                    repoViewModel.sortByStar();
                    adapter.setData(repoViewModel.getData().getValue());
                }
                return true;
            case R.id.action_name:
                if (repoViewModel != null) {
                    repoViewModel.sortByName();
                    adapter.setData(repoViewModel.getData().getValue());
                }
                return true;

            default:
                break;
        }

        return false;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragmet_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SwipeRefreshLayout swipeRefreshLayout = getView().findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorFontGreyDark);

        repoViewModel = ViewModelProviders.of(this, factory).get(TrendingViewModel.class);
        repoViewModel.loadData();

        RecyclerView recyclerView = getView().findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

        final SkeletonScreen skeletonScreen = Skeleton.bind(recyclerView)
                .adapter(adapter)
                .shimmer(true)
                .angle(20)
                .frozen(false)
                .duration(1200)
                .count(10)
                .load(R.layout.trending_skelton_item)
                .show();


        repoViewModel.getData().observe(this, repositories -> {
            skeletonScreen.hide();
            adapter.setData(repositories);
            swipeRefreshLayout.setRefreshing(false);
        });

        repoViewModel.isNetworkError.observe(this, aBoolean -> {
            if (aBoolean) {
                skeletonScreen.hide();
                getFragmentManager().beginTransaction().replace(R.id.container, new NoNetworkFragment()).commit();

            }
        });

        repoViewModel.errorMessage.observe(this, message -> {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        });

        swipeRefreshLayout.setOnRefreshListener(() -> {
            repoViewModel.loadData();
        });

    }


}
