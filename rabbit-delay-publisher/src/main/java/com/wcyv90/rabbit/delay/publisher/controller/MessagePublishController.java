package com.wcyv90.rabbit.delay.publisher.controller;

import com.wcyv90.rabbit.delay.publisher.domain.service.DelayMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/delay")
public class MessagePublishController {

    @Autowired
    DelayMessageService delayMessageService;

    @PostMapping("/plugin")
    public boolean publishDelayMsgByPlugin(
            @RequestParam("msg") String msg,
            @RequestParam("delay") Long delaySeconds
    ) {
        delayMessageService.sendByPlugin("SEND_BY_PLUGIN_DELAY", msg, delaySeconds);
        return true;
    }

    @PostMapping("/ttl")
    public boolean publishDelayMsgByTTL(
            @RequestParam("msg") String msg,
            @RequestParam("delay") String delaySeconds
    ) {
        //TODO: ttl
        return true;
    }

}
