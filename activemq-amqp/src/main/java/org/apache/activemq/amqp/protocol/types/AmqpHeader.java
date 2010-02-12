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
import java.lang.Long;
import java.lang.Short;
import java.math.BigInteger;
import java.util.Date;
import java.util.Iterator;
import org.apache.activemq.amqp.protocol.marshaller.AmqpEncodingError;
import org.apache.activemq.amqp.protocol.marshaller.AmqpMarshaller;
import org.apache.activemq.amqp.protocol.marshaller.Encoded;
import org.apache.activemq.amqp.protocol.types.IAmqpList;
import org.apache.activemq.util.buffer.Buffer;

/**
 * Represents a transport headers for a Message
 * <p>
 * The header struct carries information about the transfer of a Message over a specific
 * Link.
 * </p>
 */
public interface AmqpHeader extends AmqpList {



    /**
     * specify durability requirements
     * <p>
     * Durable Messages MUST NOT be lost even if an intermediary is unexpectedly terminated and
     * restarted.
     * </p>
     */
    public void setDurable(Boolean durable);

    /**
     * specify durability requirements
     * <p>
     * Durable Messages MUST NOT be lost even if an intermediary is unexpectedly terminated and
     * restarted.
     * </p>
     */
    public void setDurable(AmqpBoolean durable);

    /**
     * specify durability requirements
     * <p>
     * Durable Messages MUST NOT be lost even if an intermediary is unexpectedly terminated and
     * restarted.
     * </p>
     */
    public Boolean getDurable();

    /**
     * relative Message priority
     * <p>
     * This field contains the relative Message priority. Higher numbers indicate higher
     * priority Messages. Messages with higher priorities MAY be delivered before those with
     * lower priorities.
     * </p>
     * <p>
     * An AMQP intermediary implementing distinct priority levels MUST do so in the following
     * manner:
     * </p>
     * <ul>
     * <li>
     * <p>
     * If n distinct priorities are implemented and n is less than 10 -- priorities 0 to
     * (5 - ceiling(n/2)) MUST be treated equivalently and MUST be the lowest effective
     * priority. The priorities (4 + floor(n/2)) and above MUST be treated equivalently
     * and MUST be the highest effective priority. The priorities (5 - ceiling(n/2)) to (4
     * + floor(n/2)) inclusive MUST be treated as distinct priorities.
     * </p>
     * <
     * ></li>
     * <li>
     * <p>
     * If n distinct priorities are implemented and n is 10 or greater -- priorities 0 to
     * (n - 1) MUST be distinct, and priorities n and above MUST be equivalent to priority
     * (n - 1).
     * </p>
     * <
     * ></li>
     * <
     * ></ul>
     * <p>
     * Thus, for example, if 2 distinct priorities are implemented, then levels 0 to 4 are
     * equivalent, and levels 5 to 9 are equivalent and levels 4 and 5 are distinct. If 3
     * distinct priorities are implements the 0 to 3 are equivalent, 5 to 9 are equivalent and
     * 3, 4 and 5 are distinct.
     * </p>
     * <p>
     * This scheme ensures that if two priorities are distinct for a server which implements m
     * separate priority levels they are also distinct for a server which implements n
     * different priority levels where n > m.
     * </p>
     */
    public void setPriority(Short priority);

    /**
     * relative Message priority
     * <p>
     * This field contains the relative Message priority. Higher numbers indicate higher
     * priority Messages. Messages with higher priorities MAY be delivered before those with
     * lower priorities.
     * </p>
     * <p>
     * An AMQP intermediary implementing distinct priority levels MUST do so in the following
     * manner:
     * </p>
     * <ul>
     * <li>
     * <p>
     * If n distinct priorities are implemented and n is less than 10 -- priorities 0 to
     * (5 - ceiling(n/2)) MUST be treated equivalently and MUST be the lowest effective
     * priority. The priorities (4 + floor(n/2)) and above MUST be treated equivalently
     * and MUST be the highest effective priority. The priorities (5 - ceiling(n/2)) to (4
     * + floor(n/2)) inclusive MUST be treated as distinct priorities.
     * </p>
     * <
     * ></li>
     * <li>
     * <p>
     * If n distinct priorities are implemented and n is 10 or greater -- priorities 0 to
     * (n - 1) MUST be distinct, and priorities n and above MUST be equivalent to priority
     * (n - 1).
     * </p>
     * <
     * ></li>
     * <
     * ></ul>
     * <p>
     * Thus, for example, if 2 distinct priorities are implemented, then levels 0 to 4 are
     * equivalent, and levels 5 to 9 are equivalent and levels 4 and 5 are distinct. If 3
     * distinct priorities are implements the 0 to 3 are equivalent, 5 to 9 are equivalent and
     * 3, 4 and 5 are distinct.
     * </p>
     * <p>
     * This scheme ensures that if two priorities are distinct for a server which implements m
     * separate priority levels they are also distinct for a server which implements n
     * different priority levels where n > m.
     * </p>
     */
    public void setPriority(AmqpUbyte priority);

    /**
     * relative Message priority
     * <p>
     * This field contains the relative Message priority. Higher numbers indicate higher
     * priority Messages. Messages with higher priorities MAY be delivered before those with
     * lower priorities.
     * </p>
     * <p>
     * An AMQP intermediary implementing distinct priority levels MUST do so in the following
     * manner:
     * </p>
     * <ul>
     * <li>
     * <p>
     * If n distinct priorities are implemented and n is less than 10 -- priorities 0 to
     * (5 - ceiling(n/2)) MUST be treated equivalently and MUST be the lowest effective
     * priority. The priorities (4 + floor(n/2)) and above MUST be treated equivalently
     * and MUST be the highest effective priority. The priorities (5 - ceiling(n/2)) to (4
     * + floor(n/2)) inclusive MUST be treated as distinct priorities.
     * </p>
     * <
     * ></li>
     * <li>
     * <p>
     * If n distinct priorities are implemented and n is 10 or greater -- priorities 0 to
     * (n - 1) MUST be distinct, and priorities n and above MUST be equivalent to priority
     * (n - 1).
     * </p>
     * <
     * ></li>
     * <
     * ></ul>
     * <p>
     * Thus, for example, if 2 distinct priorities are implemented, then levels 0 to 4 are
     * equivalent, and levels 5 to 9 are equivalent and levels 4 and 5 are distinct. If 3
     * distinct priorities are implements the 0 to 3 are equivalent, 5 to 9 are equivalent and
     * 3, 4 and 5 are distinct.
     * </p>
     * <p>
     * This scheme ensures that if two priorities are distinct for a server which implements m
     * separate priority levels they are also distinct for a server which implements n
     * different priority levels where n > m.
     * </p>
     */
    public Short getPriority();

    /**
     * the time of Message transmit
     * <p>
     * The point in time at which the sender considers the Message to be transmitted. The ttl
     * field, if set by the sender, is relative to this point in time.
     * </p>
     */
    public void setTransmitTime(Date transmitTime);

    /**
     * the time of Message transmit
     * <p>
     * The point in time at which the sender considers the Message to be transmitted. The ttl
     * field, if set by the sender, is relative to this point in time.
     * </p>
     */
    public void setTransmitTime(AmqpTimestamp transmitTime);

    /**
     * the time of Message transmit
     * <p>
     * The point in time at which the sender considers the Message to be transmitted. The ttl
     * field, if set by the sender, is relative to this point in time.
     * </p>
     */
    public Date getTransmitTime();

    /**
     * time to live in ms
     * <p>
     * Duration in milliseconds for which the Message should be considered "live". If this is
     * set then a Message expiration time will be computed based on the transmit-time plus this
     * value. Messages that live longer than their expiration time will be discarded (or dead
     * lettered). If the transmit-time is not set, then the expiration is computed relative to
     * the Message arrival time.
     * </p>
     */
    public void setTtl(BigInteger ttl);

    /**
     * time to live in ms
     * <p>
     * Duration in milliseconds for which the Message should be considered "live". If this is
     * set then a Message expiration time will be computed based on the transmit-time plus this
     * value. Messages that live longer than their expiration time will be discarded (or dead
     * lettered). If the transmit-time is not set, then the expiration is computed relative to
     * the Message arrival time.
     * </p>
     */
    public void setTtl(AmqpUlong ttl);

    /**
     * time to live in ms
     * <p>
     * Duration in milliseconds for which the Message should be considered "live". If this is
     * set then a Message expiration time will be computed based on the transmit-time plus this
     * value. Messages that live longer than their expiration time will be discarded (or dead
     * lettered). If the transmit-time is not set, then the expiration is computed relative to
     * the Message arrival time.
     * </p>
     */
    public BigInteger getTtl();

    /**
     * <p>
     * The number of other DESTRUCTIVE Links to which delivery of this Message was previously
     * attempted unsuccessfully. This does not include the current Link even if delivery to the
     * current Link has been previously attempted.
     * </p>
     */
    public void setFormerAcquirers(Long formerAcquirers);

    /**
     * <p>
     * The number of other DESTRUCTIVE Links to which delivery of this Message was previously
     * attempted unsuccessfully. This does not include the current Link even if delivery to the
     * current Link has been previously attempted.
     * </p>
     */
    public void setFormerAcquirers(AmqpUint formerAcquirers);

    /**
     * <p>
     * The number of other DESTRUCTIVE Links to which delivery of this Message was previously
     * attempted unsuccessfully. This does not include the current Link even if delivery to the
     * current Link has been previously attempted.
     * </p>
     */
    public Long getFormerAcquirers();

    /**
     * the number of prior unsuccessful delivery attempts
     * <p>
     * The number of unsuccessful previous attempts to deliver this message. If this value is
     * non-zero it may be taken as an indication that the Message may be a duplicate. The
     * delivery-failures value is initially set to the same value as the Message has when it
     * arrived at the source. It is incremented When:
     * </p>
     * <ol>
     * <li>
     * <p>
     * the Message has previously been sent from this Node using a Source with a
     * distribution-mode of DESTRUCTIVE which closed without the Message being acknowledged
     * </p>
     * </li>
     * <li>
     * <p>
     * and the delivery-failed field set to true.
     * </p>
     * </li>
     * </ol>
     */
    public void setDeliveryFailures(Long deliveryFailures);

    /**
     * the number of prior unsuccessful delivery attempts
     * <p>
     * The number of unsuccessful previous attempts to deliver this message. If this value is
     * non-zero it may be taken as an indication that the Message may be a duplicate. The
     * delivery-failures value is initially set to the same value as the Message has when it
     * arrived at the source. It is incremented When:
     * </p>
     * <ol>
     * <li>
     * <p>
     * the Message has previously been sent from this Node using a Source with a
     * distribution-mode of DESTRUCTIVE which closed without the Message being acknowledged
     * </p>
     * </li>
     * <li>
     * <p>
     * and the delivery-failed field set to true.
     * </p>
     * </li>
     * </ol>
     */
    public void setDeliveryFailures(AmqpUint deliveryFailures);

    /**
     * the number of prior unsuccessful delivery attempts
     * <p>
     * The number of unsuccessful previous attempts to deliver this message. If this value is
     * non-zero it may be taken as an indication that the Message may be a duplicate. The
     * delivery-failures value is initially set to the same value as the Message has when it
     * arrived at the source. It is incremented When:
     * </p>
     * <ol>
     * <li>
     * <p>
     * the Message has previously been sent from this Node using a Source with a
     * distribution-mode of DESTRUCTIVE which closed without the Message being acknowledged
     * </p>
     * </li>
     * <li>
     * <p>
     * and the delivery-failed field set to true.
     * </p>
     * </li>
     * </ol>
     */
    public Long getDeliveryFailures();

    /**
     * indicates the format of the Message
     */
    public void setFormatCode(Long formatCode);

    /**
     * indicates the format of the Message
     */
    public void setFormatCode(AmqpUint formatCode);

    /**
     * indicates the format of the Message
     */
    public Long getFormatCode();

    /**
     * message attributes
     * <p>
     * command and
     * dispositions which allow for updates to the message-attrs.
     * </p>
     */
    public void setMessageAttrs(AmqpMessageAttributes messageAttrs);

    /**
     * message attributes
     * <p>
     * command and
     * dispositions which allow for updates to the message-attrs.
     * </p>
     */
    public AmqpMessageAttributes getMessageAttrs();

    /**
     * delivery attributes
     * <p>
     * command
     * and dispositions which allow for updates to the delivery-attrs.
     * </p>
     */
    public void setDeliveryAttrs(AmqpMessageAttributes deliveryAttrs);

    /**
     * delivery attributes
     * <p>
     * command
     * and dispositions which allow for updates to the delivery-attrs.
     * </p>
     */
    public AmqpMessageAttributes getDeliveryAttrs();

    public static class AmqpHeaderBean implements AmqpHeader{

        private AmqpHeaderBuffer buffer;
        private AmqpHeaderBean bean = this;
        private AmqpBoolean durable;
        private AmqpUbyte priority;
        private AmqpTimestamp transmitTime;
        private AmqpUlong ttl;
        private AmqpUint formerAcquirers;
        private AmqpUint deliveryFailures;
        private AmqpUint formatCode;
        private AmqpMessageAttributes messageAttrs;
        private AmqpMessageAttributes deliveryAttrs;

        public AmqpHeaderBean() {
        }

        public AmqpHeaderBean(IAmqpList value) {
            //TODO we should defer decoding of the described type:
            for(int i = 0; i < value.getListCount(); i++) {
                set(i, value.get(i));
            }
        }

        public AmqpHeaderBean(AmqpHeader.AmqpHeaderBean other) {
            this.bean = other;
        }

        public final AmqpHeaderBean copy() {
            return new AmqpHeader.AmqpHeaderBean(bean);
        }

        public final AmqpHeader.AmqpHeaderBuffer getBuffer(AmqpMarshaller marshaller) throws AmqpEncodingError{
            if(buffer == null) {
                buffer = new AmqpHeaderBuffer(marshaller.encode(this));
            }
            return buffer;
        }

        public final void marshal(DataOutput out, AmqpMarshaller marshaller) throws IOException, AmqpEncodingError{
            getBuffer(marshaller).marshal(out, marshaller);
        }


        public void setDurable(Boolean durable) {
            setDurable(new AmqpBoolean.AmqpBooleanBean(durable));
        }


        public final void setDurable(AmqpBoolean durable) {
            copyCheck();
            bean.durable = durable;
        }

        public final Boolean getDurable() {
            return bean.durable.getValue();
        }

        public void setPriority(Short priority) {
            setPriority(new AmqpUbyte.AmqpUbyteBean(priority));
        }


        public final void setPriority(AmqpUbyte priority) {
            copyCheck();
            bean.priority = priority;
        }

        public final Short getPriority() {
            return bean.priority.getValue();
        }

        public void setTransmitTime(Date transmitTime) {
            setTransmitTime(new AmqpTimestamp.AmqpTimestampBean(transmitTime));
        }


        public final void setTransmitTime(AmqpTimestamp transmitTime) {
            copyCheck();
            bean.transmitTime = transmitTime;
        }

        public final Date getTransmitTime() {
            return bean.transmitTime.getValue();
        }

        public void setTtl(BigInteger ttl) {
            setTtl(new AmqpUlong.AmqpUlongBean(ttl));
        }


        public final void setTtl(AmqpUlong ttl) {
            copyCheck();
            bean.ttl = ttl;
        }

        public final BigInteger getTtl() {
            return bean.ttl.getValue();
        }

        public void setFormerAcquirers(Long formerAcquirers) {
            setFormerAcquirers(new AmqpUint.AmqpUintBean(formerAcquirers));
        }


        public final void setFormerAcquirers(AmqpUint formerAcquirers) {
            copyCheck();
            bean.formerAcquirers = formerAcquirers;
        }

        public final Long getFormerAcquirers() {
            return bean.formerAcquirers.getValue();
        }

        public void setDeliveryFailures(Long deliveryFailures) {
            setDeliveryFailures(new AmqpUint.AmqpUintBean(deliveryFailures));
        }


        public final void setDeliveryFailures(AmqpUint deliveryFailures) {
            copyCheck();
            bean.deliveryFailures = deliveryFailures;
        }

        public final Long getDeliveryFailures() {
            return bean.deliveryFailures.getValue();
        }

        public void setFormatCode(Long formatCode) {
            setFormatCode(new AmqpUint.AmqpUintBean(formatCode));
        }


        public final void setFormatCode(AmqpUint formatCode) {
            copyCheck();
            bean.formatCode = formatCode;
        }

        public final Long getFormatCode() {
            return bean.formatCode.getValue();
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

        public void set(int index, AmqpType<?, ?> value) {
            switch(index) {
            case 0: {
                setDurable((AmqpBoolean) value);
                break;
            }
            case 1: {
                setPriority((AmqpUbyte) value);
                break;
            }
            case 2: {
                setTransmitTime((AmqpTimestamp) value);
                break;
            }
            case 3: {
                setTtl((AmqpUlong) value);
                break;
            }
            case 4: {
                setFormerAcquirers((AmqpUint) value);
                break;
            }
            case 5: {
                setDeliveryFailures((AmqpUint) value);
                break;
            }
            case 6: {
                setFormatCode((AmqpUint) value);
                break;
            }
            case 7: {
                setMessageAttrs((AmqpMessageAttributes) value);
                break;
            }
            case 8: {
                setDeliveryAttrs((AmqpMessageAttributes) value);
                break;
            }
            default : {
                throw new IndexOutOfBoundsException(String.valueOf(index));
            }
            }
        }

        public AmqpType<?, ?> get(int index) {
            switch(index) {
            case 0: {
                return bean.durable;
            }
            case 1: {
                return bean.priority;
            }
            case 2: {
                return bean.transmitTime;
            }
            case 3: {
                return bean.ttl;
            }
            case 4: {
                return bean.formerAcquirers;
            }
            case 5: {
                return bean.deliveryFailures;
            }
            case 6: {
                return bean.formatCode;
            }
            case 7: {
                return bean.messageAttrs;
            }
            case 8: {
                return bean.deliveryAttrs;
            }
            default : {
                throw new IndexOutOfBoundsException(String.valueOf(index));
            }
            }
        }

        public int getListCount() {
            return 9;
        }

        public IAmqpList getValue() {
            return bean;
        }

        public Iterator<AmqpType<?, ?>> iterator() {
            return new AmqpListIterator(bean);
        }


        private final void copyCheck() {
            if(buffer != null) {;
                throw new IllegalStateException("unwriteable");
            }
            if(bean != this) {;
                copy(bean);
            }
        }

        private final void copy(AmqpHeader.AmqpHeaderBean other) {
            this.durable= other.durable;
            this.priority= other.priority;
            this.transmitTime= other.transmitTime;
            this.ttl= other.ttl;
            this.formerAcquirers= other.formerAcquirers;
            this.deliveryFailures= other.deliveryFailures;
            this.formatCode= other.formatCode;
            this.messageAttrs= other.messageAttrs;
            this.deliveryAttrs= other.deliveryAttrs;
            bean = this;
        }

        public boolean equivalent(AmqpType<?,?> t){
            if(this == t) {
                return true;
            }

            if(t == null || !(t instanceof AmqpHeader)) {
                return false;
            }

            return equivalent((AmqpHeader) t);
        }

        public boolean equivalent(AmqpHeader b) {

            if(b.getDurable() == null ^ getDurable() == null) {
                return false;
            }
            if(b.getDurable() != null && !b.getDurable().equals(getDurable())){ 
                return false;
            }

            if(b.getPriority() == null ^ getPriority() == null) {
                return false;
            }
            if(b.getPriority() != null && !b.getPriority().equals(getPriority())){ 
                return false;
            }

            if(b.getTransmitTime() == null ^ getTransmitTime() == null) {
                return false;
            }
            if(b.getTransmitTime() != null && !b.getTransmitTime().equals(getTransmitTime())){ 
                return false;
            }

            if(b.getTtl() == null ^ getTtl() == null) {
                return false;
            }
            if(b.getTtl() != null && !b.getTtl().equals(getTtl())){ 
                return false;
            }

            if(b.getFormerAcquirers() == null ^ getFormerAcquirers() == null) {
                return false;
            }
            if(b.getFormerAcquirers() != null && !b.getFormerAcquirers().equals(getFormerAcquirers())){ 
                return false;
            }

            if(b.getDeliveryFailures() == null ^ getDeliveryFailures() == null) {
                return false;
            }
            if(b.getDeliveryFailures() != null && !b.getDeliveryFailures().equals(getDeliveryFailures())){ 
                return false;
            }

            if(b.getFormatCode() == null ^ getFormatCode() == null) {
                return false;
            }
            if(b.getFormatCode() != null && !b.getFormatCode().equals(getFormatCode())){ 
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

    public static class AmqpHeaderBuffer extends AmqpList.AmqpListBuffer implements AmqpHeader{

        private AmqpHeaderBean bean;

        protected AmqpHeaderBuffer(Encoded<IAmqpList> encoded) {
            super(encoded);
        }

    public void setDurable(Boolean durable) {
            bean().setDurable(durable);
        }

        public final void setDurable(AmqpBoolean durable) {
            bean().setDurable(durable);
        }

        public final Boolean getDurable() {
            return bean().getDurable();
        }

    public void setPriority(Short priority) {
            bean().setPriority(priority);
        }

        public final void setPriority(AmqpUbyte priority) {
            bean().setPriority(priority);
        }

        public final Short getPriority() {
            return bean().getPriority();
        }

    public void setTransmitTime(Date transmitTime) {
            bean().setTransmitTime(transmitTime);
        }

        public final void setTransmitTime(AmqpTimestamp transmitTime) {
            bean().setTransmitTime(transmitTime);
        }

        public final Date getTransmitTime() {
            return bean().getTransmitTime();
        }

    public void setTtl(BigInteger ttl) {
            bean().setTtl(ttl);
        }

        public final void setTtl(AmqpUlong ttl) {
            bean().setTtl(ttl);
        }

        public final BigInteger getTtl() {
            return bean().getTtl();
        }

    public void setFormerAcquirers(Long formerAcquirers) {
            bean().setFormerAcquirers(formerAcquirers);
        }

        public final void setFormerAcquirers(AmqpUint formerAcquirers) {
            bean().setFormerAcquirers(formerAcquirers);
        }

        public final Long getFormerAcquirers() {
            return bean().getFormerAcquirers();
        }

    public void setDeliveryFailures(Long deliveryFailures) {
            bean().setDeliveryFailures(deliveryFailures);
        }

        public final void setDeliveryFailures(AmqpUint deliveryFailures) {
            bean().setDeliveryFailures(deliveryFailures);
        }

        public final Long getDeliveryFailures() {
            return bean().getDeliveryFailures();
        }

    public void setFormatCode(Long formatCode) {
            bean().setFormatCode(formatCode);
        }

        public final void setFormatCode(AmqpUint formatCode) {
            bean().setFormatCode(formatCode);
        }

        public final Long getFormatCode() {
            return bean().getFormatCode();
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

        public void set(int index, AmqpType<?, ?> value) {
            bean().set(index, value);
        }

        public AmqpType<?, ?> get(int index) {
            return bean().get(index);
        }

        public int getListCount() {
            return bean().getListCount();
        }

        public Iterator<AmqpType<?, ?>> iterator() {
            return bean().iterator();
        }

        public IAmqpList getValue() {
            return bean().getValue();
        }

        public AmqpHeader.AmqpHeaderBuffer getBuffer(AmqpMarshaller marshaller) throws AmqpEncodingError{
            return this;
        }

        protected AmqpHeader bean() {
            if(bean == null) {
                bean = new AmqpHeader.AmqpHeaderBean(encoded.getValue());
                bean.buffer = this;
            }
            return bean;
        }

        public boolean equivalent(AmqpType<?, ?> t) {
            return bean().equivalent(t);
        }

        public static AmqpHeader.AmqpHeaderBuffer create(Encoded<IAmqpList> encoded) {
            if(encoded.isNull()) {
                return null;
            }
            return new AmqpHeader.AmqpHeaderBuffer(encoded);
        }

        public static AmqpHeader.AmqpHeaderBuffer create(DataInput in, AmqpMarshaller marshaller) throws IOException, AmqpEncodingError {
            return create(marshaller.unmarshalAmqpHeader(in));
        }

        public static AmqpHeader.AmqpHeaderBuffer create(Buffer buffer, int offset, AmqpMarshaller marshaller) throws AmqpEncodingError {
            return create(marshaller.decodeAmqpHeader(buffer, offset));
        }
    }
}