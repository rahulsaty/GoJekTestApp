package com.gojektestapp.ui;

import android.content.Context;
import android.os.Build;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gojektestapp.MockRepository;
import com.gojektestapp.R;
import com.gojektestapp.ui.adapter.BaseViewHolder;
import com.gojektestapp.ui.adapter.TrendingListAdapter;
import com.gojektestapp.ui.viemodel.TrendingViewModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.N)
public class TrendingActivityTest {


    Context context;
    @Mock
    TrendingViewModel model;

    NoNetworkFragment noNetworkFragment;
    TrendingListFragment trendingListFragment;
    ActivityController<TrendingActivity> controller;


    @Before
    public void setup() {
        context = RuntimeEnvironment.systemContext;
        noNetworkFragment = new NoNetworkFragment();
        controller = Robolectric.buildActivity(TrendingActivity.class).create().start().resume();
        controller.get().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, noNetworkFragment)
                .commit();
    }

    @Test
    public void shouldNotNull() {
        assertNotNull(controller.get());
        assertTrue(controller.get() instanceof TrendingActivity);
    }

    @Test
    public void validateTitleContent() {
        TextView appNameTextView = controller.get().findViewById(R.id.toolbar_title);
        assertEquals("Trending", appNameTextView.getText().toString());
    }


    @Test
    public void validateText() {
        TextView title = noNetworkFragment.getView().findViewById(R.id.errorTitle);
        assertEquals(controller.get().getResources().getString(R.string.something_went_wrong), title.getText());

        TextView description = noNetworkFragment.getView().findViewById(R.id.errorDescription);
        assertEquals(controller.get().getResources().getString(R.string.an_alien_is_probably_blocking_your_signal), description.getText().toString());

        ImageView errorImage = noNetworkFragment.getView().findViewById(R.id.errorImage);
        int drawableResId = Shadows.shadowOf(errorImage.getDrawable()).getCreatedFromResId();

        assertEquals(R.drawable.nointernet_connection, drawableResId);

        TextView retryBtn = noNetworkFragment.getView().findViewById(R.id.retry);
        assertEquals(controller.get().getResources().getString(R.string.retry), retryBtn.getText().toString());
        retryBtn.performLongClick();

    }


    @Test
    public void validateNoInternet_Fragment() {
        TextView title = noNetworkFragment.getView().findViewById(R.id.errorTitle);
        assertEquals(controller.get().getResources().getString(R.string.something_went_wrong), title.getText().toString());

        TextView description = noNetworkFragment.getView().findViewById(R.id.errorDescription);
        assertEquals(controller.get().getResources().getString(R.string.an_alien_is_probably_blocking_your_signal), description.getText().toString());

        ImageView errorImage = noNetworkFragment.getView().findViewById(R.id.errorImage);
        int drawableResId = Shadows.shadowOf(errorImage.getDrawable()).getCreatedFromResId();

        assertEquals(R.drawable.nointernet_connection, drawableResId);

        TextView retryBtn = noNetworkFragment.getView().findViewById(R.id.retry);
        assertEquals(controller.get().getResources().getString(R.string.retry), retryBtn.getText().toString());
        retryBtn.performLongClick();

    }

    @Test
    public void validateList_Fragment() {
        trendingListFragment = new TrendingListFragment();
        controller.get().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, trendingListFragment)
                .commit();

        testAdapter();
    }


    private void testAdapter() {

        TrendingListAdapter trendingListAdapter = new TrendingListAdapter();
        trendingListAdapter.setData(MockRepository.mockDummyData());
        RecyclerView rvParent = trendingListFragment.getView().findViewById(R.id.recyclerView);
        rvParent.setLayoutManager(new LinearLayoutManager(context));

        // Run test
        BaseViewHolder viewHolder =
                trendingListAdapter.onCreateViewHolder(rvParent, 0);

        trendingListAdapter.onBindViewHolder(viewHolder, 0);


        // JUnit Assertion
        assertEquals("User " + 0, ((TextView) viewHolder.getView(R.id.name)).getText());
        assertEquals("Author " + 0, ((TextView) viewHolder.getView(R.id.title)).getText());

        trendingListAdapter.onBindViewHolder(viewHolder, 1);


        // JUnit Assertion
        assertEquals("User " + 1, ((TextView) viewHolder.getView(R.id.name)).getText());
        assertEquals("Author " + 1, ((TextView) viewHolder.getView(R.id.title)).getText());

    }
}