package pl.recompiled.demo.order;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderPosition {

    private UUID id;
    private Product product;
    private Integer quantity;

    static OrderPosition of(Product product, Integer quantity) {
        final OrderPosition position = new OrderPosition();
        position.id = UUID.randomUUID();
        position.product = product;
        position.quantity = quantity;
        return position;
    }

    String toXml() {
        return """
                <order-position>
                    <id>%s</id>
                    %s
                    <quantity>%d</quantity>
                </order-position>
                """.formatted(id, product.toXml(), quantity);
    }

}
