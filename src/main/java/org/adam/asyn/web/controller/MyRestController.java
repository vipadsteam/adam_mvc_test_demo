/**
 * 
 */
package org.adam.asyn.web.controller;

import org.adam.asyn.web.chain.adam1.AdamTestStep1_2;
import org.adam.asyn.web.request.RequestMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.adam.common.utils.AdamRegexUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author USER
 *
 */
@RestController
@RequestMapping("/test")
public class MyRestController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public static volatile int sleepTime = 1;

	/**
	 * @param req
	 * @return
	 */
	@RequestMapping("/request")
	@ResponseBody
	public String request() {
		try {
			Thread.sleep(sleepTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "success:" + System.currentTimeMillis();
	}

	/**
	 * @param req
	 * @return
	 */
	@RequestMapping("/settime")
	@ResponseBody
	public String settime(RequestMsg req) {
		try {
			sleepTime = Integer.parseInt(req.getParam());
			return "success";
		} catch (Exception e) {
			return "fail";
		}
	}

	/**
	 * @param req
	 * @return
	 */
	@RequestMapping("/setpath")
	@ResponseBody
	public String setpath(RequestMsg req) {
		if (AdamRegexUtil.isIp(req.getParam())) {
			AdamTestStep1_2.ip = req.getParam();
			return "success";
		} else {
			return "fail";
		}
	}

	/**
	 * @param req
	 * @return
	 */
	@RequestMapping("/settype")
	@ResponseBody
	public String settype(RequestMsg req) {
		AdamTestStep1_2.type = req.getParam();
		return req.getParam();
	}
}
