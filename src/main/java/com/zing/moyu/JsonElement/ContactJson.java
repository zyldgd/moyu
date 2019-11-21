package com.zing.moyu.JsonElement;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@XmlRootElement(name = "ContactJson")
public class ContactJson {

    @XmlElement(name = "contactId")
    private String contactId;

    @XmlElement(name = "peerId")
    private String peerId;

    @XmlElement(name = "type")
    private String type;

    @XmlElement(name = "remarkName")
    private String remarkName;

    public ContactJson() {
    }

    public ContactJson(String contactId, String peerId, String type, String remarkName) {
        this.contactId = contactId;
        this.peerId = peerId;
        this.type = type;
        this.remarkName = remarkName;
    }

    @Override
    public String toString() {
        return String.format("contactId:%-10s   peerId:%-10s   type:%-10s   remarkName:%-10s", contactId, peerId, type, remarkName);
    }
}
