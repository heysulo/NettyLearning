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
package com.wl.net.handlers.client;

import com.wl.net.handlers.Client;
import com.wl.net.messages.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 *
 * @author sulochana
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {

    private final Client attachedClient;
    
    public ClientHandler(Client client) {
        this.attachedClient = client;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        attachedClient.setContext(ctx);
        this.attachedClient.getCallback().OnMessage(attachedClient, (Message)msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        attachedClient.setContext(ctx);
        this.attachedClient.getCallback().OnError(attachedClient, cause);
    }
    
    @Override
    public void channelActive(ChannelHandlerContext ctx){
        attachedClient.setContext(ctx);
        this.attachedClient.getCallback().OnConnect(attachedClient);
    }
    
    @Override
    public void channelInactive(ChannelHandlerContext ctx){
        attachedClient.setContext(ctx);
        this.attachedClient.getCallback().OnDisconnect(attachedClient);
    }
}
