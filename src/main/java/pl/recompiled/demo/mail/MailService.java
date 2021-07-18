package pl.recompiled.demo.mail;

import pl.recompiled.demo.order.OrderClosedEvent;
import pl.recompiled.demo.order.Publisher;
import pl.recompiled.demo.order.Subscriber;

import java.util.UUID;

class MailService implements Subscriber<OrderClosedEvent> {

    public MailService(Publisher<OrderClosedEvent> publisher) {
        publisher.subscribe(this);
    }

    @Override
    public void onNext(OrderClosedEvent event) {
        sendConfirmationMail(event.orderId(), event.timestamp());
    }

    private void sendConfirmationMail(UUID orderId, Long timestamp) {
        // sending mail
    }

}
