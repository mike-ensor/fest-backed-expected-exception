package com.clickconcepts.exception;

import org.fest.assertions.api.AbstractAssert;
import org.fest.assertions.core.WritableAssertionInfo;

/**
 * FEST exception matcher for CustomException
 */
public class CustomExceptionAssert extends AbstractAssert<CustomExceptionAssert, Exception> {

    private WritableAssertionInfo info;

    private CustomExceptionAssertContainer exceptionHelper = CustomExceptionAssertContainer.instance();

    protected CustomExceptionAssert(Exception actual) {
        super(actual, CustomExceptionAssert.class);
        info = new WritableAssertionInfo();
    }

    public static CustomExceptionAssert assertThat(Exception actual) {
        return new CustomExceptionAssert(actual);
    }

    public CustomExceptionAssert hasCode(int code) {
        exceptionHelper.hasCode(info, actual, code);
        return this;
    }

    public CustomExceptionAssert hasMessage(String message) {
        exceptionHelper.hasMessage(info, actual, message);
        return this;
    }

}