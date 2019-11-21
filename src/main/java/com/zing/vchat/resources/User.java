package com.zing.vchat.resources;


import com.zing.vchat.JsonElement.UserJson;
import com.zing.vchat.base.StatusCode;
import com.zing.vchat.cache.UsersCache;
import com.zing.vchat.dao.ContactsDao;
import com.zing.vchat.dao.MessagesDao;
import com.zing.vchat.dao.UsersDao;
import com.zing.vchat.util.AuthorizationUtils;

import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@Singleton
@Path("user")
public class User {

    
    /**
     * 获取/查询用户信息
     * @param request HTTP 请求
     * @return 状态码
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getUser(@Context HttpServletRequest request, @QueryParam("userId") String userId) {
        if (!AuthorizationUtils.isPass(request)){
            return Response.status(StatusCode.Forbidden.getCode()).build();
        }
        UserJson userJson = UsersDao.queryById(userId);
        if (null == userJson) return Response.status(StatusCode.NotFound.getCode()).build();
        return Response.ok(userJson).build();
    }


    /**
     * 获取/查询多个用户信息
     * @param request HTTP 请求
     * @return 状态码
     */
    @POST
    @Path("/users")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response getUsers(@Context HttpServletRequest request, List<String> userIdList) {
        if (!AuthorizationUtils.isPass(request)){
            return Response.status(StatusCode.Forbidden.getCode()).build();
        }
        return Response.ok(UsersDao.querysById(userIdList)).build();
    }


    /**
     * 注册新用户
     * @param userJson 用户信息
     * @return 状态码
     */
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response newUser(UserJson userJson) {
        if (UsersDao.exist(userJson.getUsername())){
            return Response.status(StatusCode.Conflict.getCode()).build();
        }
        addNewUser(userJson);
        return Response.ok().build();
    }


    /**
     * 修改用户信息
     * @param request HTTP 请求
     * @return 状态码
     */
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response modifyUser(@Context HttpServletRequest request, UserJson userJson) {
        // TODO 修改用户信息
        return Response.ok().build();
    }


    /**
     * 注销用户
     * @param request HTTP 请求
     * @return 状态码
     */
    @DELETE
    public Response deleteUser(@Context HttpServletRequest request) {
        // TODO 注销用户
        return Response.ok().build();
    }


    private boolean addNewUser(UserJson userJson){
        try {
            userJson.setActive(true);
            userJson.setGrade(1);
            UsersDao.insert(userJson);
            String userId = UsersDao.queryByName(userJson.getUsername()).getUserId();
            MessagesDao.createMessageTable(userId);
            ContactsDao.createContactsTable(userId);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
