/**
 * 
 */
package org.adam.asyn.web.chain.adam9;

import org.adam.asyn.web.common.WebMVCConstants;
import org.adam.asyn.web.common.log.LogService;
import org.adam.asyn.web.request.RequestMsg;
import org.adam.asyn.web.response.ResponseMsg;
import org.adam.asyn.web.service.callback.TestHttpFutureCallback;
import org.adam.asyn.web.service.client.HttpTestClient;
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
@ServiceType(WebMVCConstants.ADAM_TEST9)
@ServiceOrder(40)
@ServiceErrorCode(WebMVCConstants.ADAM_TEST_ERROR)
public class AdamTestStep9_4 implements IService<RequestMsg, DeferredResult<ResponseMsg<String>>> {

	@Autowired
	private LogService log;

	@Autowired
	private HttpTestClient httpTestClient;

	@Override
	public AbsCallbacker doService(RequestMsg income, ResultVo<DeferredResult<ResponseMsg<String>>> output) throws Exception {
		String url = "http://127.0.0.1:8080/test/request?sleep=100";
		TestHttpFutureCallback callback = httpTestClient.call(url);
		return callback;
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
