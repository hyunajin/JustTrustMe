package com.example.asus.justtrustme;

import java.util.Vector;

public class ParentData {

    private String title;
    public Vector<Child> child;

    public ParentData(String title) {

        this.title= title;

        child = new Vector<>();

    }

    String getTitle() {

        return this.title;

    }

}