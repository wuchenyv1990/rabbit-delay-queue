package com.wcyv90.rabbit.delay.listener.infra.amqp;

import com.wcyv90.rabbit.message.XMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RabbitListener(
        bindings = @QueueBinding(
                value = @Queue(value = "ttl-delay-receive-queue"),
                exchange = @Exchange(value = "ttl-delay-dead-exchange"),
                key = "delay-route"
        )
)
@Slf4j
public class TTLDelayMessageListener {

    @RabbitHandler
    public void handleMessage(XMessage<String> xMessage) {
        log.info("Receive ttl delay message: {} at {}.", xMessage, LocalDateTime.now());
    }

}
