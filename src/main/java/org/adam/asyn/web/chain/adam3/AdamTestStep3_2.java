/**
 * 
 */
package org.adam.asyn.web.chain.adam3;

import org.adam.asyn.web.common.WebMVCConstants;
import org.adam.asyn.web.request.RequestMsg;
import org.adam.asyn.web.response.ResponseMsg;
import org.adam.asyn.web.service.callback.TestHttpFutureCallback;
import org.adam.asyn.web.service.client.HttpTestClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.adam.common.bean.ResultVo;
import org.springframework.adam.common.bean.annotation.service.ServiceErrorCode;
import org.springframework.adam.common.bean.annotation.service.ServiceOrder;
import org.springframework.adam.common.bean.annotation.service.ServiceType;
import org.springframework.adam.service.AbsCallbacker;
import org.springframework.adam.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;;

/**
 * @author user
 *
 */
@Component
@ServiceType(WebMVCConstants.ADAM_TEST3)
@ServiceOrder(20)
@ServiceErrorCode(WebMVCConstants.ADAM_TEST_ERROR)
public class AdamTestStep3_2 implements IService<RequestMsg, DeferredResult<ResponseMsg<String>>> {

	private static final Log log = LogFactory.getLog(AdamTestStep3_2.class);

	private static final String STEP = "step 3-2 ";

	public volatile static String urlBase1 = "http://";
	public volatile static String ip = "127.0.0.1";
	public volatile static String urlBase2 = ":8080/test/request";
	public volatile static String type = "Y";

	@Autowired
	private HttpTestClient httpTestClient;

	@Override
	public AbsCallbacker doService(RequestMsg income, ResultVo<DeferredResult<ResponseMsg<String>>> output) throws Exception {
		System.out.println(STEP+" doService");
		String url = urlBase1 + ip + urlBase2;
		if ("Y".equals(type)) {
			TestHttpFutureCallback callback = httpTestClient.call(url);
			return callback;
		} else {
			String result = httpTestClient.callSyn(url);
			income.setParam(result);
			return null;
		}
	}

	@Override
	public AbsCallbacker doSuccess(RequestMsg income, ResultVo<DeferredResult<ResponseMsg<String>>> output) throws Exception {
		System.out.println(STEP+" doSuccess");
		return null;
	}

	@Override
	public AbsCallbacker doFail(RequestMsg income, ResultVo<DeferredResult<ResponseMsg<String>>> output) throws Exception {
		System.out.println(STEP+" doFail");
		return null;
	}

	@Override
	public AbsCallbacker doComplate(RequestMsg income, ResultVo<DeferredResult<ResponseMsg<String>>> output) throws Exception {
		System.out.println(STEP+" doComplate");
		return null;
	}

}
