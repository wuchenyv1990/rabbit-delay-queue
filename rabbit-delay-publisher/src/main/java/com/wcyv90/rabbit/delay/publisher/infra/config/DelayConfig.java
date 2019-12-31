package com.wcyv90.rabbit.delay.publisher.infra.config;

public class DelayConfig {

    public static final String DELAY_ROUTE = "delay-route";
    public static final String TTL_DELAY_ROUTE = "ttl-delay-route";
    public static final String PLUGIN_DELAY_EXCHANGE = "plugin-delay-exchange";
    public static final String PLUGIN_DELAY_QUEUE = "plugin-delay-queue";
    public static final String TTL_DELAY_EXCHANGE = "ttl-delay-exchange";
    public static final String TTL_DELAY_BUFFER_QUEUE = "ttl-delay-buffer-queue";
    public static final String TTL_DELAY_DEAD_EXCHANGE = "ttl-delay-dead-exchange";
    public static final String TTL_DELAY_QUEUE = "ttl-delay-receive-queue";

    public static enum DelayType {
        PLUGIN,
        TTL
    }

}
