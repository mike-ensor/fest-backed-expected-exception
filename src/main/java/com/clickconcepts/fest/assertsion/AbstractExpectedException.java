package com.clickconcepts.fest.assertsion;

import com.google.common.base.Throwables;
import org.fest.assertions.internal.Failures;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import static com.google.common.base.Strings.isNullOrEmpty;
import static org.fest.assertions.api.Assertions.assertThat;

public abstract class AbstractExpectedException<CHILD extends AbstractExpectedException<CHILD>> implements TestRule {

    private Class<? extends Throwable> expectedExceptionInstanceType;
    private String message;
    private Class<? extends Throwable> isExactlyType;
    private boolean hasNoCauseQuery = false;
    private String hasMessageStartingWith;
    private boolean expectedException = false;

    @Override
    public Statement apply(Statement base, Description description) {
        return new ExpectedExceptionStatement(base);
    }

    /********** FEST Exception API  ***********************************************************/

    @SuppressWarnings("unchecked")
    public CHILD isExactlyInstanceOf(Class<? extends Throwable> type) {
        markExpectedException();
        this.isExactlyType = type;
        return (CHILD) this;
    }

    @SuppressWarnings("unchecked")
    public CHILD instanceOf(Class<? extends Throwable> type) {
        markExpectedException();
        this.expectedExceptionInstanceType = type;
        return (CHILD) this;
    }

    @SuppressWarnings("unchecked")
    public CHILD hasMessage(String message) {
        markExpectedException();
        this.message = message;
        return (CHILD) this;
    }

    @SuppressWarnings("unchecked")
    public CHILD hasNoCause() {
        markExpectedException();
        this.hasNoCauseQuery = true;
        return (CHILD) this;
    }

    @SuppressWarnings("unchecked")
    public CHILD hasMessageStartingWith(String description) {
        markExpectedException();
        this.hasMessageStartingWith = description;
        return (CHILD) this;
    }

    /******************************************************************************************/

    protected void checkAssertions(Exception e) {
        if (getExactlyType() != null) {
            assertThat(e).isExactlyInstanceOf(getExactlyType());
        }
        if (getType() != null) {
            assertThat(e).isInstanceOf(getType());
        }
        // message checking
        if (!isNullOrEmpty(getHasMessageStartingWith())) {
            assertThat(e).hasMessageStartingWith(getHasMessageStartingWith());
        }
        if (!isNullOrEmpty(getMessage())) {
            assertThat(e).hasMessage(getMessage());
        }
        if (checkForHasNoCause()) {
            assertThat(e).hasNoCause();
        }
    }

    /**
     * Used to mark an exception is being queried for since the default is "none"
     */
    protected void markExpectedException() {
        this.expectedException = true;
    }

    private Class<? extends Throwable> getType() {
        return this.expectedExceptionInstanceType;
    }

    private String getMessage() {
        return message;
    }

    private boolean checkForHasNoCause() {
        return hasNoCauseQuery;
    }

    private String getHasMessageStartingWith() {
        return hasMessageStartingWith;
    }

    private Class<? extends Throwable> getExactlyType() {
        return isExactlyType;
    }

    private class ExpectedExceptionStatement extends Statement {

        private final Statement baseStatement;

        private ExpectedExceptionStatement(Statement base) {
            baseStatement = base;
        }

        @Override
        public void evaluate() throws Throwable {
            boolean exceptionThrown = false;
            try {
                // run test case
                baseStatement.evaluate();
            } catch (Exception e) {
                exceptionThrown = true;
                if (!expectedException) {
                    throw Throwables.propagate(e);
                }
                checkAssertions(e);
            }
            if (!exceptionThrown && expectedException) {
                // fail if no exception was thrown
                throw Failures.instance().failure("Should have thrown an exception");
            }
        }

    }
}
