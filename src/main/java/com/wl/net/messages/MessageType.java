/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wl.net.messages;

/**
 *
 * @author sulochana
 */
public enum MessageType {
    HEARTBEAT(0);
    
    private final int FQMID;
    
    private MessageType(int FQMID) {
        this.FQMID = FQMID;
    }

    public int getFQMID() {
        return FQMID;
    }
    
    
}
