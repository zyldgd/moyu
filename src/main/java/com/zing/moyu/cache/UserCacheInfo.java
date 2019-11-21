package com.zing.moyu.cache;

import com.zing.moyu.JsonElement.UserJson;
import com.zing.moyu.base.Token;
import com.zing.moyu.message.MessageBox;
import org.glassfish.jersey.media.sse.EventOutput;

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
