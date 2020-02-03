package com.wcyv90.rabbit.delay.listener.infra.amqp;

import com.wcyv90.rabbit.message.XMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Slf4j
public class PluginDelayMessageListener {

    @RabbitListener(queues = "plugin-delay-queue")
    public void handleMessage(XMessage<String> xMessage) {
        log.info("Receive plugin delay message: {} at {}.", xMessage, LocalDateTime.now());
    }

}
