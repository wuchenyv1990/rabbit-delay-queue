package com.wcyv90.rabbit.message;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

import static com.wcyv90.rabbit.message.XMessageUtils.newUnknownMessage;

/**
 * 只接受处理XMessage格式消息
 */
public class XMessageConverter extends SimpleMessageConverter {

    @Override
    protected Message createMessage(Object object, MessageProperties messageProperties)
            throws MessageConversionException {
        String msgStr = Optional.ofNullable(JsonMapper.dumps(object))
                .orElseThrow(() -> new RuntimeException("Convert message failed."));
        return super.createMessage(msgStr, messageProperties);
    }

    /**
     * 这里接消息全部作文本处理
     */
    @Override
    public Object fromMessage(Message message) throws MessageConversionException {
        Object content = null;
        MessageProperties properties = message.getMessageProperties();
        if (properties != null) {
            String contentType = properties.getContentType();
            //处理所有文本
            if (contentType == null || contentType.startsWith("text")) {
                String encoding = properties.getContentEncoding();
                if (encoding == null) {
                    encoding = this.DEFAULT_CHARSET;
                }
                try {
                    String originStr = new String(message.getBody(), encoding);
                    String deTransferredStr = deTransfer(originStr);
                    content = deTransfer(deTransferredStr);
                }
                catch (UnsupportedEncodingException e) {
                    throw new MessageConversionException(
                            "failed to convert text-based Message content", e);
                }
            }
        }
        if (content == null) {
            content = message.getBody();
        }
        return content;
    }

    private String deTransfer(String transferredMsg) {
        String deTransferredStr = transferredMsg;
        if (StringUtils.isEmpty(deTransferredStr)) {
            return deTransferredStr;
        }
        //去传输后的转义符
        transferredMsg = transferredMsg.replaceAll("\\\\", "");
        //去字符串json首尾"
        if (transferredMsg.indexOf('"') == 0
            && transferredMsg.lastIndexOf('"') == transferredMsg.length() - 1) {
            deTransferredStr = transferredMsg.substring(1, transferredMsg.length() - 1);
        }
        return deTransferredStr;
    }

    private XMessage deserialize(String jsonStr) {
        XMessage xMessage = JsonMapper.load(jsonStr, XMessage.class);
        if (xMessage == null) {
            return newUnknownMessage();
        }
        if (xMessage.getBody() != null && !(xMessage.getBody() instanceof String)) {
            //body是对象时此时为LinkedHashMap
            xMessage.setBody(JsonMapper.dumps(xMessage.getBody()));
        }
        return xMessage;
    }

}
