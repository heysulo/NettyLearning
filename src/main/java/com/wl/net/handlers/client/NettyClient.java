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

import com.wl.net.callback.ClientCallback;
import com.wl.net.handlers.Client;
import com.wl.net.messages.HeartBeatMessage;
import com.wl.net.messages.Message;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLException;

/**
 *
 * @author sulochana
 */
public class NettyClient implements ClientCallback{
    
    public static String serverIp = "127.0.0.1";
//    public static String serverIp = "35.197.137.197";

    public static void main(String[] args){

        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Client client = new Client(serverIp, 3000, workerGroup, new NettyClient());
            client.Connect();
        } catch (SSLException ex) {
            Logger.getLogger(NettyClient.class.getName()).log(Level.SEVERE, "SSLException", ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(NettyClient.class.getName()).log(Level.SEVERE, "InterruptedException", ex);
            workerGroup.shutdownGracefully();            
        } catch (Exception ex){
            Logger.getLogger(NettyClient.class.getName()).log(Level.SEVERE, "Exception", ex);
            workerGroup.shutdownGracefully();
        }
    }

    @Override
    public void OnConnect(Client client) {
        System.out.println("OnConnect");  
    }

    @Override
    public void OnDisconnect(Client client) {
        System.out.println("OnDisconnect");
        System.out.println("isConnected: " + client.isConnected());
    }

    @Override
    public void OnMessage(Client client, Message msg) {
        System.out.println("OnMessage");
        client.Send(msg);
        HeartBeatMessage ts = (HeartBeatMessage) msg;
        System.out.println("HTT : " + (System.currentTimeMillis()- ts.getCreationTime()) + "ms");
    }

    @Override
    public void OnError(Client client, Throwable cause) {
        System.out.println("OnError");
    }

    @Override
    public void OnEvent(Client client, Object event) {
        System.out.println("OnEvent");
    }

    @Override
    public void OnSSLHandshakeSuccess(Client client) {
        System.out.println("OnSSLHandshakeSuccess");
        System.out.println("IP: " + client.getIPAddress() + " Port: " + client.getPort());
        System.out.println("Cipher: " + client.getCipherSuite()+ " Protocol: " + client.getProtocol());
        System.out.println("isConnected: " + client.isConnected());
    }

    @Override
    public void OnSSLHandshakeFailure(Client client) {
        System.out.println("OnSSLHandshakeFailure");
    }
}
