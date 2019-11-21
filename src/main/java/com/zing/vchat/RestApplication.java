package com.zing.vchat;

import com.zing.vchat.JsonElement.TokenJson;
import com.zing.vchat.cache.UsersCache;
import com.zing.vchat.filter.CrossDomainFilter;
import com.zing.vchat.filter.EncodingFilter;
import com.zing.vchat.filter.ResourceMappingFilter;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;


public class RestApplication extends ResourceConfig {
    public RestApplication() {
        // 资源类所在的包路径
        packages("com.zing.vchat.resources");
        System.out.println("com.zing.vchat.resources UP!!");
        // 注册 MultiPart
        register(MultiPartFeature.class);

        // 注册CORS过滤器
        register(CrossDomainFilter.class);

        // 注册编码过滤器
        register(EncodingFilter.class);

        // 注册编码过滤器
        register(ResourceMappingFilter.class);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //UsersCache.init();
    }
}
