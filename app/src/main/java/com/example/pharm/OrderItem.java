package com.example.pharm;

public class OrderItem {
    private String userId;
    private String timestamp;
    private String productName;
    private int productQuantity;
    private double productPrice;
    private double totalAmount;

    public OrderItem(String userId, String timestamp, String productName, int productQuantity, double productPrice, double totalAmount) {
        this.userId = userId;
        this.timestamp = timestamp;
        this.productName = productName;
        this.productQuantity = productQuantity;
        this.productPrice = productPrice;
        this.totalAmount = totalAmount;
    }

    public String getUserId() {
        return userId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getProductName() {
        return productName;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public double getTotalAmount() {
        return totalAmount;
    }
}

