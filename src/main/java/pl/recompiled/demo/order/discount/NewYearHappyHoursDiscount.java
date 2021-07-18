package pl.recompiled.demo.order.discount;

import pl.recompiled.demo.order.OrderPosition;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.function.Predicate;

import static java.time.Month.JANUARY;

public class NewYearHappyHoursDiscount implements Discount {

    @Override
    public Predicate<OrderPosition> applicable() {
        return position -> {
            final LocalDateTime now = LocalDateTime.now();
            return now.toLocalDate().isEqual(LocalDate.of(now.getYear(), JANUARY, 1))
                    && now.getHour() > 6 && now.getHour() < 10;
        };
    }

    @Override
    public int apply(int price) {
        return (int) Math.ceil(price * 0.5);
    }
}
