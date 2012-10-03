package com.clickconcepts.fest.assertsion;

import com.clickconcepts.exception.CustomExceptionAssert;

public class ExpectedCustomException extends AbstractExpectedException<ExpectedCustomException> {

    private Integer code;

    public static ExpectedCustomException none() {
        return new ExpectedCustomException();
    }

    /**
     * Checks to see if the CustomException has the specified code
     * @param code int
     * @return AbstractExpectedException
     */
    public AbstractExpectedException hasCode(int code) {
        markExpectedException();
        this.code = code;
        return this;
    }

    private Integer getCode() {
        return code;
    }

    @Override
    protected void checkAssertions(Exception e) {
        // check parent's exceptions
        super.checkAssertions(e);

        if (getCode() != null) {
            CustomExceptionAssert.assertThat(e).hasCode(code);
        }
    }
}
