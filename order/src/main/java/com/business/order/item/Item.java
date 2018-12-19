package com.business.order.item;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonView;

@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(View.Summary.class)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "saleOrderId")
    private SaleOrder saleOrder;

    @ManyToOne
    @JoinColumn(name = "productId")
    @JsonView(View.Summary.class)
    private Product product;

    @JsonView(View.Summary.class)
    private Integer qty;

    public Item() {

    }

    public Item(SaleOrder saleOrder, Product product, Integer qty) {
        this.saleOrder = saleOrder;
        this.product = product;
        this.qty = qty;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SaleOrder getSaleOrder() {
        return this.saleOrder;
    }

    public void setSaleOrder(SaleOrder saleOrder) {
        this.saleOrder = saleOrder;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQty() {
        return this.qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

}