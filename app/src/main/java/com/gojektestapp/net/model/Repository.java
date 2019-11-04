
package com.gojektestapp.net.model;

import java.util.List;

import lombok.Data;


@Data
@SuppressWarnings("unused")
public class Repository {

    private String author;
    private String avatar;
    private long currentPeriodStars;
    private String description;
    private long forks;
    private String language;
    private String languageColor;
    private String name;
    private long stars;
    private String url;
    private boolean expanded;

}
