package com.business.order.item;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonView;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/order")
public class SaleOrderController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SaleOrderController.class);

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    SaleOrderRepository saleRepo;

    @Autowired
    ProductRepository productRepo;

    @Autowired
    ItemRepository itemRepo;

    @GetMapping("/newOrder")
    public SaleOrder createOrder() {

        SaleOrder saleOrder = new SaleOrder(new Date(), "Prepare", null, 0d, 0d, 0d, null);
        saleRepo.save(saleOrder);
        return saleOrder;
    }

    @PostMapping("/addItem/{orderId}")
    public Map<String, Object> addItem(@PathVariable("orderId") long id, @RequestBody Map<String, Object> body) {
        Map<String, Object> res = new HashMap<>();
        Optional<SaleOrder> sale = saleRepo.findById(id);
        if (sale.isPresent()) {
            SaleOrder saleOrder = sale.get();

            List<Product> product = productRepo.findBySku(body.get("sku").toString());

            Double total = product.get(0).getPrice() * (Integer) body.get("qty");
            saleOrder.setNet(total);
            saleOrder.setSubnet(total);

            Item item = new Item(saleOrder, product.get(0), (Integer) body.get("qty"));
            saleOrder.getItems().add(item);

            saleRepo.save(saleOrder);
            res.put("success", true);
            res.put("data", saleOrder.getId());
        } else {
            res.put("success", false);
            res.put("data", "no data found");
        }
        return res;

    }

    @JsonView(View.Summary.class)
    @GetMapping("/orderDetail/{orderId}")
    public SaleOrder orderDetail(@PathVariable("orderId") long id) {
        SaleOrder saleOrder = null;

        Optional<SaleOrder> sale = saleRepo.findById(id);
        if (sale.isPresent()) {
            saleOrder = sale.get();
        }
        return saleOrder;
    }

    @PutMapping("/updateItem/{itemId}")
    public Map<String, Object> updateItem(@PathVariable("itemId") long id, @RequestBody Map<String, Object> body) {
        Map<String, Object> res = new HashMap<>();
        Optional<Item> itemOpt = itemRepo.findById(id);

        if (itemOpt.isPresent()) {
            Item item = itemOpt.get();

            List<Product> product = productRepo.findBySku(body.get("sku").toString());
            Integer qty = (Integer) body.get("qty");

            Double total = product.get(0).getPrice() * qty;

            item.setProduct(product.get(0));
            item.setQty(qty);
            item.getSaleOrder().setNet(total);
            item.getSaleOrder().setSubnet(total);

            itemRepo.save(item);
            res.put("success", true);
            res.put("data", item.getId());
        } else {
            res.put("success", false);
            res.put("data", "no data found");
        }
        return res;
    }

    @DeleteMapping("/removeItem/{itemId}")
    public Map<String, Object> removeItem(@PathVariable("itemId") long id) {
        Map<String, Object> res = new HashMap<>();
        if (itemRepo.existsById(id)) {
            itemRepo.deleteById(id);
            res.put("success", true);
            res.put("data", id);
        } else {
            res.put("success", false);
            res.put("message", "no data found");
        }
        return res;
    }

    @HystrixCommand(fallbackMethod = "getPromotionFallback")
    @GetMapping("/setPromotion/{id}/{promotionCode}")
    public Map<String, Object> findOrderById(@PathVariable Long id, @PathVariable String promotionCode) {
        LOGGER.info("call to promotion service");
        @SuppressWarnings("unchecked")
        Map<String, Object> promotion = restTemplate.getForObject("http://promotion-service/promotion/" + promotionCode,
                Map.class);
        LOGGER.info("response from promotion");

        Map<String, Object> res = new HashMap<>();
        Optional<SaleOrder> saleOrder = saleRepo.findById(id);
        if (saleOrder.isPresent()) {
            SaleOrder sale = saleOrder.get();
            Double net = sale.getNet() - Double.parseDouble(promotion.get("discount").toString());
            res.put("id", id);
            res.put("promotion", promotionCode);
            res.put("subnet", sale.getNet());
            res.put("discount", promotion.get("discount"));
            res.put("net", net);
        } else {
            res.put("success", false);
            res.put("message", "No data found.");
        }

        return res;
    }

    public Map<String, Object> getPromotionFallback(Long id, String promotionCode, Throwable hystrixCommand) {
        Map<String, Object> result = new HashMap<>();
        System.out.println(hystrixCommand.getMessage());
        result.put("success", false);
        result.put("message", hystrixCommand.getMessage());
        return result;
    }

}