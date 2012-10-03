package com.clickconcepts.fest.assertsion;

import com.google.common.base.Throwables;
import org.fest.assertions.internal.Failures;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import static com.google.common.base.Strings.isNullOrEmpty;
import static org.fest.assertions.api.Assertions.assertThat;

public class ExpectedException implements TestRule {

    private Class<? extends Throwable> type;
    private String message;
    private Class<? extends Throwable> isExactlyType;
    private boolean hasNoCauseQuery = false;
    private String hasMessageStartingWith;
    private boolean expectedException = false;

    public static ExpectedException none() {
        return new ExpectedException();
    }

    protected ExpectedException() {
    }

    @Override
    public Statement apply(Statement base, Description description) {
        return new ExpectedExceptionStatement(base);
    }

    public ExpectedException isExactlyInstanceOf(Class<? extends Throwable> type) {
        markExpectedException();
        this.isExactlyType = type;
        return this;
    }

    public ExpectedException instanceOf(Class<? extends Throwable> type) {
        markExpectedException();
        this.type = type;
        return this;
    }

    public ExpectedException hasMessage(String message) {
        markExpectedException();
        this.message = message;
        return this;
    }

    public ExpectedException hasNoCause() {
        markExpectedException();
        this.hasNoCauseQuery = true;
        return this;
    }

    public ExpectedException hasMessageStartingWith(String description) {
        markExpectedException();
        this.hasMessageStartingWith = description;
        return this;
    }

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

    protected void markExpectedException() {
        this.expectedException = true;
    }

    protected Class<? extends Throwable> getType() {
        return this.type;
    }

    protected String getMessage() {
        return message;
    }

    protected boolean checkForHasNoCause() {
        return hasNoCauseQuery;
    }

    protected String getHasMessageStartingWith() {
        return hasMessageStartingWith;
    }

    protected Class<? extends Throwable> getExactlyType() {
        return isExactlyType;
    }

    private class ExpectedExceptionStatement extends Statement {

        private final Statement baseStatement;

        public ExpectedExceptionStatement(Statement base) {
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
