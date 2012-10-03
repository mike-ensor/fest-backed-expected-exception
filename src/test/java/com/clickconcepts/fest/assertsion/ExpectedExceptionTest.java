package com.clickconcepts.fest.assertsion;

import com.clickconcepts.junit.annotation.ExpectedFailure;
import com.clickconcepts.junit.annotation.ExpectedTestFailureWatcher;
import org.junit.Rule;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class ExpectedExceptionTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    /**
     * test watcher from the ExpectedFailure package provided by com.clickconcepts.junit package
     */
    @Rule
    public ExpectedTestFailureWatcher expectedTestFailureWatcher = ExpectedTestFailureWatcher.instance();

    @Test
    public void assertSimpleExceptionAssert_exceptionIsOfType() {
        exception.instanceOf(SimpleException.class);
        throw new SimpleException("this is an exception");
    }

    @Test
    public void noExceptionsAreThrown() {
        assertThat("abc").isNotEmpty();
    }

    @Test
    @ExpectedFailure
    public void exceptionThrownNotUsingExpectedException() {
        throw new IllegalArgumentException("This should throw");
    }

    @Test
    public void assertSimpleExceptionAssert_exceptionIsOfType_subClass() {
        exception.instanceOf(SimpleException.class);
        throw new SubClassSimpleException("Subclass exception");
    }

    @Test
    public void assertSimpleExceptionAssert_exactInstanceOf() {
        exception.isExactlyInstanceOf(SimpleException.class);
        throw new SimpleException("this is an exception");
    }

    @Test
    @ExpectedFailure
    public void assertSimpleExceptionAssert_isExactlyInstance_butSubClass() {
        exception.isExactlyInstanceOf(SimpleException.class);
        throw new SubClassSimpleException("Subclass exception");
    }

    @Test
    @ExpectedFailure
    public void assertSimpleExceptionAssert_Cause() {
        exception.instanceOf(SimpleException.class).hasNoCause();
        throw new SimpleException("this is an exception", new Throwable("Some deep down cause"));
    }

    @Test
    public void assertSimpleExceptionAssert_onlyInstanceType_onlyMessage() {
        exception.hasMessageStartingWith("this is");
        throw new SimpleException("this is an exception");
    }

    @Test
    @ExpectedFailure
    public void assertSimpleExceptionAssert_messageDoesNotStartWith() {
        exception.hasMessageStartingWith("does not start with this is an exception");
        throw new SimpleException("this is an exception");
    }

    @Test
    public void assertSimpleExceptionAssert_messagesMatch() {
        exception.hasMessage("this is an exception");
        throw new SimpleException("this is an exception");
    }

    @Test
    @ExpectedFailure(reason = "Assertion should fail on messages not matching")
    public void assertSimpleExceptionAssert_messagesDoNotMatch() {

        exception.hasMessage("this is an exception but looking for more in the message");

        throw new SimpleException("this is an exception");
    }

    private class SimpleException extends RuntimeException {

        public SimpleException(String message) {
            super(message);
        }

        public SimpleException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    private class SubClassSimpleException extends SimpleException {

        public SubClassSimpleException(String message) {
            super(message);
        }

        public SubClassSimpleException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
