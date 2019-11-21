package com.zing.moyu.message;


import com.zing.moyu.JsonElement.MessageJson;
import com.zing.moyu.cache.UsersCache;
import com.zing.moyu.dao.GroupDao;

import javax.inject.Singleton;
import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingQueue;

@Singleton
public class MessageDistributor extends Thread {
    private LinkedBlockingQueue<MessageJson> MessageQueue = new LinkedBlockingQueue<>(10000);

    private static MessageDistributor messageDistributor = new MessageDistributor();

    public static MessageDistributor getInstance() {
        return messageDistributor;
    }

    public void putToMessageQueue(MessageJson messageJson) {
        MessageQueue.add(messageJson);
    }

    private MessageDistributor() {
        this.start();
    }

    private void ProcessMessages() {
        try {
            MessageBox messageBox = null;
            MessageJson messageJson = MessageQueue.take();
            switch (messageJson.getContactType()) {
                case "friend":
                    messageBox = UsersCache.getMessageBox(messageJson.getTo());
                    if (messageBox == null) return;
                    messageBox.ProcessMessage(messageJson);
                    break;
                case "group":
                    LinkedList<String> memberIds = GroupDao.getAllMemberId(messageJson.getTo());
                    if (memberIds == null) return;
                    for (String memberId : memberIds) {
                        messageBox = UsersCache.getMessageBox(memberId);
                        if (null != messageBox) {
                            messageBox.ProcessMessage(messageJson);
                        }
                    }
                    break;
                default:
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    @SuppressWarnings("InfiniteLoopStatement")
    public void run() {
        System.out.println("[INFO] MessageDistributor started!");
        while (true) {
            ProcessMessages();
        }
    }

}
