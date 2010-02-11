/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * his work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.activemq.amqp.protocol.types;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.lang.Boolean;
import java.util.HashMap;
import org.apache.activemq.amqp.protocol.marshaller.AmqpEncodingError;
import org.apache.activemq.amqp.protocol.marshaller.AmqpMarshaller;
import org.apache.activemq.amqp.protocol.marshaller.Encoded;
import org.apache.activemq.util.buffer.Buffer;

/**
 * Represents a the released disposition
 * <p>
 * The released disposition is used to indicate that a given transfer was not and will not be
 * acted upon. The Messages carried by released transfers transition to the available state.
 * Messages that have been released MAY subsequently be delivered out of order.
 * Implementations SHOULD ensure that released Messages keep their position with respect to
 * undelivered Messages of the same priority.
 * </p>
 */
public interface AmqpReleased extends AmqpMap {



    /**
     * permit truncation of the remaining transfer
     * <p>
     * The truncate flag, if true, indicates that the receiver is not interested in the rest of
     * the Message content, and the sender is free to omit it.
     * </p>
     */
    public void setTruncate(Boolean truncate);

    /**
     * permit truncation of the remaining transfer
     * <p>
     * The truncate flag, if true, indicates that the receiver is not interested in the rest of
     * the Message content, and the sender is free to omit it.
     * </p>
     */
    public void setTruncate(AmqpBoolean truncate);

    /**
     * permit truncation of the remaining transfer
     * <p>
     * The truncate flag, if true, indicates that the receiver is not interested in the rest of
     * the Message content, and the sender is free to omit it.
     * </p>
     */
    public Boolean getTruncate();

    /**
     * count the transfer as an unsuccessful delivery attempt
     * <p>
     * If the delivery-failed flag is set, any Messages released MUST have their
     * delivery-failures count incremented.
     * </p>
     */
    public void setDeliveryFailed(Boolean deliveryFailed);

    /**
     * count the transfer as an unsuccessful delivery attempt
     * <p>
     * If the delivery-failed flag is set, any Messages released MUST have their
     * delivery-failures count incremented.
     * </p>
     */
    public void setDeliveryFailed(AmqpBoolean deliveryFailed);

    /**
     * count the transfer as an unsuccessful delivery attempt
     * <p>
     * If the delivery-failed flag is set, any Messages released MUST have their
     * delivery-failures count incremented.
     * </p>
     */
    public Boolean getDeliveryFailed();

    /**
     * prevent redelivery
     * <p>
     * If the deliver-elsewhere is set, then any Messages released MUST NOT be redelivered to
     * the releasing Link Endpoint.
     * </p>
     */
    public void setDeliverElsewhere(Boolean deliverElsewhere);

    /**
     * prevent redelivery
     * <p>
     * If the deliver-elsewhere is set, then any Messages released MUST NOT be redelivered to
     * the releasing Link Endpoint.
     * </p>
     */
    public void setDeliverElsewhere(AmqpBoolean deliverElsewhere);

    /**
     * prevent redelivery
     * <p>
     * If the deliver-elsewhere is set, then any Messages released MUST NOT be redelivered to
     * the releasing Link Endpoint.
     * </p>
     */
    public Boolean getDeliverElsewhere();

    /**
     * message attributes
     * <p>
     * held in the
     * Message's header section. Where the existing message-attrs of the Message contain an
     * entry with the same key as an entry in this field, the value in this field associated
     * with that key replaces the one in the existing headers; where the existing message-attrs
     * has no such value, the value in this map is added.
     * </p>
     */
    public void setMessageAttrs(AmqpMessageAttributes messageAttrs);

    /**
     * message attributes
     * <p>
     * held in the
     * Message's header section. Where the existing message-attrs of the Message contain an
     * entry with the same key as an entry in this field, the value in this field associated
     * with that key replaces the one in the existing headers; where the existing message-attrs
     * has no such value, the value in this map is added.
     * </p>
     */
    public AmqpMessageAttributes getMessageAttrs();

    /**
     * delivery attributes
     * <p>
     * held in the
     * Message's header section. Where the existing delivery-attrs of the Message contain an
     * entry with the same key as an entry in this field, the value in this field associated
     * with that key replaces the one in the existing headers; where the existing
     * delivery-attrs has no such value, the value in this map is added.
     * </p>
     */
    public void setDeliveryAttrs(AmqpMessageAttributes deliveryAttrs);

    /**
     * delivery attributes
     * <p>
     * held in the
     * Message's header section. Where the existing delivery-attrs of the Message contain an
     * entry with the same key as an entry in this field, the value in this field associated
     * with that key replaces the one in the existing headers; where the existing
     * delivery-attrs has no such value, the value in this map is added.
     * </p>
     */
    public AmqpMessageAttributes getDeliveryAttrs();

    public static class AmqpReleasedBean implements AmqpReleased{

        private AmqpReleasedBuffer buffer;
        private AmqpReleasedBean bean = this;
        private AmqpBoolean truncate;
        private AmqpBoolean deliveryFailed;
        private AmqpBoolean deliverElsewhere;
        private AmqpMessageAttributes messageAttrs;
        private AmqpMessageAttributes deliveryAttrs;
        private HashMap<AmqpType<?,?>, AmqpType<?,?>> value;

        public AmqpReleasedBean() {
        }

        public AmqpReleasedBean(HashMap<AmqpType<?,?>, AmqpType<?,?>> value) {
            this.value = value;
        }

        public AmqpReleasedBean(AmqpReleased.AmqpReleasedBean other) {
            this.bean = other;
        }

        public final AmqpReleasedBean copy() {
            return new AmqpReleased.AmqpReleasedBean(bean);
        }

        public final AmqpReleased.AmqpReleasedBuffer getBuffer(AmqpMarshaller marshaller) throws AmqpEncodingError{
            if(buffer == null) {
                buffer = new AmqpReleasedBuffer(marshaller.encode(this));
            }
            return buffer;
        }

        public final void marshal(DataOutput out, AmqpMarshaller marshaller) throws IOException, AmqpEncodingError{
            getBuffer(marshaller).marshal(out, marshaller);
        }


        public void setTruncate(Boolean truncate) {
            setTruncate(new AmqpBoolean.AmqpBooleanBean(truncate));
        }


        public final void setTruncate(AmqpBoolean truncate) {
            copyCheck();
            bean.truncate = truncate;
        }

        public final Boolean getTruncate() {
            return bean.truncate.getValue();
        }

        public void setDeliveryFailed(Boolean deliveryFailed) {
            setDeliveryFailed(new AmqpBoolean.AmqpBooleanBean(deliveryFailed));
        }


        public final void setDeliveryFailed(AmqpBoolean deliveryFailed) {
            copyCheck();
            bean.deliveryFailed = deliveryFailed;
        }

        public final Boolean getDeliveryFailed() {
            return bean.deliveryFailed.getValue();
        }

        public void setDeliverElsewhere(Boolean deliverElsewhere) {
            setDeliverElsewhere(new AmqpBoolean.AmqpBooleanBean(deliverElsewhere));
        }


        public final void setDeliverElsewhere(AmqpBoolean deliverElsewhere) {
            copyCheck();
            bean.deliverElsewhere = deliverElsewhere;
        }

        public final Boolean getDeliverElsewhere() {
            return bean.deliverElsewhere.getValue();
        }

        public final void setMessageAttrs(AmqpMessageAttributes messageAttrs) {
            copyCheck();
            bean.messageAttrs = messageAttrs;
        }

        public final AmqpMessageAttributes getMessageAttrs() {
            return bean.messageAttrs;
        }

        public final void setDeliveryAttrs(AmqpMessageAttributes deliveryAttrs) {
            copyCheck();
            bean.deliveryAttrs = deliveryAttrs;
        }

        public final AmqpMessageAttributes getDeliveryAttrs() {
            return bean.deliveryAttrs;
        }
        public void put(AmqpType<?, ?> key, AmqpType<?, ?> value) {
            bean.value.put(key, value);
        }

        public AmqpType<?, ?> get(AmqpType<?, ?> key) {
            return bean.value.get(key);
        }

        public HashMap<AmqpType<?,?>, AmqpType<?,?>> getValue() {
            return bean.value;
        }


        private final void copyCheck() {
            if(buffer != null) {;
                throw new IllegalStateException("unwriteable");
            }
            if(bean != this) {;
                copy(bean);
            }
        }

        private final void copy(AmqpReleased.AmqpReleasedBean other) {
            this.truncate= other.truncate;
            this.deliveryFailed= other.deliveryFailed;
            this.deliverElsewhere= other.deliverElsewhere;
            this.messageAttrs= other.messageAttrs;
            this.deliveryAttrs= other.deliveryAttrs;
            bean = this;
        }

        public boolean equivalent(AmqpType<?,?> t){
            if(this == t) {
                return true;
            }

            if(t == null || !(t instanceof AmqpReleased)) {
                return false;
            }

            return equivalent((AmqpReleased) t);
        }

        public boolean equivalent(AmqpReleased b) {

            if(b.getTruncate() == null ^ getTruncate() == null) {
                return false;
            }
            if(b.getTruncate() != null && !b.getTruncate().equals(getTruncate())){ 
                return false;
            }

            if(b.getDeliveryFailed() == null ^ getDeliveryFailed() == null) {
                return false;
            }
            if(b.getDeliveryFailed() != null && !b.getDeliveryFailed().equals(getDeliveryFailed())){ 
                return false;
            }

            if(b.getDeliverElsewhere() == null ^ getDeliverElsewhere() == null) {
                return false;
            }
            if(b.getDeliverElsewhere() != null && !b.getDeliverElsewhere().equals(getDeliverElsewhere())){ 
                return false;
            }

            if(b.getMessageAttrs() == null ^ getMessageAttrs() == null) {
                return false;
            }
            if(b.getMessageAttrs() != null && !b.getMessageAttrs().equals(getMessageAttrs())){ 
                return false;
            }

            if(b.getDeliveryAttrs() == null ^ getDeliveryAttrs() == null) {
                return false;
            }
            if(b.getDeliveryAttrs() != null && !b.getDeliveryAttrs().equals(getDeliveryAttrs())){ 
                return false;
            }
            return true;
        }
    }

    public static class AmqpReleasedBuffer extends AmqpMap.AmqpMapBuffer implements AmqpReleased{

        private AmqpReleasedBean bean;

        protected AmqpReleasedBuffer(Encoded<HashMap<AmqpType<?,?>, AmqpType<?,?>>> encoded) {
            super(encoded);
        }

    public void setTruncate(Boolean truncate) {
            bean().setTruncate(truncate);
        }

        public final void setTruncate(AmqpBoolean truncate) {
            bean().setTruncate(truncate);
        }

        public final Boolean getTruncate() {
            return bean().getTruncate();
        }

    public void setDeliveryFailed(Boolean deliveryFailed) {
            bean().setDeliveryFailed(deliveryFailed);
        }

        public final void setDeliveryFailed(AmqpBoolean deliveryFailed) {
            bean().setDeliveryFailed(deliveryFailed);
        }

        public final Boolean getDeliveryFailed() {
            return bean().getDeliveryFailed();
        }

    public void setDeliverElsewhere(Boolean deliverElsewhere) {
            bean().setDeliverElsewhere(deliverElsewhere);
        }

        public final void setDeliverElsewhere(AmqpBoolean deliverElsewhere) {
            bean().setDeliverElsewhere(deliverElsewhere);
        }

        public final Boolean getDeliverElsewhere() {
            return bean().getDeliverElsewhere();
        }

        public final void setMessageAttrs(AmqpMessageAttributes messageAttrs) {
            bean().setMessageAttrs(messageAttrs);
        }

        public final AmqpMessageAttributes getMessageAttrs() {
            return bean().getMessageAttrs();
        }

        public final void setDeliveryAttrs(AmqpMessageAttributes deliveryAttrs) {
            bean().setDeliveryAttrs(deliveryAttrs);
        }

        public final AmqpMessageAttributes getDeliveryAttrs() {
            return bean().getDeliveryAttrs();
        }
        public void put(AmqpType<?, ?> key, AmqpType<?, ?> value) {
            bean().put(key, value);
        }

        public AmqpType<?, ?> get(AmqpType<?, ?> key) {
            return bean().get(key);
        }

        public HashMap<AmqpType<?,?>, AmqpType<?,?>> getValue() {
            return bean().getValue();
        }

        public AmqpReleased.AmqpReleasedBuffer getBuffer(AmqpMarshaller marshaller) throws AmqpEncodingError{
            return this;
        }

        protected AmqpReleased bean() {
            if(bean == null) {
                bean = new AmqpReleased.AmqpReleasedBean(encoded.getValue());
                bean.buffer = this;
            }
            return bean;
        }

        public boolean equivalent(AmqpType<?, ?> t) {
            return bean().equivalent(t);
        }

        public static AmqpReleased.AmqpReleasedBuffer create(Encoded<HashMap<AmqpType<?,?>, AmqpType<?,?>>> encoded) {
            if(encoded.isNull()) {
                return null;
            }
            return new AmqpReleased.AmqpReleasedBuffer(encoded);
        }

        public static AmqpReleased.AmqpReleasedBuffer create(DataInput in, AmqpMarshaller marshaller) throws IOException, AmqpEncodingError {
            return create(marshaller.unmarshalAmqpReleased(in));
        }

        public static AmqpReleased.AmqpReleasedBuffer create(Buffer buffer, int offset, AmqpMarshaller marshaller) throws AmqpEncodingError {
            return create(marshaller.decodeAmqpReleased(buffer, offset));
        }
    }
}