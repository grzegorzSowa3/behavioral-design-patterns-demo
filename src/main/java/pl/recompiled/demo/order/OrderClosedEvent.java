package pl.recompiled.demo.order;

import java.util.UUID;

public record OrderClosedEvent(
        UUID orderId,
        Long timestamp
) {
}
