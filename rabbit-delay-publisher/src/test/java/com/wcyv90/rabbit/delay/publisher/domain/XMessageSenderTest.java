package com.wcyv90.rabbit.delay.publisher.domain;

import com.wcyv90.rabbit.message.XMessage;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static com.wcyv90.rabbit.delay.publisher.infra.config.DelayConfig.DelayType.PLUGIN;
import static org.mockito.Mockito.doNothing;

public class XMessageSenderTest {

    @InjectMocks
    private XMessageSender xMessageSender;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldSendDelayMsgSuccess() {
        XMessage xMessage = new XMessage();
        String exchange = RandomStringUtils.random(8);
        String route = RandomStringUtils.random(8);
        long milliseconds = RandomUtils.nextLong(1, 10);
        xMessageSender.sendDelayMsg(
                xMessage,
                exchange,
                route,
                PLUGIN,
                milliseconds
        );
        doNothing().when(rabbitTemplate).convertAndSend(exchange, route, message -> {
            message.getMessageProperties().setHeader("x-delay", milliseconds);
            return message;
        });
    }

}
