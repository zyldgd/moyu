package com.zing.vchat.resources;


import com.zing.vchat.JsonElement.ContactJson;
import com.zing.vchat.base.StatusCode;
import com.zing.vchat.dao.ContactsDao;
import com.zing.vchat.util.AuthorizationUtils;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.glassfish.jersey.servlet.ServletContainer;

import javax.inject.Singleton;
import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.util.LinkedList;

@Singleton
@Path("fileResource")
public class FileResource{

    /**
     * 获取文件
     *
     * @param request HTTP 请求
     * @return EventOutput
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getFile(@Context HttpServletRequest request) {
        if (!AuthorizationUtils.isPass(request)) {
            return Response.status(StatusCode.Forbidden.getCode()).build();
        }
        String userId = AuthorizationUtils.getUserId(request);
        LinkedList<ContactJson> contactJsons = ContactsDao.getContacts(userId);
        return Response.ok(contactJsons).build();
    }


    /**
     * 上传文件
     *
     * @param request HTTP 请求
     * @return EventOutput
     */
    @PUT
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces({MediaType.APPLICATION_JSON})
    public Response postFile(@Context HttpServletRequest request) {
        if (!AuthorizationUtils.isPass(request)) {
            return Response.status(StatusCode.Forbidden.getCode()).build();
        }
        String userId = AuthorizationUtils.getUserId(request);
        LinkedList<ContactJson> contactJsons = ContactsDao.getContacts(userId);
        return Response.ok(contactJsons).build();
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces({MediaType.APPLICATION_JSON})
    public Response uploadFile(@Context HttpServletRequest request,
            @FormDataParam("file") InputStream fileInputStream,
            @FormDataParam("file") FormDataContentDisposition contentDispositionHeader)
            throws IOException {

        String url = request.getServletContext().getRealPath("/");
        System.out.println(url);
        String rootPath = System.getProperty("user.dir") + "/";
        System.out.println(rootPath);

        String fileName = contentDispositionHeader.getFileName();

        File file = new File(fileName);
        File parent = file.getParentFile();
        //判断目录是否存在，不在创建
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }
        file.createNewFile();

        OutputStream outpuStream = new FileOutputStream(file);
        int read = 0;
        byte[] bytes = new byte[1024];

        while ((read = fileInputStream.read(bytes)) != -1) {
            outpuStream.write(bytes, 0, read);
        }

        outpuStream.flush();
        outpuStream.close();

        fileInputStream.close();

        return Response.status(Response.Status.OK).entity("Upload Success!").build();
    }
}
