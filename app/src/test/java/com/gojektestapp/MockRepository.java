package com.gojektestapp;

import com.gojektestapp.net.model.Repository;

import java.util.ArrayList;
import java.util.List;

public class MockRepository {


    public static List<Repository> mockDummyData() {
        List<Repository> dummydata = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Repository repository = new Repository();
            repository.setName("User " + i);
            repository.setAuthor("Author " + i);
            repository.setAvatar("www.google.com/sample.png " + i);
            repository.setStars(i);
            repository.setForks(i);
            repository.setLanguage("a " + i);
            dummydata.add(repository);
        }
        return dummydata ;

    }
}
