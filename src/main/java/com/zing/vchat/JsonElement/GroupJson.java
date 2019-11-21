package com.zing.vchat.JsonElement;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@XmlRootElement(name = "GroupJson")
public class GroupJson {

    @XmlElement(name = "groupId")
    private String groupId;

    @XmlElement(name = "groupName")
    private String groupName;

    @XmlElement(name = "date")
    private String date;

    @XmlElement(name = "announcement")
    private String announcement;

    @XmlElement(name = "imagePath")
    private String imagePath;

}
