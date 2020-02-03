package com.wcyv90.rabbit.delay.publisher.infra.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

import static com.wcyv90.rabbit.delay.publisher.infra.config.DelayConfig.DELAY_ROUTE;


@Configuration
public class TTLBasedDelayConsumeConfig {

    @Bean
    public Exchange ttlBasedDelayExchange() {
        return new DirectExchange(DelayConfig.TTL_DELAY_EXCHANGE);
    }

    @Bean
    public Queue ttlBasedBufferQueue() {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-dead-letter-exchange", DelayConfig.TTL_DELAY_DEAD_EXCHANGE);
        arguments.put("x-dead-letter-routing-key", DELAY_ROUTE);
        return new Queue(DelayConfig.TTL_DELAY_BUFFER_QUEUE, true, false, false, arguments);
    }

    @Bean
    public Binding tbBinding() {
        return BindingBuilder.bind(ttlBasedBufferQueue())
                .to(ttlBasedDelayExchange())
                .with(DelayConfig.TTL_DELAY_ROUTE)
                .noargs();
    }

    @Bean
    public Exchange ttlDelayDeadExchange() {
        return new DirectExchange(DelayConfig.TTL_DELAY_DEAD_EXCHANGE);
    }

    @Bean
    public Queue ttlDelayReceiveQueue() {
        return new Queue(DelayConfig.TTL_DELAY_QUEUE);
    }

    @Bean
    public Binding drBinding() {
        return BindingBuilder.bind(ttlDelayReceiveQueue())
                .to(ttlDelayDeadExchange())
                .with(DELAY_ROUTE)
                .noargs();
    }

}
