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
import com.wl.net.messages.core.MessageDecoder;
import com.wl.net.messages.core.MessageEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.ssl.SslContext;

/**
 *
 * @author sulochana
 */
public class SecureClientInitializer extends ChannelInitializer<SocketChannel> {

    private final SslContext sslCtx;
    private final String host;
    private final int port;
    private final Client client;

    public SecureClientInitializer(SslContext sslCtx, String host, int port, Client client) {
        this.sslCtx = sslCtx;
        this.host = host;
        this.port = port;
        this.client = client;
    }

    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(sslCtx.newHandler(ch.alloc(), this.host, this.port));
        pipeline.addLast(new MessageEncoder());
        pipeline.addLast(new MessageDecoder());
        pipeline.addLast(new ClientHandler(this.client));
    }
}
