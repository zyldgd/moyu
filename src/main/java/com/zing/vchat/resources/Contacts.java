package com.zing.vchat.resources;

import com.zing.vchat.JsonElement.ContactJson;
import com.zing.vchat.base.StatusCode;
import com.zing.vchat.dao.ContactsDao;
import com.zing.vchat.dao.GroupDao;
import com.zing.vchat.dao.UsersDao;
import com.zing.vchat.util.AuthorizationUtils;

import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.LinkedList;

@Singleton
@Path("contacts")
public class Contacts {

    /**
     * 获取通讯录
     *
     * @param request HTTP 请求
     * @return EventOutput
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getContacts(@Context HttpServletRequest request) {
        if (!AuthorizationUtils.isPass(request)) {
            return Response.status(StatusCode.Forbidden.getCode()).build();
        }
        String userId = AuthorizationUtils.getUserId(request);
        LinkedList<ContactJson> contactJsons = ContactsDao.getContacts(userId);
        return Response.ok(contactJsons).build();
    }


    /**
     * 添加好友
     *
     * @param request HTTP 请求
     */
    @POST
    @Path("/friend")
    @Produces({MediaType.APPLICATION_JSON})
    public Response addFriend(@Context HttpServletRequest request, @QueryParam("userId") String userId) {
        if (!AuthorizationUtils.isPass(request)) {
            return Response.status(StatusCode.Forbidden.getCode()).build();
        }

        if (!UsersDao.userIdExist(userId)){
            return Response.status(StatusCode.NotAcceptable.getCode()).build();
        }
        // TODO  申请添加好友

        String selfId = AuthorizationUtils.getUserId(request);
        ContactsDao.insert(selfId, new ContactJson(null, userId, "friend", null));
        ContactsDao.insert(userId, new ContactJson(null, selfId, "friend", null));
        return Response.ok().build();
    }


    /**
     * 加入群聊
     *
     * @param request HTTP 请求
     */
    @POST
    @Path("/group")
    @Produces({MediaType.APPLICATION_JSON})
    public Response addGroup(@Context HttpServletRequest request, @QueryParam("groupId") String groupId) {
        if (!AuthorizationUtils.isPass(request)) {
            return Response.status(StatusCode.Forbidden.getCode()).build();
        }
        String selfId = AuthorizationUtils.getUserId(request);
        ContactsDao.insert(selfId, new ContactJson(null, groupId, "group", null));
        GroupDao.insertMember(groupId, selfId);
        // TODO  添加通信录信息
        return Response.ok().build();
    }


    /**
     * 解除好友
     *
     * @param request HTTP 请求
     */
    @DELETE
    @Path("/friend")
    @Produces({MediaType.APPLICATION_JSON})
    public Response deleteFriend(@Context HttpServletRequest request, @QueryParam("userId") String userId) {
        if (!AuthorizationUtils.isPass(request)) {
            return Response.status(StatusCode.Forbidden.getCode()).build();
        }
        // TODO  解除好友
        return Response.ok().build();
    }


    /**
     * 退出群聊
     *
     * @param request HTTP 请求
     */
    @DELETE
    @Path("/group")
    @Produces({MediaType.APPLICATION_JSON})
    public Response deleteGroup(@Context HttpServletRequest request, @QueryParam("groupId") String groupId) {
        if (!AuthorizationUtils.isPass(request)) {
            return Response.status(StatusCode.Forbidden.getCode()).build();
        }
        // TODO  退出群聊
        return Response.ok().build();
    }


    /**
     * 修改通信录信息
     *
     * @param request HTTP 请求
     * @return EventOutput
     */
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response putContact(@Context HttpServletRequest request, ContactJson contactJson) {
        if (!AuthorizationUtils.isPass(request)) {
            return Response.status(StatusCode.Forbidden.getCode()).build();
        }
        // TODO  修改通信录信息
        return Response.ok().build();
    }
}
