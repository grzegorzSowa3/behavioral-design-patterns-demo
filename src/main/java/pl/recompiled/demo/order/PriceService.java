package pl.recompiled.demo.order;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.chrono.ChronoLocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;

import static java.time.Month.JANUARY;
import static java.time.Month.NOVEMBER;
import static pl.recompiled.demo.order.Category.*;

public class PriceService {

    Integer calculatePrice(OrderPosition position) {
        int price = position.getQuantity() * position.getProduct().getPrice();
        final LocalDateTime now = LocalDateTime.now();

        // multi item promotion for fashion and health
        if (Arrays.asList(FASHION, HEALTH).contains(position.getProduct().getCategory())
                && position.getQuantity() > 3) {
            price = (int) Math.ceil(price * 0.8);
        }
        // cyber monday electronics promotion
        if (now.toLocalDate().equals(getCyberMondayFor(now.getYear()))
                && position.getProduct().getCategory().equals(ELECTRONICS)) {
            price = (int) Math.ceil(price * 0.65);
        }
        // new year's happy hours
        if (now.toLocalDate().isEqual(LocalDate.of(now.getYear(), JANUARY, 1))
                && now.getHour() > 6 && now.getHour() < 10) {
            price = (int) Math.ceil(price * 0.5);
        }
        // lots of other ifs, changing frequently
        return price;
    }

    private LocalDate getCyberMondayFor(Integer year) {
        return LocalDate.of(year, NOVEMBER, 1)
                .with(TemporalAdjusters.dayOfWeekInMonth(4, DayOfWeek.THURSDAY));
    }

}
