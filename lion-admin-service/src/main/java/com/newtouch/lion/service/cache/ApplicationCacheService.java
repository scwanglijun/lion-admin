package com.newtouch.lion.service.cache;

/**
 * Created by wanglijun on 16/1/6.
 */
public interface ApplicationCacheService {

    public  void clear(String ehcacheName);

    public void  clear(String ehcacheName,String cacheName);
}
