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
import java.lang.Integer;
import org.apache.activemq.amqp.protocol.marshaller.AmqpEncodingError;
import org.apache.activemq.amqp.protocol.marshaller.AmqpMarshaller;
import org.apache.activemq.amqp.protocol.marshaller.Encoded;
import org.apache.activemq.util.buffer.Buffer;

/**
 * Represents a integer in the range 0 to 2^16 - 1
 */
public interface AmqpUshort extends AmqpType<AmqpUshort.AmqpUshortBean, AmqpUshort.AmqpUshortBuffer> {


    public Integer getValue();

    public static class AmqpUshortBean implements AmqpUshort{

        private AmqpUshortBuffer buffer;
        private AmqpUshortBean bean = this;
        private Integer value;

        protected AmqpUshortBean() {
        }

        public AmqpUshortBean(Integer value) {
            this.value = value;
        }

        public AmqpUshortBean(AmqpUshort.AmqpUshortBean other) {
            this.bean = other;
        }

        public final AmqpUshortBean copy() {
            return bean;
        }

        public final AmqpUshort.AmqpUshortBuffer getBuffer(AmqpMarshaller marshaller) throws AmqpEncodingError{
            if(buffer == null) {
                buffer = new AmqpUshortBuffer(marshaller.encode(this));
            }
            return buffer;
        }

        public final void marshal(DataOutput out, AmqpMarshaller marshaller) throws IOException, AmqpEncodingError{
            getBuffer(marshaller).marshal(out, marshaller);
        }


        public Integer getValue() {
            return bean.value;
        }


        public boolean equals(Object o){
            if(this == o) {
                return true;
            }

            if(o == null || !(o instanceof AmqpUshort)) {
                return false;
            }

            return equivalent((AmqpUshort) o);
        }

        public int hashCode() {
            if(getValue() == null) {
                return AmqpUshort.AmqpUshortBean.class.hashCode();
            }
            return getValue().hashCode();
        }

        public boolean equivalent(AmqpType<?,?> t){
            if(this == t) {
                return true;
            }

            if(t == null || !(t instanceof AmqpUshort)) {
                return false;
            }

            return equivalent((AmqpUshort) t);
        }

        public boolean equivalent(AmqpUshort b) {
            if(b == null) {
                return false;
            }

            if(b.getValue() == null ^ getValue() == null) {
                return false;
            }

            return b.getValue() == null || b.getValue().equals(getValue());
        }
    }

    public static class AmqpUshortBuffer implements AmqpUshort, AmqpBuffer< Integer> {

        private AmqpUshortBean bean;
        protected Encoded<Integer> encoded;

        protected AmqpUshortBuffer() {
        }

        protected AmqpUshortBuffer(Encoded<Integer> encoded) {
            this.encoded = encoded;
        }

        public final Encoded<Integer> getEncoded() throws AmqpEncodingError{
            return encoded;
        }

        public final void marshal(DataOutput out, AmqpMarshaller marshaller) throws IOException, AmqpEncodingError{
            encoded.marshal(out);
        }

        public Integer getValue() {
            return bean().getValue();
        }

        public AmqpUshort.AmqpUshortBuffer getBuffer(AmqpMarshaller marshaller) throws AmqpEncodingError{
            return this;
        }

        protected AmqpUshort bean() {
            if(bean == null) {
                bean = new AmqpUshort.AmqpUshortBean(encoded.getValue());
                bean.buffer = this;
            }
            return bean;
        }

        public boolean equals(Object o){
            return bean().equals(o);
        }

        public int hashCode() {
            return bean().hashCode();
        }

        public boolean equivalent(AmqpType<?, ?> t) {
            return bean().equivalent(t);
        }

        public static AmqpUshort.AmqpUshortBuffer create(Encoded<Integer> encoded) {
            if(encoded.isNull()) {
                return null;
            }
            return new AmqpUshort.AmqpUshortBuffer(encoded);
        }

        public static AmqpUshort.AmqpUshortBuffer create(DataInput in, AmqpMarshaller marshaller) throws IOException, AmqpEncodingError {
            return create(marshaller.unmarshalAmqpUshort(in));
        }

        public static AmqpUshort.AmqpUshortBuffer create(Buffer buffer, int offset, AmqpMarshaller marshaller) throws AmqpEncodingError {
            return create(marshaller.decodeAmqpUshort(buffer, offset));
        }
    }
}