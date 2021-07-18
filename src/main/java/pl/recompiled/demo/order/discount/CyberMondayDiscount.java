package pl.recompiled.demo.order.discount;

import pl.recompiled.demo.order.OrderPosition;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;
import java.util.function.Predicate;

import static java.time.Month.NOVEMBER;
import static pl.recompiled.demo.order.Category.*;

public class CyberMondayDiscount implements Discount {

    @Override
    public Predicate<OrderPosition> applicable() {
        return position -> {
            final LocalDateTime now = LocalDateTime.now();
            return now.toLocalDate().equals(getCyberMondayFor(now.getYear()))
                    && position.getProduct().getCategory().equals(ELECTRONICS);
        };
    }

    @Override
    public int apply(int price) {
        return (int) Math.ceil(price * 0.65);
    }

    private LocalDate getCyberMondayFor(Integer year) {
        return LocalDate.of(year, NOVEMBER, 1)
                .with(TemporalAdjusters.dayOfWeekInMonth(4, DayOfWeek.THURSDAY));
    }
}
