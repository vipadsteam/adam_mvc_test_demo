/**
 * 
 */
package org.adam.asyn.web.controller;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.adam.asyn.web.common.WebMVCConstants;
import org.adam.asyn.web.common.factory.MyThreadFactory;
import org.adam.asyn.web.request.RequestMsg;
import org.adam.asyn.web.response.ResponseMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.adam.common.bean.ResultVo;
import org.springframework.adam.service.AdamFuture;
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

	private static final ScheduledExecutorService instance = Executors.newScheduledThreadPool(800, new MyThreadFactory("MyThreads"));

	
	/**
	 * @param req
	 * @return
	 */
	@RequestMapping("/request")
	@ResponseBody
	public DeferredResult<ResponseMsg<String>> request(RequestMsg req) {
		logger.debug("request1:请求参数{}", req.getParam());
		DeferredResult<ResponseMsg<String>> result = new DeferredResult<ResponseMsg<String>>();
		ResultVo<DeferredResult<ResponseMsg<String>>> output = new ResultVo<DeferredResult<ResponseMsg<String>>>();
		output.setData(result);
		serviceChain.doServer(req, output, WebMVCConstants.ADAM_TEST1);
		return result;
	}
	
	
	/**
	 * @param req
	 * @return
	 */
	@RequestMapping("/request1")
	@ResponseBody
	public DeferredResult<ResponseMsg<String>> request1(RequestMsg req) {
		logger.debug("request1:请求参数{}", req.getParam());
		DeferredResult<ResponseMsg<String>> result = new DeferredResult<ResponseMsg<String>>();
		ResultVo<DeferredResult<ResponseMsg<String>>> output1 = new ResultVo<DeferredResult<ResponseMsg<String>>>();
		ResultVo<DeferredResult<ResponseMsg<String>>> output2 = new ResultVo<DeferredResult<ResponseMsg<String>>>();
		ResultVo<DeferredResult<ResponseMsg<String>>> output3 = new ResultVo<DeferredResult<ResponseMsg<String>>>();
		output1.setData(result);
		output2.setData(result);
		output3.setData(result);
		AdamFuture future = new AdamFuture(3);
		output1.setFuture(future);
		output2.setFuture(future);
		output3.setFuture(future);
		serviceChain.doServer(req, output1, WebMVCConstants.ADAM_TEST1);
		serviceChain.doServer(req, output2, WebMVCConstants.ADAM_TEST2);
		serviceChain.doServer(req, output3, WebMVCConstants.ADAM_TEST3);
		future.workNext();
		return result;
	}

	/**
	 * @param req
	 * @return
	 */
	@RequestMapping("/request2")
	@ResponseBody
	public DeferredResult<String> request2() {
		DeferredResult<String> result = new DeferredResult<String>();
		instance.schedule(new Runnable() {
			@Override
			public void run() {
				result.setResult("success");
			}
		}, MyRestController.sleepTime, TimeUnit.MILLISECONDS);
		return result;
	}
}
