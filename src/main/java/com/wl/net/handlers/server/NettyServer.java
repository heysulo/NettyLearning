/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wl.net.handlers.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.ssl.ClientAuth;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.SslProvider;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import java.io.IOException;
import java.security.cert.CertificateException;

/**
 *
 * @author sulochana
 */
public class NettyServer {

    public static void main(String[] args) throws IOException, InterruptedException, CertificateException {
        
        SelfSignedCertificate ssc = new SelfSignedCertificate();
        SslContext sslCtx = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey())
                .trustManager(InsecureTrustManagerFactory.INSTANCE)
                .sslProvider(SslProvider.JDK)
                .clientAuth(ClientAuth.OPTIONAL)
                .build();
        
        NioEventLoopGroup boosGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(boosGroup, workerGroup);
        bootstrap.channel(NioServerSocketChannel.class);
        bootstrap.childHandler(new SecureChatServerInitializer(sslCtx));

        bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
        System.out.println("Running");
        bootstrap.bind(3000).sync();
        System.out.println("Running");
    }

}
