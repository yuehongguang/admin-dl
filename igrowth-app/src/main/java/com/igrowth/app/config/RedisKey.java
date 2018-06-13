package com.igrowth.app.config;

/**
 * <pre>
 *    author  : lpf
 *    time    : 2017/10/2418:56
 *    desc    : 输入描述
 * </pre>
 */
public class RedisKey {

    /**
     * 	存入对象为hash
     *  某个功能实体对象是否被fav.  eg:  igarticlesUp:articleId:accountId (igarticlesDown:articleId:accountId)
     */
    public static final String FAV_MODULE_ID_KEY = "%s:%s:%s";


    /**
     * 	存入对象为hash
     *  某个功能实体对象是否被fav.  eg:  igarticlesUpAll:articleId (igarticlesDownAll:articleId)
     */
    public static final String FAV_ARTICLE_ID_ALL_KEY = "%s:%s";
}
