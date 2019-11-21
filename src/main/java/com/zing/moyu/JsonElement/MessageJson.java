package com.zing.moyu.JsonElement;


import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@Getter
@Setter
@XmlRootElement(name = "MessageJson")
public class MessageJson {

    /**
     * 消息Hash
     */
    @XmlElement(name = "messageHash")
    private String messageHash;

    /**
     * 消息内容
     */
    @XmlElement(name = "content")
    private String content;

    /**
     * 日期
     */
    @XmlElement(name = "date")
    private String date;

    /**
     * 时间
     */
    @XmlElement(name = "time")
    private String time;

    /**
     * 发送者ID
     */
    @XmlElement(name = "from")
    private String from;

    /**
     * 接受者ID
     */
    @XmlElement(name = "to")
    private String to;

    /**
     *
     */
    @XmlElement(name = "contactType")
    private String contactType;


    public String get_content(){
        String a = this.content;
        return a;
    }

    @Override
    public String toString() {
        return String.format("HASH:%s    DATE:%s     FORM:%s    TO:%s     CONTENT:%s", messageHash, date, from, to, content);
    }

}
