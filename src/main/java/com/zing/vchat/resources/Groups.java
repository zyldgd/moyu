package com.zing.vchat.resources;

import com.zing.vchat.JsonElement.GroupJson;
import com.zing.vchat.base.StatusCode;
import com.zing.vchat.util.AuthorizationUtils;

import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Singleton
@Path("groups")
public class Groups {

    /**
     * 获取群信息
     *
     * @param request HTTP 请求
     * @return EventOutput
     */
    @GET
    @Path("{groupId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getGroup(@Context HttpServletRequest request, @PathParam("groupId") String groupId) {
        if (!AuthorizationUtils.isPass(request)) {
            return Response.status(StatusCode.Forbidden.getCode()).build();
        }
        // TODO 返回ID为groupId的群信息
        return Response.ok().build();
    }


    /**
     * 新建群
     *
     * @param request HTTP 请求
     * @return EventOutput
     */
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response postGroup(@Context HttpServletRequest request, GroupJson groupJson) {
        if (!AuthorizationUtils.isPass(request)) {
            return Response.status(StatusCode.Forbidden.getCode()).build();
        }
        // TODO 返回ID为groupId的群信息
        return Response.ok().build();
    }


    /**
     * 解散群
     *
     * @param request HTTP 请求
     * @return EventOutput
     */
    @DELETE
    @Path("{groupId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response deleteGroup(@Context HttpServletRequest request, @PathParam("groupId") String groupId) {
        if (!AuthorizationUtils.isPass(request)) {
            return Response.status(StatusCode.Forbidden.getCode()).build();
        }
        // TODO 返回ID为groupId的群信息
        return Response.ok().build();
    }


    /**
     * 修改群信息
     *
     * @param request HTTP 请求
     * @return EventOutput
     */
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response putGroup(@Context HttpServletRequest request, GroupJson groupJson) {
        if (!AuthorizationUtils.isPass(request)) {
            return Response.status(StatusCode.Forbidden.getCode()).build();
        }
        // TODO 修改群昵称，图片，公告；群成员通过Members接口实现。
        return Response.ok().build();
    }

}
