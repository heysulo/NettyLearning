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
import io.netty.handler.codec.MessageToByteEncoder;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;

/**
 *
 * @author sulochana
 */
public class MessageEncoder extends MessageToByteEncoder<Message>{

    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) 
            throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(outStream);
        oos.writeObject(msg);
        oos.flush();
        
        out.writeInt(outStream.toByteArray().length);
        out.writeBytes(outStream.toByteArray());        
    }
        
}
