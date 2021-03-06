package com.wcyv90.rabbit.message;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.ImmediateRequeueAmqpException;

import static java.text.MessageFormat.format;
import static org.springframework.amqp.support.converter.SimpleMessageConverter.DEFAULT_CHARSET;

@Slf4j
public class XMessageUtils {

    public static final String UNKNOWN_EVENT = "UNKNOWN_EVENT";
    static final String DISCARD_MESSAGE = "Discard message: {0}";
    static final String REQUEUE_MESSAGE = "Requeue message: {0}";

    public static XMessage handleBytesMsg(byte[] bytes, boolean discard) {
        String payload;
        try {
            payload = new String(bytes, DEFAULT_CHARSET);
        } catch (Exception e) {
            log.error("Msg[{}] is illegal.", Hex.encodeHexString(bytes));
            if (discard) {
                throw new AmqpRejectAndDontRequeueException(format(DISCARD_MESSAGE, Hex.encodeHexString(bytes)));
            } else {
                return newUnknownMessage();
            }
        }
        XMessage xMessage = JsonMapper.load(payload, XMessage.class);
        if (xMessage == null) {
            log.error("Msg[{}] is not XMessage.", payload);
            if (discard) {
                throw new AmqpRejectAndDontRequeueException(format(DISCARD_MESSAGE, payload));
            } else {
                return newUnknownMessage();
            }
        }
        if (xMessage.getBody() != null && !(xMessage.getBody() instanceof String)) {
            //body是对象时此时为LinkedHashMap
            xMessage.setBody(JsonMapper.dumps(xMessage.getBody()));
        }
        return xMessage;
    }

    public static void ackOrRequeueNow(XMessage xMessage) {
        if (xMessage.isRequeueIfFail()) {
            throw new AmqpRejectAndDontRequeueException(format(DISCARD_MESSAGE, xMessage));
        } else {
            throw new ImmediateRequeueAmqpException(format(REQUEUE_MESSAGE, xMessage));
        }
    }

    public static XMessage newUnknownMessage() {
        return XMessage.discardableMsgBuilder().eventType(UNKNOWN_EVENT).build();
    }

}
