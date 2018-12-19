package com.business.promotion.promotion;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Promotion {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String code;
    private Double discount;

    public Promotion() {
    }

    public Promotion(String code, Double discount) {
        this.code = code;
        this.discount = discount;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getDiscount() {
        return this.discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

}