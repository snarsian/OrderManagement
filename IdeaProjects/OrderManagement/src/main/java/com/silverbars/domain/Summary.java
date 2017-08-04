package com.silverbars.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Summary {

    private double price;
    private double quantity;
    private OrderType orderType;

    private static final String NEW_LINE = "\n";
    private static final String DISPLAY_TEXT = " kg for £";

    public Summary (double price, double quantity, OrderType orderType) {
        this.price = price;
        this.quantity = quantity;
        this.orderType = orderType;
    }

    public double getPrice() {
        return price;
    }

    public double getQuantity() {
        return quantity;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Summary summary = (Summary) o;

        if (Double.compare(summary.price, price) != 0) return false;
        if (Double.compare(summary.quantity, quantity) != 0) return false;
        return orderType == summary.orderType;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(price);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(quantity);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (orderType != null ? orderType.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder summary = new StringBuilder();
        summary
                .append(quantity)
                .append(DISPLAY_TEXT)
                .append(price)
                .append(NEW_LINE);

        return summary.toString();
    }

    public static List<Summary> buildSummary(Map<Double, Double> sortedMap, OrderType orderType) {
        List<Summary> summaryList = new ArrayList<>();
        sortedMap.keySet().forEach(key -> {
            summaryList.add(new Summary(key, sortedMap.get(key), orderType));
        });
        return summaryList;
    }
}
