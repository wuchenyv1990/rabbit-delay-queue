package com.wcyv90.rabbit.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.ImmediateRequeueAmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.retry.MessageRecoverer;

import static com.wcyv90.rabbit.message.XMessageUtils.DISCARD_MESSAGE;
import static com.wcyv90.rabbit.message.XMessageUtils.REQUEUE_MESSAGE;
import static java.text.MessageFormat.format;

/**
 * for retry template
 */
@Slf4j
public class XMessageRecoverer implements MessageRecoverer {

    @Override
    public void recover(Message message, Throwable cause) {
        XMessage xMessage = JsonMapper.load(new String(message.getBody()), XMessage.class);
        if (xMessage == null) {
            log.error("Convert msg to x-message error, discard msg: {}", message.getBody());
            throw new AmqpRejectAndDontRequeueException("Discard message in unknown format.", cause);
        }
        if (!xMessage.isRequeueIfFail()) {
            throw new AmqpRejectAndDontRequeueException(format(DISCARD_MESSAGE, xMessage), cause);
        } else {
            throw new ImmediateRequeueAmqpException(format(REQUEUE_MESSAGE, xMessage), cause);
        }
    }

}
