package com.zing.vchat.cache;

import com.zing.vchat.JsonElement.ContactJson;
import com.zing.vchat.JsonElement.UserJson;
import com.zing.vchat.base.Token;
import com.zing.vchat.message.MessageBox;
import org.glassfish.jersey.media.sse.EventOutput;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserCacheInfo {
    private UserJson userJson;
    private Token token;
    private MessageBox massageBox;

    public UserCacheInfo(UserJson userJson) {
        this.userJson = userJson;
        this.token = new Token();
        this.massageBox = new MessageBox();
        System.out.println("[INFO] "+ userJson.getUsername() + " UserCacheInfo Created!");
    }

    public void deleteEventOutput() {
        this.massageBox.deleteEventOutput();
    }

    public EventOutput getEventOutput() {
        return this.massageBox.getEventOutput();
    }

    public EventOutput setEventOutput() {
        return this.massageBox.setEventOutput();
    }

    public void deleteToken() {
        token = null;
    }

    public Token getToken() {
        return this.token;
    }

    public Token setToken() {
        this.token = new Token();
        return this.token;
    }

    public MessageBox getMassageBox() {
        return this.massageBox;
    }

    public UserJson getUserJson() {
        return userJson;
    }

    public void setUserJson(UserJson userJson) {
        this.userJson = userJson;
    }

}
