package com.zing.moyu.base;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class Token {
    private UUID token;
    private Date expiration;

    public Token(String str) {
        this.token = UUID.fromString(str);
        this.extendExpiration();
    }

    public Token() {
        this.token = UUID.randomUUID();
        this.extendExpiration();
    }

    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
    }

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }

    public void extendExpiration() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + 5);
        this.expiration = calendar.getTime();
    }

    public boolean isValid() {
        if (null == token) return false;
        return this.expiration.after(new Date());
    }

    public boolean isValid(String token_str) {
        if (null == this.token || null == token_str) return false;
        if (!this.token.toString().equals(token_str)) return false;
        return this.expiration.after(new Date());
    }

    @Override
    public String toString() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return String.format("token: %s , valid: %6s ,  expiration: %s", this.token.toString(), this.isValid(), df.format(this.expiration));
    }
}
