package com.business.order.item;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonView;

@Entity
public class SaleOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(View.Summary.class)
    private Long id;

    @JsonView(View.Summary.class)
    private Date date;

    @JsonView(View.Summary.class)
    private String status;

    @JsonView(View.Summary.class)
    private String promotion;

    @JsonView(View.Summary.class)
    private Double subnet;

    @JsonView(View.Summary.class)
    private Double discount;

    @JsonView(View.Summary.class)
    private Double net;

    @OneToMany(mappedBy = "saleOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonView(View.Summary.class)
    private Set<Item> items;

    public SaleOrder() {

    }

    public SaleOrder(Date date, String status, String promotion, Double subnet, Double discount, Double net,
            Set<Item> items) {
        this.date = date;
        this.status = status;
        this.promotion = promotion;
        this.subnet = subnet;
        this.discount = discount;
        this.net = net;
        this.items = items;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPromotion() {
        return this.promotion;
    }

    public void setPromotion(String promotion) {
        this.promotion = promotion;
    }

    public Double getSubnet() {
        return this.subnet;
    }

    public void setSubnet(Double subnet) {
        this.subnet = subnet;
    }

    public Double getDiscount() {
        return this.discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getNet() {
        return this.net;
    }

    public void setNet(Double net) {
        this.net = net;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Item> getItems() {
        return this.items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }
}