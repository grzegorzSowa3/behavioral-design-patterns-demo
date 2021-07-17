package pl.recompiled.demo.order;

import lombok.RequiredArgsConstructor;
import pl.recompiled.demo.order.mail.MailService;
import pl.recompiled.demo.order.marketing.MarketingService;
import pl.recompiled.demo.order.payment.PaymentService;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class OrderFacade {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final PriceService priceService;
    private final MailService mailService;
    private final MarketingService marketingService;
    private final PaymentService paymentService;

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

        // many calls, many dependencies
        mailService.notifyOrderClosed(orderId);
        marketingService.notifyOrderClosed(orderId);
        paymentService.notifyOrderClosed(orderId);

    }

    // query part

    public Set<OrderDto> getClientsOrders(UUID userId) {
        return orderRepository.findAllByClientId(userId)
                .stream()
                .map(order -> OrderDto.of(order, priceService))
                .collect(Collectors.toSet());
    }


}
