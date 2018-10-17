/**
 * 
 */
package org.adam.asyn.web.chain.adam6;

import org.adam.asyn.web.common.TestThreadPool;
import org.adam.asyn.web.common.WebMVCConstants;
import org.adam.asyn.web.request.RequestMsg;
import org.adam.asyn.web.response.ResponseMsg;
import org.adam.asyn.web.service.callback.TestHttpFutureCallback;
import org.springframework.adam.common.bean.ResultVo;
import org.springframework.adam.common.bean.annotation.service.ServiceErrorCode;
import org.springframework.adam.common.bean.annotation.service.ServiceOrder;
import org.springframework.adam.common.bean.annotation.service.ServiceType;
import org.springframework.adam.service.AbsCallbacker;
import org.springframework.adam.service.CallbackCombiner;
import org.springframework.adam.service.IService;
import org.springframework.adam.service.chain.ServiceChain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;;

/**
 * @author user
 *
 */
@Component
@ServiceType(WebMVCConstants.ADAM_TEST6)
@ServiceOrder(30)
@ServiceErrorCode(WebMVCConstants.ADAM_TEST_ERROR)
public class AdamTestStep6_3 implements IService<RequestMsg, DeferredResult<ResponseMsg<String>>> {

	@Autowired
	private ServiceChain serviceChain;

	@Override
	public AbsCallbacker doService(RequestMsg income, ResultVo<DeferredResult<ResponseMsg<String>>> output) throws Exception {
		ResultVo<DeferredResult<ResponseMsg<String>>> newResultVo1 = new ResultVo<>();
		newResultVo1.setData(output.getData());
		ResultVo<DeferredResult<ResponseMsg<String>>> newResultVo2 = new ResultVo<>();
		newResultVo2.setData(output.getData());

		AbsCallbacker callback1 = serviceChain.doServerWithCallback(income, newResultVo1, WebMVCConstants.ADAM_TEST7);
		AbsCallbacker callback2 = serviceChain.doServerWithCallback(income, newResultVo2, WebMVCConstants.ADAM_TEST8);
		// CallbackCombiner callbackCombiner = new CallbackCombiner(TestThreadPool.instance().getThreadPoolExecutor(), true);
		CallbackCombiner callbackCombiner = new CallbackCombiner(TestThreadPool.instance().getThreadPoolExecutor());
		callbackCombiner.combine(callback1);
		callbackCombiner.combine(callback2);
//		Thread.sleep(10000);
//		System.out.println("ccccccccccccccccc");
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
