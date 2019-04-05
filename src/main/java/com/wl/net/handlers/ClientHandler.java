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

import com.wl.net.messages.HeartBeatMessage;
import com.wl.net.messages.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 *
 * @author sulochana
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {
 
  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    HeartBeatMessage ts = (HeartBeatMessage) msg;
    ctx.writeAndFlush(ts); //recieved message sent back directly
    System.out.println("HTT : " + (System.currentTimeMillis()- ts.getCreationTime()) + "ms");
  }
 
  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
    // Close the connection when an exception is raised.
    cause.printStackTrace();
    ctx.close();
  }
}
