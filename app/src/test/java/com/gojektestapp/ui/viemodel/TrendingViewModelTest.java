package com.gojektestapp.ui.viemodel;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.gojektestapp.TestSchedulerProvider;
import com.gojektestapp.net.GitHubClient;
import com.gojektestapp.net.model.Repository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.schedulers.TestScheduler;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Response;

import static com.gojektestapp.MockRepository.mockDummyData;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TrendingViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    TrendingViewModel trendingViewModel;

    @Mock
    GitHubClient client;
    private TestScheduler mTestScheduler;



    @Before
    public void setUp() {
        trendingViewModel = new TrendingViewModel();
        trendingViewModel.gitHubClient = client;
        mTestScheduler = new TestScheduler();
        TestSchedulerProvider testSchedulerProvider = new TestSchedulerProvider(mTestScheduler);

    }

    @Test
    public void testWith_NetWorkError() {
        when(client.getRepositoryList()).thenReturn(Single.error(new IOException()));
        trendingViewModel.loadData();
        mTestScheduler.triggerActions();
        assertTrue(trendingViewModel.isNetworkError.getValue());
    }

    @Test
    public void testWith_HttpError() {
        when(client.getRepositoryList()).thenReturn(Single.error(new HttpException(Response.error(400, ResponseBody.create(MediaType.parse("text/plain"), "{}"
                )))
        ));
        trendingViewModel.loadData();
        mTestScheduler.triggerActions();
        assertEquals("HTTP 400 Response.error()", trendingViewModel.errorMessage.getValue());
    }

    @Test
    public void getDatatTest_Success() {


        List<Repository> dummydata = mockDummyData();

        when(client.getRepositoryList()).thenReturn(Single.just(dummydata));
        trendingViewModel.loadData();
        assertTrue(trendingViewModel.getData().getValue().size() > 0);
        for (int i = 0; i < trendingViewModel.getData().getValue().size(); i++) {
            Repository repository = trendingViewModel.getData().getValue().get(i);
            assertEquals("User " + i, repository.getName());
            assertEquals("Author " + i, repository.getAuthor());
            assertEquals("www.google.com/sample.png " + i, repository.getAvatar());
            assertEquals(i, repository.getStars());
            assertEquals(i, repository.getForks());
            assertEquals("a " + i, repository.getLanguage());
        }

    }




}