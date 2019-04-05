/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wl.net.messages;

import java.io.Serializable;

/**
 *
 * @author sulochana
 */
public class Message implements Serializable{
    final MessageType messageType;

    public Message(MessageType messageType) {
        this.messageType = messageType;
    }

    public MessageType getMessageType() {
        return messageType;
    }
    
    public int GetFQID(){
        return messageType.getFQMID();
    }
    
    
}
