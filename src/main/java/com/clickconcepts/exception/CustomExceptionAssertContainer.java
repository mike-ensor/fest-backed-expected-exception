package com.clickconcepts.exception;

import org.fest.assertions.core.AssertionInfo;
import org.fest.assertions.core.WritableAssertionInfo;
import org.fest.assertions.error.ShouldContainString;
import org.fest.assertions.internal.Failures;
import org.fest.util.VisibleForTesting;

import static org.fest.assertions.error.ShouldBeEqual.shouldBeEqual;

/**
 * Class provides a container for the CustomExceptionAssert class and is used in the Asserts.assertThat() class for exceptions
 */
public class CustomExceptionAssertContainer {

    private static final CustomExceptionAssertContainer INSTANCE = new CustomExceptionAssertContainer();

    public static CustomExceptionAssertContainer instance() {
        return INSTANCE;
    }

    private Failures failures = Failures.instance();

    @VisibleForTesting
    CustomExceptionAssertContainer() {
    }

    public void hasCode(AssertionInfo info, Exception actual, int expectedCode) {
        CustomException exception = (CustomException) actual;

        if (exception.getCode() != expectedCode) {
            throw failures.failure(info, shouldBeEqual(exception.getCode(), expectedCode));
        }
    }

    public void hasMessage(WritableAssertionInfo info, Exception actual, String message) {
        if (!actual.getMessage().contains(message)) {
            throw failures.failure(info, ShouldContainString.shouldContain(actual.getMessage(), message));
        }
    }

    public void isInstanceOf(WritableAssertionInfo info, Exception actual, Class<? extends RuntimeException> type) {
        if (!type.isInstance(actual)) {
            throw failures.failure(info, shouldBeEqual(actual.getClass().toString(), type.toString()));
        }
    }
}
