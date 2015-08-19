package com.example.vanteo89.model;

import com.example.vanteo89.demoparse.UserList;

import org.w3c.dom.ProcessingInstruction;

import java.util.Date;

/**
 * Created by vanteo89 on 17/08/2015.
 */
public class Conversation {
    public static final int STATUS_SENDING=0;
    public static final int STATUS_SENT=1;
    public static final int STATUS_FAILED=2;
    private String msg;
    private int status=STATUS_SENT;
    private Date date;
    private String sender;

    public Conversation(String msg,Date date,String sender) {
        this.msg = msg;
        this.sender = sender;
        this.date = date;
    }

    public Conversation() {
    }
public boolean isSent(){
    return UserList.parseUser.getUsername().equals(sender);
}
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSender() {

        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
