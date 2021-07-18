package pl.recompiled.demo.order.discount;

import pl.recompiled.demo.order.OrderPosition;

import java.util.Arrays;
import java.util.function.Predicate;

import static pl.recompiled.demo.order.Category.FASHION;
import static pl.recompiled.demo.order.Category.HEALTH;

public class MultiPackDiscount implements Discount {

    @Override
    public Predicate<OrderPosition> applicable() {
        return position -> Arrays.asList(FASHION, HEALTH).contains(position.getProduct().getCategory())
                && position.getQuantity() > 3;
    }

    @Override
    public int apply(int price) {
        return (int) Math.ceil(price * 0.8);
    }
}
