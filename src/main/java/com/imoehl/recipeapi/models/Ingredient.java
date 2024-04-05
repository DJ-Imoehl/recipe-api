package com.imoehl.recipeapi.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class Ingredient {

    private @Id @GeneratedValue Long id;
    private String name;
    private String unit;
    private Double amount;
    private boolean optional;

    public Ingredient(){}

    public Ingredient(String name, String unit, double amount, boolean optional){
        this.name = name;
        this.unit = unit;
        this.amount = amount;
        this.optional = optional;
    }

    public Ingredient(String name, boolean optional) {
        this.name = name;
        this.optional = optional;
        this.unit = null;
        this.amount = null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public boolean isOptional() {
        return optional;
    }

    public void setOptional(boolean optional) {
        this.optional = optional;
    }

    public String toString() {
        return this.name + " " + this.amount + this.unit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingredient that = (Ingredient) o;
        return Objects.equals(amount, that.amount) && Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(unit, that.unit) && optional == that.optional;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, unit, amount, optional);
    }
}
