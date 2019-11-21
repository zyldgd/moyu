package com.zing.vchat.JsonElement;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.LinkedList;


@Getter
@Setter
@XmlRootElement(name = "ConversationJson")
public class ConversationJson {
    @XmlElement(name = "id")
    private String id;

    @XmlElement(name = "name")
    private String name;

    @XmlElement(name = "members")
    private LinkedList<UserJson> members;

}
