package com.zing.moyu.resources;

import com.zing.moyu.JsonElement.MessageJson;
import com.zing.moyu.base.StatusCode;
import com.zing.moyu.message.MessageCollector;
import com.zing.moyu.util.AuthorizationUtils;

import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.io.UnsupportedEncodingException;

@Singleton
@Path("messages")
public class Messages {


    /**
     * 获取消息记录（历史消息）
     *
     * @param request HTTP 请求
     * @return EventOutput
     */
    @GET
    @Path("/unread")
    @Consumes("application/json;charset=utf-8")
    public Response getMessageRecords(@Context HttpServletRequest request, @QueryParam("senderId") String senderId, @QueryParam("messageType") String messageType, @QueryParam("number") Integer number) {
        if (!AuthorizationUtils.isPass(request)) {
            return Response.status(StatusCode.Forbidden.getCode()).build();
        }
        return Response.ok().build();
    }


    /**
     * 上传消息
     *
     * @param request HTTP 请求
     * @param message 消息
     * @return 状态码
     */
    @POST
    @Produces("application/json;charset=utf-8")
    @Consumes("application/json;charset=utf-8")
    public Response postMessages(@Context HttpServletRequest request, MessageJson message) throws UnsupportedEncodingException {
        if (!AuthorizationUtils.isPass(request)) {
            return Response.status(StatusCode.Forbidden.getCode()).build();
        }

        String a = message.getContent();
        String c = message.get_content();
        System.out.println(a);
        System.out.println(c);

        MessageCollector.getInstance().putToMessageQueue(message);
        return Response.ok().build();
    }

    /**
     * 删除消息
     *
     * @param request HTTP 请求
     * @param message 消息
     * @return 状态码
     */
    @DELETE
    @Produces("application/json;charset=utf-8")
    @Consumes("application/json;charset=utf-8")
    public Response deleteMessages(@Context HttpServletRequest request, Object message) {
        //TODO 删除消息
        return null;
    }

    /**
     * 撤回消息
     *
     * @param request HTTP 请求
     * @param message 撤回消息的ID, 和消息所在会话的ID
     * @return 状态码
     */
    @PUT
    @Produces("application/json;charset=utf-8")
    @Consumes("application/json;charset=utf-8")
    public Response WithdrawMessages(@Context HttpServletRequest request, Object message) {
        //TODO 撤回消息，并删除消息
        return null;
    }

}
