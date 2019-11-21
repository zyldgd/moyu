package com.zing.moyu.filter;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import java.io.IOException;

public class CrossDomainFilter implements ContainerResponseFilter {
    public CrossDomainFilter() {
        // TODO Auto-generated constructor stub
    }

    /* (non-Javadoc)
     * @see javax.ws.rs.container.ContainerResponseFilter#filter(javax.ws.rs.container.ContainerRequestContext, javax.ws.rs.container.ContainerResponseContext)
     */
    @Override
    public void filter(ContainerRequestContext requestContext,
                       ContainerResponseContext responseContext) throws IOException {

        // 响应头添加了对允许访问的域，* 代表是全部域
        responseContext.getHeaders().add("Access-Control-Allow-Origin", "*");

    }
}
