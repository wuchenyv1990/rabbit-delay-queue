package com.wcyv90.rabbit.delay.publisher.domain.service;

import com.wcyv90.rabbit.delay.publisher.domain.XMessageSender;
import com.wcyv90.rabbit.delay.publisher.infra.config.DelayConfig;
import com.wcyv90.rabbit.message.XMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.wcyv90.rabbit.delay.publisher.infra.config.DelayConfig.DELAY_ROUTE;
import static com.wcyv90.rabbit.delay.publisher.infra.config.DelayConfig.PLUGIN_DELAY_EXCHANGE;

@Service
public class DelayMessageService {

    @Autowired
    XMessageSender xMessageSender;

    public <T> void sendByPlugin(String eventType, T message, Long delayMilliseconds) {
        xMessageSender.sendDelayMsg(XMessage.discardableMsgBuilder()
                .eventType(eventType)
                .body(message)
                .build(),
                PLUGIN_DELAY_EXCHANGE,
                DELAY_ROUTE,
                DelayConfig.DelayType.PLUGIN,
                delayMilliseconds
        );
    }

}
