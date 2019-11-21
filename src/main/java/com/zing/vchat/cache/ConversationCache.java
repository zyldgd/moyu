package com.zing.vchat.cache;

import com.zing.vchat.JsonElement.ConversationJson;
import org.glassfish.jersey.media.sse.SseBroadcaster;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConversationCache {

    /**
     * 缓存每个会话的成员信息
     */
    private static Map<String, LinkedList<UserCacheInfo>> userCacheInfoList = new ConcurrentHashMap<>();

    /**
     * 缓存每个会话的会话信息
     */
    private static Map<String, ConversationJson> conversationCache = new ConcurrentHashMap<>();

    /**
     *缓存每个会话的EventSource信息
     */
    private static Map<String, SseBroadcaster> BroadcasterCache = new ConcurrentHashMap<>();


    /*********************************************************************************/

    public static LinkedList<UserCacheInfo> getUserCacheInfoList(String conversationId) {
        return userCacheInfoList.get(conversationId);
    }

    public static void addUserCacheInfoToList(String conversationId, UserCacheInfo userCacheInfo){
        userCacheInfoList.get(conversationId).add(userCacheInfo);
    }

    public static void removeUserCacheInfoFromList(String conversationId, UserCacheInfo userCacheInfo){
        userCacheInfoList.get(conversationId).remove(userCacheInfo);
    }

    public static void deleteUserCacheInfoList(String conversationId) {
        userCacheInfoList.remove(conversationId);
    }

    /*********************************************************************************/

    public static ConversationJson getConversation(String conversationId) {
        return conversationCache.get(conversationId);
    }

    public static void addConversation(ConversationJson conversationJson) {
        conversationCache.put(conversationJson.getId(), conversationJson);
    }

    public static void deleteConversation(String conversationId) {
        conversationCache.remove(conversationId);
    }

    /*********************************************************************************/

    public static void addEventOutput(String conversationId, String userId) {
        BroadcasterCache.get(conversationId).add(UsersCache.getEventOutput(userId));
    }

    public static void deleteEventOutput(String conversationId, String userId) {
        BroadcasterCache.get(conversationId).remove(UsersCache.getEventOutput(userId));
    }

    public static SseBroadcaster getBroadcaster(String conversationId) {
        return BroadcasterCache.get(conversationId);
    }


}
