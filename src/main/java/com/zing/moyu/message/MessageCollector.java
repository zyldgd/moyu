package com.zing.moyu.message;

import com.zing.moyu.JsonElement.MessageJson;
import com.zing.moyu.dao.MessagesDao;

import javax.inject.Singleton;
import java.util.concurrent.LinkedBlockingQueue;

@Singleton
public class MessageCollector extends Thread {
    private LinkedBlockingQueue<MessageJson> MessageQueue = new LinkedBlockingQueue<>(10000);
    private static MessageCollector messageCollector = new MessageCollector();

    public static MessageCollector getInstance() {
        return messageCollector;
    }

    public void putToMessageQueue(MessageJson messageJson) {
        MessageQueue.add(messageJson);
        System.out.println("[INFO] Get new message: " + messageJson);
    }

    private MessageCollector() {
        this.start();
    }

    private void ProcessMessages() {
        try {
            MessageJson messageJson = MessageQueue.take();
            MessagesDao.insert(messageJson);
            MessageDistributor.getInstance().putToMessageQueue(messageJson);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    @SuppressWarnings("InfiniteLoopStatement")
    public void run() {
        System.out.println("[INFO] MessageCollector started!");
        while (true) {
            ProcessMessages();
        }
    }
}
