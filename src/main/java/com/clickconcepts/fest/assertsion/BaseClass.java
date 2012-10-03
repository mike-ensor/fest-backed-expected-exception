package com.clickconcepts.fest.assertsion;

public class BaseClass<T> {
    private final Class<T> aClass;

    protected BaseClass(Class<T> aClass) {

        this.aClass = aClass;
    }
}
