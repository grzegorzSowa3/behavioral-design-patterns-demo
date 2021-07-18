package pl.recompiled.demo.order.discount;

import pl.recompiled.demo.order.OrderPosition;

import java.util.function.Predicate;

public interface Discount {

    Predicate<OrderPosition> applicable();

    int apply(int price);

}
