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
package org.apache.activemq.amqp.protocol;

import org.apache.activemq.amqp.protocol.types.AmqpUnlink;
import org.apache.activemq.amqp.protocol.types.AmqpFlow;
import org.apache.activemq.amqp.protocol.types.AmqpRelink;
import org.apache.activemq.amqp.protocol.types.AmqpClose;
import org.apache.activemq.amqp.protocol.types.AmqpOpen;
import org.apache.activemq.amqp.protocol.types.AmqpTransfer;
import org.apache.activemq.amqp.protocol.types.AmqpDetach;
import org.apache.activemq.amqp.protocol.types.AmqpEnlist;
import org.apache.activemq.amqp.protocol.types.AmqpDrain;
import org.apache.activemq.amqp.protocol.types.AmqpBar;
import org.apache.activemq.amqp.protocol.types.AmqpNoop;
import org.apache.activemq.amqp.protocol.types.AmqpLink;
import org.apache.activemq.amqp.protocol.types.AmqpTxn;
import org.apache.activemq.amqp.protocol.types.AmqpDisposition;
import org.apache.activemq.amqp.protocol.types.AmqpAttach;
public interface AmqpCommandHandler {

    public void handleUnlink(AmqpUnlink unlink) throws Exception;

    public void handleFlow(AmqpFlow flow) throws Exception;

    public void handleRelink(AmqpRelink relink) throws Exception;

    public void handleClose(AmqpClose close) throws Exception;

    public void handleOpen(AmqpOpen open) throws Exception;

    public void handleTransfer(AmqpTransfer transfer) throws Exception;

    public void handleDetach(AmqpDetach detach) throws Exception;

    public void handleEnlist(AmqpEnlist enlist) throws Exception;

    public void handleDrain(AmqpDrain drain) throws Exception;

    public void handleBar(AmqpBar bar) throws Exception;

    public void handleNoop(AmqpNoop noop) throws Exception;

    public void handleLink(AmqpLink link) throws Exception;

    public void handleTxn(AmqpTxn txn) throws Exception;

    public void handleDisposition(AmqpDisposition disposition) throws Exception;

    public void handleAttach(AmqpAttach attach) throws Exception;
}