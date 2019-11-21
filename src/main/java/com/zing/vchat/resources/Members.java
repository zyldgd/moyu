package com.zing.vchat.resources;

import com.zing.vchat.base.StatusCode;
import com.zing.vchat.util.AuthorizationUtils;

import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Singleton
@Path("members")
public class Members {


    /**
     * 获取成员列表
     *
     * @param request HTTP 请求
     * @return EventOutput
     */
    @GET
    @Path("{groupId}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response getMembers(@Context HttpServletRequest request, @PathParam("groupId") String groupId) {
        if (!AuthorizationUtils.isPass(request)) {
            return Response.status(StatusCode.Forbidden.getCode()).build();
        }
        // TODO 返回ID为groupId的群成员信息
        return Response.ok().build();
    }


    /**
     * 添加成员
     *
     * @param request HTTP 请求
     * @return EventOutput
     */
    @POST
    @Path("{groupId}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response postMembers(@Context HttpServletRequest request, @PathParam("groupId") String groupId) {
        if (!AuthorizationUtils.isPass(request)) {
            return Response.status(StatusCode.Forbidden.getCode()).build();
        }
        // TODO 向ID为groupId的群添加新成员
        return Response.ok().build();
    }


    /**
     * 删除成员
     *
     * @param request HTTP 请求
     * @return EventOutput
     */
    @DELETE
    @Path("{groupId}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response deleteMembers(@Context HttpServletRequest request, @PathParam("groupId") String groupId) {
        if (!AuthorizationUtils.isPass(request)) {
            return Response.status(StatusCode.Forbidden.getCode()).build();
        }
        // TODO 删除成员信息
        return Response.ok().build();
    }
}
