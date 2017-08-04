package com.silverbars.domain;

public enum OrderType {

    BUY ("HIGH_PRICE"),
    SELL ("LOW_PRICE");

    private String priceSortingOrder;

    OrderType(String priceSortingOrder) {
        this.priceSortingOrder = priceSortingOrder;
    }

    public static String getSortingOrder(OrderType orderType) {
        return orderType.priceSortingOrder;
    }
}
