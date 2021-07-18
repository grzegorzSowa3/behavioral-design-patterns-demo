package pl.recompiled.demo.order.xml;

import lombok.Getter;
import pl.recompiled.demo.order.Order;
import pl.recompiled.demo.order.OrderPosition;
import pl.recompiled.demo.order.Product;
import pl.recompiled.demo.order.Visitor;

class XmlExportVisitor implements Visitor {

    private final StringBuilder result;

    public XmlExportVisitor() {
        result = new StringBuilder();
    }

    public String getResult() {
        return result.toString();
    }

    @Override
    public void doForOrder(Order order) {
        result.append(
                """
                        <order>
                            <id>%s</id>
                            <clientId>%s</clientId>
                            <positions>
                        """.formatted(order.getId(), order.getClientId())
        );
        order.getPositions()
                .forEach(this::doForOrderPosition);
        result.append(
                """
                            </positions>
                        </order>
                        """
        );
    }

    @Override
    public void doForOrderPosition(OrderPosition position) {
        result.append(
                """
                        <order-position>
                            <id>%s</id>
                            """.formatted(position.getId())
        );
        doForProduct(position.getProduct());
        result.append(
                """
                        <quantity>%d</quantity>
                        </order-position>
                        """.formatted(position.getQuantity())
        );
    }

    @Override
    public void doForProduct(Product product) {
        result.append(
                """
                        <product>
                           <id>%s</id>
                           <category>%s</category>
                           <name>%s</name>
                           <price>%d</price>
                        </product>
                        """.formatted(product.getId(), product.getCategory(), product.getName(), product.getPrice())
        );
    }

}
