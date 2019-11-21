package com.zing.vchat.resources;


import com.zing.vchat.JsonElement.ConversationJson;
import com.zing.vchat.JsonElement.MessageJson;

import com.zing.vchat.base.StatusCode;
import com.zing.vchat.cache.ConversationCache;
import com.zing.vchat.util.AuthorizationUtils;

import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Singleton
@Path("conversation")
public class Conversation {

    /**
     * 获取会话列表
     * @param request HTTP 请求
     * @return 状态码
     */
    @GET
    @Path("/{conversationId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getConversations(@Context HttpServletRequest request, @PathParam("conversationId") String conversationId) {
        if(!AuthorizationUtils.isPass(request)){
            return Response.status(StatusCode.Forbidden.getCode()).build();
        }
        ConversationJson conversationJson = ConversationCache.getConversation(conversationId);
        if (null == conversationJson){
            return Response.status(StatusCode.NoContent.getCode()).build();
        }

        return Response.ok(conversationJson).build();
    }


    /**
     * 新建会话
     * @param request HTTP 请求
     * @param message 消息
     * @return 状态码
     */
    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response newConversation(@Context HttpServletRequest request, MessageJson message) {
        return Response.ok().build();
    }

    /**
     * 删除会话
     * @param request HTTP 请求
     * @param message 消息
     * @return 状态码
     */
    @DELETE
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response deleteConversation(@Context HttpServletRequest request, MessageJson message) {
        return Response.ok().build();
    }

    /**
     * 修改会话(退出、邀请、修改)
     * @param request HTTP 请求
     * @param message 消息
     * @return 状态码
     */
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response modifyConversation(@Context HttpServletRequest request, MessageJson message) {
        return Response.ok().build();
    }
}
