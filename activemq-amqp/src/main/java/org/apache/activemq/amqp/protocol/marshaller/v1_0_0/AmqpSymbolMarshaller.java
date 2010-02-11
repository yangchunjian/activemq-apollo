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
package org.apache.activemq.amqp.protocol.marshaller.v1_0_0;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.lang.String;
import org.apache.activemq.amqp.protocol.marshaller.AmqpEncodingError;
import org.apache.activemq.amqp.protocol.marshaller.AmqpVersion;
import org.apache.activemq.amqp.protocol.marshaller.Encoded;
import org.apache.activemq.amqp.protocol.marshaller.Encoding;
import org.apache.activemq.amqp.protocol.marshaller.UnexpectedTypeException;
import org.apache.activemq.amqp.protocol.marshaller.v1_0_0.Encoder;
import org.apache.activemq.amqp.protocol.marshaller.v1_0_0.Encoder.*;
import org.apache.activemq.amqp.protocol.types.AmqpSymbol;
import org.apache.activemq.util.buffer.Buffer;

public class AmqpSymbolMarshaller {

    private static final Encoder ENCODER = Encoder.SINGLETON;
    private static final Encoded<String> NULL_ENCODED = new Encoder.NullEncoded<String>();

    public static final byte SYM8_FORMAT_CODE = (byte) 0xa3;
    public static final byte SYM32_FORMAT_CODE = (byte) 0xb3;

    public static enum SYMBOL_ENCODING implements Encoding{
        SYM8 (SYM8_FORMAT_CODE), // up to 2^8 - 1 seven bit ASCII characters representing a symbolic value
        SYM32 (SYM32_FORMAT_CODE); // up to 2^32 - 1 seven bit ASCII characters representing a symbolic value

        public final byte FORMAT_CODE;
        public final FormatSubCategory CATEGORY;

        SYMBOL_ENCODING(byte formatCode) {
            this.FORMAT_CODE = formatCode;
            this.CATEGORY = FormatSubCategory.getCategory(formatCode);
        }

        public final byte getEncodingFormatCode() {
            return FORMAT_CODE;
        }

        public final AmqpVersion getEncodingVersion() {
            return AmqpMarshaller.VERSION;
        }

        public static SYMBOL_ENCODING getEncoding(byte formatCode) throws UnexpectedTypeException {
            switch(formatCode) {
            case SYM8_FORMAT_CODE: {
                return SYM8;
            }
            case SYM32_FORMAT_CODE: {
                return SYM32;
            }
            default: {
                throw new UnexpectedTypeException("Unexpected format code for Symbol: " + formatCode);
            }
            }
        }

        static final AmqpSymbolEncoded createEncoded(EncodedBuffer buffer) throws AmqpEncodingError {
            switch(buffer.getEncodingFormatCode()) {
            case SYM8_FORMAT_CODE: {
                return new AmqpSymbolSym8Encoded(buffer);
            }
            case SYM32_FORMAT_CODE: {
                return new AmqpSymbolSym32Encoded(buffer);
            }
            default: {
                throw new UnexpectedTypeException("Unexpected format code for Symbol: " + buffer.getEncodingFormatCode());
            }
            }
        }
        static final AmqpSymbolEncoded createEncoded(byte formatCode, String value) throws AmqpEncodingError {
            switch(formatCode) {
            case SYM8_FORMAT_CODE: {
                return new AmqpSymbolSym8Encoded(value);
            }
            case SYM32_FORMAT_CODE: {
                return new AmqpSymbolSym32Encoded(value);
            }
            default: {
                throw new UnexpectedTypeException("Unexpected format code for Symbol: " + formatCode);
            }
            }
        }
    }
    public static abstract class AmqpSymbolEncoded extends AbstractEncoded <String> {
        public AmqpSymbolEncoded(EncodedBuffer encoded) {
            super(encoded);
        }

        public AmqpSymbolEncoded(byte formatCode, String value) throws AmqpEncodingError {
            super(formatCode, value);
        }
    }

    /**
     * up to 2^8 - 1 seven bit ASCII characters representing a symbolic value
     */
    private static class AmqpSymbolSym8Encoded extends AmqpSymbolEncoded {

        private final SYMBOL_ENCODING encoding = SYMBOL_ENCODING.SYM8;
        public AmqpSymbolSym8Encoded(EncodedBuffer encoded) {
            super(encoded);
        }

        public AmqpSymbolSym8Encoded(String value) throws AmqpEncodingError {
            super(SYMBOL_ENCODING.SYM8.FORMAT_CODE, value);
        }

        protected final int computeDataSize() throws AmqpEncodingError {
            return ENCODER.getEncodedSizeOfSymbol(value, encoding);
        }

        public final void encode(String value, Buffer encoded, int offset) throws AmqpEncodingError {
            ENCODER.encodeSymbolSym8(value, encoded, offset);
        }

        public final String decode(EncodedBuffer encoded) throws AmqpEncodingError {
            return ENCODER.decodeSymbolSym8(encoded.getBuffer(), encoded.getDataOffset(), encoded.getDataSize());
        }

        public final void marshalData(DataOutput out) throws IOException {
            ENCODER.writeSymbolSym8(value, out);
        }

        public final String unmarshalData(DataInput in) throws IOException {
            return ENCODER.readSymbolSym8(getDataSize(), in);
        }
    }

    /**
     * up to 2^32 - 1 seven bit ASCII characters representing a symbolic value
     */
    private static class AmqpSymbolSym32Encoded extends AmqpSymbolEncoded {

        private final SYMBOL_ENCODING encoding = SYMBOL_ENCODING.SYM32;
        public AmqpSymbolSym32Encoded(EncodedBuffer encoded) {
            super(encoded);
        }

        public AmqpSymbolSym32Encoded(String value) throws AmqpEncodingError {
            super(SYMBOL_ENCODING.SYM32.FORMAT_CODE, value);
        }

        protected final int computeDataSize() throws AmqpEncodingError {
            return ENCODER.getEncodedSizeOfSymbol(value, encoding);
        }

        public final void encode(String value, Buffer encoded, int offset) throws AmqpEncodingError {
            ENCODER.encodeSymbolSym32(value, encoded, offset);
        }

        public final String decode(EncodedBuffer encoded) throws AmqpEncodingError {
            return ENCODER.decodeSymbolSym32(encoded.getBuffer(), encoded.getDataOffset(), encoded.getDataSize());
        }

        public final void marshalData(DataOutput out) throws IOException {
            ENCODER.writeSymbolSym32(value, out);
        }

        public final String unmarshalData(DataInput in) throws IOException {
            return ENCODER.readSymbolSym32(getDataSize(), in);
        }
    }


    private static final SYMBOL_ENCODING chooseEncoding(AmqpSymbol val) throws AmqpEncodingError {
        return Encoder.chooseSymbolEncoding(val.getValue());
    }

    private static final SYMBOL_ENCODING chooseEncoding(String val) throws AmqpEncodingError {
        return Encoder.chooseSymbolEncoding(val);
    }

    static final Encoded<String> encode(AmqpSymbol data) throws AmqpEncodingError {
        if(data == null) {
            return NULL_ENCODED;
        }
        return SYMBOL_ENCODING.createEncoded(chooseEncoding(data).FORMAT_CODE, data.getValue());
    }

    static final Encoded<String> createEncoded(Buffer source, int offset) throws AmqpEncodingError {
        return createEncoded(FormatCategory.createBuffer(source, offset));
    }

    static final Encoded<String> createEncoded(String val) throws AmqpEncodingError {
        return SYMBOL_ENCODING.createEncoded(chooseEncoding(val).FORMAT_CODE, val);
    }

    static final Encoded<String> createEncoded(DataInput in) throws IOException, AmqpEncodingError {
        return createEncoded(FormatCategory.createBuffer(in.readByte(), in));
    }

    static final Encoded<String> createEncoded(EncodedBuffer buffer) throws AmqpEncodingError {
        if(buffer.getEncodingFormatCode() == AmqpNullMarshaller.FORMAT_CODE) {
            return NULL_ENCODED;
        }
        return SYMBOL_ENCODING.createEncoded(buffer);
    }
}
