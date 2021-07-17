package pl.recompiled.demo.order.payment;

import java.util.UUID;

public interface PaymentService {

    void notifyOrderClosed(UUID id);

}
