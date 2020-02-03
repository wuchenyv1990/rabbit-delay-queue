package com.wcyv90.rabbit.delay.publisher.domain;

import com.wcyv90.rabbit.delay.publisher.infra.config.DelayConfig;
import com.wcyv90.rabbit.message.XMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class XMessageSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendDelayMsg(
            XMessage<?> xMessage,
            String exchange,
            String route,
            DelayConfig.DelayType delayType,
            Long milliseconds
    ) {
        MessagePostProcessor messagePostProcessor;
        switch (delayType) {
            case PLUGIN:
                messagePostProcessor = message -> {
                    message.getMessageProperties().setHeader("x-delay", milliseconds);
                    return message;
                };
                break;
            default:
                messagePostProcessor = message -> {
                    message.getMessageProperties().setExpiration(milliseconds.toString());
                    return message;
                };
        }
        log.info("Send message {} by delay {} at {}", xMessage, milliseconds, LocalDateTime.now());
        rabbitTemplate.convertAndSend(exchange, route, xMessage, messagePostProcessor);
    }

}
