package com.jesse.pricingproject.domain;


import java.io.Serializable;

public class Tag implements Comparable<Tag>, Serializable {

    private static final long serialVersionUID = 540567218318333786L;

    long id;
    String title;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    @Override
    public int compareTo(Tag tag) {
        return 0;
    }
}
