package pl.recompiled.demo.order;

import lombok.RequiredArgsConstructor;
import pl.recompiled.demo.order.discount.Discount;

import java.util.List;

@RequiredArgsConstructor
public class PriceService {

    private final List<Discount> discounts;

    Integer calculatePrice(OrderPosition position) {
        int price = position.getQuantity() * position.getProduct().getPrice();

        for (Discount discount : discounts) {
            if (discount.applicable().test(position)) {
                price = discount.apply(price);
            }
        }

        return price;
    }

}
