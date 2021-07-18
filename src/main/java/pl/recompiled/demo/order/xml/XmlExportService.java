package pl.recompiled.demo.order.xml;

import pl.recompiled.demo.order.Order;

public class XmlExportService {

    public String exportToExm(Order order) {
        final XmlExportVisitor visitor = new XmlExportVisitor();
        order.accept(visitor);
        return visitor.getResult();
    }
}
