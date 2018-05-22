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
import org.springframework.adam.common.bean.annotation.service.RpcService;
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
	 * 第一种方法：全异步，serviceChain的doServer完成后则主线程返回了，不等callback，
	 * 由DeferredResult去返回response
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping("/request1")
	@ResponseBody
	@RpcService
	public DeferredResult<ResponseMsg<String>> request1(RequestMsg req) {
		logger.debug("request1:请求参数{}", req.getParam());
		DeferredResult<ResponseMsg<String>> result = new DeferredResult<ResponseMsg<String>>();
		ResultVo<DeferredResult<ResponseMsg<String>>> output = new ResultVo<DeferredResult<ResponseMsg<String>>>();
		output.setData(result);
		serviceChain.doServer(req, output, WebMVCConstants.ADAM_TEST1);
		return result;
	}

	/**
	 * 第二种方法：全异步，声明串联的serviceChain，执行future.work()才真正work，serviceChain都是全异步串联起来的
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping("/request2")
	@ResponseBody
	@RpcService
	public DeferredResult<ResponseMsg<String>> request2(RequestMsg req) {
		logger.debug("request2:请求参数{}", req.getParam());
		DeferredResult<ResponseMsg<String>> result = new DeferredResult<ResponseMsg<String>>();
		// 声明3个ResultVO，注意VO是不能支持并发的，每个serviceChain的流程都要有自己的ResultVO
		ResultVo<DeferredResult<ResponseMsg<String>>> output1 = new ResultVo<DeferredResult<ResponseMsg<String>>>();
		ResultVo<DeferredResult<ResponseMsg<String>>> output2 = new ResultVo<DeferredResult<ResponseMsg<String>>>();
		ResultVo<DeferredResult<ResponseMsg<String>>> output3 = new ResultVo<DeferredResult<ResponseMsg<String>>>();
		output1.setData(result);
		output2.setData(result);
		output3.setData(result);

		// 声明个future来串联3个serviceChain
		AdamFuture future = new AdamFuture(3);
		output1.setFuture(future);
		output2.setFuture(future);
		output3.setFuture(future);

		// 声明具体serviceChain操作流程
		serviceChain.doServer(req, output1, WebMVCConstants.ADAM_TEST1);
		serviceChain.doServer(req, output2, WebMVCConstants.ADAM_TEST2);
		serviceChain.doServer(req, output3, WebMVCConstants.ADAM_TEST3);

		// 开始工作
		future.work();
		System.out.println("aaaaaaaaaa");
		future.waitEnd();
		System.out.println("bbbbbbbbbb");
		return result;
	}
	

	/**
	 * 第三种方法：全异步并发
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping("/request3")
	@ResponseBody
	@RpcService
	public DeferredResult<ResponseMsg<String>> request3(RequestMsg req) {
		logger.debug("request3:请求参数{}", req.getParam());
		DeferredResult<ResponseMsg<String>> result = new DeferredResult<ResponseMsg<String>>();
		ResultVo<DeferredResult<ResponseMsg<String>>> output = new ResultVo<DeferredResult<ResponseMsg<String>>>();
		output.setData(result);
		serviceChain.doServer(req, output, WebMVCConstants.ADAM_TEST4);
		return result;
	}

	/**
	 * @param req
	 * @return
	 */
	@RequestMapping("/request")
	@ResponseBody
	@RpcService
	public DeferredResult<String> request() {
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
