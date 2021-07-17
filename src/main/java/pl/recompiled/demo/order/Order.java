package pl.recompiled.demo.order;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
class Order {

    private UUID id;
    private UUID clientId;
    private Set<OrderPosition> positions;
    private Boolean closed;

    static Order newInstance() {
        final Order order = new Order();
        order.id = UUID.randomUUID();
        order.positions = new HashSet<>();
        order.closed = false;
        return order;
    }

    static Order of(UUID clientId, OrderPosition... positions) {
        final Order order = newInstance();
        order.clientId = clientId;
        order.closed = false;
        for (OrderPosition position : positions) {
            order.addPosition(position);
        }
        return order;
    }

    void close() {
        closed = true;
    }

    void addPosition(OrderPosition orderPosition) {
        if (closed) {
            throw new OrderClosedException();
        }
        this.positions.add(orderPosition);
    }

    Set<OrderPosition> getPositions() {
        return Collections.unmodifiableSet(positions);
    }

    String toXml() {
        return """
                <order>
                    <id>%s</id>
                    <clientId>%s</clientId>
                    <positions>
                    %s
                    </positions>
                </order>
                """.formatted(id, clientId,
                positions.stream()
                .map(OrderPosition::toXml)
                .collect(Collectors.joining())
                );
    }

}
