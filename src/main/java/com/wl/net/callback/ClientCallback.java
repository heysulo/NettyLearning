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
package com.wl.net.callback;

import com.wl.net.handlers.Client;
import com.wl.net.messages.Message;

/**
 *
 * @author sulochana
 */
public abstract interface ClientCallback {
    public void OnConnect(Client client);
    public void OnDisconnect(Client client);
    public void OnMessage(Client client, Message msg);
    public void OnError(Client client, Throwable cause);
    public void OnEvent(Client client, Object event);  
    public void OnSSLHandshakeSuccess(Client client);  
    public void OnSSLHandshakeFailure(Client client);  
}
