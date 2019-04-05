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

import com.wl.net.messages.core.MessageDecoder;
import com.wl.net.messages.core.MessageEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import java.io.File;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLException;

/**
 *
 * @author sulochana
 */
public class NettyClient {
    
    public static String serverIp = "127.0.0.1";
//    public static String serverIp = "35.197.137.197";

    public static void main(String[] args) throws SSLException, InterruptedException {

        EventLoopGroup workerGroup = new NioEventLoopGroup();
//        final SslContext sslCtx = SslContextBuilder.forClient()
//            .keyManager(new File("cert.crt"), new File("private.pem")).build();
        final SslContext sslCtx = SslContextBuilder.forClient()
            .trustManager(new File("cert.crt")).build();
        Bootstrap b = new Bootstrap();
        b.group(workerGroup);
        b.channel(NioSocketChannel.class);
        b.handler(new SecureChatClientInitializer(sslCtx));

        
        System.out.println("ServerIP: " + serverIp);
        b.connect(serverIp, 3000).sync().channel();;
    }
}
