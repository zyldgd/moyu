package com.zing.moyu.util;

import com.zing.moyu.base.HttpHeaderKey;
import com.zing.moyu.cache.UsersCache;
import com.zing.moyu.dao.UsersDao;

import javax.servlet.http.HttpServletRequest;

public class AuthorizationUtils {


    public static boolean isPass(HttpServletRequest request) {
        String userId = request.getHeader(HttpHeaderKey.USER_ID.toString());
        String token_str = request.getHeader(HttpHeaderKey.USER_TOKEN.toString());
        if (null == UsersCache.getToken(userId)) return false;
        return UsersCache.getToken(userId).isValid(token_str);
    }

    public static boolean isPass(String userId, String token) {
        return UsersCache.getToken(userId).isValid(token);
    }

    public static String getUserId(HttpServletRequest request) {
        return request.getHeader(HttpHeaderKey.USER_ID.toString());
    }

    public static String getTokenStr(HttpServletRequest request) {
        return request.getHeader(HttpHeaderKey.USER_TOKEN.toString());
    }

    public static boolean verify(HttpServletRequest request) {
        String username = request.getHeader(HttpHeaderKey.USER_NAME.toString());
        String password = request.getHeader(HttpHeaderKey.USER_PASSWORD.toString());
        return UsersDao.verifyBuName(username, password);
    }
}
