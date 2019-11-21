package com.zing.vchat.resources;

import com.zing.vchat.JsonElement.TokenJson;
import com.zing.vchat.JsonElement.UserJson;
import com.zing.vchat.base.HttpHeaderKey;
import com.zing.vchat.base.StatusCode;
import com.zing.vchat.base.Token;
import com.zing.vchat.cache.UsersCache;
import com.zing.vchat.dao.UsersDao;
import com.zing.vchat.util.AuthorizationUtils;

import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.Calendar;
import java.util.Date;

@Singleton
@Path("login")
public class Login {

    @GET
    @Path("/test")
    @Produces({MediaType.APPLICATION_JSON})
    public Response login(@Context HttpServletRequest request) {
        if (!AuthorizationUtils.isPass(request)) {
            return Response.status(StatusCode.Forbidden.getCode()).build();
        }
        return Response.ok().build();
    }


    @DELETE
    @Path("/token")
    @Produces({MediaType.APPLICATION_JSON})
    public Response deleteToken(@Context HttpServletRequest request) {
        if (!AuthorizationUtils.isPass(request)) {
            return Response.status(StatusCode.Forbidden.getCode()).build();
        }
        String userId = request.getHeader(HttpHeaderKey.USER_ID.toString());
        UsersCache.deleteToken(userId);
        return Response.ok().build();
    }


    @GET
    @Path("/token")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getToken(@Context HttpServletRequest request) {
        String username = request.getHeader(HttpHeaderKey.USER_NAME.toString());
        if (AuthorizationUtils.verify(request)) {
            UserJson userJson = UsersDao.queryByName(username);
            UsersCache.crateUserCacheInfo(userJson);
            //Response.ResponseBuilder response = Response.ok();
            TokenJson tokenJson = new TokenJson(userJson.getUserId(), UsersCache.getToken(userJson.getUserId()).getToken().toString());
            return Response.ok(tokenJson).build();
            /*
                Response.ResponseBuilder response = Response.ok()
                        .cookie(NewCookie.valueOf("userId=" + userId))
                        .cookie(NewCookie.valueOf("token="+ tokenJson.getToken()))
                        .contentLocation(URI.create("/"))
                        .expires(Calendar.getInstance().getTime());
                return response.build();
            */

        } else {
            return Response.status(StatusCode.Forbidden.getCode()).build();
        }
    }
}




