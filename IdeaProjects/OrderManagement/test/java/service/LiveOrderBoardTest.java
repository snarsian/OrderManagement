package service;

import com.silverbars.domain.Order;
import com.silverbars.domain.OrderType;
import com.silverbars.domain.Summary;
import exception.OrderException;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LiveOrderBoardTest {

    private LiveOrderBoard underTest = new LiveOrderBoard();

    @Test(expected = OrderException.class)
    public void orderWillNotBeRegisteredIfUserIdPassedIsNull() throws OrderException {
        underTest.registerOrder(null, 2.0, 200, OrderType.BUY);
    }

    @Test(expected = OrderException.class)
    public void orderWillNotBeRegisteredIfUserIdPassedIsEmpty() throws OrderException {
        underTest.registerOrder("", 2.0, 200, OrderType.BUY);
    }

    @Test(expected = OrderException.class)
    public void orderWillNotBeRegisteredIfQuantityIsZero() throws OrderException {
        underTest.registerOrder(null, 0, 200, OrderType.BUY);
    }

    @Test
    public void registerBuyOrder() throws OrderException {
        boolean orderRegistered = underTest.registerOrder("user1", 2.0, 200, OrderType.BUY);

        List<Order> actualOrders = underTest.retrieveRegisteredOrders(OrderType.BUY);
        assertTrue(orderRegistered);
        assertEquals(1, actualOrders.size());
        assertEquals(new Order("user1", 2.0, 200, OrderType.BUY), actualOrders.get(0));
    }

    @Test
    public void registerSellOrders() throws OrderException {
        underTest.registerOrder("user1", 2.0, 200, OrderType.SELL);

        List<Order> actualOrders = underTest.retrieveRegisteredOrders(OrderType.SELL);
        assertEquals(1, actualOrders.size());
        assertEquals(new Order("user1", 2.0, 200, OrderType.SELL), actualOrders.get(0));
    }

    @Test
    public void cancelOrderShouldRemoveOrderFromRegisteredList() throws OrderException {
        underTest.registerOrder("user1", 2.0, 200, OrderType.BUY);
        underTest.registerOrder("user2", 1.5, 100, OrderType.SELL);
        underTest.registerOrder("user3", 2.5, 200, OrderType.SELL);

        boolean cancelOrder = underTest.cancelOrder("user1", 2.0, 200, OrderType.BUY);
        assertEquals(true, cancelOrder);

        List<Order> actualOrders = underTest.retrieveRegisteredOrders();
        assertEquals(2, actualOrders.size());
        assertEquals(new Order("user2", 1.5, 100, OrderType.SELL), actualOrders.get(0));
        assertEquals(new Order("user3", 2.5, 200, OrderType.SELL), actualOrders.get(1));
    }

    @Test
    public void displayOrderSummaryForBuyOrdersHaving2OrdersOfSamePriceAndDifferentQuantity() throws OrderException {
        registerOrderWith2OrdersOfSamePrice(OrderType.BUY);

        List<Summary> summaryList = underTest.orderSummaryFor(OrderType.BUY);
        assertEquals(3, summaryList.size());
        assertEquals(new Summary(400.0, 3.0, OrderType.BUY), summaryList.get(0));
        assertEquals(new Summary(200.0, 4.5, OrderType.BUY), summaryList.get(1));
        assertEquals(new Summary(100.0, 1.5, OrderType.BUY), summaryList.get(2));
    }

    @Test
    public void displayOrderSummaryForBuyOrdersHavingOrdersOfAllDifferentPrice() throws OrderException {
        underTest.registerOrder("user1", 2.0, 200, OrderType.BUY);
        underTest.registerOrder("user2", 1.5, 100, OrderType.BUY);

        List<Summary> summaryList = underTest.orderSummaryFor(OrderType.BUY);
        assertEquals(2, summaryList.size());
        assertEquals(new Summary(200.0, 2.0, OrderType.BUY), summaryList.get(0));
        assertEquals(new Summary(100.0, 1.5, OrderType.BUY), summaryList.get(1));
    }

    @Test
    public void displayOrderSummaryForSellOrdersWith2OrdersOfSamePriceAndDifferentQuantity() throws OrderException {
        registerOrderWith2OrdersOfSamePrice(OrderType.SELL);

        List<Summary> summaryList = underTest.orderSummaryFor(OrderType.SELL);

        assertEquals(3, summaryList.size());
        assertEquals(new Summary(100.0, 1.5, OrderType.SELL), summaryList.get(0));
        assertEquals(new Summary(200.0, 4.5, OrderType.SELL), summaryList.get(1));
        assertEquals(new Summary(400.0, 3.0, OrderType.SELL), summaryList.get(2));
    }

    @Test
    public void displayLiveOrderSummary() throws OrderException {
        underTest.registerOrder("user1", 2.0, 200, OrderType.BUY);
        underTest.registerOrder("user2", 1.5, 100, OrderType.BUY);
        underTest.registerOrder("user5", 3.5, 100, OrderType.BUY);

        underTest.registerOrder("user3", 2.5, 200, OrderType.SELL);
        underTest.registerOrder("user4", 3.0, 400, OrderType.SELL);

        List<Summary> summaryList = underTest.orderSummary();
        assertEquals(4, summaryList.size());
        assertTrue(summaryList.contains(new Summary(200.0, 2.0, OrderType.BUY)));
        assertTrue(summaryList.contains(new Summary(100.0, 5.0, OrderType.BUY)));
        assertTrue(summaryList.contains(new Summary(200.0, 2.5, OrderType.SELL)));
        assertTrue(summaryList.contains(new Summary(400.0, 3.0, OrderType.SELL)));
    }

    private void registerOrderWith2OrdersOfSamePrice(OrderType orderType) throws OrderException {
        underTest.registerOrder("user1", 2.0, 200, orderType);
        underTest.registerOrder("user2", 1.5, 100, orderType);
        underTest.registerOrder("user3", 2.5, 200, orderType);
        underTest.registerOrder("user4", 3.0, 400, orderType);
    }
}