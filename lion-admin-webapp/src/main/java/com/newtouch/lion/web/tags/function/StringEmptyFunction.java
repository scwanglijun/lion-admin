/*
 * Copyright (c)  2014, NewTouch
 * All rights reserved. 
 *
 * $id: StringEmptyMehtod.java 9552 2014-2-19 下午03:07:13 WangLijun$
 */
package com.newtouch.lion.web.tags.function;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

/**
 * <p>
 * Title:函数判断字符是否为空
 * </p>
 * <p>
 * Description:函数判断字符是否为空
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
public class StringEmptyFunction implements TemplateMethodModelEx {
 

	@SuppressWarnings("rawtypes")
	@Override
	public Boolean exec(List args) throws TemplateModelException {
		if (args.size() != 1) {
			throw new TemplateModelException("Wrong arguments");
		}
		return StringUtils.isEmpty(String.valueOf(args.get(0)));
	}

}
