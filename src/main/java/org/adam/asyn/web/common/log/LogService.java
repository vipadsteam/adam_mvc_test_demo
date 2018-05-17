package org.adam.asyn.web.common.log;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.adam.client.ILogService;
import org.springframework.adam.common.bean.ResultVo;
import org.springframework.adam.common.utils.ThreadLocalHolder;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

@Component
public class LogService implements ILogService {

	private static final Log log = LogFactory.getLog(LogService.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.adam.client.sevendays.ILogService#
	 * sendRunningAccountLog(java.lang.Object)
	 */
	@Override
	public void sendRunningAccountLog(Object obj) {
//		log.info("running_account:" + ra() + JSON.toJSONString(obj));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.adam.client.sevendays.ILogService#
	 * sendRunningAccountLog(java.lang.Object,
	 * org.springframework.adam.common.bean.ResultVo, java.lang.String,
	 * java.lang.String, java.lang.Long)
	 */
	@Override
	public void sendRunningAccountLog(Object income, ResultVo output, String methodName, String remark, Long beginTime) {
//		log.info("running_account: " + ra() + getFormatParamString(income, output, methodName, remark));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.adam.client.sevendays.ILogService#sendRequestLog(java
	 * .lang.Object)
	 */
	@Override
	public void sendRequestLog(Object obj) {
		log.info("request_log: " + ra() + JSON.toJSONString(obj));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.adam.client.sevendays.ILogService#sendBeginRequestLog
	 * (java.lang.Object)
	 */
	@Override
	public void sendBeginRequestLog(Object obj) {
		log.info("request_begin_log: " + ra() + JSON.toJSONString(obj));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.adam.client.sevendays.ILogService#sendEndRequestLog(
	 * java.lang.Object)
	 */
	@Override
	public void sendEndRequestLog(Object obj) {
		log.info("request_end_log: " + ra() + JSON.toJSONString(obj));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.adam.client.sevendays.ILogService#sendErrorAccountLog
	 * (java.lang.Object)
	 */
	@Override
	public void sendErrorAccountLog(Object obj) {
		log.error("error_account_log: " + ra() + JSON.toJSONString(obj));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.adam.client.sevendays.ILogService#sendErrorAccountLog
	 * (java.lang.Object, java.lang.Object, java.lang.String, java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void sendErrorAccountLog(Object income, Object output, String methodName, String type, String remark) {
		log.error("error_account_log: " + ra() + getFormatParamString(income, output, methodName, remark));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.adam.client.sevendays.ILogService#
	 * sendBussinessErrorAccountLog(java.lang.Object, java.lang.Object,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public void sendBussinessErrorAccountLog(Object income, Object output, String methodName, String remark) {
		log.error("bussiness_error_account_log: " + ra() + getFormatParamString(income, output, methodName, remark));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.adam.client.sevendays.ILogService#
	 * sendOverTimeAccountLog(java.lang.Object, java.lang.Object,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public void sendOverTimeAccountLog(Object income, Object output, String methodName, String remark) {
		log.error("overtime_error_account_log: " + ra() + getFormatParamString(income, output, methodName, remark));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.adam.client.sevendays.ILogService#
	 * sendTechnologyErrorAccountLog(java.lang.Object, java.lang.Object,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public void sendTechnologyErrorAccountLog(Object income, Object output, String methodName, String remark) {
		log.error("technology_error_account_log: " + ra() + getFormatParamString(income, output, methodName, remark));
	}

	@Override
	public boolean isNeedLog() {
		return true;
	}

	/**
	 * @return
	 */
	private String ra() {
		String runningAccount = ThreadLocalHolder.getRunningAccount();
		return "ra:" + runningAccount + ", ";
	}

	/**
	 * @param income
	 * @param output
	 * @param methodName
	 * @param remark
	 * @return
	 */
	private String getFormatParamString(Object income, Object output, String methodName, String remark) {
		String tmpStr = "///methodName: " + methodName + "///remark: " + remark;
		return tmpStr + "///income: " + JSON.toJSONString(income) + "///output: " + JSON.toJSONString(output);
	}

}
