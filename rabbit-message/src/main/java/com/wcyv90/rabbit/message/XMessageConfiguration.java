package com.wcyv90.rabbit.message;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.MessageRecoverer;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass({ RabbitTemplate.class, Channel.class })
@ComponentScan("com.wcyv90.rabbit.message")
public class XMessageConfiguration {

    @Bean
    public MessageConverter messageConverter() {
        return new XMessageConverter();
    }

    @Bean
    public MessageRecoverer messageRecoverer() {
        return new XMessageRecoverer();
    }

}
