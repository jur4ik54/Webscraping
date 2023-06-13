package com.keycon.spring.scraping.utils;

public enum Portal{
    //value,link
    CUMPUTERFUTURES("1"),
    FRELANCERMAP("2"),
    ETENGO("3"),
    SOLCOM("4"),
    GULP("5"),
    FRELANCER("6");
    public final String id;

    private Portal(String id) {
        this.id = id;
    }
}