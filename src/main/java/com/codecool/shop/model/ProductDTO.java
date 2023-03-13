package com.codecool.shop.model;

public class ProductDTO {
    private final String id;
    private String name;
    private final String defaultPrice;
    private String description;
    private final String productCategory;
    private String supplier;
    private String userId;

    public ProductDTO(String id, String name, String defaultPrice, String description, String productCategory, String supplier) {
        this.id = id;
        this.name = name;
        this.defaultPrice = defaultPrice;
        this.description = description;
        this.productCategory = productCategory;
        this.supplier = supplier;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDefaultPrice() {
        return defaultPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProductCategory() {
        return productCategory;
    }
}

