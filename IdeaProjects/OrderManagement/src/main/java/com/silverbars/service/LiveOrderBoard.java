package com.silverbars.service;

import com.silverbars.domain.Order;
import com.silverbars.domain.OrderType;
import com.silverbars.exception.OrderException;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.LoggerFactory;
import com.silverbars.domain.Summary;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LiveOrderBoard implements OrderBoard {

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());
    private static final String HIGH_PRICE = "HIGH_PRICE";
    private static final String LOW_PRICE = "LOW_PRICE";

    private List<Order> orders = new ArrayList<>();

    public LiveOrderBoard() {
        orders = new ArrayList<>();
    }

    /**
     * Register Order
     * @param userId
     * @param quantity
     * @param price
     * @param orderType
     * @return
     */
    @Override
    public boolean registerOrder(String userId, double quantity, double price, OrderType orderType) throws OrderException {
        logger.info("Start registerOrder with userId {}, Quantity {}, Price {}, OrderNewType {} ",
                userId, quantity, price, orderType);
        boolean isOrderNewRegistered = false;
        if (validateIfInputParametersAreValid(userId, quantity, price, orderType)) {
            orders.add(createOrderObject(userId, quantity, price, orderType));
            isOrderNewRegistered = true;
        } else {
            logger.error("Order cannot be registered due to invalid input params");
            throw new OrderException("Order cannot be registered due to invalid input params");
        }
        return isOrderNewRegistered;
    }

    /**
     * Cancels Order
     * @param userId
     * @param quantity
     * @param price
     * @param orderType
     * @return
     */
    @Override
    public boolean cancelOrder(String userId, double quantity, double price, OrderType orderType) throws OrderException {
        logger.info("Start cancelOrder with userId {}, Quantity {}, Price {}, OrderNewType {} ",
                userId, quantity, price, orderType);
        boolean isOrderCancelled = false;
        if (validateIfInputParametersAreValid(userId, quantity, price, orderType)) {
            orders.remove(createOrderObject(userId, quantity, price, orderType));
            isOrderCancelled = true;
        } else {
            logger.error("Order does not exists");
            throw new OrderException("Order does not exists");
        }
        return isOrderCancelled;
    }

    //method added just for test assertion

    /**
     * This method returns list of registered orders, used only for test assertion
     * @return List of all orders added
     */
    List<Order> retrieveRegisteredOrders() {
        return orders;
    }

    /**
     * This method returns list of registered orders for OrderType, used only for test assertion
     * @param orderType
     * @return List of orders added for an OrderType
     */
    public List<Order> retrieveRegisteredOrders(OrderType orderType) {
        List<Order> ordersForSpecifiedType = new ArrayList<>();
        orders.forEach(order -> {
            if (order.getOrderType() == orderType) {
                ordersForSpecifiedType.add(order);
            }
        });
        return ordersForSpecifiedType;
    }

    /**
     * Retrieve Summary For Both Order Types
     * @return List of Summary
     */
    @Override
    public List<Summary> orderSummary() {
        logger.info("Order Summary For Both BUY and SELL orders");
        List<Summary> summaryList = new ArrayList<>();
        Arrays.asList(OrderType.values()).forEach(orderType -> {
            summaryList.addAll(orderSummaryFor(orderType));
        });
        return summaryList;
    }

    /**
     * Retrieve Summary Based On Order Type
     * @param orderType
     * @return
     */
    @Override
    public List<Summary> orderSummaryFor(OrderType orderType) {
        logger.info("Order Summary For Order Type {}", orderType);
        Map<Double, Double> groupByPrice = groupOrdersByPriceAndSumQuantity(orderType);
        Map<Double, Double> sortedMap = sortTheGroupedOrder(groupByPrice, OrderType.getSortingOrder(orderType));
        return Summary.buildSummary(sortedMap, orderType);

    }

    private LinkedHashMap<Double, Double> sortTheGroupedOrder(Map<Double, Double> groupByPrice, String sortingOrder) {
        logger.debug("Sort the Map {} based on sorting order {}", groupByPrice, sortingOrder);
        Stream<Map.Entry<Double, Double>> stream = groupByPrice.entrySet().stream();
        Stream<Map.Entry<Double, Double>> sortedStream = null;
        if (HIGH_PRICE.equals(sortingOrder)) {
            sortedStream = stream.sorted(Map.Entry.comparingByKey(Collections.reverseOrder()));
        } else {
            sortedStream = stream.sorted(Map.Entry.comparingByKey());
        }
        return sortedStream
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }

    /**
     * Group the order with the same price and sum the quantity
     * @param orderType
     * @return
     */
    private Map<Double, Double> groupOrdersByPriceAndSumQuantity(OrderType orderType) {
        logger.debug("Group the order based on orderType {}, Same Price and Sum the Quantity", orderType);
        return orders.stream()
                .filter(order -> {
                    return order.getOrderType() == orderType;
                })
                .collect(Collectors.groupingBy(Order::getPrice,
                        Collectors.summingDouble(Order::getQuantity)));
    }

    boolean validateIfInputParametersAreValid(String userId, double quantity, double price, OrderType orderType) {
        return ObjectUtils.allNotNull(userId, orderType)
                && userId.length() > 0
                && quantity > 0;
    }

    private Order createOrderObject(String userId, double quantity, double price, OrderType orderType) {
        return new Order(userId, quantity, price, orderType);
    }
}
