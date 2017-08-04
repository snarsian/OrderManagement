package com.silverbars.domain;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class SummaryTest {

    private Summary underTest;

    @Before
    public void setUp() {
        underTest = new Summary(200, 1.5, OrderType.BUY);
    }

    @Test
    public void testGetPrice() {
        assertEquals(200, underTest.getPrice(), 0);
    }

    @Test
    public void testGetQuantity() {
        assertEquals(1.5, underTest.getQuantity(), 0);
    }

    @Test
    public void testGetOrderType() {
        assertEquals(OrderType.BUY, underTest.getOrderType());
    }

    @Test
    public void testBuildSumary() {
        Map<Double, Double> summaryMap = new HashMap<>();
        summaryMap.put(200.0, 1.5);
        summaryMap.put(400.0, 3.5);
        summaryMap.put(100.0, 1.0);

        List<Summary> summaryList = Summary.buildSummary(summaryMap, OrderType.BUY);
        assertEquals(3, summaryList.size());
        assertEquals(new Summary(200, 1.5, OrderType.BUY), summaryList.get(0));
        assertEquals(new Summary(400, 3.5, OrderType.BUY), summaryList.get(1));
        assertEquals(new Summary(100, 1.0, OrderType.BUY), summaryList.get(2));
    }
}