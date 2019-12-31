package com.wcyv90.rabbit.message;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class XMessage<T> {

    private String eventType;

    private boolean requeueIfFail;

    private T body;

    private LocalDateTime createTime;

    public <E>Optional<E> getBody(Class<E> clazz) {
        if (body != null && body.getClass().equals(clazz)) {
            return Optional.of((E) body);
        }
        if (body instanceof String) {
            return Optional.of(JsonMapper.load((String) body, clazz));
        }
        return Optional.empty();
    }

    //body是泛型
    public <E> Optional<E> getGenericBody(TypeReference<E> typeReference) {
        if (body != null && body.getClass().equals(typeReference.getType())) {
            return Optional.of((E) body);
        }
        if (body instanceof String) {
            return Optional.of(JsonMapper.loadGeneric((String) body, typeReference));
        }
        return Optional.empty();
    }

    public static <E> Builder<E> builder() {
        return new Builder<>(new XMessage<>());
    }

    public static <E> Builder<E> discardableMsgBuilder() {
        return XMessage.<E>builder().requeueIfFail(false);
    }

    public static <E> Builder<E> undiscardableMsgBuilder() {
        return XMessage.<E>builder().requeueIfFail(true);
    }

    public static class Builder<E> {

        private XMessage<E> xMessage;

        Builder(XMessage<E> xMessage) {
            this.xMessage = xMessage;
        }

        public Builder<E> requeueIfFail(boolean requeueIfFail) {
            xMessage.setRequeueIfFail(requeueIfFail);
            return this;
        }

        public Builder<E> eventType(String eventType) {
            xMessage.setEventType(eventType);
            return this;
        }

        public Builder<E> body(E body) {
            xMessage.setBody(body);
            return this;
        }

        public XMessage<E> build() {
            xMessage.setCreateTime(LocalDateTime.now());
            return xMessage;
        }

    }

}
