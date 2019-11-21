package com.zing.moyu.filter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ResourceMappingFilter implements Filter {
    private List<String> dirs = new ArrayList<String>();//资源文件夹
    private String mapping;
    private ServletContext application;
    private String debug = "true";

    @Override
    public void destroy() {
        // TODO Auto-generated method stub
    }

    @Override
    public void doFilter(ServletRequest arg0, ServletResponse arg1,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) arg0;
        HttpServletResponse response = (HttpServletResponse) arg1;
        String uri = request.getRequestURI();
        if (uri.indexOf(request.getContextPath() + mapping) != 0) {
            chain.doFilter(arg0, arg1);
            return;
        }
        int start = uri.lastIndexOf(mapping) + mapping.length();
        int end = uri.length();
        String name = uri.substring(start, end);
        for (String patt : dirs) {
            String resPath = patt + name;
            String realPath = application.getRealPath(resPath);
            if (new File(realPath).exists()) {
                RequestDispatcher disp = request.getRequestDispatcher(resPath);
                disp.forward(arg0, arg1);
                return;
            }
        }
        chain.doFilter(arg0, arg1);
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
        System.out.println(config.getInitParameter("location"));
        System.out.println("=================");

        String[] arr = config.getInitParameter("location").split(",");
        this.mapping = config.getInitParameter("mapping");
        this.debug = config.getInitParameter("debug");
        application = config.getServletContext();
        for (String str : arr) {
            if (str.endsWith("/*")) {
                String res = str.substring(0, str.length() - 2);
                String real = application.getRealPath(res);
                File file = new File(real);
                readDirectory(res, file, file);
            } else if (str.endsWith("/")) {
                dirs.add(str.substring(0, str.length() - 1));
            } else {
                dirs.add(str);
            }
        }
        for (String dr : dirs) {
            log("ResourceMapping: " + mapping + " --> " + dr);
        }
    }

    private void readDirectory(String dir, File src, File file) {
        if (file.isDirectory()) {
            String res = dir + file.getAbsolutePath().substring(src.getAbsolutePath().length());
            dirs.add(res);
            for (File fo : file.listFiles()) {
                readDirectory(dir, src, fo);
            }
        }
    }

    private void log(String info) {
        if (debug.equals("true")) {
            System.out.println(info);
        }
    }
}