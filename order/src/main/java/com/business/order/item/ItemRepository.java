package com.business.order.item;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface ItemRepository extends CrudRepository<Item, Long> {
    Optional<Item> findById(Long id);

    List<Item> findBySaleOrder(SaleOrder saleOrder);
}
