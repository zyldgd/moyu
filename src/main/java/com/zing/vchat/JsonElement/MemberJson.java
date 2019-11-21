package com.zing.vchat.JsonElement;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@XmlRootElement(name = "ContactJson")
public class MemberJson {

    @XmlElement(name = "memberId")
    private String memberId;

    @XmlElement(name = "role")
    private String role;

    @XmlElement(name = "nickname")
    private String nickname;

}
