package com.wcyv90.rabbit.delay.publisher.infra.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

import static com.wcyv90.rabbit.delay.publisher.infra.config.DelayConfig.DELAY_ROUTE;

/**
 * 声明延迟队列：基于rabbitmq_delayed_message_exchange插件实现
 */
@Configuration
public class PluginDelayConfig {

    @Bean
    public Exchange pluginDelayExchange() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type", "direct");
        return new CustomExchange(
                DelayConfig.PLUGIN_DELAY_EXCHANGE,
                "x-delayed-message",
                true,
                false,
                args);
    }

    @Bean
    public Queue pluginDelayQueue() {
        return new Queue(DelayConfig.PLUGIN_DELAY_QUEUE);
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(pluginDelayQueue()).to(pluginDelayExchange()).with(DELAY_ROUTE).noargs();
    }

}
