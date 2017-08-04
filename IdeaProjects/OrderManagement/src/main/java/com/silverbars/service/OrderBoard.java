package com.silverbars.service;

import com.silverbars.domain.OrderType;
import com.silverbars.domain.Summary;
import com.silverbars.exception.OrderException;

import java.util.List;

public interface OrderBoard {

    boolean registerOrder(String userId, double quantity, double price, OrderType orderType) throws OrderException;

    boolean cancelOrder(String userId, double quantity, double price, OrderType orderType) throws OrderException;

    List<Summary> orderSummary();

    List<Summary> orderSummaryFor(OrderType orderType);
}
