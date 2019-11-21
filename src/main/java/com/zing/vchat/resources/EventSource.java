package com.zing.vchat.resources;


import com.zing.vchat.base.StatusCode;
import com.zing.vchat.cache.UsersCache;
import com.zing.vchat.util.AuthorizationUtils;
import org.glassfish.jersey.media.sse.EventOutput;
import org.glassfish.jersey.media.sse.OutboundEvent;
import org.glassfish.jersey.media.sse.SseFeature;

import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Singleton
@Path("eventSource")
public class EventSource {

    /**
     * 提供 SSE 事件输出通道的资源方法
     * @return EventOutput
     */
    @GET
    @Produces(SseFeature.SERVER_SENT_EVENTS)
    public EventOutput getEventSource(@QueryParam("userId") String userId, @QueryParam("token") String token) throws IOException {
        if(!AuthorizationUtils.isPass(userId,token)){
            return null;//Response.status(StatusCode.Forbidden.getCode()).build();
        }
        System.out.println("getting SERVER_SENT_EVENTS");
        EventOutput eventOutput = UsersCache.setEventOutput(userId);
        if (eventOutput == null) return null;
        //eventOutput.write(new OutboundEvent.Builder().name("getEventSource").data("yes").build());
        System.out.println("got SERVER_SENT_EVENTS");
        return eventOutput;
    }


    /**
     * 提供 删除 SSE 事件通道的资源方法
     * @param request HTTP 请求
     */
    @DELETE
    @Consumes({MediaType.APPLICATION_JSON})
    public Response deleteEventOutput(@Context HttpServletRequest request) {
        if(!AuthorizationUtils.isPass(request)){
            return Response.status(StatusCode.Forbidden.getCode()).build();
        }
        UsersCache.deleteEventOutput(request.getHeader("userId"));
        return Response.ok().build();
    }

}
