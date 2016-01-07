package com.newtouch.lion.web.shiro.listener;

import com.google.common.eventbus.Subscribe;
import com.newtouch.lion.web.shiro.chain.ShiroFilterChainDefinitionManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class ShiroResourceListener {

    private static final Logger logger=LoggerFactory.getLogger(ShiroResourceListener.class);

    @Autowired
    private ShiroFilterChainDefinitionManager shiroFilterChainDefinitionManager;

    @Subscribe
    public void listenerResouce(Resource resource){
        logger.info("更新权限列表");
        shiroFilterChainDefinitionManager.updatePermission();
    }
}
