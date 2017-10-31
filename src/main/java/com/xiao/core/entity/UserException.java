package com.xiao.core.entity;

/**
 * UserException : 用户自定义异常
 *
 * @author XiaoZhang
 * @since 2017-09-27 18:12
 */
public class UserException extends RuntimeException {

    /**
     * 异常发生时间
     */
    private long date = System.currentTimeMillis();

    public long getDate() {
        return date;
    }
}
