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
package com.wl.net.handlers;

import com.wl.net.callback.ClientCallback;
import com.wl.net.handlers.client.SecureClientInitializer;
import com.wl.net.messages.Message;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import java.io.File;
import java.net.InetSocketAddress;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;

/**
 *
 * @author sulochana
 */
public class Client {

    final String remoteServerIpAddress;
    final int remoteServerPort;
    final ClientCallback callback;
    Bootstrap bootstrap;
    Channel channel = null;
    ChannelHandlerContext context = null;
    Client self = this;

    public Client(String host, int port, EventLoopGroup eventLoopGroup, ClientCallback callback)
            throws SSLException {
        this.remoteServerIpAddress = host;
        this.remoteServerPort = port;
        this.callback = callback;

        final SslContext sslCtx = SslContextBuilder.forClient()
                .trustManager(InsecureTrustManagerFactory.INSTANCE)
//                .trustManager((File)null)
                .build();

        this.bootstrap = new Bootstrap();
        this.bootstrap.group(eventLoopGroup);
        this.bootstrap.channel(NioSocketChannel.class);
        this.bootstrap.handler(new SecureClientInitializer(
                sslCtx,
                this.remoteServerIpAddress,
                this.remoteServerPort, this));
    }

    public void Connect() throws InterruptedException {
        ChannelFuture connection = this.bootstrap.connect(remoteServerIpAddress, remoteServerPort);
        this.channel = connection.sync().channel();
        this.channel.pipeline().get(SslHandler.class).handshakeFuture().sync().addListener(new GenericFutureListener<Future<Channel>>() {
                    @Override
                    public void operationComplete(Future<Channel> future) throws Exception {
                        if (future.isSuccess()){
                            callback.OnSSLHandshakeSuccess(self);
                        }else{
                            callback.OnSSLHandshakeFailure(self);
                        }
                    }
                });
    }

    public ClientCallback getCallback() {
        return callback;
    }

    public void setContext(ChannelHandlerContext context) {
        this.context = context;
    }

    public void Send(Message msg) {
        this.context.writeAndFlush(msg);
    }
    
    private SSLEngine getSSLEngine(){
        return this.context.pipeline().get(SslHandler.class).engine();
    }
    
    private SSLSession getSSLSession(){
        return this.getSSLEngine().getSession();
    }
    
    public String getCipherSuite(){
        return this.getSSLSession().getCipherSuite();
    }
    
    public String getProtocol(){
        return this.getSSLSession().getProtocol();
    }
    
    private InetSocketAddress getLocalAddress(){
        return ((InetSocketAddress)this.context.channel().localAddress());
    }
    
    public String getIPAddress(){
        return getLocalAddress().getAddress().getHostAddress();
    }
    
    public int getPort(){
        return getLocalAddress().getPort();
    }
    
    public String getHostName(){
        return getLocalAddress().getHostName();
    }
    
    public boolean isConnected(){
        if (this.context == null){
            return false;
        }
        return this.context.channel().isActive();
    }

}
