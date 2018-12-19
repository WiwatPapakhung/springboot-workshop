package com.business.promotion.promotion;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface PromotionRepository extends CrudRepository<Promotion, Long> {
    Optional<Promotion> findByCode(String code);
}
