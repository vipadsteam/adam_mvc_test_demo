/**
 * 
 */
package org.adam.asyn.web.controller;

import org.adam.asyn.web.common.WebMVCConstants;
import org.adam.asyn.web.request.RequestMsg;
import org.adam.asyn.web.response.ResponseMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.adam.common.bean.ResultVo;
import org.springframework.adam.service.chain.ServiceChain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

/**
 * @author USER
 *
 */
@RestController
@RequestMapping("/adam")
public class DeferredAdamController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ServiceChain serviceChain;

	/**
	 * 第一个请求
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping("/request")
	@ResponseBody
	public DeferredResult<ResponseMsg<String>> request1(RequestMsg req) {
		logger.debug("request1:请求参数{}", req.getParam());
		DeferredResult<ResponseMsg<String>> result = new DeferredResult<ResponseMsg<String>>();
		ResultVo<DeferredResult<ResponseMsg<String>>> output = new ResultVo<DeferredResult<ResponseMsg<String>>>();
		output.setData(result);
		serviceChain.doServer(req, output, WebMVCConstants.ADAM_TEST);
		return result;
	}
}
