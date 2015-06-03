package com.agh.studio_projektowe.model;



public class Relation {

    private String from;
    private String to;

    public Relation(String from, String to) {
        this.from = from;
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }
}
