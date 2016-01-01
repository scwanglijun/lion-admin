
/*
* Copyright (c)  2014, Newtouch
* All rights reserved. 
*
* $id: PositionServiceImpl.java 9552 2014年12月25日 上午11:25:25 WangLijun$
*/
package com.newtouch.lion.service.system.impl;

import com.newtouch.lion.dao.system.PositionDao;
import com.newtouch.lion.service.AbstractService;
import com.newtouch.lion.service.system.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * Title: 
 * </p>
 * <p>
 * Description: 
 * </p>
 * <p>
 * Copyright: Copyright (c) 2014
 * </p>
 * <p>
 * Company: Newtouch
 * </p>
 * 
 * @author WangLijun
 * @version 1.0
 */
@Service
public class PositionServiceImpl extends AbstractService implements PositionService{
	
	/**岗位信息Dao*/
	@Autowired
	private PositionDao positionDao;
}

	