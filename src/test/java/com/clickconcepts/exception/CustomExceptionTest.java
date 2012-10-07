package com.clickconcepts.exception;

import com.clickconcepts.fest.assertsion.ExpectedCustomException;
import com.clickconcepts.junit.annotation.ExpectedFailure;
import com.clickconcepts.junit.annotation.ExpectedTestFailureWatcher;
import org.junit.Rule;
import org.junit.Test;

public class CustomExceptionTest {

    @Rule
    public ExpectedCustomException exception = ExpectedCustomException.none();

    @Rule
    /**
     * Used for expected failures
     */
    public ExpectedTestFailureWatcher expectedTestFailureWatcher = ExpectedTestFailureWatcher.instance();

    @Test
    public void hasCode_worksAsExpected() {
        exception.hasCode(123);

        throw new CustomException("Message", 123);
    }

    @Test
    public void hasMessage_worksAsExpected() {
        exception.hasMessage("Message");
        throw new CustomException("Message", 123);
    }

    @Test
    @ExpectedFailure
    public void getCode_fails() {
        exception.hasCode(456);
        throw new CustomException("Message", 123);
    }

    @Test
    @ExpectedFailure
    public void getMessage_fails() {
        exception.hasMessage("This is not the exception you are looking for");
        throw new CustomException("Message", 123);
    }

    @Test
    public void getMessageAndCode_worksAsExpected() {
        exception.hasCode(123).hasMessage("Message");
        throw new CustomException("Message", 123);
    }

    @Test
    @ExpectedFailure
    public void getMessageAndCode_codeFailsFirst() {
        exception.hasCode(1).hasMessage("Message");
        throw new CustomException("Message", 123);
    }

    @Test
    @ExpectedFailure
    public void getMessageAndCode_messageFailsFirst() {
        exception.hasMessage("This is not the exception you are looking for").hasCode(123);
        throw new CustomException("Message", 123);
    }

    @Test
    @ExpectedFailure
    public void getMessageAndCode_messageSucceedsCodeDoesNot() {
        exception.hasMessage("Message").hasCode(1234);
        throw new CustomException("Message", 123);
    }

    @Test
    @ExpectedFailure
    public void getMessageAndCode_bothFail() {
        exception.hasMessage("Message2").hasCode(1234);
        throw new CustomException("Message", 123);
    }

    @Test
    public void isInstanceOf() {
        exception.isInstanceOf(CustomException.class).hasMessage("Message").hasCode(123);
        throw new CustomException("Message", 123);
    }

    @Test
    @ExpectedFailure
    public void isInstanceOf_notRightType() {
        exception.isInstanceOf(IllegalAccessError.class).hasMessage("Message").hasCode(123);
        throw new CustomException("Message", 123);
    }

}
