/*
 * Copyright (c)  2014, NewTouch
 * All rights reserved. 
 *
 * $id: ParameterController.java 9552 2014-2-18 下午01:28:48 WangLijun$
 */
package com.newtouch.lion.admin.web.controller.system.parameter;

import com.newtouch.lion.admin.web.model.system.parameter.ParameterVo;
import com.newtouch.lion.common.date.DateUtil;
import com.newtouch.lion.common.file.FileUtil;
import com.newtouch.lion.data.DataTable;
import com.newtouch.lion.model.datagrid.DataGrid;
import com.newtouch.lion.model.system.Parameter;
import com.newtouch.lion.page.PageResult;
import com.newtouch.lion.query.QueryCriteria;
import com.newtouch.lion.service.datagrid.DataColumnService;
import com.newtouch.lion.service.datagrid.DataGridService;
import com.newtouch.lion.service.excel.ExcelExportService;
import com.newtouch.lion.service.system.CodeService;
import com.newtouch.lion.service.system.ParameterService;
import com.newtouch.lion.web.constant.ConstantMessage;
import com.newtouch.lion.web.controller.AbstractController;
import com.newtouch.lion.web.model.QueryDt;
import com.newtouch.lion.web.servlet.view.support.BindMessage;
import com.newtouch.lion.web.servlet.view.support.BindResult;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * Title: 系统参数控制类
 * </p>
 * <p>
 * Description: 系统参数控制类
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
@Controller
@RequestMapping("/system/parameter/")
public class ParameterController extends AbstractController{
	
	private final Logger logger = LoggerFactory.getLogger(super.getClass());

	/** 参数首页 */
	private static final String INDEX_RETURN = "/system/parameter/index";
	/** 默认排序字段名称 */
	private static final String DEFAULT_ORDER_FILED_NAME = "id";
	/**字典类型*/
	private static final String CODE_TYPE="SystemParamter";
	
	@Autowired
	private ParameterService parameterService;
	/**表格行数*/
	@Autowired
	protected DataColumnService dataColumnService;
	/**IM*/
	@Autowired
	private CodeService codeService;
	/**DataGrid表格*/
	@Autowired
	private DataGridService dataGridService;
	/**Excel通用导出*/
	@Autowired
	private ExcelExportService excelExportService;

	@RequestMapping(value = "index")
	public String index(Model model) {
		model.addAttribute("codeType", CODE_TYPE);
		return INDEX_RETURN;
	}

 

	@RequestMapping(value = "edit")
	@ResponseBody
	public ModelAndView edit(
			@Valid @ModelAttribute("parameter") ParameterVo parameterVo,
			Errors errors, ModelAndView modelAndView) {
		modelAndView=this.getJsonView(modelAndView);
		if (!errors.hasErrors() && parameterVo.getId() == null) {
			errors.reject("sys.parameter.form.id.empty");
			modelAndView.addObject(BindMessage.ERRORS_MODEL_KEY, errors);
			return modelAndView;
		}
		Parameter parameter = parameterService.doFindById(parameterVo.getId());
		if (parameter == null) {
			errors.reject("sys.parameter.form.id.empty");
			return modelAndView;
		}
		
		if (!errors.hasErrors()&& this.isExistByNameEn(parameterVo.getNameEn(),parameter.getNameEn())) {
			errors.rejectValue(ParameterVo.NAMEEN,	"sys.parameter.form.nameen.existed.message",new Object[] { parameterVo.getNameEn() }, null);

		}

		if (errors.hasErrors()) {
			modelAndView.addObject(BindMessage.ERRORS_MODEL_KEY, errors);
			return  this.getJsonView(modelAndView);
		}

		BeanUtils.copyProperties(parameterVo, parameter);
		parameterService.doUpdate(parameter);

		Map<String, String> params = new HashMap<String, String>();
		params.put(BindResult.SUCCESS, "sys.parameter.edit.success");
		modelAndView.addObject(BindMessage.SUCCESS, params);
		return this.getJsonView(modelAndView);
	}

	@RequestMapping(value = "delete")
	@ResponseBody
	public ModelAndView delete(@RequestParam Long id, ModelAndView modelAndView) {
		Map<String, String> params = new HashMap<String, String>();
		int updateRow = this.parameterService.doDeleteById(id);
		if (updateRow > 0) {
			params.put(BindResult.SUCCESS,ConstantMessage.DELETE_SUCCESS_MESSAGE_CODE);
		} else {
			params.put(BindResult.SUCCESS,ConstantMessage.DELETE_FAIL_MESSAGE_CODE);
		}
		modelAndView.addObject(BindMessage.SUCCESS, params);
		return this.getJsonView(modelAndView);
	}

	@RequestMapping(value = "add")
	@ResponseBody
	public ModelAndView add(
			@Valid @ModelAttribute("parameter") ParameterVo parameterVo,
			Errors errors, ModelAndView modelAndView) {
		
		if (!errors.hasErrors()&& this.isExistByNameEn(parameterVo.getNameEn())) {
			errors.rejectValue(ParameterVo.NAMEEN,
					"sys.parameter.form.nameen.existed.message",
					new Object[] { parameterVo.getNameEn() }, null);
		}
		//是否错误消息
		if (errors.hasErrors()) {
			modelAndView.addObject(BindMessage.ERRORS_MODEL_KEY, errors);
			return this.getJsonView(modelAndView);
		}
		Parameter parameter = new Parameter();

		BeanUtils.copyProperties(parameterVo, parameter);
		parameterService.doCreate(parameter);
		Map<String, String> params = new HashMap<String, String>();
		params.put(BindResult.SUCCESS, ConstantMessage.ADD_SUCCESS_MESSAGE_CODE);
		modelAndView.addObject(BindMessage.SUCCESS, params);
		
		return this.getJsonView(modelAndView);
	}

	@RequestMapping(value = "checkisexitnameen")
	@ResponseBody
	public String checkIsExistByNameEn(HttpServletRequest servletRequest,
			@RequestParam(required = false) String nameEn,@RequestParam(required=false) Long id) {
		Boolean flag=Boolean.FALSE;
		
		if(id==null){
			flag = this.isExistByNameEn(nameEn)? false : true;
		}else{
			Parameter parameter = parameterService.doFindById(id);
			if(parameter==null){
				flag = this.isExistByNameEn(nameEn)? false : true;
			}else{
				flag=this.isExistByNameEn(nameEn, parameter.getNameEn())?false:true;
			}
		}
		return flag.toString();
	}

	private Boolean isExistByNameEn(String nameEn) {
		Boolean flag = false;
		if (StringUtils.isNotEmpty(nameEn)) {
			flag = parameterService.doIsExistByNameEn(nameEn.trim());
		}
		return flag;
	}

	private Boolean isExistByNameEn(String nameEn, String oldNameEn) {
		Boolean flag = false;
		if (StringUtils.isNotEmpty(nameEn) && !nameEn.equals(oldNameEn)) {
			flag = parameterService.doIsExistByNameEn(nameEn.trim());
		}
		return flag;
	}

	@RequestMapping(value = "list")
	@ResponseBody
	public DataTable<Parameter> list(QueryDt query,@ModelAttribute("parameter") ParameterVo parameterVo) {
		QueryCriteria queryCriteria = new QueryCriteria();

		// 设置分页 启始页
		queryCriteria.setStartIndex(query.getPage());
		// 每页大小
		queryCriteria.setPageSize(query.getRows());
		// 设置排序字段及排序方向
		if (StringUtils.isNotEmpty(query.getSort()) && StringUtils.isNotEmpty(query.getOrder())) {
			queryCriteria.setOrderField(query.getSort());
			queryCriteria.setOrderDirection(query.getOrder());
		} else {
			queryCriteria.setOrderField(DEFAULT_ORDER_FILED_NAME);
		}
		// 查询条件 参数类型
		if (StringUtils.isNotEmpty(parameterVo.getType())) {
			queryCriteria.addQueryCondition("type", parameterVo.getType());
		}
		//查询条件 中文参数名称按模糊查询
		if(StringUtils.isNotEmpty(parameterVo.getNameZh())){
			queryCriteria.addQueryCondition("nameZh","%"+parameterVo.getNameZh()+"%");
		}

		PageResult<Parameter> pageResult = parameterService.doFindByCriteria(queryCriteria);
		return pageResult.getDataTable(query.getRequestId());
	}
	/****
	 * 
	 * @param tableId
	 * @param parameterVo
	 * @param modelAndView
	 * @return
	 */
	@RequestMapping(value = "export")
	@ResponseBody
	public ModelAndView exportExcel(@RequestParam(required=false) String tableId,@RequestParam(required = false) String sort,@RequestParam(required = false) String order,@ModelAttribute("parameter") ParameterVo parameterVo,ModelAndView modelAndView){
				
		DataGrid dataGrid=dataGridService.doFindByTableIdAndSort(tableId);
		QueryCriteria queryCriteria=new QueryCriteria();
		queryCriteria.setPageSize(10000);
		// 设置排序字段及排序方向
		if (StringUtils.isNotEmpty(sort) && StringUtils.isNotEmpty(order)) {
			queryCriteria.setOrderField(sort);
			queryCriteria.setOrderDirection(order);
		} else {
			queryCriteria.setOrderField(DEFAULT_ORDER_FILED_NAME);
			queryCriteria.setOrderDirection("ASC");
		}
		// 查询条件 参数类型
		if (StringUtils.isNotEmpty(parameterVo.getType())) {
			queryCriteria.addQueryCondition("type", parameterVo.getType());
		}
		//查询条件 中文参数名称按模糊查询
		if(StringUtils.isNotEmpty(parameterVo.getNameZh())){
			queryCriteria.addQueryCondition("nameZh","%"+parameterVo.getNameZh()+"%");
		}

		PageResult<Parameter> result=parameterService.doFindByCriteria(queryCriteria);
		Map<String, Map<Object, Object>> fieldCodeTypes = new HashMap<String, Map<Object, Object>>();
		fieldCodeTypes.put("id",this.codeService.doFindMap(CODE_TYPE));

		Map<String, String> dataFormats = new HashMap<String, String>();		
		dataFormats.put("birthday", DateUtil.FORMAT_DATE_YYYY_MM_DD);
		//创建.xls的文件名
		String fileName=this.createFileName(FileUtil.EXCEL_EXTENSION);
		
		modelAndView.addObject("title", dataGrid.getTitle());
		
		Long startTime=System.currentTimeMillis();
		
		fileName=excelExportService.export(dataGrid, result.getContent(), fileName, fieldCodeTypes, dataFormats);
		
		logger.info("fileName:{}",fileName);
		
		Long costTime=System.currentTimeMillis()-startTime;
		
		modelAndView.addObject(FILENAME,fileName);
		
		logger.info("export Excel {} cost:{} time,fileName:{}",dataGrid.getTitle(),costTime,fileName);
		logger.info("out Excel导出");
		return this.getExcelView(modelAndView);
	}

	 
}
