/*
 * Copyright (c)  2014, NewTouch
 * All rights reserved. 
 *
 * $id: CacheController.java 9552 2014-4-9 上午01:04:34 WangLijun$
 */
package com.newtouch.lion.admin.web.controller.system.cache;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.newtouch.lion.json.JSONParser;
import com.newtouch.lion.model.cache.CacheManagerModel;
import com.newtouch.lion.model.cache.CacheModel;
import com.newtouch.lion.service.cache.ApplicationCacheManager;
import com.newtouch.lion.service.datagrid.DataColumnService;

/**
 * <p>
 * Title: 缓存管理监控类
 * </p>
 * <p>
 * Description: 缓存管理监控类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2014
 * </p>
 * <p>
 * Company: NewTouch
 * </p>
 * 
 * @author WangLijun
 * @version 1.0
 */
@Controller("webCacheController")
@RequestMapping("/system/cache/")
public class CacheController {

	private static final String INDEX_RETRUN = "/system/cache/index";

	private static final String INDEX_LIST_TB = "sys_cachelist_tb";

	@Autowired
	private ApplicationCacheManager applicationCacheManager;
	@Autowired
	private DataColumnService dataColumnService;

	/** 缓存首页 */
	@RequestMapping("/index")
	public String index(Model model) {
		CacheManagerModel cacheManagerModel = applicationCacheManager
				.getCacheManager();
		// 缓存信息
		model.addAttribute("cacheManagerModel", cacheManagerModel);
		return INDEX_RETRUN;
	}

	/** 缓存管理列表 */
	@RequestMapping("/lists")
	@ResponseBody
	public String list() {
		CacheManagerModel cacheManagerModel = applicationCacheManager
				.getCaches();
		List<CacheModel> caches = cacheManagerModel.getCacheModels();
		Set<String> properties = dataColumnService
				.doFindColumnsByTableId(INDEX_LIST_TB);
		return JSONParser.toJSONDataGridString(caches, properties);
	}
}
