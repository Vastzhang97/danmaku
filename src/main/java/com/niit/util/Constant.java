package com.niit.util;

public interface Constant {

    static final int SUCCEEDCODE = 1;//成功返回码
    static final int FAILEDCODE = 0;//失败返回码

    static final String JWT_ID = "jwt";
    static final String JWT_SECRET = "hong1mu2zhi3ruan4jian5";
    static final int JWT_TTL = 60 * 60 * 1000;  //millisecond
    static final int JWT_REFRESH_INTERVAL = 55 * 60 * 1000;  //millisecond
    static final int JWT_REFRESH_TTL = 12 * 60 * 60 * 1000;  //millisecond

}
