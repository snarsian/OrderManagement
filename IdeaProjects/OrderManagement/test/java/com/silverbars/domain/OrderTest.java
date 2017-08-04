package com.silverbars.domain;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class OrderTest {

    private Order underTest;

    @Before
    public void setUp() {
        underTest = new Order("user1", 2.5, 350, OrderType.BUY);
    }

    @Test
    public void testGetUserId() {
        assertEquals("user1", underTest.getUserId());
    }

    @Test
    public void testGetPrice() {
        assertEquals(350, underTest.getPrice(), 0);
    }

    @Test
    public void testGetQuantity() {
        assertEquals(2.5, underTest.getQuantity(), 0);
    }

    @Test
    public void testGetOrderType() {
        assertEquals(OrderType.BUY, underTest.getOrderType());
    }
}