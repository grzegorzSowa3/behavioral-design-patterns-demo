package pl.recompiled.demo.order;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

interface OrderRepository {

    Order save(Order order);

    Set<Order> findAllByClientId(UUID clientId);

    Optional<Order> findById(UUID orderId);

}
