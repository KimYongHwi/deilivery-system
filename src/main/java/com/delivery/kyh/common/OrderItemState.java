package com.delivery.kyh.common;

public enum OrderItemState {
    ORDER("ORDER"),

    WAIT_DELIVERY("WAIT_DELIVERY"),

    IN_DELIVERY("IN_DELIVERY"),

    DELIVERY_COMPLETE("DELIVERY_COMPLETE"),

    CANCEL("CANCEL");

    private String state;

    OrderItemState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}
