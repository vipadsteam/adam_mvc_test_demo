package org.adam.asyn.web.common.log;

import java.util.Arrays;

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
	 * sendRunningAccountLog(java.lang.Object,
	 * org.springframework.adam.common.bean.ResultVo, java.lang.String,
	 * java.lang.String, java.lang.Long)
	 */
	@Override
	public void sendRunningAccountLog(Object income, ResultVo output, String methodName, String remark, Long beginTime) {
		if ("begin".equals(remark)) {
			sendInfoLog(methodName);
		}
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

	public void sendInfoLog(String... strs) {
		log.info(Arrays.stream(strs).map(str -> (str = str + " ")).reduce("", String::concat) + " " + ra() + " logFlag:" + ThreadLocalHolder.getRequestLogFlag() + " tId:" + Thread.currentThread().getId());
	}

	public static void main(String[] args) {
		LogService ls = new LogService();
		ls.sendInfoLog(new String[] { "aaa", "bbb", "ccc" });
	}

	/**
	 * @return
	 */
	private String ra() {
		String runningAccount = ThreadLocalHolder.getRunningAccount();
		return runningAccount + " ";
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
