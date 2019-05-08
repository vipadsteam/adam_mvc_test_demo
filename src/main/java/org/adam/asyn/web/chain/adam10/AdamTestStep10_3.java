/**
 * 
 */
package org.adam.asyn.web.chain.adam10;

import org.adam.asyn.web.common.WebMVCConstants;
import org.adam.asyn.web.request.RequestMsg;
import org.adam.asyn.web.response.ResponseMsg;
import org.springframework.adam.common.bean.ResultVo;
import org.springframework.adam.common.bean.annotation.service.ServiceErrorCode;
import org.springframework.adam.common.bean.annotation.service.ServiceOrder;
import org.springframework.adam.common.bean.annotation.service.ServiceType;
import org.springframework.adam.service.AbsCallbacker;
import org.springframework.adam.service.IService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;;

/**
 * @author user
 *
 */
@Component
@ServiceType(WebMVCConstants.ADAM_TEST10)
@ServiceOrder(30)
@ServiceErrorCode(WebMVCConstants.ADAM_TEST_ERROR)
public class AdamTestStep10_3 implements IService<RequestMsg, DeferredResult<ResponseMsg<String>>> {

	@Override
	public AbsCallbacker doService(RequestMsg income, ResultVo<DeferredResult<ResponseMsg<String>>> output) throws Exception {
		return null;
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
