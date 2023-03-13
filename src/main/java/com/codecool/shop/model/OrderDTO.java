package com.codecool.shop.model;

import java.math.BigDecimal;

public class OrderDTO {
    private int id;
    private int userId;
    private int quantity;
    private BigDecimal total;
    private Status status;

    public OrderDTO(int userId, int quantity, BigDecimal total, String stringStatus) {
        this.userId = userId;
        this.quantity = quantity;
        this.total = total;
        this.status = Status.valueOf(stringStatus);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public Status getStatus() {
        return status;
    }
}
