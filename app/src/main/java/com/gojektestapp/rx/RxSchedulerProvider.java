package com.gojektestapp.rx;

import io.reactivex.Scheduler;


public interface RxSchedulerProvider {

    Scheduler computation();

    Scheduler io();

    Scheduler ui();
}
