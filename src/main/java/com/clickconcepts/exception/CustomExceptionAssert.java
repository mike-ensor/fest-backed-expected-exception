package com.clickconcepts.exception;

import org.fest.assertions.api.AbstractAssert;
import org.fest.assertions.core.WritableAssertionInfo;

import static org.fest.assertions.api.Assertions.fail;

/**
 * FEST acception matcher for CustomException
 */
public class CustomExceptionAssert extends AbstractAssert<CustomExceptionAssert, Exception> {

    WritableAssertionInfo info;

    CustomExceptionAssertContainer exceptionHelper = CustomExceptionAssertContainer.instance();

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

    public CustomExceptionAssert isInstanceOf(Class<CustomException> type) {
        exceptionHelper.isInstanceOf(info, actual, type);
        return this;
    }
}