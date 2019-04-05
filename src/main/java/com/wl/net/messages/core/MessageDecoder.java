/*
 * Copyright (C) 2019 sulochana.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */
package com.wl.net.messages.core;

import com.wl.net.messages.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.io.ByteArrayInputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;
import java.util.List;

/**
 *
 * @author sulochana
 */
public class MessageDecoder extends ByteToMessageDecoder{

    @Override
    protected void decode(ChannelHandlerContext chc, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < Integer.BYTES){
            System.out.println("Waiting for first byte");
            return;
        }
        
        final int messageLength = in.readInt();
        System.out.println("Message Size:" + messageLength);
        if (in.readableBytes() + Integer.BYTES < messageLength) {
          System.out.println("Waiting :" + in.readableBytes() + "/" +messageLength);
          in.resetReaderIndex();
          return;
        }

        byte [] ba = new byte[messageLength];
        in.readBytes(ba, 0, messageLength);
        
        Message msg;
        ByteArrayInputStream bis = new ByteArrayInputStream(ba);
        ObjectInput oin = new ObjectInputStream(bis);
        msg = (Message)oin.readObject();
        out.add(msg);
    }
    
}
