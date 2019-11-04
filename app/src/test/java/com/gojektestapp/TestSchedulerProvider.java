package com.gojektestapp;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.TestScheduler;

public class TestSchedulerProvider {

    private final TestScheduler mTestScheduler;

    public TestSchedulerProvider(TestScheduler testScheduler) {
        this.mTestScheduler = testScheduler;
    }

    public Scheduler computation() {
        return mTestScheduler;
    }

    public Scheduler io() {
        return mTestScheduler;
    }

    public Scheduler ui() {
        return mTestScheduler;
    }
}
