package pl.recompiled.demo.order;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
class OrderDto {

    private UUID id;

    private Set<OrderPositionDto> positions;
    private Integer totalPrice;

    static OrderDto of(Order order, PriceService priceService) {
        final OrderDto dto = new OrderDto();
        dto.positions = new HashSet<>();
        dto.id = order.getId();
        dto.positions.addAll(
                order.getPositions()
                        .stream()
                        .map(position -> new OrderPositionDto(position.getId(), position.getProduct().getName(), position.getQuantity()))
                        .collect(Collectors.toSet()));
        dto.totalPrice = order.getPositions().stream()
                .map(priceService::calculatePrice)
                .mapToInt(Integer::intValue).sum();
        return dto;
    }

    static record OrderPositionDto(UUID id, String product, Integer quantity) {
    }

}
