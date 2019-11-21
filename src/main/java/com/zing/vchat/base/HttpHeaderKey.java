package com.zing.vchat.base;

public enum HttpHeaderKey {
    USER_NAME("username"),
    USER_ID("userId"),
    USER_PASSWORD("password"),
    USER_TOKEN("token");

    private String Key;

    HttpHeaderKey(String Key){
        this.Key = Key;
    }

    private String getKey() {
        return Key;
    }

    @Override
    public String toString() {
        return getKey();
    }
}
