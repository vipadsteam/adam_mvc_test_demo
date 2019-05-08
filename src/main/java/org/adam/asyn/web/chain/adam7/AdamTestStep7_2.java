/**
 * 
 */
package org.adam.asyn.web.chain.adam7;

import org.adam.asyn.web.common.TestThreadPool;
import org.adam.asyn.web.common.WebMVCConstants;
import org.adam.asyn.web.request.RequestMsg;
import org.adam.asyn.web.response.ResponseMsg;
import org.adam.asyn.web.service.callback.TestHttpFutureCallback;
import org.adam.asyn.web.service.client.HttpTestClient;
import org.springframework.adam.common.bean.ResultVo;
import org.springframework.adam.common.bean.annotation.service.ServiceErrorCode;
import org.springframework.adam.common.bean.annotation.service.ServiceOrder;
import org.springframework.adam.common.bean.annotation.service.ServiceType;
import org.springframework.adam.service.AbsCallbacker;
import org.springframework.adam.service.CallbackCombiner;
import org.springframework.adam.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;;

/**
 * @author user
 *
 */
@Component
@ServiceType(WebMVCConstants.ADAM_TEST7)
@ServiceOrder(20)
@ServiceErrorCode(WebMVCConstants.ADAM_TEST_ERROR)
public class AdamTestStep7_2 implements IService<RequestMsg, DeferredResult<ResponseMsg<String>>> {

	public volatile static String urlBase1 = "http://";
	public volatile static String ip = "127.0.0.1";
	public volatile static String urlBase2 = ":8080/test/request";
	public volatile static String type = "Y";

	@Autowired
	private HttpTestClient httpTestClient;

	@Override
	public AbsCallbacker doService(RequestMsg income, ResultVo<DeferredResult<ResponseMsg<String>>> output) throws Exception {
		String url = urlBase1 + ip + urlBase2;
		TestHttpFutureCallback callback1 = httpTestClient.call(url + "?sleep=1010");
//		TestHttpFutureCallback callback2 = httpTestClient.call(url + "?sleep=1000");
		CallbackCombiner callbackCombiner = new CallbackCombiner(TestThreadPool.instance().getThreadPoolExecutor(), true);
//		CallbackCombiner callbackCombiner = new CallbackCombiner(TestThreadPool.instance().getThreadPoolExecutor());
		callbackCombiner.combine(callback1);
//		callbackCombiner.combine(callback2);
		return callbackCombiner;
	}

	@Override
	public AbsCallbacker doSuccess(RequestMsg income, ResultVo<DeferredResult<ResponseMsg<String>>> output) throws Exception {
		return null;
	}

	@Override
	public AbsCallbacker doFail(RequestMsg income, ResultVo<DeferredResult<ResponseMsg<String>>> output) throws Exception {
		return null;
	}

	@Override
	public AbsCallbacker doComplate(RequestMsg income, ResultVo<DeferredResult<ResponseMsg<String>>> output) throws Exception {
		return null;
	}

}
