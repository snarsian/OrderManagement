package com.silverbars.domain;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class OrderTypeTest {

    @Test
    public void checkValidOrderTypes() {
        List<OrderType> orderTypes = Arrays.asList(OrderType.values());
        assertEquals(2, orderTypes.size());
        assertTrue(orderTypes.contains(OrderType.BUY));
        assertTrue(orderTypes.contains(OrderType.SELL));
    }

    @Test
    public void getSortingOrderForBuyOrderType() {
        assertEquals("HIGH_PRICE", OrderType.getSortingOrder(OrderType.BUY));
    }

    @Test
    public void getSortingOrderForSellOrderType() {
        assertEquals("LOW_PRICE", OrderType.getSortingOrder(OrderType.SELL));
    }
}