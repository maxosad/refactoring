package ru.akirakozov.sd.refactoring.entity;

public class Product {
    String name;
    Integer price;

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public Product(String name, Integer price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return name + " " + price;
    }
}
