package com.zing.moyu.JsonElement;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@XmlRootElement(name = "UserJson")
public class UserJson {

    @XmlElement(name = "userId")
    private String userId;

    @XmlElement(name = "username")
    private String username;

    @XmlElement(name = "password")
    private String password;

    @XmlElement(name = "nickname")
    private String nickname;

    @XmlElement(name = "email")
    private String email;

    @XmlElement(name = "avatarPath")
    private String avatarPath;

    @XmlElement(name = "remark")
    private String remark;

    @XmlElement(name = "grade")
    private Integer grade;

    /**
     * 账号状态：0：注销  1：激活
     */
    @XmlElement(name = "active")
    private Boolean active;

    public UserJson(){

    }

    public UserJson(String username, String nickname) {
        this.username = username;
        this.nickname = nickname;
        this.password = "******";
        this.grade = 1;
        this.active = true;
    }

    public String getUserId() {
        return userId;
    }


    @Override
    public String toString() {
        return "userId:" + userId +  "  username:" + username +"  password:" + password + "  nickname:" + nickname;
    }
}