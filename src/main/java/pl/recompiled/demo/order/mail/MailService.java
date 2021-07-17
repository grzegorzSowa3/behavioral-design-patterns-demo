package pl.recompiled.demo.order.mail;

import java.util.UUID;

public interface MailService {

    void notifyOrderClosed(UUID id);

}
