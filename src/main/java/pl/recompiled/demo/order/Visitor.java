package pl.recompiled.demo.order;

public interface Visitor {

    void doForOrder(Order order);

    void doForOrderPosition(OrderPosition position);

    void doForProduct(Product product);

}
