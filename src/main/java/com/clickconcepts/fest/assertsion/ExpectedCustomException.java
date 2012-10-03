package com.clickconcepts.fest.assertsion;

import com.clickconcepts.exception.CustomExceptionAssert;

public class ExpectedCustomException extends ExpectedException {

    private Integer code;

    public static ExpectedCustomException none() {
        return new ExpectedCustomException();
    }

    private ExpectedCustomException() {
        super();
    }

    public ExpectedException hasCode(int code) {
        markExpectedException();
        this.code = code;
        return this;
    }

    @Override
    public ExpectedCustomException hasMessage(String message) {
        super.hasMessage(message);
        return this;
    }

    private Integer getCode() {
        return code;
    }

    @Override
    protected void checkAssertions(Exception e) {
        // check existing exceptions
        super.checkAssertions(e);

        if (getCode() != null) {
            CustomExceptionAssert.assertThat(e).hasCode(code);
        }

    }

}
