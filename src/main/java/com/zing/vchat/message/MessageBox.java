package com.zing.vchat.message;

import com.zing.vchat.JsonElement.MessageJson;
import org.glassfish.jersey.media.sse.EventOutput;
import org.glassfish.jersey.media.sse.OutboundEvent;

import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

public class MessageBox {
    private EventOutput eventOutput;
    private LinkedBlockingQueue<MessageJson> messagesCache = new LinkedBlockingQueue<>(50);
    private LinkedBlockingQueue<MessageJson> messageQueue = new LinkedBlockingQueue<>(50);
    private static OutboundEvent outboundEvent = new OutboundEvent.Builder().name("message").data(1).build();
    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式

    public MessageBox() {
        ProcessMessages();
    }

    @SuppressWarnings("InfiniteLoopStatement")
    private void ProcessMessages() {
        System.out.println("[INFO] MessageBox ProcessMessages!");
        new Thread(() -> {
            while (true) {
                try {
                    MessageJson messageJson = messageQueue.take();
                    if (null != this.eventOutput && !this.eventOutput.isClosed()) {
                        this.eventOutput.write(new OutboundEvent.Builder().name("message").data(MessageJson.class, messageJson).build());
                    }
                } catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 采用 当收到消息后通过 <strong>EventSource</strong> 直接转发用户
     *
     * @param message 将要处理的消息
     */
    public void ProcessMessage(MessageJson message) {
        //new Thread(() -> {
        System.out.println("[INFO] MessageBox start--------------!");
        if (null != this.eventOutput && !this.eventOutput.isClosed()) {
            try {
                String now = df.format(new Date()); //获取当前系统时间
                OutboundEvent.Builder outboundEvent = new OutboundEvent.Builder();
                outboundEvent.id(now).name("message").mediaType(MediaType.APPLICATION_JSON_TYPE).data(message);
                this.eventOutput.write(outboundEvent.build());
            } catch (IOException | NullPointerException e) {
                e.printStackTrace();
            }
        }

        //}).start();
    }

    /**
     * 采用 当收到消息后通过 <strong>EventSource</strong>提醒用户有新的消息
     *
     * @param message 将要处理的消息
     */
    public void ProcessMessage2(MessageJson message) {
        new Thread(() -> {
            if (messagesCache.remainingCapacity() == 0) {
                messagesCache.remove();
            }
            messagesCache.add(message);
            if (null != this.eventOutput) {
                try {
                    eventOutput.write(outboundEvent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public EventOutput getEventOutput() {
        return eventOutput;
    }

    public EventOutput setEventOutput() {
        this.deleteEventOutput();
        this.eventOutput = new EventOutput();
        return this.eventOutput;
    }

    public void deleteEventOutput() {
        if (this.eventOutput != null && !this.eventOutput.isClosed()) {
            try {
                this.eventOutput.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.eventOutput = null;
    }

    public void putToMessageQueue(MessageJson message) {
        if (messageQueue.remainingCapacity() == 0) {
            messageQueue.remove();
        }
        messageQueue.add(message);
    }
}
