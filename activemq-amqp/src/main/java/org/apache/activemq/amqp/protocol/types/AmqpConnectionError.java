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
import java.lang.String;
import java.util.HashMap;
import java.util.Iterator;
import org.apache.activemq.amqp.protocol.marshaller.AmqpEncodingError;
import org.apache.activemq.amqp.protocol.marshaller.AmqpMarshaller;
import org.apache.activemq.amqp.protocol.marshaller.Encoded;
import org.apache.activemq.amqp.protocol.types.IAmqpList;
import org.apache.activemq.util.buffer.Buffer;

/**
 * Represents a details of a Connection error
 */
public interface AmqpConnectionError extends AmqpList {



    /**
     * Connection close code
     * <p>
     * A numeric code indicating the reason for the Connection closure.
     * </p>
     */
    public void setErrorCode(AmqpConnectionErrorCode errorCode);

    /**
     * Connection close code
     * <p>
     * A numeric code indicating the reason for the Connection closure.
     * </p>
     */
    public AmqpConnectionErrorCode getErrorCode();

    /**
     * descriptive text about the exception
     * <p>
     * This text supplies any supplementary details not indicated by the error-code.
     * This text can be logged as an aid to resolving issues.
     * </p>
     */
    public void setDescription(String description);

    /**
     * descriptive text about the exception
     * <p>
     * This text supplies any supplementary details not indicated by the error-code.
     * This text can be logged as an aid to resolving issues.
     * </p>
     */
    public void setDescription(AmqpString description);

    /**
     * descriptive text about the exception
     * <p>
     * This text supplies any supplementary details not indicated by the error-code.
     * This text can be logged as an aid to resolving issues.
     * </p>
     */
    public String getDescription();

    /**
     * map to carry additional information about the error
     */
    public void setErrorInfo(HashMap<AmqpType<?,?>, AmqpType<?,?>> errorInfo);

    /**
     * map to carry additional information about the error
     */
    public void setErrorInfo(AmqpMap errorInfo);

    /**
     * map to carry additional information about the error
     */
    public HashMap<AmqpType<?,?>, AmqpType<?,?>> getErrorInfo();

    public static class AmqpConnectionErrorBean implements AmqpConnectionError{

        private AmqpConnectionErrorBuffer buffer;
        private AmqpConnectionErrorBean bean = this;
        private AmqpConnectionErrorCode errorCode;
        private AmqpString description;
        private AmqpMap errorInfo;

        public AmqpConnectionErrorBean() {
        }

        public AmqpConnectionErrorBean(IAmqpList value) {
            //TODO we should defer decoding of the described type:
            for(int i = 0; i < value.getListCount(); i++) {
                set(i, value.get(i));
            }
        }

        public AmqpConnectionErrorBean(AmqpConnectionError.AmqpConnectionErrorBean other) {
            this.bean = other;
        }

        public final AmqpConnectionErrorBean copy() {
            return new AmqpConnectionError.AmqpConnectionErrorBean(bean);
        }

        public final AmqpConnectionError.AmqpConnectionErrorBuffer getBuffer(AmqpMarshaller marshaller) throws AmqpEncodingError{
            if(buffer == null) {
                buffer = new AmqpConnectionErrorBuffer(marshaller.encode(this));
            }
            return buffer;
        }

        public final void marshal(DataOutput out, AmqpMarshaller marshaller) throws IOException, AmqpEncodingError{
            getBuffer(marshaller).marshal(out, marshaller);
        }


        public final void setErrorCode(AmqpConnectionErrorCode errorCode) {
            copyCheck();
            bean.errorCode = errorCode;
        }

        public final AmqpConnectionErrorCode getErrorCode() {
            return bean.errorCode;
        }

        public void setDescription(String description) {
            setDescription(new AmqpString.AmqpStringBean(description));
        }


        public final void setDescription(AmqpString description) {
            copyCheck();
            bean.description = description;
        }

        public final String getDescription() {
            return bean.description.getValue();
        }

        public void setErrorInfo(HashMap<AmqpType<?,?>, AmqpType<?,?>> errorInfo) {
            setErrorInfo(new AmqpMap.AmqpMapBean(errorInfo));
        }


        public final void setErrorInfo(AmqpMap errorInfo) {
            copyCheck();
            bean.errorInfo = errorInfo;
        }

        public final HashMap<AmqpType<?,?>, AmqpType<?,?>> getErrorInfo() {
            return bean.errorInfo.getValue();
        }

        public void set(int index, AmqpType<?, ?> value) {
            switch(index) {
            case 0: {
                setErrorCode(AmqpConnectionErrorCode.get((AmqpUshort)value));
                break;
            }
            case 1: {
                setDescription((AmqpString) value);
                break;
            }
            case 2: {
                setErrorInfo((AmqpMap) value);
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
                if(errorCode == null) {
                    return null;
                }
                return errorCode.getValue();
            }
            case 1: {
                return bean.description;
            }
            case 2: {
                return bean.errorInfo;
            }
            default : {
                throw new IndexOutOfBoundsException(String.valueOf(index));
            }
            }
        }

        public int getListCount() {
            return 3;
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

        private final void copy(AmqpConnectionError.AmqpConnectionErrorBean other) {
            this.errorCode= other.errorCode;
            this.description= other.description;
            this.errorInfo= other.errorInfo;
            bean = this;
        }

        public boolean equivalent(AmqpType<?,?> t){
            if(this == t) {
                return true;
            }

            if(t == null || !(t instanceof AmqpConnectionError)) {
                return false;
            }

            return equivalent((AmqpConnectionError) t);
        }

        public boolean equivalent(AmqpConnectionError b) {

            if(b.getErrorCode() == null ^ getErrorCode() == null) {
                return false;
            }
            if(b.getErrorCode() != null && !b.getErrorCode().equals(getErrorCode())){ 
                return false;
            }

            if(b.getDescription() == null ^ getDescription() == null) {
                return false;
            }
            if(b.getDescription() != null && !b.getDescription().equals(getDescription())){ 
                return false;
            }

            if(b.getErrorInfo() == null ^ getErrorInfo() == null) {
                return false;
            }
            if(b.getErrorInfo() != null && !b.getErrorInfo().equals(getErrorInfo())){ 
                return false;
            }
            return true;
        }
    }

    public static class AmqpConnectionErrorBuffer extends AmqpList.AmqpListBuffer implements AmqpConnectionError{

        private AmqpConnectionErrorBean bean;

        protected AmqpConnectionErrorBuffer(Encoded<IAmqpList> encoded) {
            super(encoded);
        }

        public final void setErrorCode(AmqpConnectionErrorCode errorCode) {
            bean().setErrorCode(errorCode);
        }

        public final AmqpConnectionErrorCode getErrorCode() {
            return bean().getErrorCode();
        }

    public void setDescription(String description) {
            bean().setDescription(description);
        }

        public final void setDescription(AmqpString description) {
            bean().setDescription(description);
        }

        public final String getDescription() {
            return bean().getDescription();
        }

    public void setErrorInfo(HashMap<AmqpType<?,?>, AmqpType<?,?>> errorInfo) {
            bean().setErrorInfo(errorInfo);
        }

        public final void setErrorInfo(AmqpMap errorInfo) {
            bean().setErrorInfo(errorInfo);
        }

        public final HashMap<AmqpType<?,?>, AmqpType<?,?>> getErrorInfo() {
            return bean().getErrorInfo();
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

        public AmqpConnectionError.AmqpConnectionErrorBuffer getBuffer(AmqpMarshaller marshaller) throws AmqpEncodingError{
            return this;
        }

        protected AmqpConnectionError bean() {
            if(bean == null) {
                bean = new AmqpConnectionError.AmqpConnectionErrorBean(encoded.getValue());
                bean.buffer = this;
            }
            return bean;
        }

        public boolean equivalent(AmqpType<?, ?> t) {
            return bean().equivalent(t);
        }

        public static AmqpConnectionError.AmqpConnectionErrorBuffer create(Encoded<IAmqpList> encoded) {
            if(encoded.isNull()) {
                return null;
            }
            return new AmqpConnectionError.AmqpConnectionErrorBuffer(encoded);
        }

        public static AmqpConnectionError.AmqpConnectionErrorBuffer create(DataInput in, AmqpMarshaller marshaller) throws IOException, AmqpEncodingError {
            return create(marshaller.unmarshalAmqpConnectionError(in));
        }

        public static AmqpConnectionError.AmqpConnectionErrorBuffer create(Buffer buffer, int offset, AmqpMarshaller marshaller) throws AmqpEncodingError {
            return create(marshaller.decodeAmqpConnectionError(buffer, offset));
        }
    }
}