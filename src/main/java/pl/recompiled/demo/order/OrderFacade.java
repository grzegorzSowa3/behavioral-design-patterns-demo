package pl.recompiled.demo.order;

import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class OrderFacade implements Publisher<OrderClosedEvent> {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final PriceService priceService;
    private final List<Subscriber<OrderClosedEvent>> orderClosedSubscribers = new ArrayList<>();

    @Override
    public void subscribe(Subscriber<OrderClosedEvent> subscriber) {
        orderClosedSubscribers.add(subscriber);
    }

    // command part

    public UUID createNewOrderForUser(UUID userId) {
        final Order order = orderRepository.save(Order.of(userId));
        return order.getId();
    }

    public UUID addOrderPosition(UUID orderId, AddOrderPositionDto dto) {
        final Product product = productRepository.findById(dto.productId())
                .orElseThrow(EntityNotFoundException::new);
        final Order order = orderRepository.findById(orderId)
                .orElseThrow(EntityNotFoundException::new);
        final OrderPosition position = OrderPosition.of(product, dto.quantity());
        order.addPosition(position);
        orderRepository.save(order);
        return position.getId();
    }

    static record AddOrderPositionDto(UUID productId, Integer quantity) {
    }

    public void closeOrder(UUID orderId) {
        final Order order = orderRepository.findById(orderId)
                .orElseThrow(EntityNotFoundException::new);
        order.close();

        final OrderClosedEvent event = new OrderClosedEvent(orderId, Instant.now().getEpochSecond());
        for (var subscriber : orderClosedSubscribers) {
            subscriber.onNext(event);
        }

    }

    // query part

    public Set<OrderDto> getClientsOrders(UUID userId) {
        return orderRepository.findAllByClientId(userId)
                .stream()
                .map(order -> OrderDto.of(order, priceService))
                .collect(Collectors.toSet());
    }


}
