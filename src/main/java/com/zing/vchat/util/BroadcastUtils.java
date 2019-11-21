package com.zing.vchat.util;

import org.glassfish.jersey.media.sse.EventOutput;
import org.glassfish.jersey.media.sse.OutboundEvent;
import org.glassfish.jersey.media.sse.SseBroadcaster;

import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

public class BroadcastUtils {
    private static SseBroadcaster broadcaster = new SseBroadcaster();
    private static OutboundEvent.Builder eventBuilder = new OutboundEvent.Builder();
    private static Map<String, EventOutput> eventOutputMap = new HashMap<String, EventOutput>(); // 存储连接数

    public static EventOutput getEventOutput(String token) {
        return BroadcastUtils.eventOutputMap.get(token);
    }

    public static EventOutput addEventOutput(String token) {
        EventOutput eventOutput = new EventOutput();
        if (!BroadcastUtils.eventOutputMap.containsKey(token)) {
            BroadcastUtils.eventOutputMap.put(token, eventOutput);
            BroadcastUtils.broadcaster.add(eventOutput);
            System.out.println("add token:" + token + " into EventOutputMap!");
            return eventOutput;
        } else {
            BroadcastUtils.broadcaster.remove(BroadcastUtils.eventOutputMap.get(token));
            BroadcastUtils.eventOutputMap.put(token, eventOutput);
            BroadcastUtils.broadcaster.add(eventOutput);
            System.out.println("add token:" + token + " into EventOutputMap again!");
            return eventOutput;
        }
    }

    public static void removeEventOutput(String token) {
        if (BroadcastUtils.eventOutputMap.containsKey(token)) {
            BroadcastUtils.broadcaster.remove(BroadcastUtils.eventOutputMap.get(token));
            BroadcastUtils.eventOutputMap.remove(token);
            System.out.println("remove token:" + token + " from EventOutputMap!");
        }
    }


    public static void broadcast(MediaType mediaType, Object object) {
        try {
            OutboundEvent event = BroadcastUtils.eventBuilder.name("message")
                    .mediaType(mediaType)
                    .data(object.getClass(), object)
                    .build();
            BroadcastUtils.broadcaster.broadcast(event);
        } catch (Exception e) {
            System.out.println("e:" + e);
        }

    }


}
