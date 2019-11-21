package com.zing.vchat.JsonElement;

import com.zing.vchat.base.Token;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@XmlRootElement(name = "TokenJson")
public class TokenJson {

    @XmlElement(name = "userId")
    private String userId;

    @XmlElement(name = "token")
    private String token;


    public TokenJson(String userId, Token token) {
        this.userId = userId;
        this.token = token.getToken().toString();
    }

    public TokenJson(String userId, String token) {
        this.userId = userId;
        this.token = token;
    }

    @Override
    public String toString() {
        return "userId:" + userId + " token:" + token;
    }
}