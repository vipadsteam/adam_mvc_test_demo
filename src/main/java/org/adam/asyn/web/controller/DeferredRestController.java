/**
 * 
 */
package org.adam.asyn.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.adam.asyn.web.request.RequestMsg;
import org.adam.asyn.web.response.ResponseMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

/**
 * @author USER
 *
 */
@RestController
@RequestMapping("/api")
public class DeferredRestController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final Map<Integer, RequestMsg> requestBodyMap = new HashMap<Integer, RequestMsg>();

	private final Map<Integer, DeferredResult<ResponseMsg<String>>> responseBodyMap = new HashMap<Integer, DeferredResult<ResponseMsg<String>>>();

	/**
	 * 第一个请求
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping("/request1")
	@ResponseBody
	public DeferredResult<ResponseMsg<String>> request1(RequestMsg req) {
		logger.debug("request1:请求参数{}", req.getParam());
		DeferredResult<ResponseMsg<String>> result = new DeferredResult<ResponseMsg<String>>();
		requestBodyMap.put(1, req);// 把请求放到第一个请求map中
		responseBodyMap.put(1, result);// 把请求响应的DeferredResult实体放到第一个响应map中
		return result;
	}

	/**
	 * 第二个请求
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping("/request2")
	@ResponseBody
	public DeferredResult<ResponseMsg<String>> request2(RequestMsg req) {
		logger.debug("request2:请求参数{}", req.getParam());
		DeferredResult<ResponseMsg<String>> result = new DeferredResult<ResponseMsg<String>>();
		requestBodyMap.put(2, req);// 把请求放到第二个请求map中
		responseBodyMap.put(2, result);// 把请求响应的DeferredResult实体放到第二个响应map中
		return result;
	}

	/**
	 * 第三个请求
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping("/request3")
	@ResponseBody
	public DeferredResult<ResponseMsg<String>> request3(RequestMsg req) {
		logger.debug("request3:请求参数{}", req.getParam());
		DeferredResult<ResponseMsg<String>> result = new DeferredResult<ResponseMsg<String>>();
		requestBodyMap.put(3, req);// 把请求放到第三个请求map中
		responseBodyMap.put(3, result);// 把请求响应的DeferredResult实体放到第三个响应map中
		return result;
	}

	/**
	 * 控制第x个请求执行返回操作，同时自己也返回同样的值
	 * 
	 * @param x
	 * @return
	 */
	@RequestMapping(value = "/requestXReturn")
	@ResponseBody
	public ResponseMsg<String> request1Return(Integer x) {
		ResponseMsg<String> msg = new ResponseMsg<String>();
		logger.debug("requestXReturn--1:请求参数{}", x);
		DeferredResult<ResponseMsg<String>> result = responseBodyMap.get(x);
		if (result == null) {
			msg.fail("錯誤!请求已经释放");
			return msg;
		}
		String resultStr = "result" + x.toString() + ". Received:" + requestBodyMap.get(x).getParam();
		msg.success("成功", resultStr);
		result.setResult(msg);// 设置DeferredResult的结果值，设置之后，它对应的请求进行返回处理
		responseBodyMap.remove(x);// 返回map删除
		logger.debug("requestXReturn--2:请求参数{}", x);
		logger.debug("requestXReturn--3:返回参数{}", msg);
		return msg;
	}
}
