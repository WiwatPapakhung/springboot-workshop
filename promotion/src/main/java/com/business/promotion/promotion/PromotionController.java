package com.business.promotion.promotion;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * PromotionController
 */
@RestController
@RequestMapping("/promotion")
public class PromotionController {

    @Autowired
    PromotionRepository promotionRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(PromotionController.class);

    @GetMapping("/{promotionCode}")
    public Map<String, Object> findByPromotionCode(@PathVariable String promotionCode) {
        Optional<Promotion> promotion = promotionRepository.findByCode(promotionCode);
        LOGGER.info("------ promotion service -------");
        Map<String, Object> res = new HashMap<>();
        if (promotion.isPresent()) {
            Promotion promo = promotion.get();
            res.put("valid", true);
            res.put("discount", promo.getDiscount());
        } else {
            res.put("valid", false);
        }
        return res;
    }
}