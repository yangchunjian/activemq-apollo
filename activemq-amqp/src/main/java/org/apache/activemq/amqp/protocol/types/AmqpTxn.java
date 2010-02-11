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
import java.util.Iterator;
import org.apache.activemq.amqp.protocol.AmqpCommand;
import org.apache.activemq.amqp.protocol.AmqpCommandHandler;
import org.apache.activemq.amqp.protocol.marshaller.AmqpEncodingError;
import org.apache.activemq.amqp.protocol.marshaller.AmqpMarshaller;
import org.apache.activemq.amqp.protocol.marshaller.Encoded;
import org.apache.activemq.amqp.protocol.types.IAmqpList;
import org.apache.activemq.util.buffer.Buffer;

/**
 * Represents a mark transaction boundaries
 * <p>
 * This command is called when the work done on behalf a transaction branch finishes or needs
 * to be suspended. If neither fail nor suspend are specified then the portion of work has
 * completed successfully. When a Session is closed then the currently associated transaction
 * branches MUST be marked rollback-only.
 * </p>
 */
public interface AmqpTxn extends AmqpList, AmqpCommand {



    /**
     * options map
     */
    public void setOptions(AmqpOptions options);

    /**
     * options map
     */
    public AmqpOptions getOptions();

    /**
     * Failure flag
     * <p>
     * If set, indicates that this portion of work has failed; otherwise this portion of work
     * has completed successfully. An implementation MAY elect to roll a transaction back if
     * this failure notification is received. Should an implementation elect to implement this
     * behavior, and this bit is set, then then the transaction branch SHOULD be marked as
     * rollback-only and the end result SHOULD have the xa-rbrollback status set.
     * </p>
     */
    public void setFail(Boolean fail);

    /**
     * Failure flag
     * <p>
     * If set, indicates that this portion of work has failed; otherwise this portion of work
     * has completed successfully. An implementation MAY elect to roll a transaction back if
     * this failure notification is received. Should an implementation elect to implement this
     * behavior, and this bit is set, then then the transaction branch SHOULD be marked as
     * rollback-only and the end result SHOULD have the xa-rbrollback status set.
     * </p>
     */
    public void setFail(AmqpBoolean fail);

    /**
     * Failure flag
     * <p>
     * If set, indicates that this portion of work has failed; otherwise this portion of work
     * has completed successfully. An implementation MAY elect to roll a transaction back if
     * this failure notification is received. Should an implementation elect to implement this
     * behavior, and this bit is set, then then the transaction branch SHOULD be marked as
     * rollback-only and the end result SHOULD have the xa-rbrollback status set.
     * </p>
     */
    public Boolean getFail();

    /**
     * Temporary suspension flag
     * <p>
     * Indicates that the transaction branch is temporarily suspended in an incomplete state.
     * The transaction context is in a suspended state and must be resumed via the enlist
     * command with resume specified.
     * </p>
     */
    public void setSuspend(Boolean suspend);

    /**
     * Temporary suspension flag
     * <p>
     * Indicates that the transaction branch is temporarily suspended in an incomplete state.
     * The transaction context is in a suspended state and must be resumed via the enlist
     * command with resume specified.
     * </p>
     */
    public void setSuspend(AmqpBoolean suspend);

    /**
     * Temporary suspension flag
     * <p>
     * Indicates that the transaction branch is temporarily suspended in an incomplete state.
     * The transaction context is in a suspended state and must be resumed via the enlist
     * command with resume specified.
     * </p>
     */
    public Boolean getSuspend();

    public static class AmqpTxnBean implements AmqpTxn{

        private AmqpTxnBuffer buffer;
        private AmqpTxnBean bean = this;
        private AmqpOptions options;
        private AmqpBoolean fail;
        private AmqpBoolean suspend;

        public AmqpTxnBean() {
        }

        public AmqpTxnBean(IAmqpList value) {
            //TODO we should defer decoding of the described type:
            for(int i = 0; i < value.getListCount(); i++) {
                set(i, value.get(i));
            }
        }

        public AmqpTxnBean(AmqpTxn.AmqpTxnBean other) {
            this.bean = other;
        }

        public final AmqpTxnBean copy() {
            return new AmqpTxn.AmqpTxnBean(bean);
        }

        public final void handle(AmqpCommandHandler handler) throws Exception {
            handler.handleTxn(this);
        }

        public final AmqpTxn.AmqpTxnBuffer getBuffer(AmqpMarshaller marshaller) throws AmqpEncodingError{
            if(buffer == null) {
                buffer = new AmqpTxnBuffer(marshaller.encode(this));
            }
            return buffer;
        }

        public final void marshal(DataOutput out, AmqpMarshaller marshaller) throws IOException, AmqpEncodingError{
            getBuffer(marshaller).marshal(out, marshaller);
        }


        public final void setOptions(AmqpOptions options) {
            copyCheck();
            bean.options = options;
        }

        public final AmqpOptions getOptions() {
            return bean.options;
        }

        public void setFail(Boolean fail) {
            setFail(new AmqpBoolean.AmqpBooleanBean(fail));
        }


        public final void setFail(AmqpBoolean fail) {
            copyCheck();
            bean.fail = fail;
        }

        public final Boolean getFail() {
            return bean.fail.getValue();
        }

        public void setSuspend(Boolean suspend) {
            setSuspend(new AmqpBoolean.AmqpBooleanBean(suspend));
        }


        public final void setSuspend(AmqpBoolean suspend) {
            copyCheck();
            bean.suspend = suspend;
        }

        public final Boolean getSuspend() {
            return bean.suspend.getValue();
        }

        public void set(int index, AmqpType<?, ?> value) {
            switch(index) {
            case 0: {
                setOptions((AmqpOptions) value);
                break;
            }
            case 1: {
                setFail((AmqpBoolean) value);
                break;
            }
            case 2: {
                setSuspend((AmqpBoolean) value);
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
                return bean.options;
            }
            case 1: {
                return bean.fail;
            }
            case 2: {
                return bean.suspend;
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

        private final void copy(AmqpTxn.AmqpTxnBean other) {
            this.options= other.options;
            this.fail= other.fail;
            this.suspend= other.suspend;
            bean = this;
        }

        public boolean equivalent(AmqpType<?,?> t){
            if(this == t) {
                return true;
            }

            if(t == null || !(t instanceof AmqpTxn)) {
                return false;
            }

            return equivalent((AmqpTxn) t);
        }

        public boolean equivalent(AmqpTxn b) {

            if(b.getOptions() == null ^ getOptions() == null) {
                return false;
            }
            if(b.getOptions() != null && !b.getOptions().equals(getOptions())){ 
                return false;
            }

            if(b.getFail() == null ^ getFail() == null) {
                return false;
            }
            if(b.getFail() != null && !b.getFail().equals(getFail())){ 
                return false;
            }

            if(b.getSuspend() == null ^ getSuspend() == null) {
                return false;
            }
            if(b.getSuspend() != null && !b.getSuspend().equals(getSuspend())){ 
                return false;
            }
            return true;
        }
    }

    public static class AmqpTxnBuffer extends AmqpList.AmqpListBuffer implements AmqpTxn{

        private AmqpTxnBean bean;

        protected AmqpTxnBuffer(Encoded<IAmqpList> encoded) {
            super(encoded);
        }

        public final void setOptions(AmqpOptions options) {
            bean().setOptions(options);
        }

        public final AmqpOptions getOptions() {
            return bean().getOptions();
        }

    public void setFail(Boolean fail) {
            bean().setFail(fail);
        }

        public final void setFail(AmqpBoolean fail) {
            bean().setFail(fail);
        }

        public final Boolean getFail() {
            return bean().getFail();
        }

    public void setSuspend(Boolean suspend) {
            bean().setSuspend(suspend);
        }

        public final void setSuspend(AmqpBoolean suspend) {
            bean().setSuspend(suspend);
        }

        public final Boolean getSuspend() {
            return bean().getSuspend();
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

        public AmqpTxn.AmqpTxnBuffer getBuffer(AmqpMarshaller marshaller) throws AmqpEncodingError{
            return this;
        }

        protected AmqpTxn bean() {
            if(bean == null) {
                bean = new AmqpTxn.AmqpTxnBean(encoded.getValue());
                bean.buffer = this;
            }
            return bean;
        }

        public final void handle(AmqpCommandHandler handler) throws Exception {
            handler.handleTxn(this);
        }

        public boolean equivalent(AmqpType<?, ?> t) {
            return bean().equivalent(t);
        }

        public static AmqpTxn.AmqpTxnBuffer create(Encoded<IAmqpList> encoded) {
            if(encoded.isNull()) {
                return null;
            }
            return new AmqpTxn.AmqpTxnBuffer(encoded);
        }

        public static AmqpTxn.AmqpTxnBuffer create(DataInput in, AmqpMarshaller marshaller) throws IOException, AmqpEncodingError {
            return create(marshaller.unmarshalAmqpTxn(in));
        }

        public static AmqpTxn.AmqpTxnBuffer create(Buffer buffer, int offset, AmqpMarshaller marshaller) throws AmqpEncodingError {
            return create(marshaller.decodeAmqpTxn(buffer, offset));
        }
    }
}