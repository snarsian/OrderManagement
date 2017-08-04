package com.silverbars.domain;

public class Order {

    private String userId;
    private double quantity;
    private double price;
    private OrderType orderType;

    public Order(String userId, double quantity, double price, OrderType orderType) {
        this.userId = userId;
        this.quantity = quantity;
        this.price = price;
        this.orderType = orderType;
    }

    public String getUserId() {
        return userId;
    }

    public double getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (Double.compare(order.quantity, quantity) != 0) return false;
        if (Double.compare(order.price, price) != 0) return false;
        if (userId != null ? !userId.equals(order.userId) : order.userId != null) return false;
        return orderType == order.orderType;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = userId != null ? userId.hashCode() : 0;
        temp = Double.doubleToLongBits(quantity);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (orderType != null ? orderType.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Order{" +
                "userId='" + userId + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", orderType=" + orderType +
                '}';
    }
}
