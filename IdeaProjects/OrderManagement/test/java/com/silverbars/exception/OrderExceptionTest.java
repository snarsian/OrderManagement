package com.silverbars.exception;

import org.junit.Test;

import static org.junit.Assert.*;

public class OrderExceptionTest {

    private OrderException underTest;

    @Test
    public void testCreationOfOrderExceptionWithMessageAsString() {
        underTest = new OrderException("test exception message");
        assertEquals("test exception message", underTest.getMessage());
    }

    @Test
    public void testCreationOfOrderExceptionWithMessageAsException() {
        underTest = new OrderException(new IllegalArgumentException("illegal argument exception"));
        assertEquals("java.lang.IllegalArgumentException: illegal argument exception", underTest.getMessage());
    }
}