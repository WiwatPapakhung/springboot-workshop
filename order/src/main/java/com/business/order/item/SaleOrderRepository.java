package com.business.order.item;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface SaleOrderRepository extends CrudRepository<SaleOrder, Long> {

    Optional<SaleOrder> findById(Long id);
}
